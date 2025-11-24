<script lang="ts">
	import Card, { Content, Actions, ActionButtons } from '@smui/card';
	import Button, { Label } from '@smui/button';
	import Icon from '@smui/select/icon';
	import ConfirmStudentDialog from '$lib/components/ConfirmStudentPickDialog.svelte';

	let student = {
		homeUniversity: 'Home faculty',
		destinationFaculty: 'Destination faculty',
		bio: 'Ahoy there! I be Patchy the Pirate, the self-appointed President of the SpongeBob SquarePants Fan Club!...',
		gender: 'Male',
		country: 'Bikini Bottom',
		avatar: 'https://media.istockphoto.com/id/1268716253/vector/freshman-black-glyph-icon.jpg?s=612x612&w=0&k=20&c=_0e-sfr9RfJnNRyif6bVMLqc1rR3tBx5lVIjKfUPH2k='
	};

	let dialogOpen = false;

	const handleOpenDialog = () => dialogOpen = true;
	const handleConfirmPick = () => {
		console.log('Student picked:', student);
		dialogOpen = false;
	};
	const handleCloseDialog = () => {
		dialogOpen = false;
	};

	function getGenderIcon(gender: string) {
		return gender.toLowerCase() === 'male' ? '♂️' : gender.toLowerCase() === 'female' ? '♀️' : '⚧️';
	}

	function getCountryFlag(country: string) {
		return '🌍';
	}
</script>

<div class="card-display">
	<Card class="custom-card mdc-elevation--z12">

		<div class="avatar-container">
			<div class="avatar-wrapper">

				<div class="badge-icon gender" title={student.gender}>
					{getGenderIcon(student.gender)}
				</div>

				<div
					class="avatar-pic"
					style="background-image: url('{student.avatar}')"
				></div>

				<div class="badge-icon country" title={student.country}>
					{getCountryFlag(student.country)}
				</div>

			</div>
		</div>

		<div class="student-info-container">
			<Content class="card-content">
				<div class="line">
					<Icon class="material-icons">school</Icon>
					<h3 class="faculty-subtitle">{student.destinationFaculty}</h3>
				</div>
				<div class="line">
					<Icon class="material-icons">home</Icon>
					<h3 class="faculty-subtitle">{student.homeUniversity}</h3>
				</div>
				<div class="line">
					<Icon class="material-icons">public</Icon>
					<h3 class="card-title">{student.country}</h3>
				</div>

				<div class="bio-block">
					<p>"{student.bio}"</p>
				</div>

			</Content>
		</div>

		<Actions class="actions-flex">
			<ActionButtons>
				<Button variant="raised" onclick={handleOpenDialog}>
					<Label>Pick Student</Label>
				</Button>
			</ActionButtons>
		</Actions>
	</Card>

	<ConfirmStudentDialog
		bind:open={dialogOpen}
		{student}
		onConfirm={handleConfirmPick}
		onClose={handleCloseDialog}
	/>
</div>

<style>
    .card-display {
        max-width: 400px;
        margin: 2rem 0;
        position: relative;
        flex: 1;
    }

    :global(.custom-card) {
        border-radius: 16px;
        z-index: 0;
        overflow: visible;
        flex: 1;
        border: 1px solid #e0e0e0;
        transition: transform 0.25s ease, box-shadow 0.25s ease;
        display: flex;
        flex-direction: column;
        padding-top: 5rem;
        position: relative;
        background-color: var(--mdc-theme-surface, #fff);
    }

    :global(.mdc-button) {
        min-width: 200px;
    }

    :global(.custom-card:hover) {
        transform: translateY(-6px) scale(1.02);
        box-shadow: 0 18px 35px rgba(0, 0, 0, 0.12);
        background-color: var(--mdc-theme-text-hint-on-background);
    }

    /* Floating Container */
    .avatar-container {
        position: absolute;
        top: -50px;
        left: 0;
        width: 100%;
        z-index: 5;
        display: flex;
        justify-content: center;
        pointer-events: none; /* Allows clicking through empty space */
    }

    /* Wrapper to hold badges relative to avatar */
    .avatar-wrapper {
        position: relative;
        width: 150px;
        height: 150px;
    }

    .avatar-pic {
        width: 150px;
        height: 150px;
        background-size: cover;
        background-position: center;
        border-radius: 50%;
        border: 4px solid var(--mdc-theme-surface, #fff);
        box-shadow: 0 6px 20px rgba(0, 0, 0, 0.25);
        position: relative;
        z-index: 2;
    }

    /* Badge Styles */
    .badge-icon {
        position: absolute;
        bottom: 0px;
        width: 3.5rem;
        height: 3.5rem;
        background-color: var(--mdc-theme-surface, #fff);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 1.8rem;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
        z-index: 3;
        color: var(--mdc-theme-on-surface, #000);
        pointer-events: auto; /* Re-enable clicks for tooltips */
    }

    .badge-icon.gender {
        left: -15px;
    }

    .badge-icon.country {
        right: -15px;
    }

    .student-info-container {
        display: flex;
        flex: 1;
    }

    :global(.card-content) {
        display: flex;
        flex-direction: column;
        flex: 1;
        padding-bottom: 0;
        color: var(--mdc-theme-on-surface, #000);
    }

    .line {
        display: flex;
        align-items: center;
        flex: 1;
        border-bottom: 1px solid var(--mdc-theme-text-hint-on-background, #ddd);
        padding: 0;
        color: var(--mdc-theme-text-primary-on-background, rgba(0, 0, 0, 0.87));
    }

    :global(.material-icons) {
        margin: 0 0.5rem;
    }

    .line h2,
    .line h3 {
        margin: 0.75rem;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .line h2 {
        font-size: clamp(1.25rem, 1vw + 0.25rem, 1.5rem);
        font-weight: 600;
    }

    .line h3 {
        font-size: clamp(1.1rem, 0.5vw + 0.65rem, 2rem);
        font-weight: 500;
    }

    .bio-block {
        padding: 1rem 0.5rem;
        color: var(--mdc-theme-text-secondary-on-background, rgba(0, 0, 0, 0.6));
        font-style: italic;
    }

    .bio-block p {
        margin: 0;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
        overflow: hidden;
        line-height: 1.4;
    }

    :global(.actions-flex) {
        display: flex;
        justify-content: center;
        padding: 1rem;
    }

    @media (max-width: 800px) {
        .card-display {
            width: 100%;
            justify-self: center;
        }
    }
</style>