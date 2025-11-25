<script lang="ts">
    import Dialog, {
        Title as DialogTitle,
        Content as DialogContent,
        Actions as DialogActions
    } from '@smui/dialog';
    import Button, { Label } from '@smui/button';
    import Fab from '@smui/fab';
    import Icon from '@smui/select/icon';
    import { tick } from 'svelte';

    let {
        student,
        open = $bindable(false),
        onConfirm = () => {},
        onClose = () => {}
    } = $props();

    function handleClose() {
        open = false;
        onClose();
    }

    function handleConfirm() {
        onConfirm(student);
        handleClose();
    }

    async function resetScroll() {
        await tick();
        const scrollContainer = document.querySelector('.mdc-dialog__content');
        if (scrollContainer) scrollContainer.scrollTop = 0;
    }
</script>

<Dialog bind:open>
    <div class="fab-container">
        <Fab color="primary" class="dialog-close-button" onclick={handleClose}>
            <Icon class="material-icons" color="secondary">close</Icon>
        </Fab>
    </div>

    <DialogTitle>Student Details</DialogTitle>
    <DialogContent class="dialog-content-column">
        <div class="details-top-row">
            <div class="dialog-info-container">
                <table class="student-details-table">
                    <thead>
                    <tr>
                        <th colspan="2" class="table-heading">Personal information</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><strong>Name:</strong></td>
                        <td>{student.firstName} {student.lastName}</td>
                    </tr>
                    <tr>
                        <td><strong>Email:</strong></td>
                        <td>{student.email}</td>
                    </tr>
                    <tr>
                        <td><strong>Phone number:</strong></td>
                        <td>{student.phoneNumber}</td>
                    </tr>
                    <tr>
                        <td><strong>Country:</strong></td>
                        <td>{student.countryName}</td>
                    </tr>
                    </tbody>

                    <thead>
                    <tr>
                        <th colspan="2" class="table-heading">Academic Information</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><strong>Destination Faculty:</strong></td>
                        <td>{student.faculty.facultyNameInternational}</td>
                    </tr>
                    <tr>
                        <td><strong>Home Faculty:</strong></td>
                        <td>{student.homeUniversity}</td>
                    </tr>
                    <tr>
                        <td><strong>Born:</strong></td>
                        <td>{new Date(student.dateOfBirth).toLocaleDateString()}</td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="dialog-picture-wrapper">
                <div class="picture-container-sized">
                    <img
                      class="dialog-picture"
                      src="https://preview.redd.it/pc9b705en1r91.jpg?width=640&crop=smart&auto=webp&s=4d27efd62c32e9ba94e9a522bc7d0d11ad3cf2c6"
                      alt="Student picture"
                    />
                    <div class="picture-badges">
                        <span class="badge-gender">{student.genderIcon}</span>
                        <span class="badge-country">{student.countryFlag}</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="bio-section">
            <h4 class="table-heading">About me</h4>
            <div class="bio-content-full">{student.bio}</div>
        </div>
    </DialogContent>

    <DialogActions>
        <Button variant="outlined" onclick={handleClose}>
            <Label>Close</Label>
        </Button>
    </DialogActions>
</Dialog>

