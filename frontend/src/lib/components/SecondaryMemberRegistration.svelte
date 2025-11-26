<script lang="ts">
  import Dialog, { Header, Title, Content, Actions } from '@smui/dialog';
  import IconButton from '@smui/icon-button';
  import Button, { Label } from '@smui/button';
  import Select, { Option } from "@smui/select";
  import Checkbox from "@smui/checkbox";
  import FormField from "@smui/form-field";
	import Textfield from '@smui/textfield';
	import type { FacultyResponse, SettingsServiceApi, UserManagementApi } from '$lib/api';
	import { onMount } from 'svelte';

  let { 
    open = false,
    settingsClient,
    userManagementClient
  }: {
    open: boolean;
    settingsClient: SettingsServiceApi;
    userManagementClient: UserManagementApi;
  } = $props();

   const formInitial = {
    details: '',
    facultyId: '',
    marketingOptIn: false,
  }

  let response = $state('Nothing yet.');
  let facultyList = $state<FacultyResponse[]>([]);
  let form = $state(formInitial);
  let errorMessage = $state<string | null>(null);
  let loading = $state(false);

  onMount(async () => {
    await loadFaculties();
  });

  async function loadFaculties() {
    facultyList = await settingsClient.getAllFaculties();
  }

  function closeHandler(e: CustomEvent<{ action: string }>) {
      switch (e.detail.action) {
          case 'close':
          response = 'Closed without response.';
          break;
          case 'submit':
            submit(new SubmitEvent('submit', { cancelable: true, bubbles: true }) as any);
          response = 'Submitted.';
          break;
      }
  }

  async function submit(event: SubmitEvent & { currentTarget: EventTarget & HTMLFormElement}) {
    event.preventDefault();

    // 1) Basic validation
    errorMessage = null;
    if (!form.facultyId) {
      errorMessage = "Faculty is required.";
      return;
    }

    // 2) Submit the form
    try {
      loading = true;

      console.log('Submitting secondary member registration:', form);
      open = false;

    } catch (error: any) {
      console.error("Failed to register:", error?.message);
      errorMessage = error?.message || 'An unknown error occurred.';
    } finally {
      loading = false;
    }
  }
</script>

<Dialog
  bind:open
  fullscreen
  aria-labelledby="fullscreen-title"
  aria-describedby="fullscreen-content"
  onSMUIDialogClosed={closeHandler}
>
  <Header>
    <Title id="fullscreen-title">Additional information needs to be provided</Title>
    <IconButton action="close" class="material-icons">close</IconButton>
  </Header>
  <Content id="fullscreen-content">
    <form onsubmit={submit}>
      <section>
        <Select bind:value={form.facultyId} label="Faculty" required>
          {#each facultyList as faculty}
            <Option value={faculty.id}>{faculty.shortName}</Option>
          {/each}
        </Select>
      </section>

      <FormField>
        <Checkbox bind:checked={form.marketingOptIn} />
          {#snippet label()}
            I agree to receive marketing communications.
          {/snippet}
      </FormField>

    </form>
    {#if loading}
      <p>Submitting your registration...</p>
    {/if}
    {#if errorMessage}
      <p style="color: red;">{errorMessage}</p>
    {/if}
  </Content>
  <Actions>
    <Button action="close">
      <Label>Close</Label>
    </Button>
    <Button action="submit" defaultAction>
      <Label>Submit</Label>
    </Button>
  </Actions>
</Dialog>