<script lang="ts">
    import { keycloak } from "$lib/auth/keycloak";
    import Button from "@smui/button";
	import ProfileMenu from "./ProfileMenu.svelte";
	import ThemeSwitch from "./ThemeSwitch.svelte";
	import type { ResponseStudentNavbar } from "$lib/api";

    // use userNavData instead
    let { userData = null }: {
        userData: Promise<ResponseStudentNavbar> | null;
    } = $props();

</script>

<nav class="topbar">
    {#if userData}
    {#await userData then userDataSync}
        <section class="app-title">ARTEMIS DEMO</section>
        <section class="additional-opotions">
            <a href="/register">Register</a>
            <ThemeSwitch />
        </section>
        <!-- make navbar responsive -->
        <section class="last">
        <!-- delete some items from profile menu -->
            <ProfileMenu userData={userDataSync} />
        </section>
    {/await}
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

    section.additional-opotions {
        display: flex;
        align-items: center;
        gap: 1rem;
    }

    @media (max-width: 768px) {
        section.additional-opotions {
            display: none;
        }
    }
    
</style>