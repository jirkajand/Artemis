<script lang="ts">
	import type { SemesterResponse } from "$lib/api";
	import { Icon } from "@smui/button";
	import Card, { ActionIcons, Actions, Content } from "@smui/card";
	import IconButton from "@smui/icon-button";


    let { 
		semester,
		onedit,
		ondelete
	}: {
		semester: SemesterResponse,
		onedit: () => void,
		ondelete: () => void
	} = $props();
</script>

<Card >
    <Content >
        <div class="semester-card">
            <div class="header">
                <h2>{semester.semesterName}</h2>
            </div>

            <div class="details">
                <span class="label">Year</span>
                <p>{semester.year}</p>
                
                <span class="label">Type</span>
                <p>{semester.semesterType}</p>

                <span class="label">Open Date</span>
                <p>{semester.semesterRegisterOpenDate}</p>
            </div>
        </div>
    </Content>
    <Actions>
        <ActionIcons>
            <IconButton onclick={onedit}>
                <Icon class="material-icons" >edit</Icon>
            </IconButton>
            <IconButton style="color: var(--error)" onclick={ondelete} disabled={true}>
                <Icon class="material-icons" >delete</Icon>
            </IconButton>
        </ActionIcons>
    </Actions>
</Card>

<style>
    .semester-card {
		display: flex;
		flex-direction: column;
		gap: 0.8rem;
	}

	.header {
		display: flex;
		justify-content: space-between;
		align-items: baseline;
	}

	h2 {
		margin: 0;
		font-size: 1.3rem;
		font-weight: 600;
	}

	.details {
		display: grid;
		grid-template-columns: 1fr 1fr;
		gap: 1rem;
	}

	.label {
		font-size: 0.75rem;
		text-transform: uppercase;
		letter-spacing: 0.5px;
        color: color-mix(in srgb, currentColor 70%, transparent);
	}

	p {
		margin: 0.15rem 0 0;
		font-size: 0.95rem;
	}
</style>