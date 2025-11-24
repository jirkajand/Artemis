<script lang="ts">
    import { theme } from "$lib/stores/theme";
	import Button from "@smui/button";
</script>

<Button onclick={() => theme.toggleTheme()}>Theme: {theme.getLabel($theme)}</Button>