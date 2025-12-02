<script lang="ts">
	let { bio } = $props()
</script>

	<div class="bio-section">
		<h4 class="section-header">About Me</h4>
		<p class="bio-text">{bio}</p>
	</div>

<style lang="scss">
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
</style>
