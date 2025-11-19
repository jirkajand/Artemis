<script lang="ts">
	let { children } = $props();
</script>

<main>
	{@render children?.()}
</main>

<style>
  main {
    display: flex;
    flex-direction: column;
    align-items: start;
    justify-content: start;
    margin: 0 auto;
    padding: 2rem 1rem;
    width: 100%;
    max-width: 54rem;
  }
</style>
