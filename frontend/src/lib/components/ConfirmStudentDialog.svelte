<script lang="ts">
    import Dialog, {
        Title as DialogTitle,
        Content as DialogContent,
        Actions as DialogActions } from '@smui/dialog';
    import Button, { Label } from '@smui/button';
    import Fab from '@smui/fab';
    import Icon from '@smui/select/icon';

    export let open = false;
    export let student: {
        name: string;
        faculty: string;
        group: string;
        bio: string;
        status: string;
        gender: string
        country: string
    };

    export let onConfirm: (student: any) => void = () => {};
    export let onClose: () => void = () => {};

    function handleClose() {
        open = false;
        onClose();
    }

    function handleConfirm() {
        onConfirm(student);
        handleClose();
    }
</script>

<Dialog bind:open>
    <div class="fab-container">
        <Fab color="primary" class="dialog-close-button" onclick={handleClose}>
            <Icon class="material-icons" color="secondary">close</Icon>
        </Fab>
    </div>

    <DialogTitle>Student Details</DialogTitle>
    <DialogContent>
        <div class="dialog-info-container">
            <p><strong>Name:</strong> {student.name}</p>
            <p><strong>Faculty:</strong> {student.faculty}</p>
            <p><strong>Group:</strong> {student.group}</p>
            <p><strong>Gender:</strong> {student.gender}</p>
            <p><strong>Country:</strong> {student.country}</p>
            <p><strong>Bio:</strong> {student.bio}</p>
            <p><strong>Status:</strong> {student.status}</p>
        </div>
        <img
                class="dialog-picture"
                src="https://preview.redd.it/pc9b705en1r91.jpg?width=640&crop=smart&auto=webp&s=4d27efd62c32e9ba94e9a522bc7d0d11ad3cf2c6"
                alt="Student picture"
        />

    </DialogContent>

    <DialogActions>
        <Button class="confirm-btn" variant="raised" onclick={handleConfirm}>
            <Label>Confirm</Label>
        </Button>
        <Button variant="outlined" on:click={handleClose}>
            <Label>Cancel</Label>
        </Button>
    </DialogActions>
</Dialog>

<style>
    :global(.mdc-dialog__surface){
        min-width: 600px;
        height: max(400px, 70vh);
        width: unset !important;
        overflow: visible;
        border-radius: 20px;

    }

    :global(.mdc-dialog__content){
        padding: 2rem !important;
        display: flex;
        gap: 2rem;
        justify-content: center;
        flex-direction: row;

    }

    :global(.confirm-btn) {
        min-width: 30%
    }

    :global(.mdc-dialog .mdc-dialog__surface) {
        max-width: 45vw;
    }

    :global(.mdc-dialog__content > div){
        width: 100%;
        flex: 1;
    }

    :global(.mdc-dialog__title){
        text-align: center;
        font-size: 1.75rem;
        margin: 0;
        padding: 1rem;
    }

    :global(.mdc-dialog__actions){
        justify-content: center;
        padding: 1rem;
    }

    :global(.dialog-close-button){
        position: absolute;
        top: -20px;
        right: -25px;
        z-index: 10;
        border-radius: 12px;
    }

    .dialog-info-container {
        align-items: flex-start;
        justify-content: center;
        display: flex;
        flex-direction: column;
    }

    .dialog-info-container > p {
        width: 100%;
    }

    .dialog-info-container > * {

        flex: 1;
        margin: 0;
    }

    :global(.dialog-picture) {
        max-width: 50%;
        flex-shrink: 1;
        flex-grow: 0;
        object-fit: contain;
        width: auto;
        height: auto;
    }

    .fab-container{
        position: relative;
        height: 0;
        width: 100%;
        z-index: 1;
        display: flex;
    }
</style>
