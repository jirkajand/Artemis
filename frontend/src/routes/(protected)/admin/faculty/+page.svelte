<script lang="ts">
	import type { PageProps } from "./$types";
	import Button, { Icon, Label } from "@smui/button";
	import CrudDialog from "$lib/components/crud/CrudDialog.svelte";
	import type { FacultyCreateRequest, FacultyResponse } from "$lib/api";
	import DeleteDialog from "$lib/components/crud/DeleteDialog.svelte";
	import { invalidateAll } from "$app/navigation";
	import FacultyCreateForm from "./FacultyCreateForm.svelte";
	import FacultyCard from "./FacultyCard.svelte";
	import { onMount } from "svelte";

    const { data }: PageProps = $props();
    const { faculties, settingsClient } = $derived(data);

    let form: FacultyCreateRequest = $state({
        facultyNameInternational: '',
        facultyNameLocal: '',
        shortName: '',
        color: '#000000',
    });
    let formType = $state<'create' | 'edit' | null>('create');
    let selectedFaculty: FacultyResponse | null = $state(null);

    let formDialogOpen = $state(false);
    let deleteDialogOpen = $state(false);
    let loading = $state(false);
    let errorMessage = $state<string | null>(null);

    function openEditDialog(faculty: FacultyResponse) {
        formType = 'edit';
        formDialogOpen = true;
        selectedFaculty = faculty;
        form = {
            facultyNameInternational: faculty.facultyNameInternational,
            facultyNameLocal: faculty.facultyNameLocal,
            shortName: faculty.shortName,
            color: faculty.color,
        };
    }

    function openDeleteDialog(faculty: FacultyResponse) {
        deleteDialogOpen = true;
        selectedFaculty = faculty;
    }

    function openCreateDialog() {
        formType = 'create';
        formDialogOpen = true;
    }

    async function createNewSemester() {
        loading = true;
        errorMessage = null;

        try {
            if(!form) {
                throw new Error("Form data is incomplete.");
            }
            const response = await settingsClient.createFaculty({
                facultyCreateRequest: {
                    ...form
                }
            });

            formDialogOpen = false;
            await invalidateAll();
        } catch (error) {
            errorMessage = `Failed to create faculty: ${error instanceof Error ? error.message : String(error)}`;
        } finally {
            loading = false;
        }
    }

    async function updateSemester() {
        // Logic to update an existing semester
        loading = true;
        errorMessage = null;

        try {
            if(!form) {
                throw new Error("Form data is incomplete.");
            }
            if(!selectedFaculty) {
                throw new Error("No faculty selected for update.");
            }
            if(selectedFaculty.id === undefined) {
                throw new Error("Selected faculty has no ID.");
            }
            const response = await settingsClient.updateFaculty({
                id: selectedFaculty.id,
                facultyCreateRequest: {
                    ...form
                },
            });

            formDialogOpen = false;
            await invalidateAll();
        } catch (error) {
            errorMessage = `Failed to update faculty: ${error instanceof Error ? error.message : String(error)}`;
        } finally {
            loading = false;
        }
    }

    async function deleteSemester() {
        // Logic to delete an existing semester

        try {
            if(!selectedFaculty) {
                throw new Error("No faculty selected for update.");
            }
            if(selectedFaculty.id === undefined) {
                throw new Error("Selected faculty has no ID.");
            }
            const response = await settingsClient.deleteFaculty({
                id: selectedFaculty.id,
            });

            deleteDialogOpen = false;
            await invalidateAll();
        } catch (error) {
            console.error("Failed to delete faculty:", error);
        }
    }


    onMount(() => {
        console.log("Faculties loaded:", faculties);
    });
</script>

<div class="controls">
    <Button variant="raised" onclick={openCreateDialog}>
        <Icon class="material-icons">add</Icon>
        <Label>Add Faculty</Label>
    </Button>
    <CrudDialog
        bind:open={formDialogOpen}
        onSubmit={ formType === 'create' ? createNewSemester : updateSemester }
        title={formType === 'create' ? "Create semester" : "Edit semester"}
        {loading}
        {errorMessage}
    >
        <FacultyCreateForm bind:form />
    </CrudDialog>
    <DeleteDialog
        bind:open={deleteDialogOpen}
        title="Delete Faculty"
        message={`Are you sure you want to delete "${selectedFaculty?.facultyNameInternational}"? This action cannot be undone.`}
        onSubmit={deleteSemester}
    />
</div>

<div class="item-container">
    {#each faculties as faculty}
        <FacultyCard
            {faculty}
            onedit={() => openEditDialog(faculty)}
            ondelete={() => openDeleteDialog(faculty)}
        />
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