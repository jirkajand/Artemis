<script lang="ts">
	import ProfileLocal from './ProfileLocal.svelte';
	import ProfileInternational from './ProfileInternational.svelte';
	let {student} = $props()
</script>

<div class="page-background">
	<div class="paper-sheet">
		<div class="sheet-header">
			<h2>{student.role} Student Details</h2>
		</div>

		<div class="sheet-content">
			{#if student.role === 'local'}
				<ProfileLocal {student} />
			{:else if student.role === 'international'}
				<ProfileInternational {student} />
			{:else}
				<p>Unknown student type</p>
			{/if}
		</div>
	</div>
</div>

<style lang="scss">
  .page-background {
    width: 100%;
    background-color: var(--background);
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: flex-start;
  }

  .paper-sheet {
    background-color: var(--surface);
    width: 100%;
    max-width: 900px;
    display: flex;
    flex-direction: column;
    overflow: hidden;
  }

  .sheet-header {
    border-bottom: 2px solid var(--neutral-bg);
    padding: 1.5rem;
    text-align: center;
  }

  .sheet-header h2 {
    margin: 0;
    font-size: 2rem;
    color: var(--on-surface);
    font-weight: 700;
  }

  .sheet-content {
    padding: 2rem 3rem;
  }
</style>
