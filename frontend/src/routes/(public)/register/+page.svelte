<script lang="ts">
    import Card, { Content, Actions } from "@smui/card";
    import Button, { Label } from "@smui/button";
	import { goto } from "$app/navigation";
</script>

<div class="card-container">
    <Card variant="outlined" padded>
        <Content>
            <h2>Register as</h2>
        </Content>
        <Actions>
            <Button onclick={() => goto('/register/international')} variant="unelevated">
                <Label>International student</Label>
            </Button>
            <Button onclick={() => goto('/register/local')} variant="unelevated">
                <Label>Member/Buddy</Label>
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