<script lang="ts">
	import Dialog, {
		Title as DialogTitle,
		Content as DialogContent,
		Actions as DialogActions
	} from '@smui/dialog';
	import Button from '@smui/button';

	let {
		open = $bindable(false), onClose = () => {
		},
		dialogState = $bindable({message: '', heading: ''})
	} = $props();
</script>

<Dialog class="success-dialog" bind:open onclosed={onClose}>
	<DialogTitle>{dialogState.heading}</DialogTitle>

	<DialogContent>
		<p>{dialogState.message}</p>
	</DialogContent>

	<DialogActions>
		<Button variant="raised" onclick={() => (open = false)}>
			Close
		</Button>
	</DialogActions>
</Dialog>

<style>

		:global(.success-dialog .mdc-dialog__surface){
			border-radius: 20px !important;
        width: max(350px, 25vw) !important;
		}

		:global(.success-dialog .mdc-dialog__actions) {
				justify-content: center !important;
		}

    :global(.success-dialog .mdc-dialog__container) {
        text-align: center;
    }


</style>