<style lang="scss">
    /* Styles remain exactly the same as your input */
    :global(.dialog-content-column) {
        display: flex;
        flex-direction: column;
        padding: 1rem 3rem !important;
        gap: 1.5rem;
        height: 100%;
        overflow-y: visible;
    }
    :global(.mdc-dialog__surface .material-icons) {
        margin-right: 0;
    }
    .details-top-row {
        display: flex;
        flex-direction: row;
        gap: 2rem;
        flex: 0 0 auto;
    }
    .dialog-info-container {
        display: flex;
        flex-direction: column;
        flex: 1 1 50%;
        padding-right: 1rem;
    }
    .dialog-picture-wrapper {
        position: relative;
        height: auto;
        display: flex;
        align-items: center;
        justify-content: center;
        flex: 1 1 40%;
    }
    .picture-container-sized {
        position: relative;
        max-width: 100%;
        display: inline-block;
    }
    .bio-section {
        display: flex;
        flex-direction: column;
        flex: 1 1 auto;
        min-height: 100px;
    }
    .bio-content-full {
        text-align: center;
        flex: 1;
        overflow: visible;
        line-height: 1.6;
        font-style: italic;
        padding-top: 0.5rem;
    }
    .student-details-table {
        flex: 1;
        width: 100%;
        border-collapse: collapse;
    }
    .student-details-table td,
    .student-details-table th {
        padding: 0.2rem 0;
        text-align: left;
    }
    .student-details-table td {
        border-bottom: 2px dotted;
    }
    .student-details-table tbody:not(:last-child) tr:last-child td,
    .student-details-table tbody:last-of-type tr:last-child td {
        border-bottom: none;
    }
    .student-details-table strong {
        font-weight: 600;
        color: #555;
    }
    .student-details-table td:first-child {
        padding-left: 0;
        white-space: nowrap;
        width: 1%;
        padding-right: 1rem;
    }
    .student-details-table td:last-child {
        font-weight: 400;
        padding-right: 0;
    }
    .table-heading {
        font-weight: 600;
        text-align: center;
        color: var(--mdc-theme-primary);
        border-bottom: 2px solid #eee;
        padding-bottom: 0.5rem;
        font-size: 1.25rem;
        margin: 0;
    }
    :global(.dialog-picture) {
        max-width: 100%;
        width: 100%;
        height: auto;
        max-height: 300px;
        object-fit: contain;
        display: block;
        border-radius: 8px;
    }
    .picture-badges {
        position: absolute;
        inset: 0;
        display: flex;
        justify-content: space-between;
        align-items: flex-end;
        padding: 0;
        pointer-events: none;
        box-sizing: border-box;
        width: auto;
        height: auto;
    }
    .badge-gender,
    .badge-country {
        font-size: clamp(1.75rem, 2vw, 2.5rem);
        line-height: 1;
        background-color: white !important;
        border: 1px solid rgba(0, 0, 0, 0.1);
        padding: 10px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        color: #333;
        display: flex;
        align-items: center;
        justify-content: center;
        pointer-events: auto;
        transition: transform 0.2s ease-in-out;
    }
    .badge-gender {
        border-top-right-radius: 50%;
        border-bottom-left-radius: 10%;
    }
    .badge-country {
        border-top-left-radius: 50%;
        border-bottom-right-radius: 10%;
    }
    .badge-gender:hover,
    .badge-country:hover {
        transform: translateY(-2px) scale(1.08);
    }
    :global(.mdc-dialog__surface) {
        overflow: visible;
        display: flex;
        flex-direction: column;
        max-width: 90vw !important;
    }
    :global(.mdc-dialog .mdc-dialog__surface) {
        border-radius: 20px;
        width: max(850px, 50vw);
    }
    :global(.mdc-dialog__title) {
        text-align: center;
        font-size: 1.75rem;
        margin: 0;
        padding: 1rem;
        border-bottom: 2px solid;
        flex-shrink: 0;
    }
    :global(.mdc-dialog__actions) {
        justify-content: center;
        padding: 1rem;
        flex-shrink: 0;
    }
    :global(.mdc-dialog__actions > .mdc-button) {
        font-size: 1.25rem;
        min-width: 200px;
    }
    :global(.dialog-close-button) {
        position: absolute;
        top: -20px;
        right: -20px;
        z-index: 10;
        border-radius: 50%;
        margin: 0;
    }
    .fab-container {
        position: relative;
        height: 0;
        width: 100%;
        z-index: 1;
        display: flex;
    }
    @media (max-width: 800px) {
        :global(.mdc-dialog .mdc-dialog__surface) {
            max-width: 95vw !important;
        }
        .picture-badges {
            top: auto;
            align-items: flex-end;
        }
        .details-top-row {
            flex-direction: column;
            gap: 1rem;
        }
        .dialog-picture-wrapper {
            order: -1;
            width: 100%;
        }
        .dialog-picture {
            max-height: 200px;
        }
        .dialog-info-container {
            padding-right: 0;
        }
        .student-details-table td {
            display: table-cell;
            width: auto;
        }
        .student-details-table td:first-child {
            font-size: 1.15rem;
            white-space: nowrap;
            padding-right: 10px;
            width: 60%;

        }
        .student-details-table td:last-child {
            font-size: 1rem;
            text-align: left;
        }
        .table-heading {
            text-align: center;
            font-size: 1.1rem;
        }
    }
</style>