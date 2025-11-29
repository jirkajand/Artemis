<script lang="ts">
	import { page } from '$app/state';
	import { goto } from '$app/navigation';
	import TopAppBar, { Row, Section, Title } from '@smui/top-app-bar';
	import PaginationBar from '$lib/components/PaginationBar.svelte';

	let { children } = $props();

	let paginationData = $derived(page.data.pagination);

	function onPageChange(newPage: number) {
		const url = new URL(page.url);
		url.searchParams.set('page', newPage.toString());
		goto(url.toString());
		const mainElement = document.querySelector('main');

		if (mainElement) {
			mainElement.scrollTo({
				top: 0,
				behavior: 'smooth'
			});
		}
	}
</script>

<TopAppBar variant="static">
	<Row>
		<Section>
			<Title>Dashboard</Title>
		</Section>
	</Row>
</TopAppBar>

<div class="dashboard-container">
	<div class="card-grid">
		{@render children?.()}
	</div>

	{#if paginationData && paginationData.total > paginationData.perPage}
		<div class="pagination-wrapper">
			<PaginationBar
				total={paginationData.total}
				page={paginationData.page}
				perPage={paginationData.perPage}
				{onPageChange}
			/>
		</div>
	{/if}
</div>

<style>
    :global(main) {
        overflow: scroll;
        max-width: unset !important;
        background-color: var(--background);
    }

    .pagination-wrapper {
        display: flex;
        justify-content: center;
        padding: 1rem;
        width: 100%;
    }

    .card-grid {
        display: grid;
        gap: 1rem;
        padding: 2rem 0;
        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    }

    .dashboard-container {
        width: 100%;
    }

    :global(.mdc-top-app-bar) {
        border-radius: 10px;
        background-color: var(--menu-bg) !important;
    }

    :global(.mdc-top-app-bar__section) {
        padding: 0.5rem 1rem;
        flex: 1 !important;
        justify-content: center !important;
    }

    :global(.mdc-top-app-bar__title) {
        color: var(--on-background);
        font-size: 2rem !important;
    }
</style>