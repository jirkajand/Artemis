<script lang="ts">
	import ProfilePicture from '$lib/components/profile/ProfilePicture.svelte';
	let { editableStudent = $bindable(), editing } = $props();
</script>

<div class="image-column">
	<ProfilePicture
		profilePicture={editableStudent.profilePicture}
		genderIcon={editableStudent.genderIcon}
		countryFlag={editableStudent.countryFlag}
	/>

	{#if editableStudent.type === 'INTERNATIONAL'}
		<div class="bio-section">
			<h4 class="section-header">About Me</h4>

			{#if editing}
				<textarea class="bio-input" bind:value={editableStudent.bio} rows="6"></textarea>
			{:else}
				<p class="bio-text">{editableStudent.bio}</p>
			{/if}
		</div>
	{/if}
</div>

<style lang="scss">
	.section-header {
		margin-top: 0;
	}

  .image-column {
    flex: 1;
    display: flex;
    flex-direction: column;
		justify-content: center;
    align-items: center;
    gap: 1rem;
  }

  .bio-section {
    margin-top: 1rem;
    width: 100%;
    text-align: center;
  }

  .bio-text {
    font-style: italic;
    color: var(--on-background);
    line-height: 1.6;
    margin-top: 1rem;
    padding: 0 1rem;
  }

  .bio-input {
    width: 100%;
    padding: 0.75rem;
    font-size: 1rem;
    border-radius: 6px;
    border: 1px solid #ccc;
    background: var(--surface);
    color: var(--on-surface);
    resize: vertical;
    min-height: 120px;
    box-sizing: border-box;
		text-align: center;

    &:focus {
      border-color: var(--primary);
      box-shadow: 0 0 0 3px rgba(var(--primary-rgb, 0, 174, 239), 0.15);
      outline: none;
    }
  }
</style>
