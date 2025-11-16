<script>
  import { keycloak } from "$lib/auth/keycloak";
	import Button, { Label } from "@smui/button";
</script>

<h1>Register</h1>

<Button onclick={() => keycloak.register({ redirectUri: window.location.origin })}>
  <Label>Register via Keycloak</Label>
</Button>

<a href="/login">Back to login</a>