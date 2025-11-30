<script lang="ts">
	import ProfileCommon from './ProfileCommon.svelte';
	let {student} = $props()
	import Bio from './Bio.svelte';
</script>

<ProfileCommon {student} />

<div class="international-extra">
	<table class="details-table">
		<thead>
		<tr><th colspan="2" class="section-header">International Student Info</th></tr>
		</thead>
		<tbody>
		<tr><td class="label">Home university:</td><td class="value">{student.homeUniversity}</td></tr>
		<tr><td class="label">Country:</td><td class="value">{student.countryName}</td></tr>
		</tbody>
	</table>
</div>

<Bio bio={student.bio} />


<style lang="scss">
  .international-extra {
    margin-top: 2rem;
  }
</style>
