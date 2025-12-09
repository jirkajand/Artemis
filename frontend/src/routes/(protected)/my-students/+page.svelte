<script lang="ts">
	import TopAppBar, { Row, Section, Title } from '@smui/top-app-bar';
	// Assuming StudentCard path is correct for your project
	import StudentCard from '$lib/components/StudentCard.svelte';
	import ItemGrid from '$lib/components/ItemGrid.svelte';

	let { data } = $props();
	// Using $derived to react to data changes
	let { students } = $derived(data);

</script>

<TopAppBar variant="static" color="secondary" class="student-list-bar">
	<Row>
		<Section>
			<Title>My students</Title>
		</Section>
	</Row>
</TopAppBar>

<ItemGrid>
	{#each students as student, i (i)}
		<StudentCard {student}/>
	{/each}
</ItemGrid>

<style lang="scss">
  :global(main) {
    max-width: unset !important;
  }

  .card-grid {
    display: grid;
    gap: 1rem;
    padding: 2rem;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  }

  .student-list-bar :global(.mdc-top-app-bar) {
    border-radius: 20px;
    background-color: var(--menu-bg) !important;
  }

  .student-list-bar :global(.mdc-top-app-bar__row) {
    background-color: var(--surface);
    border: 2px solid var(--neutral-bg);
    border-radius: 20px;
    height: 70px !important;
  }

  .student-list-bar :global(.mdc-top-app-bar__section) {
    justify-content: center;
    padding: 0.5rem 1rem;
    flex: 1 !important;
  }

  .student-list-bar :global(.mdc-top-app-bar__title) {
    color: var(--on-background);
    font-size: 1.75rem;
  }

  // Segmented Button Overrides (scoped to this context)
  .student-list-bar :global(.mdc-segmented-button) {
    display: flex !important;
    width: 100%;
    justify-content: center;
    align-items: center;
    border-radius: 12px;
    overflow: visible;
    padding: 4px;
    gap: 0.5rem;
  }

  .student-list-bar :global(.mdc-segmented-button__segment) {
    border-radius: 15px !important;
    transition: background-color 0.18s ease, transform 0.18s ease, box-shadow 0.18s ease;
    padding: 0.75rem 1.4rem;
    font-size: 1rem;
  }

  .student-list-bar :global(.mdc-segmented-button__segment--selected) {
    background: var(--mdc-theme-on-primary) !important;
    color: white;
    transform: scale(1.08);
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.12);
    border-color: transparent !important;
    padding: 1.45rem 1.4rem !important;
    z-index: 10;
  }

  .student-list-bar :global(.mdc-segmented-button__label) {
    font-weight: 500;
  }

</style>