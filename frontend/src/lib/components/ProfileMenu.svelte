<script lang="ts">
	import type { KeycloakOIDCProfile } from "$lib/auth/keycloak-types";
	import List, { Item, Separator, Text, Graphic } from "@smui/list";
	import Menu from "@smui/menu";

    const { user = null }: { user: KeycloakOIDCProfile | null; } = $props();

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
    <img src="/profile-picture.jpeg" alt="Profile" />
    <span>{user?.name}</span>
    <span class="material-icons">arrow_drop_down</span>
    <Menu
        id="profile-dropdown-menu"
        bind:this={menu}
        anchor={true}
        anchorElement={anchor}
        anchorCorner="BOTTOM_LEFT"
    >
        <List class="demo-list" dense>
            <Item onSMUIAction={() => (console.log('Edit'))}>
                <Graphic class="material-icons">person</Graphic>
                <Text>My Profile</Text>
            </Item>
            <Item onSMUIAction={() => (console.log('Send'))}>
                <Graphic class="material-icons">edit</Graphic>
                <Text>Edit Profile</Text>
            </Item>
            <Item onSMUIAction={() => (console.log('Archive'))}>
                <Graphic class="material-icons">key</Graphic>
                <Text>Change Password</Text>
            </Item>
            <Separator />
            <Item onSMUIAction={() => (console.log('Archive'))}>
                <Graphic class="material-icons">book</Graphic>
                <Text>Documentation</Text>
            </Item>
            <Separator />
            <Item onSMUIAction={() => (console.log('Archive'))}>
                <Graphic class="material-icons">logout</Graphic>
                <Text>Log Out</Text>
            </Item>
        </List>
    </Menu>
</button>


<style>
    :global(#profile-dropdown-menu) {
        margin-top: 0.5rem;
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