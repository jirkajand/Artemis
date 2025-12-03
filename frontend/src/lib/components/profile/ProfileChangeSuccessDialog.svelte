<script lang="ts">
	import Dialog, {
		Title as DialogTitle,
		Content as DialogContent,
		Actions as DialogActions
	} from '@smui/dialog';
	import Button from '@smui/button';

	let {
		open = $bindable(false), onClose = () => {
		}
	} = $props();
</script>

<Dialog bind:open onclosed={onClose}>
	<DialogTitle>Profile Updated</DialogTitle>

	<DialogContent>
		<p>Your profile has been successfully updated.</p>
	</DialogContent>

	<DialogActions>
		<Button variant="raised" onclick={() => (open = false)}>
			Close
		</Button>
	</DialogActions>
</Dialog>

<style>

    :global(.mdc-dialog) {

    }

		:global(.mdc-dialog__surface){
			border-radius: 20px !important;
        width: max(350px, 25vw) !important;
		}

		:global(.mdc-dialog__actions) {
				justify-content: center !important;
		}

    :global(.mdc-dialog__container) {
        text-align: center;
    }


</style>
