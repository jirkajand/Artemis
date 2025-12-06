<script lang="ts">
    import { keycloak } from "$lib/auth/keycloak";
	import List, { Item, Separator, Text, Graphic } from "@smui/list";
	import Menu from "@smui/menu";
	import type { ResponseStudentNavbar } from "$lib/api";
	import { goto } from "$app/navigation";

    const { 
        userData = null,
        userPicture = null
    }: {
        userData: ResponseStudentNavbar | null;
        userPicture: Blob | null;
    } = $props();

    let menu: Menu;
    let anchor: HTMLElement | undefined = $state();
    let isOpen = $state(false);

    function toggleMenu() {
        isOpen = !isOpen;
        menu.setOpen(isOpen);
    }
</script>

<button
    class="profile-menu"
    bind:this={anchor}
    onclick={() => toggleMenu()}
>
    {#if userPicture}
        <img
            src={URL.createObjectURL(userPicture)}
            alt="Profile"
        />
    {:else}
        <img src="/profile-picture.jpeg" alt="Profile" />
    {/if}
    <span>{`${userData?.firstName} ${userData?.lastName}`}</span>
    <span class="material-icons">arrow_drop_down</span>
    <Menu
        id="profile-dropdown-menu"
        bind:this={menu}
        anchor={true}
        anchorElement={anchor}
        anchorCorner="BOTTOM_LEFT"
    >
        <List class="demo-list" dense>
            <Item onSMUIAction={() => (goto("/student-profile"))}>
                <Graphic class="material-icons">person</Graphic>
                <Text>My Profile</Text>
            </Item>
            <Separator />
            <Item onSMUIAction={() => (keycloak.logout())}>
                <Graphic class="material-icons">logout</Graphic>
                <Text>Log Out</Text>
            </Item>
        </List>
    </Menu>
</button>


<style>
    :global(#profile-dropdown-menu) {
        margin-top: 0.5rem;
        width: 100%;
    }
    button.profile-menu {
        background: none;
        border: none;
        padding: 0;
        margin: 0;
        font: inherit;
        color: inherit;
    }
    .profile-menu {
        display: flex;
        align-items: center;
        gap: 0.5rem;
        cursor: pointer;
    }

    .profile-menu img {
        width: 32px;
        height: 32px;
        border-radius: 50%;
        object-fit: cover;
    }
</style>