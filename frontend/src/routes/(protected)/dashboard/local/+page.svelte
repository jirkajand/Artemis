<script lang="ts">
	import { goto, invalidateAll } from '$app/navigation';
	import TopAppBar, { Section } from '@smui/top-app-bar';
	import Autocomplete from '@smui-extra/autocomplete';
	import Select, { Option } from '@smui/select';
	import ItemGrid from '$lib/components/ItemGrid.svelte';
	import PaginationBar from '$lib/components/PaginationBar.svelte';
	import AnonymisedStudentCard from '$lib/components/AnonymisedStudentCard.svelte';
	import SegmentedButton, { Segment } from '@smui/segmented-button';
	import { Label } from '@smui/tab';
	import SimpleInfoDialog from '$lib/components/profile/SimpleInfoDialog.svelte';

	let { data } = $props();
	let { students, management, filterValues, pagination, activeFilters } = $derived(data);
	let { countries, destinationFaculties, semesters } = $derived(filterValues);

	const choices = ['Show unassigned', 'Show assigned'];

	let page = $state(pagination.page);
	let faculty = $state(activeFilters.faculty);
	let semester = $state(activeFilters.semester);
	let assignedFilter = $state(activeFilters.containAssigned ? choices[1] : choices[0]);
	let selectedCountry = $state(
		activeFilters.country
			? countries.find((c) => c.value === activeFilters.country)
			: undefined
	);

	let showSuccessDialog = $state(false);
	let dialogState = $state({ heading: '', message: '' });

	let currentParams = $derived({
		country: selectedCountry?.value ?? '',
		faculty,
		semester,
		containAssigned: assignedFilter === choices[1]
	});

	let lastParams = JSON.stringify(currentParams);

	$effect(() => {
		const paramsStr = JSON.stringify(currentParams);

		if (paramsStr !== lastParams) {
			page = 1;
			lastParams = paramsStr;
		}

		const q = new URLSearchParams();
		if (currentParams.country) q.set('country', currentParams.country);
		if (currentParams.faculty) q.set('faculty', currentParams.faculty);
		if (currentParams.semester) q.set('semester', currentParams.semester);
		q.set('containAssigned', String(currentParams.containAssigned));
		q.set('page', String(page));

		const query = q.toString();
		if (query !== new URLSearchParams(location.search).toString()) {
			goto(`?${query}`, {
				keepFocus: true,
				replaceState: true
			});
		}
	});

	function onPageChange(newPage: number) {
		page = newPage;
		document.querySelector('.content-scroll')?.scrollTo({ top: 0, behavior: 'smooth' });
	}

	async function handleBuddyMatch(student: { id?: string; countryName: string, countryFlag: string }) {
		try {
			await management.assignInternationalStudentToLocalStudent({
				internationalStudentId: student.id
			});

			showSuccessDialog = true;
			dialogState = {
				heading: 'Buddy matched',
				message: `Student from ${student.countryFlag} ${student.countryName} ${student.countryFlag} picked successfully!`
			};
			await invalidateAll();

			if (students.length === 1 && page > 1) {
				page -= 1;
			}
		} catch (e) {
			showSuccessDialog = true;
			dialogState = { heading: 'Error', message: 'Something went wrong!' };
		}
	}
</script>

<!-- FILTER BAR -->
<TopAppBar variant="static" class="filters-bar">
	<div class="filters-bar-inner">
		<Section class="filters-left section" >
			<Autocomplete
				class="filter-autocomplete"
				textfield$variant="outlined"
				options={countries}
				bind:value={selectedCountry}
				label="Country"
				getOptionLabel={(option) => option?.label || ''}
			/>

			<Select class="filter-select" variant="outlined" bind:value={faculty} label="Faculty">
				<Option value=""></Option>
				{#each destinationFaculties as f}
					<Option value={f.id}>{f.label}</Option>
				{/each}
			</Select>

			<Select class="filter-select section" variant="outlined" bind:value={semester} label="Semester">
				<Option value=""></Option>
				{#each semesters as s}
					<Option value={s.id}>{s.label}</Option>
				{/each}
			</Select>
		</Section>

		<Section class="filters-right section">
			<SegmentedButton
				class="assigned-toggle"
				segments={choices}
				singleSelect
				bind:selected={assignedFilter}
			>
				{#snippet segment(segment)}
					<Segment class="toggle-segment" {segment}>
						<Label>{segment}</Label>
					</Segment>
				{/snippet}
			</SegmentedButton>
		</Section>
	</div>
</TopAppBar>

<!-- GRID -->
<ItemGrid class="students-grid">
	{#each students as student (student.id)}
		<AnonymisedStudentCard {student} onPick={() => handleBuddyMatch(student)} />
	{/each}
</ItemGrid>

<!-- PAGINATION -->
{#if pagination && pagination.total > pagination.perPage}
	<div class="pagination-wrapper">
		<PaginationBar
			total={pagination.total}
			perPage={pagination.perPage}
			bind:page
			{onPageChange}
		/>
	</div>
{/if}

<SimpleInfoDialog bind:open={showSuccessDialog} bind:dialogState={dialogState}
									onClose={() => showSuccessDialog = false} />


<style>
    :global(main) {
        max-width: unset !important;
    }

    :global(.filters-bar-inner) {
        display: flex;
        flex-flow: row wrap;
        width: 100%;
        padding: 0.5rem;
        gap: 0.5rem;
    }

    .filters-left {
        display: flex;
        flex-wrap: wrap;
        flex: 1 1 auto;
        gap: 0.5rem;
    }

    .filter-select,
    .filter-autocomplete {
        flex: 1;
        min-width: 8rem;
        max-width: 12rem;
    }

    .filters-right {
        display: flex;
        flex: 1 1 auto;
        justify-content: flex-end;
    }

    .assigned-toggle {
        --toggle-bg: var(--neutral-bg);
        --toggle-selected-bg: var(--primary);
        --toggle-selected-color: var(--on-surface);
    }

    .toggle-segment {
        background: var(--toggle-bg);
        border-radius: 6px;
        padding: 0.4rem 1rem;
        cursor: pointer;
        transition: background 120ms;
        user-select: none;
    }

    .toggle-segment[data-selected="true"] {
        background: var(--toggle-selected-bg);
        color: var(--toggle-selected-color);
    }

    .pagination-wrapper {
        display: flex;
        justify-content: center;
        padding: 1rem;
        width: 100%;
    }

    :global(.mdc-top-app-bar) {
        flex-direction: row !important;
        background-color: var(--menu-bg) !important;
        border-radius: 15px;
    }

    :global(.mdc-top-app-bar__row) {
        margin: 0.5rem;
    }

    :global(.mdc-top-app-bar__section) {
        gap: 1rem;
        display: flex;
        justify-content: center !important;
    }

    :global(.mdc-top-app-bar__title) {
        color: var(--on-background);
        font-size: 2rem !important;
    }

    @media (max-width: 1200px) {
        :global(.filters-bar-inner) {
            flex-direction: column;
        }

    }


</style>
