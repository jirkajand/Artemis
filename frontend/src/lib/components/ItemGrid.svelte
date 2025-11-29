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