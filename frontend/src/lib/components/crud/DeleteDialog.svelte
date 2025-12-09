<script lang="ts">
    import Dialog, { Header, Title, Content, Actions } from '@smui/dialog';
    import IconButton from '@smui/icon-button';
    import Button, { Label } from '@smui/button';

    let { 
        open = $bindable<boolean>(),
        title,
        message,
        onSubmit,
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
        <p>{message}</p>
    </Content>
    <Actions>
        <Button action="close">
            <Label>Close</Label>
        </Button>
        <Button action="" style="color: var(--error)" onclick={() => onSubmit()}>
            <Label>Delete</Label>
        </Button>
    </Actions>
</Dialog>