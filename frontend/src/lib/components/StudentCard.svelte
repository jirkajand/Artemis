<script lang="ts">
    import Card, {Content, PrimaryAction, Actions, ActionButtons} from '@smui/card';
    import Button, {Label} from '@smui/button';
    import Icon from '@smui/select/icon';
    import StudentInfoDialog from '$lib/components/StudentInfoDialog.svelte';

    let open = $state(false);

    let student = {
        name: 'Really long student name',
        homeFaculty: 'Home faculty',
        destinationFaculty: 'Destination faculty',
        group: 'Group name',
        bio: 'Ahoy there! I be Patchy the Pirate, the self-appointed President of the SpongeBob SquarePants Fan Club!...',
        status: 'Assigned',
        gender: 'Male',
        country: 'Bikini Bottom'
    };

    // Assuming these utilities are available or manually calculated elsewhere
    const avatarUrl = 'https://preview.redd.it/pc9b705en1r91.jpg?width=640&crop=smart&auto=webp&s=4d27efd62c32e9ba94e9a522bc7d0d11ad3cf2c6';
    const genderIcon = student.gender === 'Male' ? '♂️' : '♀️';
    const countryFlag = '🏴‍☠️';

    const handleConfirm = (studentData: typeof student) => {
        console.log('Confirmed student:', studentData);
    };

    const handleClose = () => {
        console.log('Dialog closed');
    };

    // Prevent event propagation from button click to card click
    const handleOpenDialog = (e: Event) => {
        e.stopPropagation();
        open = true;
    }
</script>

<div class="card-display" onclick={() => open = true}>
    <Card class="custom-card mdc-elevation--z12">

        <!-- Aligned Avatar and Badges (Matching Anonymised structure) -->
        <div class="avatar-container">
            <div class="avatar-wrapper">

                <div class="badge-icon gender" title={student.gender}>
                    {genderIcon}
                </div>

                <div class="avatar-pic"
                     style={`background-image: url('${avatarUrl}')`}>
                </div>

                <div class="badge-icon country" title={student.country}>
                    {countryFlag}
                </div>
            </div>
        </div>

        <div class="student-info-container">
            <Content class="card-content">

                <!-- 1. Name -->
                <div class="line">
                    <Icon class="material-icons">person</Icon>
                    <div class="text-wrapper">
                        <h2 class="card-title" title={student.name}>
                            {student.name}
                        </h2>
                    </div>
                </div>

                <!-- 2. Destination Faculty -->
                <div class="line">
                    <Icon class="material-icons">school</Icon>
                    <div class="text-wrapper">
                        <h3 class="faculty-subtitle" title={student.destinationFaculty}>
                            {student.destinationFaculty}
                        </h3>
                    </div>
                </div>

                <!-- 3. Home Faculty -->
                <div class="line">
                    <Icon class="material-icons">home</Icon>
                    <div class="text-wrapper">
                        <h3 class="faculty-subtitle" title={student.homeFaculty}>
                            {student.homeFaculty}
                        </h3>
                    </div>
                </div>

                <!-- 4. Status -->
                <div class="line">
                    <Icon class="material-icons">done_outline</Icon>
                    <div class="text-wrapper">
                        <h3 class="faculty-subtitle status-label" title={student.status}>
                            {student.status}
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
                <!-- Use the new handler to prevent card click propagation -->
                <Button variant="raised" onclick={handleOpenDialog} class="pick-btn">
                    <Label>View Details</Label>
                </Button>
            </ActionButtons>
        </Actions>
    </Card>
</div>

<StudentInfoDialog
  bind:open
  {student}
  onConfirm={handleConfirm}
  onClose={handleClose}
/>


<style>
    /* 1. Slimmer Card Width */
    .card-display {
        width: 100%;
        max-width: 340px; /* Matching anonymized card width */
        margin: 2rem auto;
        position: relative;
        flex: 1;
        display: flex;
        flex-direction: column;
        cursor: pointer;
    }

    :global(.custom-card) {
        border-radius: 16px;
        z-index: 0;
        overflow: visible;
        flex: 1;
        border: 1px solid #e0e0e0;
        transition: transform 0.2s ease, box-shadow 0.2s ease;
        display: flex;
        flex-direction: column;
        padding-top: 4rem; /* Adjusted space for smaller avatar */
        position: relative;
        background-color: var(--mdc-theme-surface, #fff);
        height: 100%;
    }

    :global(.custom-card:hover) {
        transform: translateY(-4px);
        box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
    }

    /* --- Avatar Area (Matching Anonymised structure) --- */
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
        width: 120px;
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

    .badge-icon {
        position: absolute;
        bottom: 0;
        width: 2.5rem;
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
        min-width: 0; /* CRITICAL: Enables ellipsis */
    }

    .line h2, .line h3 {
        margin: 0;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        color: var(--mdc-theme-text-primary-on-background, rgba(0, 0, 0, 0.87));
    }

    /* Standardized Font Sizes */
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
        color: var(--mdc-theme-primary); /* Highlight status */
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