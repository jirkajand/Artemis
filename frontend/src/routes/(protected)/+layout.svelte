<script lang="ts">
	import { keycloak } from "$lib/auth/keycloak";
	import Button from "@smui/button";
	import type { LayoutProps } from "./$types";

	let { children, data }: LayoutProps = $props();

	const { user } = data;
</script>

<nav class="topbar">
	{#if user}
		<div>Welcome, {user?.preferred_username}</div>
		<Button onclick={() => keycloak.logout()}>Logout</Button>
	{:else}
		<Button onclick={() => keycloak.login()}>Login</Button>
	{/if}
</nav>

{@render children?.()}
