<script lang="ts">
	import type { LayoutProps } from "./$types";
	import Navbar from "$lib/components/Navbar.svelte";
	import Sidebar from "$lib/components/Sidebar.svelte";
	import SecondaryMemberRegistrationPopup from "$lib/components/registration/SecondaryMemberRegistrationPopup.svelte";

	let { children, data }: LayoutProps = $props();

	const { user, clients, userNavbarData } = data;
	const { settings, management } = clients;
</script>

<header>
	<Navbar {user} />
</header>

<div class="layout">
	<Sidebar />
	<main>
		{#await userNavbarData then userNavbarDataSync}
			{#if userNavbarDataSync.type == "LOCAL"}
			<SecondaryMemberRegistrationPopup
				settingsClient={settings}
				userManagementClient={management}
				navbarData={userNavbarDataSync}
			/>
			{/if}
		{/await}
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
		display: flex;
		flex-direction: column;
		align-items: start;
		justify-content: start;
		flex: 1;
		margin: 0 auto;
		padding: 2rem 1rem;
		width: 100%;
		max-width: 54rem;
	}
</style>