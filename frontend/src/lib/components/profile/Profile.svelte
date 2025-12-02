<script lang="ts">
	import ProfilePicture from '$lib/components/profile/ProfilePicture.svelte';
	import Bio from '$lib/components/profile/Bio.svelte';

	let { student } = $props();
</script>

<div class="split-layout">
	<div class="data-column">
		<table class="details-table">
			<thead>
			<tr>
				<th colspan="2" class="section-header">Personal Information</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td class="label">First name:</td>
				<td class="value">{student.firstName}</td>
			</tr>
			<tr>
				<td class="label">Last name:</td>
				<td class="value">{student.lastName}</td>
			</tr>
			<tr>
				<td class="label">Email:</td>
				<td class="value">{student.email}</td>
			</tr>
			<tr>
				<td class="label">Phone number:</td>
				<td class="value">{student.phoneNumber}</td>
			</tr>
			<tr>
				<td class="label">Faculty:</td>
				<td class="value">{student.faculty.facultyNameInternational}</td>
			</tr>
			<tr>
				<td class="label">Gender:</td>
				<td class="value">{student.gender}</td>
			</tr>
			</tbody>
			{#if student.type === 'INTERNATIONAL'}
				<thead>
				<tr>
					<th colspan="2" class="section-header">International Student Info</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td class="label">Gender:</td>
					<td class="value">{student.gender}</td>
				</tr>
				<tr>
					<td class="label">Home university:</td>
					<td class="value">{student.homeUniversity}</td>
				</tr>
				<tr>
					<td class="label">Country:</td>
					<td class="value">{student.countryName}</td>
				</tr>
				</tbody>
			{/if}
		</table>
	</div>

	<div class="image-column">
		<ProfilePicture
			profilePicture={student.profilePicture}
			genderIcon={student.genderIcon}
			countryFlag={student.countryFlag} />
		{#if student.type === 'INTERNATIONAL'}
			<Bio bio={student.bio}></Bio>
		{/if}
	</div>
</div>

<style lang="scss">
  .page-background {
    width: 100%;
    background-color: var(--background);
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: flex-start;
  }

  .paper-sheet {
    background-color: var(--surface);
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    overflow: hidden;
  }

  .sheet-header {
    border-bottom: 2px solid var(--neutral-bg);
    padding: 1.5rem;
    text-align: center;
  }

  .sheet-header h2 {
    margin: 0;
    font-size: 2rem;
    color: var(--on-surface);
    font-weight: 700;
  }

  .sheet-content {
    padding: 2rem 12%;
  }

  .split-layout {
		padding: 2rem 10%;
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
		flex-direction: column;
    align-items: center;
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
    width: 30%;
    padding-right: 1.5rem;
    vertical-align: top;
  }

  .value {
    line-height: 1.4;
  }


  /* --- Responsive (Mobile) --- */
  @media (max-width: 850px) {
    .split-layout {
      flex-direction: column;
      gap: 2rem;
    }

    /* Move Image to top on Mobile */
    .image-column {
      order: -1;
      width: 100%;
      justify-content: center;
    }

    .image-wrapper {
      max-width: 260px; /* Slightly smaller on phone */
    }

    .details-table td {
      padding: 0.5rem 0;
    }
  }
</style>
