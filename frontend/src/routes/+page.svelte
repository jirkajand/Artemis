<script lang="ts">
	import type { PageProps } from './$types';

	// data loaded from +layout.ts
	let { data }: PageProps = $props();
</script>

<h1>Artemis Frontend</h1>

<p>Bellow is a health check response status of the user management service:</p>

{#await data.health}
	<p>⏳ Loading health check...</p>
{:then health}
	<p>✅ user-management: {health.status}</p>
{:catch error}
	<p>❌ Health check failed: {error.message}</p>
{/await}
