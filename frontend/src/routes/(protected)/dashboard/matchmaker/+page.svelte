<script lang="ts">
    import AnonymisedStudentCard from '$lib/components/AnonymisedStudentCard.svelte';
    import { invalidateAll} from '$app/navigation';
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
    <AnonymisedStudentCard {student} onPick="{() => handleBuddyMatch(student)}"/>
{/each}

<style>
</style>
