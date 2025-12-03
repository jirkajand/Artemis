<script lang="ts">
	import { keycloak } from "$lib/auth/keycloak";
	import Button from "@smui/button";
	import type { LayoutProps } from "./$types";
	import Navbar from "$lib/components/Navbar.svelte";

	let { children }: LayoutProps = $props();
</script>

<header>
	<Navbar userData={null} />
</header>

<div class="layout">
	<div class="content-scroll">
		<main>
			{@render children?.()}
		</main>
	</div>
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
	.content-scroll {
		flex: 1;
		overflow-y: auto;
	}
	main {
		margin: 0 auto;
		padding: 2rem 1rem;
		width: 100%;
		max-width: 54rem;
	}
</style>