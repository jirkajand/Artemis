<script lang="ts">
	import { page } from '$app/state';
</script>

<h1>Error from home page: {page.status}</h1>
<p>{page.error?.message}</p>
