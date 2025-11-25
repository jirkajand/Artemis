<script lang="ts">
	import Card, { Content, Actions, ActionButtons } from '@smui/card';
	import Button, { Label } from '@smui/button';
	import Icon from '@smui/select/icon';
	import ConfirmStudentDialog from '$lib/components/ConfirmStudentPickDialog.svelte';

	const avatarUrl = 'https://media.istockphoto.com/id/1268716253/vector/freshman-black-glyph-icon.jpg?s=612x612&w=0&k=20&c=_0e-sfr9RfJnNRyif6bVMLqc1rR3tBx5lVIjKfUPH2k='

	let {student, onPick} = $props();
	let dialogOpen = $state(false);

	const handleOpenDialog = () => dialogOpen = true;

	const handleConfirmPick = () => {
		handleCloseDialog()
		console.log('Student picked:', student);
		onPick?.()
	};

	const handleCloseDialog = () => {
		dialogOpen = false;
	};
</script>

<div class="card-display">
	<Card class="custom-card mdc-elevation--z12">

		<div class="avatar-container">
			<div class="avatar-wrapper">
				<div class="badge-icon gender" title={student.gender}>
					{student.genderIcon}
				</div>

				<div
					class="avatar-pic"
					style={`background-image: url('${avatarUrl}')`}
				></div>

				<div class="badge-icon country" title={student.countryFlag}>
					{student.countryFlag}
				</div>
			</div>
		</div>

		<div class="student-info-container">
			<Content class="card-content">

				<div class="line">
					<Icon class="material-icons">school</Icon>
					<div class="text-wrapper">
						<h3 class="faculty-subtitle" title={student.faculty.shortName}>
							{student?.faculty.shortName}
						</h3>
					</div>
				</div>

				<div class="line">
					<Icon class="material-icons">home</Icon>
					<div class="text-wrapper">
						<h3 class="faculty-subtitle" title={student.homeUniversity}>
							{student.homeUniversity}
						</h3>
					</div>
				</div>

				<div class="line">
					<Icon class="material-icons">public</Icon>
					<div class="text-wrapper">
						<h3 class="card-title" title={student.countryName}>
							{student.countryName}
						</h3>
					</div>
				</div>

				<div class="bio-block">
					<p>"{student.bio}"</p>
				</div>

			</Content>
		</div>

		<Actions class="actions-flex">
			<ActionButtons>
				<Button variant="raised" onclick={handleOpenDialog} class="pick-btn">
					<Label>Pick Student</Label>
				</Button>
			</ActionButtons>
		</Actions>
	</Card>

	<ConfirmStudentDialog
		bind:open={dialogOpen}
		{student}
		avatarUrl={avatarUrl}
		onConfirm={handleConfirmPick}
		onClose={handleCloseDialog}
	/>
</div>

<style>
    /* 1. Slimmer Card Width */
    .card-display {
        width: 100%;
        max-width: 340px; /* Reduced from 400px */
        margin: 2rem auto;
        position: relative;
        display: flex;
        flex-direction: column;
    }

    :global(.custom-card) {
        border-radius: 16px;
        overflow: visible;
        border: 1px solid #e0e0e0;
        transition: transform 0.2s ease, box-shadow 0.2s ease;
        padding-top:5rem; /* Reduced padding */
        background-color: var(--mdc-theme-surface, #fff);
        height: 100%;
    }

    :global(.custom-card:hover) {
        transform: translateY(-4px);
        box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
    }

    /* --- Avatar Area (Resized Smaller) --- */
    .avatar-container {
        position: absolute;
        top: -40px; /* Adjusted for smaller size */
        left: 0;
        width: 100%;
        z-index: 5;
        display: flex;
        justify-content: center;
        pointer-events: none;
    }

    .avatar-wrapper {
        position: relative;
        width: 120px; /* Reduced from 150px */
        height: 120px;
    }

    .avatar-pic {
        width: 100%;
        height: 100%;
        background-size: cover;
        background-position: center;
        border-radius: 50%;
        border: 4px solid var(--mdc-theme-surface, #fff);
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        position: relative;
        z-index: 2;
    }

    /* --- Badges (Resized Smaller) --- */
    .badge-icon {
        position: absolute;
        bottom: 0;
        width: 2.5rem; /* Reduced from 3.5rem */
        height: 2.5rem;
        background-color: var(--mdc-theme-surface, #fff);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 1.5rem;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
        z-index: 3;
        color: var(--mdc-theme-on-surface, #000);
        pointer-events: auto;
    }

    .badge-icon.gender { left: -10px; }
    .badge-icon.country { right: -10px; }

    /* --- Content Area --- */
    .student-info-container {
        flex: 1;
        display: flex;
        flex-direction: column;
    }

    :global(.card-content) {
        padding: 0.5rem 1rem 0;
    }

    .line {
        display: flex;
        align-items: center;
        border-bottom: 1px solid var(--mdc-theme-text-hint-on-background, #ddd);
        padding: 0.6rem 0;
        /* CRITICAL FIX: Allows flex child to shrink below content size */
        min-width: 0;
    }

    :global(.material-icons) {
        font-size: 1.25rem;
        color: #666;
        margin-right: 0.75rem;
        flex-shrink: 0;
    }


    .text-wrapper {
        flex: 1;
        min-width: 0;
    }

    .line h3 {
        margin: 0;
        font-weight: 500;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        color: var(--mdc-theme-text-primary-on-background, rgba(0, 0, 0, 0.87));
    }

    /* Standardized Font Sizes */
    .faculty-subtitle {
        font-size: 0.975rem;
    }

    .card-title {
        font-size: 1rem;
        font-weight: 600 !important;
    }

    /* --- Bio --- */
    .bio-block {
        margin-top: 1rem;
        padding: 0.75rem;
        border-radius: 8px;
        color: var(--mdc-theme-on-surface);
        font-style: italic;
        font-size: 0.9rem;
    }

    .bio-block p {
        margin: 0;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
        overflow: hidden;
        line-height: 1.4;
    }

    /* --- Actions --- */
    :global(.actions-flex) {
        display: flex;
        justify-content: center;
        padding: 1rem;
        margin-top: auto;
    }

    :global(.pick-btn) {
        width: 100%;
    }
</style>