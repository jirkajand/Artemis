<script>
  import { keycloak } from "$lib/auth/keycloak";
	import Button, { Label } from "@smui/button";
</script>

<h1>Login</h1>

<p>Use your company account to access Artemis.</p>

<Button onclick={() => keycloak.login({
  redirectUri: `${window.location.origin}/dashboard`
})}>
  <Label>Login with Keycloak</Label>
</Button>
<br/>
<a href="/register">Create an account</a>