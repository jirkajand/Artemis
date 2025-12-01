<script lang="ts">
	import { keycloak } from '$lib/auth/keycloak';
    import Button, { Label } from '@smui/button';

    const { data } = $props()
    const { clients } = data;
    const { settings, management } = clients;

</script>

<h1>Welcome to Artemis</h1>

<p>Bellow is a health check response status of the user management service:</p>

{#await data.health}
    <p>⏳ Loading health check...</p>
{:then health}
    <p>✅ user-management: {health.status}</p>
{:catch error}
    <p>❌ Health check failed: {error.message}</p>
{/await}