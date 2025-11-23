<script lang="ts">
	import { keycloak } from "$lib/auth/keycloak";
	import Button from "@smui/button";
	import type { LayoutProps } from "./$types";

	let { children, data }: LayoutProps = $props();

	const { user } = data;
</script>

<header>
	<nav class="topbar">
		{#if user}
			<div>Welcome, {user?.preferred_username}</div>
			<Button onclick={() => keycloak.logout()}>Logout</Button>
			<a href="/register">Register</a>
		{:else}
			<Button onclick={() => keycloak.login()}>Login</Button>
		{/if}
	</nav>
</header>

<div class="layout">
	<aside class="sidebar">
		<nav>
			<ul>
				<li><a href="/dashboard">Dashboard</a></li>
				<li><a href="/my-students">My Students</a></li>
				<li><a href="/events">Events</a></li>
				<li><a href="/statistics">Statistics</a></li>
				<li><a href="/administration">Administration</a></li>
				<li><a href="/emailing">E-mailing</a></li>
				<!-- 20 more items -->
				<li><a href="/item1">Item 1</a></li>
				<li><a href="/item2">Item 2</a></li>
				<li><a href="/item3">Item 3</a></li>
				<li><a href="/item4">Item 4</a></li>
				<li><a href="/item5">Item 5</a></li>
				<li><a href="/item6">Item 6</a></li>
				<li><a href="/item7">Item 7</a></li>
				<li><a href="/item8">Item 8</a></li>
				<li><a href="/item9">Item 9</a></li>
				<li><a href="/item10">Item 10</a></li>
			</ul>
		</nav>
	</aside>
	
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
	.sidebar {
		display: flex;
		flex-flow: column;
		gap: 5rem;
		height: 100%;
		width: 14rem;
		background-color: #002533;
		overflow-x: hidden; /* Disable horizontal scroll */
		padding-top: 1rem;
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