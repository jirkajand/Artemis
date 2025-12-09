<script lang="ts">
    import { Theme, theme } from "$lib/stores/theme";
	import Button, { Icon } from "@smui/button";
	import IconButton from "@smui/icon-button";
</script>

<IconButton
    class="material-icons"
    onclick={() => theme.toggleTheme()}
    color="primary"
>
    {#if $theme + 1 == Theme.Light}
        light_mode
    {:else if $theme + 1 == Theme.Dark}
        dark_mode
    {:else}
        brightness_auto
    {/if}
</IconButton>