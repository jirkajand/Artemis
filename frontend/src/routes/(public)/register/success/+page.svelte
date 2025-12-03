<script lang="ts">
    import Card, { Content, Actions } from "@smui/card";
    import Button, { Label } from "@smui/button";
	import { goto } from "$app/navigation";
</script>


<div class="card-container">
    <Card variant="outlined" padded>
        <Content>
            <h2>Registration Successful!</h2>
            <p>Wait for the email with account verification instructions.</p>
        </Content>
        <Actions>
            <Button onclick={() => goto('/login')}>
                <Label>Go to Login</Label>
            </Button>
        </Actions>
    </Card>
</div>

<style>
    .card-container {
        display: flex;
        justify-content: center;
        align-items: center;

        width: 100%;
        height: 80vh;
    }
</style>
