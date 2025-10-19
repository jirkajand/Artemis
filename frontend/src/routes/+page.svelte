<script lang="ts"> 
    // data loaded from +layout.ts
    let { data } = $props();
</script>

<h1>Welcome to SvelteKit</h1>
<p>Visit <a href="https://svelte.dev/docs/kit">svelte.dev/docs/kit</a> to read the documentation</p>

{data.test} {data.world} <br>
{#await data.health}
    loading ...
{:then health}
    user-management: {health.status}
{/await}
