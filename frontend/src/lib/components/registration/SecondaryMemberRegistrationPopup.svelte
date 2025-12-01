<script lang="ts">
    import Dialog, { Header, Title, Content, Actions } from '@smui/dialog';
    import IconButton from '@smui/icon-button';
    import Button, { Label } from '@smui/button';
	import type { CompleteLocalStudentProfileRequest, ResponseStudentNavbar, SettingsServiceApi, UserManagementApi } from '$lib/api';
	import SecondaryMemberRegistration from './SecondaryMemberRegistration.svelte';

    let {
        settingsClient,
        userManagementClient,
        navbarData
    }: {
        settingsClient: SettingsServiceApi;
        userManagementClient: UserManagementApi;
        navbarData: ResponseStudentNavbar;
    } = $props();

    let open = $state(navbarData.hasSecondaryRegistrationDone === false);
    let errorMessage = $state<string | null>(null);
    let loading = $state(false);
    let form = $state<CompleteLocalStudentProfileRequest>();

    function closeHandler(e: CustomEvent<{ action: string }>) {
    }

    async function submit() { 
    errorMessage = null;

    // 1) Basic validation
    if(navbarData.id == undefined || navbarData.id === null) {
      errorMessage = "Unable to get user ID.";
      return;
    }
    if(!form) {
      errorMessage = "Profile picture is required.";
      return;
    }
    if (!form.profilePicture) {
      errorMessage = "Profile picture is required.";
      return;
    }
    if (!form.facultyId) {
      errorMessage = "Faculty is required.";
      return;
    }
    if (!form.description || form.description.trim().length === 0) {
      errorMessage = "Description is required.";
      return;
    }

    // 2) Submit the form
    try {
      loading = true;

      await userManagementClient.completeLocalStudentProfile({
        ...form,
        localStudentId: navbarData.id
      });

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
    <Title id="fullscreen-title">Provide additional information</Title>
    <IconButton action="close" class="material-icons">close</IconButton>
  </Header>
  <Content id="fullscreen-content">
    <SecondaryMemberRegistration
      {settingsClient}
      bind:form
    />
    {#if loading}
        <p>Submitting your registration...</p>
    {/if}
    {#if errorMessage}
        <p style="color: var(--error);">{errorMessage}</p>
    {/if}
  </Content>
  <Actions>
    <Button action="close">
      <Label>Close</Label>
    </Button>
    <Button action="" onclick={() => submit()} disabled={loading}>
      <Label>Submit</Label>
    </Button>
  </Actions>
</Dialog>