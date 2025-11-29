<script lang="ts">
	import AnonymisedStudentCard from '$lib/components/AnonymisedStudentCard.svelte';
	import { goto } from '$app/navigation';

	let { data } = $props();
	let { students , pagination , management } = $derived(data);

	async function handleBuddyMatch(student: any) {
		try {
			await management.assignInternationalStudentToLocalStudent({ internationalStudentId: student.id });
			alert(`You are now a buddy for this student from ${student.countryName}!`);
			await goto(location.href, { invalidateAll: true });
		} catch (e) {
			alert(`Something went wrong!`);
		}
	}
</script>

{#each students as student (student.id)}
	<AnonymisedStudentCard {student} onPick={() => handleBuddyMatch(student)} />
{/each}
