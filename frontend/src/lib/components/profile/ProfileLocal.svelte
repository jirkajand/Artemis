<script lang="ts">
	import ProfileCommon from './ProfileCommon.svelte';
	export let student: any;
</script>

<ProfileCommon {student} />

<div class="local-extra">
	<table class="details-table">
		<thead>
		<tr><th colspan="2" class="section-header">Local Student Info</th></tr>
		</thead>
		<tbody>
		<tr><td class="label">Local ID:</td><td class="value">{student.localId}</td></tr>
		</tbody>
	</table>
</div>

<style lang="scss">
  .local-extra {
    margin-top: 2rem;
  }
</style>
