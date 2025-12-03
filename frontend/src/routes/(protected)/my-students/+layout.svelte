<script lang="ts">
	import { page } from '$app/state';
	import { goto } from '$app/navigation';
	import TopAppBar, { Row, Section, Title } from '@smui/top-app-bar';
	import SegmentedButton, { Segment } from '@smui/segmented-button';
	import { Label } from '@smui/common';

	type DashboardPage = { label: string; path: string };
	let { children } = $props();

</script>

<TopAppBar variant="static">
	<Row>
		<Section>
			<Title>My students</Title>
		</Section>
		<!--		<Section class="segment-section">
			<SegmentedButton>
				{#snippet segment(segment)}
					<Segment {segment}>
						<Label>{segment.label}</Label>
					</Segment>
				{/snippet}
			</SegmentedButton>
		</Section>-->
	</Row>
</TopAppBar>

<div class="card-grid">
    {@render children?.()}
</div>

<style>
    :global(main) {
        max-width: unset !important;
    }

    :global(.segment-section) {
        justify-content: center;
    }

    .card-grid {
        display: grid;
        gap: 1rem;
        padding: 2rem 0;
        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    }

    :global(.mdc-top-app-bar) {
        border-radius: 10px;
        background-color: var(--menu-bg) !important;
    }

    :global(.mdc-top-app-bar__section) {
				justify-content: center;
        padding: 0.5rem 1rem;
        flex: 1 !important;
    }

    :global(.mdc-top-app-bar__title) {
				color: var(--on-background);
        font-size: 1.75rem;
    }

    :global(.mdc-segmented-button) {
        display: flex !important;
        width: 100%;
        justify-content: center;
        align-items: center;
        border-radius: 12px;
        overflow: visible;
        padding: 4px;
        gap: 0.5rem;
    }

    :global(.mdc-segmented-button__segment) {
        border-radius: 15px !important;
        transition: background-color 0.18s ease, transform 0.18s ease, box-shadow 0.18s ease;
        padding: 0.75rem 1.4rem;
        font-size: 1rem;
    }

    :global(.mdc-segmented-button__segment--selected) {
        background: var(--mdc-theme-on-primary) !important;
        color: white;
        transform: scale(1.08);
        animation: subtle-pulse 2s ease-in-out infinite;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.12);
        border-color: transparent !important;
        padding: 1.45rem 1.4rem !important;
        z-index: 10;
    }

    :global(.mdc-segmented-button__label) {
        font-weight: 500;
    }

    @keyframes subtle-pulse {
        0% { transform: scale(1.005); box-shadow: 0 0 4px rgba(0, 0, 0, 0.08); }
        50% { transform: scale(1.03); box-shadow: 0 10px 16px rgba(0, 0, 0, 0.16); }
        100% { transform: scale(1); box-shadow: 0 0 4px rgba(0, 0, 0, 0.08); }
    }
</style>
