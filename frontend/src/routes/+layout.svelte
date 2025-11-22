<script lang="ts">
	let { children } = $props();
</script>

<main>
	{@render children?.()}
</main>

<style>
  main {
    flex-direction: column;
    align-items: start;
    justify-content: start;
    margin: 0 auto;
    padding: 1rem;
    width: 100%;
  }

  *, :root, html {
	  box-sizing: border-box;
  }
</style>
