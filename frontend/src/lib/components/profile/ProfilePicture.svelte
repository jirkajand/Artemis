<script lang="ts">
	let {profilePicture, genderIcon, countryFlag } = $props()
	const displayPicture = profilePicture ?? 'https://preview.redd.it/pc9b705en1r91.jpg?width=640&crop=smart&auto=webp&s=4d27efd62c32e9ba94e9a522bc7d0d11ad3cf2c6';
</script>

<div class="image-wrapper">
	<img src={displayPicture} class="student-photo" alt="Student" />
	<div class="badges-overlay">
		<span class="badge gender-badge" title="Gender">{genderIcon}</span>
		<span class="badge country-badge" title="Country">{countryFlag}</span>
	</div>
</div>

<style lang="scss">
  .image-wrapper {
    position: relative;
    width: 100%;
    max-width: 350px;
  }

  .student-photo {
    width: 100%;
    border-radius: 12px;
    display: block;
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
  }

  .badges-overlay {
    position: absolute;
    inset: 0;
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
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
  }

  .gender-badge {
    border-top-right-radius: 50%;
    border-bottom-left-radius: 12px;
  }

  .country-badge {
    border-top-left-radius: 50%;
    border-bottom-right-radius: 12px;
  }
</style>
