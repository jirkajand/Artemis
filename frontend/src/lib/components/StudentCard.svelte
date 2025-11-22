<script lang="ts">
    import Card, { Content, PrimaryAction, Actions, ActionButtons } from '@smui/card';
    import Button, { Label } from '@smui/button';
    import Badge from '@smui-extra/badge';
    import Icon from '@smui/select/icon';
    import ConfirmStudentDialog from '$lib/components/ConfirmStudentDialog.svelte';

    let open = false;
    let student = {
        name: 'Really long student name',
        faculty: 'Designated faculty',
        group: 'Group name',
        bio: '.....',
        status: 'Assigned',
        gender: 'Male',
        country: 'Bikini Bottom'
    };

    const handleConfirm = (studentData: typeof student) => {
        console.log('Confirmed student:', studentData);
    };

    const handleClose = () => {
        console.log('Dialog closed');
    };
</script>

<div class="card-display">
    <Card class="custom-card mdc-elevation--z12">
        <div class="badge-container">
            <Badge class="gender-badge" position="inset" align="top-start">♂️</Badge>
            <Badge class="country-badge" position="inset" align="top-end">🏴‍☠️</Badge>
        </div>

        <div class="avatar-container">
            <div class="avatar-pic"
                 style="background-image: url('https://preview.redd.it/pc9b705en1r91.jpg?width=640&crop=smart&auto=webp&s=4d27efd62c32e9ba94e9a522bc7d0d11ad3cf2c6');">
            </div>
        </div>

        <PrimaryAction onclick={() => open = true}>
            <Content class="card-content">
                <div class="line">
                    <Icon class="material-icons">person</Icon>
                    <h2 class="card-title">{student.name}</h2>
                </div>
                <div class="line">
                    <Icon class="material-icons">school</Icon>
                    <h3 class="faculty-subtitle">{student.faculty}</h3>
                </div>
                <div class="line">
                    <Icon class="material-icons">group</Icon>
                    <h3 class="faculty-subtitle">{student.group}</h3>
                </div>
                <div class="line">
                    <Icon class="material-icons">done_outline</Icon>
                    <h3 class="faculty-subtitle">{student.status}</h3>
                </div>
            </Content>
        </PrimaryAction>

        <Actions class="actions-flex">
            <ActionButtons>
                <Button variant="raised"><Label>Assign student</Label></Button>
            </ActionButtons>
        </Actions>
    </Card>
</div>

<ConfirmStudentDialog
        bind:open
        {student}
        onConfirm={handleConfirm}
        onClose={handleClose}
/>



<style>
    .card-display {
        max-width: 400px;
        margin: 2.5rem 0;
        position: relative;
        flex: 1;
    }

    /* Card */
    :global(.custom-card) {
        border-radius: 16px;
        z-index: 0;
        overflow: visible;
        flex: 1;
        border: 1px solid #e0e0e0;
        transition: transform 0.25s ease, box-shadow 0.25s ease;
        display: flex;
        flex-direction: column;
        padding-top: 5rem; /* space for floating avatar */
        position: relative;
    }

    :global(.custom-card:hover) {
        transform: translateY(-6px) scale(1.02);
        box-shadow: 0 18px 35px rgba(0, 0, 0, 0.12);
    }

    /* FLOATING AVATAR */
    .avatar-container {
        position: absolute;
        top: -50px; /* raise above the top */
        left: 50%;
        transform: translateX(-50%);
        z-index: 5;
        width: 100%;
        display: flex;
        justify-content: center;
        pointer-events: none;
    }

    .avatar-pic {
        width: 150px;
        height: 150px;
        background-size: cover;
        background-position: center;
        border-radius: 50%;
        border: 4px solid white;
        box-shadow: 0 6px 20px rgba(0, 0, 0, 0.25);
    }

    /* Content */
    :global(.card-content) {
        display: flex;
        flex-direction: column;
        flex: 1;
        padding-bottom: 0;
    }

    .line {
        display: flex;
        align-items: center;
        flex: 1;
        border-bottom: 1px solid #ddd;
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
    }

    .line h3 {
        font-size: clamp(1.1rem, 0.5vw + 0.65rem, 2rem);
    }

    .faculty-subtitle {
        font-weight: 500;
    }

    /* Badges */
    :global(.smui-badge) {
        z-index: 10;
        font-size: clamp(2.25rem, 1vw + 0.5rem, 3rem);
        padding: 0.5rem;
        margin: 0;
        background-color: transparent !important;
    }

    :global(.badge-container) {
        position: absolute;
        inset: 2rem 1rem;
        display: flex;
        justify-content: space-between;
        pointer-events: none;
    }


    :global(.actions-flex) {
        display: flex;
        justify-content: center;
        padding: 1rem;
    }

    @media(max-width: 800px) {
        .card-display {
            width: 100%;
            justify-self: center;
        }
    }

    /* Dialog */
    :global(.mdc-dialog__surface){
        width: 600px;
        height: 70vh;
        max-width: calc(100vw - 32px);
        overflow: visible;
        border-radius: 10%;
    }

    :global(.mdc-dialog .mdc-dialog__surface) {
        border-radius: 10%;
    }

    :global(.mdc-dialog__content){
        padding: 2rem;
    }

    :global(.mdc-dialog__title){
        text-align: center;
    }

    :global(.mdc-dialog__actions) {
        justify-content: center;
        padding: 1rem;
    }

    :global(.dialog-confirm-button) {
        min-width: 30%;
    }

    :global(.dialog-close-button) {
        top: -25px;
        right: -15px;
        z-index: 10;
    }

    .fab-container {
        position: relative;
        height: 0;
        width: 100%;
        z-index: 1;
        display: flex;
    }





</style>
