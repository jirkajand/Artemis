<script lang="ts">
	import { keycloak } from "$lib/auth/keycloak";
	import Button from "@smui/button";
	import type { LayoutProps } from "./$types";

	let { children }: LayoutProps = $props();
</script>

<header>
	<nav class="topbar">
		<Button onclick={() => keycloak.login()}>Login</Button>
		<a href="/register">Register</a>
	</nav>
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
	nav.topbar {
		display: flex;
		justify-content: flex-start;
		align-items: center;
		padding: 1rem;
		background-color: #002533;
		border-bottom: 1px solid #ddd;
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