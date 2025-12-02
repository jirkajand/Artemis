<script lang="ts">
    import SecondaryMemberRegistrationPopup from "$lib/components/registration/SecondaryMemberRegistrationPopup.svelte";
    import SecondaryInternationalRegistrationPopup from "$lib/components/registration/SecondaryInternationalRegistrationPopup.svelte";
	import type { ResponseStudentNavbar, SettingsServiceApi, UserManagementApi } from "$lib/api";

    let { clients, userNavbarData } : {
    clients: {
        settings: SettingsServiceApi;
        management: UserManagementApi;
    };
    userNavbarData: ResponseStudentNavbar;
    } = $props();
	const { settings, management } = clients;
</script>

{#await userNavbarData then userNavbarDataSync}
    {#if userNavbarDataSync.type == "LOCAL"}
    <SecondaryMemberRegistrationPopup
        settingsClient={settings}
        userManagementClient={management}
        navbarData={userNavbarDataSync}
    />
    {:else if userNavbarDataSync.type == "INTERNATIONAL"}
    <SecondaryInternationalRegistrationPopup
        settingsClient={settings}
        userManagementClient={management}
        navbarData={userNavbarDataSync}
    />
    {/if}
{/await}