<script lang="ts">
	export let bio: string;
</script>

{#if bio}
	<div class="bio-section">
		<h4 class="section-header">About Me</h4>
		<p class="bio-text">{bio}</p>
	</div>
{/if}

<style lang="scss">
  .bio-section {
    margin-top: 1rem;
  }

  .bio-text {
    text-align: center;
    font-style: italic;
    color: #555;
    line-height: 1.6;
    margin-top: 1rem;
    padding: 0 1rem;
  }
</style>
