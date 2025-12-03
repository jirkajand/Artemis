<script>
  import { keycloak } from "$lib/auth/keycloak";
	import Button, { Label } from "@smui/button";
</script>

<h1>Login</h1>

<Button onclick={() => keycloak.login()}>
  <Label>Login with Keycloak</Label>
</Button>
<br/>
<a href="/register">Create an account</a>