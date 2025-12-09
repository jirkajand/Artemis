<script lang="ts">
    import Card, {Content, PrimaryAction, Actions, ActionButtons} from '@smui/card';
    import Button, {Label} from '@smui/button';
    import Icon from '@smui/select/icon';
    import StudentInfoDialog from '$lib/components/StudentInfoDialog.svelte';

    const {student} = $props()
    let open = $state(false);

    const handleClose = () => {
        console.log('Dialog closed');
    };
</script>

<div class="card-display">
    <Card class="custom-card mdc-elevation--z12">

        <div class="avatar-container">
            <div class="avatar-wrapper">
                <div class="badge-icon gender" title={student?.gender}>
                    {student.genderIcon}
                </div>
                <div class="avatar-pic"
                     style={`background-image: url('${student?.profilePicture}')`}>
                </div>
                <div class="badge-icon country" title={student?.countryCode}>
                    {student?.countryFlag}
                </div>
            </div>
        </div>

        <div class="student-info-container">
            <Content class="card-content">
                <div class="line">
                    <Icon class="material-icons">person</Icon>
                    <div class="text-wrapper">
                        <h2 class="card-title" title={student?.name ?? ''}>
                            {student?.name ?? ''}
                        </h2>
                    </div>
                </div>

                <div class="line">
                    <Icon class="material-icons">school</Icon>
                    <div class="text-wrapper">
                        <h3 class="faculty-subtitle" title={student?.faculty?.facultyNameInternational ?? ''}>
                            {student?.faculty?.facultyNameInternational ?? ''}
                        </h3>
                    </div>
                </div>

                <div class="line">
                    <Icon class="material-icons">home</Icon>
                    <div class="text-wrapper">
                        <h3 class="faculty-subtitle" title={student?.homeUniversity ?? ''}>
                            {student?.homeUniversity ?? ''}
                        </h3>
                    </div>
                </div>

                <div class="bio-block">
                    <p>"{student?.bio ?? ''}"</p>
                </div>
            </Content>
        </div>

        <Actions class="actions-flex">
            <ActionButtons>
                <Button variant="raised"  onclick={() => open = true} class="pick-btn">
                    <Label>View Details</Label>
                </Button>
            </ActionButtons>
        </Actions>
    </Card>
</div>

<StudentInfoDialog
  bind:open
  {student}
  onClose={handleClose}
/>


<style>
    /* Styles remain exactly the same as your input */
    .card-display {
        width: 100%;
        max-width: 340px;
        margin: 2rem auto;
        position: relative;
        flex: 1;
        display: flex;
        flex-direction: column;
    }
    :global(.custom-card) {
        color: var(--on-background);
        z-index: 0;
        overflow: visible;
        flex: 1;
        border: 1px solid #e0e0e0;
        transition: transform 0.2s ease, box-shadow 0.2s ease;
        display: flex;
        flex-direction: column;
        padding-top: 5rem;
        position: relative;
        background-color: var(--neutral-bg);
        height: 100%;
    }
    :global(.custom-card:hover) {
        transform: translateY(-4px);
        box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
    }
    .avatar-container {
        position: absolute;
        top: -40px;
        left: 0;
        width: 100%;
        display: flex;
        justify-content: center;
        pointer-events: none;
    }
    .avatar-wrapper {
        z-index: 0;
        position: relative;
        width: 120px;
        height: 120px;
    }
    .avatar-pic {
        width: 100%;
        height: 100%;
        background-size: cover;
        background-position: center;
        border-radius: 50%;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        border: 3px solid var(--on-nav-bg);
        position: relative;
    }
    .badge-icon {
        z-index: 1;
        background-color: var(--on-secondary) !important;
        position: absolute;
        bottom: 0;
        width: 2.5rem;
        height: 2.5rem;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 1.5rem;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
        color: var(--mdc-theme-on-surface, #000);
        pointer-events: auto;
    }
    .badge-icon.gender { left: -10px; }
    .badge-icon.country { right: -10px; }
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
    .line h2, .line h3 {
        margin: 0;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        color: var(--on-background);
    }
    .card-title {
        font-size: 1rem;
        font-weight: 600 !important;
    }
    .faculty-subtitle {
        font-size: 0.975rem;
        font-weight: 500;
    }
    .status-label {
        font-weight: 600;
        color: var(--mdc-theme-primary);
    }
    .bio-block {
        margin-top: 1rem;
        padding: 0.75rem;
        border-radius: 8px;
        color: var(--mdc-theme-on-surface);
        font-style: italic;
        font-size: 0.9rem;
    }
    .bio-block p {
        text-align: center;
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
        margin-top: auto;
    }
    :global(.pick-btn) {
        width: 100%;
    }
</style>