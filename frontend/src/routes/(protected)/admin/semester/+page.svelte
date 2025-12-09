<script lang="ts">
	import type { PageProps } from "./$types";
	import SemesterCard from "./SemesterCard.svelte";
	import Button, { Icon, Label } from "@smui/button";
	import CrudDialog from "./CrudDialog.svelte";
	import type { SemesterResponse } from "$lib/api";
	import SemesterCreateUpdateForm, { type SemesterFormDirty } from "./SemesterCreateUpdateForm.svelte";

    const { data }: PageProps = $props();
    const { semesters, settingsClient } = $derived(data);

    let form: SemesterFormDirty = $state({
        semesterName: 'Semester 1',
        semesterType: 'WINTER',
        year: new Date().getFullYear().toString(),
        semesterRegisterOpenDate: new Date().toISOString().split('T')[0], // format as YYYY-MM-DD
    });
    let formType = $state<'create' | 'edit' | null>('create');
    let selectedSemester: SemesterResponse | null = $state(null);

    let formDialogOpen = $state(false);
    let loading = $state(false);
    let errorMessage = $state<string | null>(null);

    function openEditDialog(semester: SemesterResponse) {
        formType = 'edit';
        formDialogOpen = true;
        selectedSemester = semester;
        form = {
            semesterName: semester.semesterName,
            semesterType: semester.semesterType,
            year: semester.year,
            semesterRegisterOpenDate: (semester.semesterRegisterOpenDate ?? new Date()).toISOString().split('T')[0],
        };

        console.log("Editing semester:", semester.semesterName, form);
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
            if('semesterRegisterOpenDate' in form === false) {
                throw new Error("Form data is for updating, expected creation data.");
            }
            const response = await settingsClient.createSemester({
                semesterCreateRequest: {
                    ...form,
                    semesterRegisterOpenDate: new Date(form.semesterRegisterOpenDate)
                }
            });
            console.log("Semester created:", response);

            formDialogOpen = false;
        } catch (error) {
            console.error("Error creating semester:", error);
            errorMessage = `Failed to create semester: ${error instanceof Error ? error.message : String(error)}`;
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
            if(!selectedSemester) {
                throw new Error("No semester selected for update.");
            }
            if(selectedSemester.id === undefined) {
                throw new Error("Selected semester has no ID.");
            }
            if('semesterRegisterOpenDate' in form === false) {
                throw new Error("Form data is for updating, expected creation data.");
            }
            const response = await settingsClient.updateSemester({
                id: selectedSemester.id,
                semesterCreateRequest: {
                    ...form,
                    semesterRegisterOpenDate: new Date(form.semesterRegisterOpenDate)
                },
            });
            console.log("Semester updated:", response);

            formDialogOpen = false;
        } catch (error) {
            console.error("Error updating semester:", error);
            errorMessage = `Failed to update semester: ${error instanceof Error ? error.message : String(error)}`;
        } finally {
            loading = false;
        }
    }

</script>

<div class="controls">
    <Button variant="raised" onclick={openCreateDialog}>
        <Icon class="material-icons">add</Icon>
        <Label>Add Semester</Label>
    </Button>
    <CrudDialog
        bind:open={formDialogOpen}
        onSubmit={ formType === 'create' ? createNewSemester : updateSemester }
        title={formType === 'create' ? "Create semester" : "Edit semester"}
        {loading}
        {errorMessage}
    >
        <SemesterCreateUpdateForm bind:form={form} />
    </CrudDialog>
</div>

<div class="item-container">
    {#each semesters as semester}
        <SemesterCard {semester} onedit={() => openEditDialog(semester)} />
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