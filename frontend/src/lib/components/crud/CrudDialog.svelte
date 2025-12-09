<script lang="ts">
  import Dialog, { Header, Title, Content, Actions } from '@smui/dialog';
  import IconButton from '@smui/icon-button';
  import Button, { Label } from '@smui/button';
	import CircularProgress from '@smui/circular-progress';

  let { 
    open = $bindable<boolean>(),
    title,
    onSubmit,
    loading = false,
    errorMessage = null,
    children
  } = $props();

</script>

<Dialog
  bind:open
  fullscreen
  aria-labelledby="fullscreen-title"
  aria-describedby="fullscreen-content"
>
  <Header>
    <Title id="fullscreen-title">{title}</Title>
    <IconButton action="close" class="material-icons">close</IconButton>
  </Header>
  <Content id="fullscreen-content">
    {@render children() }
    {#if loading}
        <CircularProgress style="height: 32px; width: 32px;" indeterminate />
    {/if}
    {#if errorMessage}
        <p style="color: var(--error);">{errorMessage}</p>
    {/if}
  </Content>
  <Actions>
    <Button action="close">
      <Label>Close</Label>
    </Button>
    <Button action="" onclick={() => onSubmit()} disabled={loading}>
      <Label>Submit</Label>
    </Button>
  </Actions>
</Dialog>