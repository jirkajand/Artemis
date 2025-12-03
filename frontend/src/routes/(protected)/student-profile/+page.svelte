<script lang="ts">
	import Profile from '$lib/components/profile/Profile.svelte';

	let { data } = $props();
	let student = $derived(data.student);
	let faculties = $derived(data.faculties);
	let { management } = data
</script>

<div class="page-background">
	<div class="paper-sheet">
		<div class="sheet-content">
			<Profile student={student} faculties={faculties} management={management}/>
		</div>
	</div>
</div>

<style lang="scss">
  :global(body) {
    margin: 0;
    padding: 0;
  }

  :global(main) {
    height: 100vh;
    width: 100vw;
    overflow-y: auto;
    padding: 0 !important;
    max-width: unset !important;
  }

  .page-background {
		width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    box-sizing: border-box;
  }

  .paper-sheet {
    background-color: var(--surface, #ffffff);
    width: 100%;
    border-radius: 8px;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
    display: flex;
    flex-direction: column;
  }

  .sheet-content {
    padding: 0 5%;

    @media (max-width: 768px) {
      padding: 1.5rem;
    }
  }
</style>