<script lang="ts">
    import { keycloak } from "$lib/auth/keycloak";
	import type { KeycloakOIDCProfile } from "$lib/auth/keycloak-types";
    import Button from "@smui/button";
	import ProfileMenu from "./ProfileMenu.svelte";
	import ThemeSwitch from "./ThemeSwitch.svelte";

    let { user = null }: {
        user: KeycloakOIDCProfile | null;
    } = $props();

</script>

<nav class="topbar">
    {#if user}
        <section>ARTEMIS DEMO</section>
        <a href="/register">Register</a>
        <ThemeSwitch />
        <section class="last">
            <ProfileMenu {user} />
        </section>
    {:else}
        <Button onclick={() => keycloak.login()}>Login</Button>
        <ThemeSwitch />
    {/if}
</nav>

<style>
    nav.topbar {
		display: flex;
		justify-content: flex-start;
		align-items: center;
        gap: 1rem;
		padding: 1rem;
		background-color: var(--nav-bg);
        color: var(--on-nav-bg);
		border-bottom: 1px solid var(--on-nav-bg);
	}

    nav.topbar .last {
        margin-left: auto;
    }
</style>