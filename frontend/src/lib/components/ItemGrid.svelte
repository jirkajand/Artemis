<script lang="ts">
	import type { Snippet } from 'svelte';

	let { children }: { children?: Snippet } = $props();
</script>

<div class="dashboard-container">
	<div class="card-grid">
		{@render children?.()}
	</div>
</div>

<style>

    .card-grid {
        display: grid;
        gap: 1rem;
        padding: 2rem 0;
        grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    }

    .dashboard-container {
				padding: 0 1rem;
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
        flex: 1 !important;
        justify-content: center !important;
    }

    :global(.mdc-top-app-bar__title) {
        color: var(--on-background);
        font-size: 2rem !important;
    }
</style>