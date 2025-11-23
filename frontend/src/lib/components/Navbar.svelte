<script lang="ts">
    import { keycloak } from "$lib/auth/keycloak";
	import type { KeycloakOIDCProfile } from "$lib/auth/keycloak-types";
    import Button from "@smui/button";

    let { user = null }: {
        user: KeycloakOIDCProfile | null;
    } = $props();

</script>

<nav class="topbar">
    {#if user}
        <div>Welcome, {user.preferred_username}</div>
        <Button onclick={() => keycloak.logout()}>Logout</Button>
        <a href="/register">Register</a>
    {:else}
        <Button onclick={() => keycloak.login()}>Login</Button>
    {/if}
</nav>

<style>
    nav.topbar {
		display: flex;
		justify-content: flex-start;
		align-items: center;
		padding: 1rem;
		background-color: var(--nav-bg);
        color: var(--on-nav-bg);
		border-bottom: 1px solid var(--on-nav-bg);
	}
</style>