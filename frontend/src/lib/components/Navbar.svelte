<script lang="ts">
    import { keycloak } from "$lib/auth/keycloak";
    import Button from "@smui/button";
    import CircularProgress from '@smui/circular-progress';
	import ProfileMenu from "./profile/ProfileMenu.svelte";
	import ThemeSwitch from "./ThemeSwitch.svelte";
	import type { ResponseStudentNavbar } from "$lib/api";

    let {
        userData = null,
        userPicture = null
    }: {
        userData: Promise<ResponseStudentNavbar> | null;
        userPicture: Promise<Blob | null> | null;
    } = $props();

</script>

<nav class="topbar">
    {#if userData && userPicture}
    <section class="app-title">ARTEMIS DEMO</section>
    <section class="additional-opotions">
        <a href="/register">Register</a>
        <ThemeSwitch />
    </section>
    <section class="last">
        {#await Promise.all([userData, userPicture])}
            <CircularProgress style="height: 32px; width: 32px;" indeterminate />
        {:then [userDataSync, userPictureSync]}
            <ProfileMenu userData={userDataSync} userPicture={userPictureSync} />
        {/await}
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