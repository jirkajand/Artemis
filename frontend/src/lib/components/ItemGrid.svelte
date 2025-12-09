<script lang="ts">
	import type { Snippet } from 'svelte';

	let { children }: { children?: Snippet } = $props();
</script>

	<div class="card-grid">
		{@render children?.()}
	</div>

<style>

    .card-grid {
        display: grid;
        gap: 2rem;
        padding: 2rem;
        grid-template-columns: repeat(auto-fit, minmax(275px, 1fr));
    }


</style>