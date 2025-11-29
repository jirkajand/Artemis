<script lang="ts">
	import AnonymisedStudentCard from '$lib/components/StudentCard.svelte';
	import { invalidateAll} from '$app/navigation';
	import StudentCard from '$lib/components/StudentCard.svelte';
	let { data } = $props();
	let { management, students } = $derived(data);

</script>

{#each students as student, i (i)}
	<StudentCard {student}/>
{/each}

<style>
</style>
