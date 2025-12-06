<script lang="ts">
	import { goto, invalidateAll } from '$app/navigation';
	import TopAppBar, { Row, Section } from '@smui/top-app-bar';
	import Autocomplete from '@smui-extra/autocomplete';
	import Select, { Option } from '@smui/select';
	import ItemGrid from '$lib/components/ItemGrid.svelte';
	import PaginationBar from '$lib/components/PaginationBar.svelte';
	import AnonymisedStudentCard from '$lib/components/AnonymisedStudentCard.svelte';
	import SegmentedButton, { Segment } from '@smui/segmented-button';
	import { Label } from '@smui/tab';

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
			goto(`?${query}`, { keepFocus: true, noScroll: true, replaceState: true });
		}
	});

	function onPageChange(newPage: number) {
		page = newPage;
		document.querySelector('main')?.scrollTo({ top: 0, behavior: 'smooth' });
	}

	async function handleBuddyMatch(student: { id?: string; countryName: string }) {
		try {
			await management.assignInternationalStudentToLocalStudent({
				internationalStudentId: student.id
			});

			alert(`You are now a buddy for this student from ${student.countryName}!`);
			await invalidateAll();

			if (students.length === 1 && page > 1) {
				page -= 1;
			}
		} catch (e) {
			alert('Something went wrong!');
		}
	}
</script>

<TopAppBar variant="static">
	<Row class="filter-container">
		<Section class="filters-left">
			<div class="country-select">
				<Autocomplete
					textfield$variant="outlined"
					options={countries}
					bind:value={selectedCountry}
					label="Country"
					getOptionLabel={(option) => option?.label || ''}
				/>
			</div>

			<Select variant="outlined" bind:value={faculty} label="Faculty">
				<Option value=""></Option>
				{#each destinationFaculties as f}
					<Option value={f.id}>{f.label}</Option>
				{/each}
			</Select>

			<Select variant="outlined" bind:value={semester} label="Semester">
				<Option value=""></Option>
				{#each semesters as s}
					<Option value={s.id}>{s.label}</Option>
				{/each}
			</Select>
		</Section>

		<Section class="filters-right">
			<SegmentedButton segments={choices} singleSelect bind:selected={assignedFilter}>
				{#snippet segment(segment)}
					<Segment {segment}>
						<Label>{segment}</Label>
					</Segment>
				{/snippet}
			</SegmentedButton>
		</Section>
	</Row>
</TopAppBar>

<ItemGrid>
	{#each students as student (student.id)}
		<AnonymisedStudentCard {student} onPick={() => handleBuddyMatch(student)} />
	{/each}
</ItemGrid>

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

<style>
    :global(main) {
        padding: 1rem !important;
        overflow: scroll;
        max-width: unset !important;
        background-color: var(--background);
    }

    .filter-container {
        display: flex;
        flex-direction: column;
        width: 100%;
        padding: 0.5rem 1rem;
        gap: 0.75rem;
    }

		:global(.mdc-segmented-button__segment){
				background-color: var(--neutral-bg) !important;
		}

		:global(.mdc-segmented-button__segment--selected){
        background: var(--primary) !important;
				color: var(--on-surface) !important;

		}

    .filters-left {
        display: flex;
        gap: 1rem;
        flex-wrap: wrap;
        align-items: center;
    }

    .filters-right {
        display: flex;
        justify-content: flex-end;
        width: 100%;
    }

    .country-select {
        width: 200px;
    }

    .pagination-wrapper {
        display: flex;
        justify-content: center;
        padding: 1rem;
        width: 100%;
    }
</style>