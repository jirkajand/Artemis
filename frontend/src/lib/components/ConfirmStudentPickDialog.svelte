<script lang="ts">
	import Dialog, { Content, Actions } from '@smui/dialog';
	import Button, { Label } from '@smui/button';
	import 'country-flag-icons/3x2/flags.css';

	let { open = $bindable(), student, avatarUrl, onConfirm, onClose } = $props();
	console.log(student);

	const handleConfirm = () => {
		onConfirm?.();
		open = false;
	};

	const handleClose = () => {
		onClose?.();
		open = false;
	};

</script>

<Dialog bind:open on:SMUIDialog:closed={handleClose} surface$class="custom-surface">
	<div class="dialog-container">
		<div class="header-bg"></div>

		<Content class="dialog-content">
			<div class="profile-header">
				<div class="avatar-wrapper">
					<div class="badge-icon gender" title={student.gender}>
						{student.genderIcon}
					</div>
					<div class="avatar" style="background-image: url({avatarUrl})"></div>
					<div class="badge-icon country" title={student.countryCode}>
						{(student.countryFlag)}
					</div>
				</div>
			</div>

			<div class="info-grid">
				<div class="info-item">
					<span class="label">Home University</span>
					<span class="value">{student.homeUniversity}</span>
				</div>
				<div class="arrow">➜</div>
				<div class="info-item">
					<span class="label">Destination</span>
					<span class="value">{student.destinationFaculty}</span>
				</div>
			</div>

			<div class="bio-section">
				<span class="quote-icon">❝</span>
				<div class="bio-text">
					{student.bio}
				</div>
			</div>
		</Content>

		<Actions class="dialog-actions">
			<Button variant="raised" onclick={handleConfirm} class="confirm-btn action-btn">
				<Label>Confirm Pick</Label>
			</Button>
			<Button onclick={handleClose} class="action-btn">
				<Label>Cancel</Label>
			</Button>
		</Actions>
	</div>
</Dialog>

<style>
    :global(.mdc-dialog .mdc-dialog__surface.custom-surface) {
        border-radius: 24px;
        overflow: hidden;
        padding: 0;
        width: 100%;
        min-width: 50vw;
        max-width: 700px;
        max-height: 90vh;
        display: flex;
        flex-direction: column;
        box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.3);
        background-color: var(--mdc-theme-surface, #fff);
    }

    .dialog-container {
        position: relative;
        background-color: var(--mdc-theme-surface, #fff);
        display: flex;
        flex-direction: column;
        height: 100%;
        overflow: hidden;
    }

    /* Header: Slightly taller max-height */
    .header-bg {
        height: clamp(80px, 15vh, 140px);
        background: linear-gradient(135deg, var(--mdc-theme-primary, #6200ee) 0%, var(--mdc-theme-secondary, #018786) 100%);
        width: 100%;
        position: absolute;
        top: 0;
        left: 0;
        z-index: 0;
    }

    .dialog-content {
        position: relative;
        z-index: 1;
        padding: 0 2rem; /* Increased side padding */
        margin-top: clamp(30px, 6vh, 50px);
        flex: 1;
        overflow-y: auto;
        scrollbar-width: none;
    }

    .dialog-content::-webkit-scrollbar {
        display: none;
    }

    .profile-header {
        display: flex;
        justify-content: center;
        margin-bottom: clamp(1.25rem, 3vh, 2rem);
    }

    .avatar-wrapper {
        position: relative;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    /* Avatar: Increased dimensions */
    .avatar {
        width: clamp(100px, 20vh, 140px); /* Increased from 80-110 */
        height: clamp(100px, 20vh, 140px);
        border-radius: 50%;
        background-size: cover;
        background-position: center;
        background-color: var(--mdc-theme-text-hint-on-background, #eee);
        border: 5px solid var(--mdc-theme-surface, #fff);
        box-shadow: 0 5px 18px rgba(0, 0, 0, 0.2);
        position: relative;
        z-index: 2;
    }

    .avatar.placeholder {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 3rem;
        color: var(--mdc-theme-text-disabled-on-background, rgba(0, 0, 0, 0.38));
    }

    /* Badges: Increased dimensions */
    .badge-icon {
        position: absolute;
        bottom: 0px;
        width: clamp(2.8rem, 6vh, 3.5rem); /* Increased */
        height: clamp(2.8rem, 6vh, 3.5rem);
        background-color: var(--mdc-theme-surface, #fff);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: clamp(1.5rem, 3vh, 1.8rem);
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        z-index: 3;
        border: 4px solid var(--mdc-theme-background, #f7fafc);
        color: var(--mdc-theme-on-surface, #000);
    }

    .badge-icon.gender {
        left: -15px;
    }

    .badge-icon.country {
        right: -15px;
    }

    .info-grid {
        display: grid;
        grid-template-columns: 1fr auto 1fr;
        align-items: center;
        gap: 1rem;
        background-color: var(--mdc-theme-background, #f7fafc);
        padding: 1.25rem;
        border-radius: 16px;
        margin-bottom: clamp(1.25rem, 3vh, 1.75rem);
    }

    .info-item {
        display: flex;
        flex-direction: column;
        text-align: center;
    }

    .arrow {
        color: var(--mdc-theme-text-disabled-on-background, rgba(0, 0, 0, 0.38));
        font-size: 1.5rem;
    }

    .label {
        font-size: 0.8rem;
        text-transform: uppercase;
        letter-spacing: 0.75px;
        color: var(--mdc-theme-text-secondary-on-background, rgba(0, 0, 0, 0.54));
        margin-bottom: 0.35rem;
        font-weight: 700;
    }

    .value {
        font-size: 1.1rem; /* Increased font size */
        font-weight: 600;
        color: var(--mdc-theme-text-primary-on-background, rgba(0, 0, 0, 0.87));
        line-height: 1.25;
    }

    .bio-section {
        position: relative;
        text-align: center;
        color: var(--mdc-theme-text-secondary-on-background, rgba(0, 0, 0, 0.6));
        font-style: italic;
        font-size: 1.05rem; /* Increased font size */
        line-height: 1.6;
        padding: 0 0.5rem 1.5rem;
    }

    .quote-icon {
        display: block;
        font-size: 2.2rem;
        color: var(--mdc-theme-text-hint-on-background, rgba(0, 0, 0, 0.1));
        line-height: 1;
        margin-bottom: -5px;
    }

    .dialog-actions {
        padding: 1rem 2rem 1.5rem;
        display: flex;
        justify-content: space-between;
        gap: 1rem;
        background-color: var(--mdc-theme-surface, #fff);
        z-index: 2;
        border-top: 1px solid rgba(0, 0, 0, 0.05);
    }

    :global(.action-btn) {
        font-size: 1rem !important;
        letter-spacing: 0.5px !important;
        height: 44px; /* Increased touch target */
    }

    :global(.confirm-btn) {
        background-color: var(--mdc-theme-primary, #6200ee) !important;
        color: var(--mdc-theme-on-primary, #fff) !important;
    }
</style>