<script lang="ts">
	import TopAppBar, { Row, Section, Title } from '@smui/top-app-bar';
	import IconButton from '@smui/icon-button';
	import Profile from '$lib/components/profile/Profile.svelte';

	let { data } = $props();
	let { student } = data;
</script>
<div class="page-background">
	<div class="paper-sheet">
		<div class="sheet-header">
			<h2>{student.type} STUDENT DETAILS</h2>
		</div>
		<Profile {student}>
		</Profile>
	</div>
</div>

<style lang="scss">

  :global(main) {
    max-width: unset !important;
    padding: 0 !important;
  }

  :global(.profile-top-bar) {
    background-color: var(--on-background);
    color: #333 !important;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05) !important;
  }

  /* --- Page Structure --- */
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
    height: 100%;
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
    height: 100%;
    align-content: flex-start;
    padding: calc(1rem + 2vw) calc(2rem + 5vw);
  }

  /* --- Responsive (Mobile) --- */
  @media (max-width: 850px) {
    .sheet-content {
      padding: 1rem;
    }
  }
</style>