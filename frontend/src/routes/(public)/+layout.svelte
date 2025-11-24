<script lang="ts">
	import { keycloak } from "$lib/auth/keycloak";
	import Button from "@smui/button";
	import type { LayoutProps } from "./$types";
	import Navbar from "$lib/components/Navbar.svelte";

	let { children }: LayoutProps = $props();
</script>

<header>
	<Navbar user={null} />
</header>

<div class="layout">
	<main>
		{@render children?.()}
	</main>
</div>


<style>
	:global(body) {
		display: flex;
		flex-direction: column;
		height: 100%;
	}
	.layout {
		display: flex;
		flex-flow: row;
		flex: 1;
		overflow: hidden;
	}
	main {
		position: relative;

		display: flex;
		flex-direction: column;
		align-items: start;
		justify-content: start;
		margin: 0 auto;
		padding: 2rem 1rem;
		width: 100%;
		max-width: 54rem;
	}
</style>