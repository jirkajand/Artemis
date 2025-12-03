<script lang="ts">
	import type { LayoutProps } from "./$types";
	import Navbar from "$lib/components/Navbar.svelte";
	import Sidebar from "$lib/components/Sidebar.svelte";
	import SecondaryRegistrationPopup from "$lib/components/registration/SecondaryRegistrationPopup.svelte";

	let { children, data }: LayoutProps = $props();

	const { user, clients, userNavbarData } = data;
</script>

<header>
	<Navbar {user} />
</header>

<div class="layout">
	<Sidebar />
	<div class="content-scroll">
		<main>
			<SecondaryRegistrationPopup {clients} {userNavbarData} />
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