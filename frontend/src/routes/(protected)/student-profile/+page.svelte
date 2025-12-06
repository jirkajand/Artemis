<script lang="ts">
	import Profile from '$lib/components/profile/Profile.svelte';
  import Paper from '@smui/paper';

	let { data } = $props();
	let student = $derived(data.student);
	let faculties = $derived(data.faculties);
</script>

<Paper>
  <Profile student={student} faculties={faculties} management={data.management}/>
</Paper>

<style>

  :global(main) {
    max-width: unset !important;
  }
</style>