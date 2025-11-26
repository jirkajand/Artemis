<script lang="ts">
	import AnonymisedStudentCard from '$lib/components/StudentCard.svelte';
	import { invalidateAll} from '$app/navigation';
	import StudentCard from '$lib/components/StudentCard.svelte';
	let { data } = $props();
	let { management, students } = $derived(data);

	async function handleBuddyMatch(student: any) {
		console.log("Processing match for:", student.id);
		try{
			await management.assignInternationalStudentToLocalStudent({internationalStudentId: student.id})
			alert(`You are now a buddy for this student from ${student.countryName}!`);
			await invalidateAll()

		} catch (e) {
			alert(`Something went wrong! Cause: ${e}`);
		}
	}
</script>

{#each students as student, i (i)}
	<StudentCard {student}/>
{/each}

<style>
</style>
