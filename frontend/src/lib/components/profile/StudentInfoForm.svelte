<script lang="ts">
	let { editableStudent = $bindable(), faculties, editing, saveChanges } = $props();
	let dateInput = editableStudent.dateOfBirth
		? new Date(editableStudent.dateOfBirth).toISOString().split('T')[0]
		: '';


	function onDateChange(e: Event) {
		const target = e.target as HTMLInputElement;
		editableStudent.dateOfBirth = target.value
			? new Date(target.value)
			: null;
	}
</script>

<form onsubmit={(e) => { e.preventDefault(); saveChanges() }}>
	<table class="details-table">
		<thead>
		<tr>
			<th colspan="2" class="section-header">Personal Information</th>
		</tr>
		</thead>

		<tbody>
		<tr>
			<td class="label">First name:</td>
			<td class="value">
				<input type="text" bind:value={editableStudent.firstName} disabled={!editing} />
			</td>
		</tr>
		<tr>
			<td class="label">Last name:</td>
			<td class="value">
				<input type="text" bind:value={editableStudent.lastName} disabled={!editing} />
			</td>
		</tr>
		<tr>
			<td class="label">Born:</td>
			<td class="value">
				<input
					type="date"
					bind:value={dateInput}
					oninput={onDateChange}
					disabled={!editing}
				/>
			</td>
		</tr>
		<tr>
			<td class="label">Email:</td>
			<td class="value">
				<input type="email" required bind:value={editableStudent.email} disabled={!editing} />
			</td>
		</tr>
		<tr>
			<td class="label">Phone:</td>
			<td class="value">
				<input type="tel" bind:value={editableStudent.phoneNumber} disabled={!editing}/>
			</td>
		</tr>
		<tr>
			<td class="label">Gender:</td>
			<td class="value">
				<select bind:value={editableStudent.gender} disabled={!editing}>
					<option value="MALE">Male</option>
					<option value="FEMALE">Female</option>
					<option value="OTHER">Other</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="label">Faculty:</td>
			<td class="value">
				<select bind:value={editableStudent.facultyId} disabled={!editing}>
					{#each faculties as faculty}
						<option value={faculty.id}>{faculty.facultyNameInternational}</option>
					{/each}
				</select>
			</td>
		</tr>
		</tbody>

		{#if editableStudent.type === 'INTERNATIONAL'}
			<thead>
			<tr>
				<th colspan="2" class="section-header">International Student Info</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td class="label">Home university:</td>
				<td class="value">
					<input type="text" bind:value={editableStudent.homeUniversity} disabled={!editing} />
				</td>
			</tr>
			<tr>
				<td class="label">Country:</td>
				<td class="value">
					<input type="text" bind:value={editableStudent.countryName} disabled />
				</td>
			</tr>
			<tr>
				<td class="label">Buddy contact:</td>
				<td class="value">
					<input type="text" bind:value={editableStudent.buddyInfo} disabled />
				</td>
			</tr>
			</tbody>
		{/if}
	</table>
	<input type="submit" style="display:none" disabled={!editing} />
</form>

<style lang="scss">
  .details-table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 1rem;

    td {
      padding: 0.05rem 0;
      border-bottom: 2.5px dotted var(--neutral-bg);
      vertical-align: middle;
    }

    .label {
      font-weight: 600;
      width: 30%;
      padding-right: 1rem;
      color: var(--on-surface);
    }

    .value {
      width: 70%;
    }
  }

  .section-header {
    font-size: 1rem;
    font-weight: 700;
    text-transform: uppercase;
    letter-spacing: 0.05em;
    border-bottom: 2px solid var(--neutral-bg);
    padding: 0.5rem 0;
    color: var(--on-surface);
    text-align: center;
  }

  input:disabled,
  select:disabled {
    background-color: transparent;
    border: 1px solid transparent;
    color: var(--on-surface, #333);
    cursor: default;
    opacity: 1;
  }

  input, select {
    width: 100%;
    padding: 0.5rem 0.75rem;
    font-size: calc(0.5vw + 0.5rem);
    border-radius: 6px;
    border: 1px solid #ccc;
    background-color: var(--surface);
    color: var(--on-surface);
    transition: all 0.2s ease-in-out;
    box-sizing: border-box;

    &::placeholder {
      color: var(--on-surface, #aaa);
      opacity: 0.6;
    }

    &:hover:not(:disabled) {
      border-color: var(--primary);
    }

    &:focus:not(:disabled) {
      border-color: var(--primary);
      box-shadow: 0 0 0 3px rgba(var(--primary-rgb, 0, 174, 239), 0.15);
      outline: none;
    }
  }
</style>
