
<script lang="ts">
  import Select, { Option } from "@smui/select";
  import Checkbox from "@smui/checkbox";
  import FormField from "@smui/form-field";
	import Textfield from '@smui/textfield';
	import type { FacultyResponse, SettingsServiceApi, CompleteInternationalStudentProfileRequest } from '$lib/api';
	import FileInput from '$lib/components/FileInput.svelte';
	import { onMount } from 'svelte';

  const formInitial: Omit<CompleteInternationalStudentProfileRequest, 'profilePicture'> & {
    profilePicture: CompleteInternationalStudentProfileRequest['profilePicture'] | null
  } = {
    internationalStudentId: '',
    facultyId: '',
    description: '',
    emailMarketingChecked: false,
    profilePicture: null,
    homeUniversity: '',
    accommodation: ''
  };

  let { 
    settingsClient,
    form = $bindable()
  }: {
    settingsClient: SettingsServiceApi;
    form: CompleteInternationalStudentProfileRequest | undefined;
  } = $props();

  let unprocessedForm = $state(formInitial);
  let facultyList = $state<FacultyResponse[]>([]);
  let profilePictureUrl = $derived(unprocessedForm.profilePicture ? URL.createObjectURL(unprocessedForm.profilePicture) : null);

  $effect(() => {
    // Update the bound form when unprocessedForm changes
    if (unprocessedForm.profilePicture !== null) {
      form = { 
        ...unprocessedForm,
        profilePicture: unprocessedForm.profilePicture
      };
    }
  });
  
  onMount(async () => {
    await loadFaculties();
  });

  async function loadFaculties() {
    facultyList = await settingsClient.getAllFaculties();
  }

</script>

<form>
  <section>
    <FileInput
      description={"Upload Profile Picture"}
      accept="image/*"
      multiple={false}
      change={(fl: FileList) => {unprocessedForm.profilePicture = fl[0]}}
    />
    {#if profilePictureUrl != null}
      <img src={profilePictureUrl} alt="Profile Preview" width="150" />
    {/if}
  </section>
  <Select bind:value={unprocessedForm.facultyId} label="Faculty" required>
      {#each facultyList as faculty}
        <Option value={faculty.id}>{faculty.shortName}</Option>
      {/each}
  </Select>
  <Textfield
    bind:value={unprocessedForm.homeUniversity}
    label="Home University"
  />
  <Textfield
    bind:value={unprocessedForm.accommodation}
    label="Accommodation"
  />
  <Textfield
    bind:value={unprocessedForm.description}
    label="Description"
    textarea
    input$rows={4}
    required
  />
  <FormField>
    <Checkbox bind:checked={unprocessedForm.emailMarketingChecked} />
      {#snippet label()}
        I agree to receive marketing communications.
      {/snippet}
  </FormField>
</form>

<style>
  img {
    margin-top: 1rem;
    border-radius: 8px;
  }
  form {
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
    max-width: 600px;
    margin: 0 auto;
    padding-top: 1rem;
  }
  form section {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 1rem;
  }
</style>