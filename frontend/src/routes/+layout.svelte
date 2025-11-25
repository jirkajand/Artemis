<script lang="ts">
	import type { LayoutProps } from "./$types";
    import { Theme, theme } from "$lib/stores/theme";

    let { children }: LayoutProps = $props();

</script>

<svelte:head>
    <!-- SMUI Styles -->
    {#if $theme == Theme.Default}
        <link rel="stylesheet" href="/smui.css" media="(prefers-color-scheme: light)" />
        <link rel="stylesheet" href="/smui-dark.css" media="(prefers-color-scheme: dark)" />
    {:else if $theme == Theme.Light}
        <link rel="stylesheet" href="/smui.css" />
    {:else if $theme == Theme.Dark}
        <link rel="stylesheet" href="/smui-dark.css" />
    {/if}
</svelte:head>


{@render children?.()}