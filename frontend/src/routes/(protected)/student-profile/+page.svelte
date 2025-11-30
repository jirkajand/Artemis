<script lang="ts">
	import TopAppBar, { Row, Section, Title } from '@smui/top-app-bar';
	import IconButton from '@smui/icon-button';

	let { data } = $props();
	let { student } = data;
</script>
<div class="page-background">
	<div class="paper-sheet">

		<div class="sheet-header">
			<h2>Student Details</h2>
		</div>

		<div class="sheet-content">

			<div class="split-layout">

				<div class="data-column">
					<table class="details-table">
						<thead>
						<tr><th colspan="2" class="section-header">Personal Information</th></tr>
						</thead>
						<tbody>
						<tr>
							<td class="label">Name:</td>
							<td class="value">{student.firstName} {student.lastName}</td>
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
							<td class="label">Country:</td>
							<td class="value">{student.countryName}</td>
						</tr>
						</tbody>

						<tbody class="spacer"><tr><td colspan="2"></td></tr></tbody>

						<thead>
						<tr><th colspan="2" class="section-header">Academic Information</th></tr>
						</thead>
						<tbody>
						<tr>
							<td class="label">Destination Faculty:</td>
							<td class="value">{student.faculty?.facultyNameInternational ?? '—'}</td>
						</tr>
						<tr>
							<td class="label">Home Faculty:</td>
							<td class="value">{student.homeUniversity}</td>
						</tr>
						<tr>
							<td class="label">Born:</td>
							<td class="value">{new Date(student.dateOfBirth).toLocaleDateString()}</td>
						</tr>
						</tbody>
					</table>
				</div>

				<div class="image-column">
					<div class="image-wrapper">
						<img
							class="student-photo"
							src="https://preview.redd.it/pc9b705en1r91.jpg?width=640&crop=smart&auto=webp&s=4d27efd62c32e9ba94e9a522bc7d0d11ad3cf2c6"
							alt="Student"
						/>
						<div class="badges-overlay">
              <span class="badge gender-badge" title="Gender">
                {student.genderIcon}
              </span>
							<span class="badge country-badge" title="Country">
                {student.countryFlag}
              </span>
						</div>
					</div>
				</div>
			</div>

			{#if student.bio}
				<div class="bio-section">
					<h4 class="section-header">About Me</h4>
					<p class="bio-text">{student.bio}</p>
				</div>
			{/if}

		</div>
	</div>
</div>

<style lang="scss">

	:global(main){
		max-width: unset !important;
		padding: 0 !important;
	}

  /* --- Navigation --- */
  .nav-wrapper {
    position: relative;
    z-index: 10;
  }

  :global(.profile-top-bar) {
    background-color: var(--on-background);
    color: #333 !important;
    box-shadow: 0 1px 3px rgba(0,0,0,0.05) !important;
  }

  /* --- Page Structure --- */
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
		height: 100%;
		align-content: flex-start;
    padding: calc(1rem + 2vw) calc(2rem + 5vw);
  }

  /* --- Layout Split (Data Left, Image Right) --- */
  .split-layout {
		height: 100%;
    display: flex;
    gap: 3rem;
    margin-bottom: 2rem;
  }

  .data-column {
    flex: 1.5; /* Takes up slightly more space for text */
  }

  .image-column {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: flex-start; /* Align image with top of table */
  }

  /* --- Table Styling (Strictly Organized) --- */
  .details-table {
    width: 100%;
    border-collapse: collapse;
  }

  .section-header {
    font-size: 1.25rem;
    text-transform: uppercase;
    border-bottom: 2px solid #eee;
    padding-bottom: 0.5rem;
    text-align: center;
    padding-top: 0.5rem;
  }

  /* Remove top padding for the very first header */
  thead:first-child .section-header {
    padding-top: 0;
  }

  /* Spacer row logic */
  .spacer td {
    height: 1.5rem;
    border: none;
  }

  .details-table td {
    padding: 0.6rem 0;
    border-bottom: 2px dotted var(--on-background);
    font-size: 1.1rem;
  }

  /* Remove border from last row in a section */
  .details-table tbody tr:last-child td {
    border-bottom: none;
  }

  /* Remove border from last row in a section */
  .details-table tbody td:first-child {
    width: 30%;
  }

  .label {
    font-weight: 700;
    white-space: nowrap;
    width: 1%; /* Shrink to fit content */
    padding-right: 1.5rem;
    vertical-align: top;
  }

  .value {
    line-height: 1.4;
  }

  /* --- Image & Badges --- */
  .image-wrapper {
    position: relative;
    width: 100%;
    max-width: 350px;
    display: inline-block;
  }

  .student-photo {
    width: 100%;
    height: auto;
    border-radius: 12px;
    display: block;
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
  }

  .badges-overlay {
    position: absolute;
    inset: 0;
    display: flex;
    justify-content: space-between;
    align-items: flex-end; /* Push badges to bottom */
    pointer-events: none;
  }

  .badge {
    background: white;
    width: 64px;
    height: 64px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 2rem;
    box-shadow: 0 4px 10px rgba(0,0,0,0.15);
    border: 1px solid rgba(0,0,0,0.05);
    pointer-events: auto;
    transition: transform 0.2s ease;
  }

  .badge:hover {
    transform: translateY(-3px) scale(1.05);
  }

  .gender-badge {
    border-top-right-radius: 50%;
    border-bottom-left-radius: 12px;
  }

  .country-badge {
    border-top-left-radius: 50%;
    border-bottom-right-radius: 12px;
  }

  /* --- Bio Section --- */
  .bio-section {
    margin-top: 1rem;
    display: flex;
    flex-direction: column;
  }

  .bio-text {
    text-align: center;
    font-style: italic;
    color: #555;
    line-height: 1.6;
    margin-top: 1rem;
    padding: 0 1rem;
  }

  /* --- Responsive (Mobile) --- */
  @media (max-width: 850px) {
    .sheet-content {
      padding: 1rem;
    }

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