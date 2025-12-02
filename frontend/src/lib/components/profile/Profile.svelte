<script lang="ts">
	import StudentInfoForm from "./StudentInfoForm.svelte";
	import ProfileSide from "./ProfileSide.svelte";
	import ProfileActions from "./ProfileActions.svelte";
	import ProfileChangeSuccessDialog from "./ProfileChangeSuccessDialog.svelte";
	import { invalidateAll } from '$app/navigation';

	// ---- props
	let { student, management, faculties } = $props();

	// ---- local state
	let editableStudent = $state({ ...student });
	let editing = $state(false);
	let showSuccessDialog = $state(false);

	// ---- toggle edit mode
	const toggleEdit = () => {
		if (editing) {
			editableStudent = { ...student };
		}
		editing = !editing;
	};

	// ---- update strategies based on student type
	const updateStrategies = {
		INTERNATIONAL: (s: any) => management.updateInternationalStudentById({
			internationalStudentId: s.id,
			internationalStudentProfileEditRequest: s
		}),
		LOCAL: (s: any) => management.updateLocalStudentById({
			localStudentId: s.id,
			localStudentProfileEditRequest: s
		})
	};

	// ---- save changes
	const saveChanges = async () => {
		try {
			const action = updateStrategies[student.type as keyof typeof updateStrategies];

			if (action) {
				await action(editableStudent);
				await invalidateAll();
				editing = false;
				showSuccessDialog = true; // <-- show dialog after saving
			} else {
				console.error("Unknown student type:", student.type);
			}
		} catch (err) {
			console.error("Failed to save changes:", err);
		}
	};

	// ---- reset editableStudent when leaving edit mode
	$effect(() => {
		if (!editing) {
			editableStudent = { ...student };
		}
	});
</script>

<div class="split-layout" class:editing-active={editing}>
	<div class="data-column">
		<StudentInfoForm
			bind:editableStudent
			{faculties}
			{editing}
			{saveChanges}
		/>
	</div>

	<ProfileSide
		bind:editableStudent
		{editing}
	/>
</div>

<ProfileActions
	{editing}
	{toggleEdit}
	{saveChanges}
/>

<!-- ---- Success dialog -->
<ProfileChangeSuccessDialog bind:open={showSuccessDialog} onClose={() => showSuccessDialog = false} />

<style lang="scss">
  .split-layout {
    display: flex;
    gap: 1rem;
    margin: 0.75rem 2rem;

    &.editing-active {
      .details-table td {
        border-bottom: 2.5px solid transparent;
      }
    }
  }

  .data-column {
    flex: 2;
  }

  @media (max-width: 850px) {
    .split-layout {
      flex-direction: column;
      gap: 2rem;
    }

    .image-column {
      order: -1;
    }

    .label {
      width: 35%;
      font-size: 0.9rem;
    }
  }
</style>
