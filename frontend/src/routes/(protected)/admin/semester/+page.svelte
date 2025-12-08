<script lang="ts">
	import { onMount } from "svelte";
	import type { PageProps } from "./$types";
	import SemesterCard from "./SemesterCard.svelte";
	import Button, { Icon, Label } from "@smui/button";
	import IconButton from "@smui/icon-button";

    const { data }: PageProps = $props();
    const { semesters } = $derived(data);


    onMount(() => {
        console.log(semesters, "semesters");
    });
</script>

<div class="controls">
    <Button variant="raised">
        <Icon class="material-icons">add</Icon>
        <Label>Add Semester</Label>
    </Button>
</div>

<div class="item-container">
    {#each semesters as semester}
        <SemesterCard {semester} />
    {/each}
</div>


<style>
    .item-container {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(17rem, 1fr));
        gap: 1rem;
    }
    .controls {
        margin-bottom: 1rem;
    }
</style>