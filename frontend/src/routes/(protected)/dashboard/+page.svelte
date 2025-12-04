<script lang="ts">
	import { goto, invalidateAll } from '$app/navigation';
	import TopAppBar, { Row, Section } from '@smui/top-app-bar';
	import Autocomplete from '@smui-extra/autocomplete';
	import Select, { Option } from '@smui/select';
	import ItemGrid from '$lib/components/ItemGrid.svelte';
	import PaginationBar from '$lib/components/PaginationBar.svelte';
	import AnonymisedStudentCard from '$lib/components/AnonymisedStudentCard.svelte';

	let { data } = $props();
	let { students, management, filterValues, pagination, activeFilters } = $derived(data);
	let { countries, destinationFaculties, semesters } = $derived(filterValues);

	let filters = $state({
		country: activeFilters.country,
		faculty: activeFilters.faculty,
		semester: activeFilters.semester,
		page: pagination.page
	});

	let selectedCountry = $state(
		activeFilters.country
			? countries.find((c) => c.value === activeFilters.country)
			: undefined
	);

	// Keep filters.country in sync with Autocomplete
	$effect(() => {
		filters.country = selectedCountry?.value ?? '';
	});

	// MAIN SYNC: every change to filters updates the URL
	$effect(() => {
		const params = new URLSearchParams();

		if (filters.country) params.set('country', filters.country);
		if (filters.faculty) params.set('faculty', filters.faculty);
		if (filters.semester) params.set('semester', filters.semester);

		params.set('page', filters.page.toString());

		const query = params.toString();
		const currentQuery = new URLSearchParams(location.search).toString();

		// Only update when something actually changed
		if (query !== currentQuery) {
			goto(`?${query}`, { keepFocus: true, noScroll: true });
		}
	});

	function onPageChange(newPage: number) {
		// FIX: this triggers the $effect and reloads data from backend
		filters.page = newPage;

		document.querySelector('main')?.scrollTo({ top: 0, behavior: 'smooth' });
	}

	async function handleBuddyMatch(student: { id: string; countryName: string }) {
		try {
			await management.assignInternationalStudentToLocalStudent({
				internationalStudentId: student.id
			});

			alert(`You are now a buddy for this student from ${student.countryName}!`);

			// This refreshes data, but we must also ensure we stay on valid page
			await invalidateAll();

			// FIX: If page became empty after removal, go back 1 page
			if (students.length === 1 && filters.page > 1) {
				filters.page -= 1;
			}

		} catch (e) {
			alert('Something went wrong!');
		}
	}
</script>

<TopAppBar variant="static">
	<Row>
		<Section class="filter-panel">
			<div class="country-select">
				<Autocomplete
					textfield$variant="outlined"
					options={countries}
					bind:value={selectedCountry}
					label="Country"
					getOptionLabel={(option) => option?.label || ''}
				/>
			</div>

			<Select variant="outlined" bind:value={filters.faculty} label="Faculty">
				<Option value=""></Option>
				{#each destinationFaculties as f}
					<Option value={f.id}>{f.label}</Option>
				{/each}
			</Select>

			<Select variant="outlined" bind:value={filters.semester} label="Semester">
				<Option value=""></Option>
				{#each semesters as s}
					<Option value={s.id}>{s.label}</Option>
				{/each}
			</Select>
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
			bind:page={filters.page}
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

    .filter-panel {
        display: flex;
        gap: 1rem;
        flex-wrap: wrap;
        align-items: center;
    }

    .pagination-wrapper {
        display: flex;
        justify-content: center;
        padding: 1rem;
        width: 100%;
    }

    .country-select {
        display: inline-flex;
        width: 200px;
    }
</style>
