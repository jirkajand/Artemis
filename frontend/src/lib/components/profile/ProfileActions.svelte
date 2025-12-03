<script lang="ts">
	import Button, { Label } from "@smui/button";
	let { editing, toggleEdit, saveChanges } = $props();
</script>

<div class="actions">
	{#if editing}
		<Button type="button" class="save-btn" onclick={saveChanges}>
			<Label>Save Changes</Label>
		</Button>

		<Button type="button" class="cancel-btn" onclick={toggleEdit}>
			<Label>Cancel</Label>
		</Button>
	{:else}
		<Button type="button" class="edit-btn" onclick={toggleEdit}>
			<Label>Edit Profile</Label>
		</Button>
	{/if}
</div>

<style lang="scss">
  .actions {
		margin-top: 1.5rem;
    display: flex;
    justify-content: center;
    gap: 1rem;
  }

  :global(.actions .mdc-button) {
    border: none;
    color: var(--on-surface) !important;
    border-radius: 6px;
    font-size: calc(0.5vw + 0.65rem) !important;
    padding: 1.5rem !important;
    cursor: pointer;
    transition: all 0.2s ease;

    &:hover {
      background-color: var(--neutral-bg);
      transform: scale(1.025);
    }

    &:active {
      transform: scale(0.98);
    }
  }

  :global(.actions .cancel-btn) {
    background-color: var(--secondary, #ccc);
  }

  :global(.actions .save-btn) {
    background-color: var(--primary, #0052cc);
    color: white !important;
  }

  :global(.actions .edit-btn) {
    background-color: var(--secondary, #ccc);
  }
</style>
