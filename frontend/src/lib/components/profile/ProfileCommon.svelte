<script lang="ts">
	import ProfilePicture from './ProfilePicture.svelte';
	let { student } = $props()

</script>
<div class="split-layout">
	<div class="data-column">
		<table class="details-table">
			<thead>
			<tr><th colspan="2" class="section-header">Personal Information</th></tr>
			</thead>
			<tbody>
			<tr><td class="label">First name:</td><td class="value">{student.firstName}</td></tr>
			<tr><td class="label">Last name:</td><td class="value">{student.lastName}</td></tr>
			<tr><td class="label">Email:</td><td class="value">{student.email}</td></tr>
			<tr><td class="label">Phone number:</td><td class="value">{student.phoneNumber}</td></tr>
			<tr><td class="label">Gender:</td><td class="value">{student.gender}</td></tr>
			<tr><td class="label">Faculty:</td><td class="value">{student.faculty.facultyNameInternational}</td></tr>
			</tbody>
		</table>
	</div>

	<div class="image-column">
		<ProfilePicture
			profilePicture={student.profilePicture}
			genderIcon={student.genderIcon}
			countryFlag={student.countryFlag} />
	</div>
</div>



<style lang="scss">
  .split-layout {
    display: flex;
    gap: 2rem;
    margin-bottom: 2rem;
  }

  .data-column {
    flex: 1.5;
  }

  .image-column {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: flex-start;
  }

  .details-table {
    width: 100%;
    border-collapse: collapse;
  }

  .section-header {
    font-size: 1.25rem;
    text-transform: uppercase;
    border-bottom: 2px solid #eee;
    padding: 0.5rem 0;
    text-align: center;
  }

  .details-table td {
    padding: 0.6rem 0;
    border-bottom: 2px dotted var(--on-background);
    font-size: 1.1rem;
  }

  .details-table tbody tr:last-child td {
    border-bottom: none;
  }

  .label {
    font-weight: 700;
    white-space: nowrap;
    width: 1%;
    padding-right: 1.5rem;
    vertical-align: top;
  }

  .value {
    line-height: 1.4;
  }
</style>
