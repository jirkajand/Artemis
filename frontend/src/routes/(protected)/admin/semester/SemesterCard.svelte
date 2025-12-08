<script lang="ts">
	import type { SemesterResponse } from "$lib/api";
	import Button, { Icon, Label } from "@smui/button";
	import Card, { ActionButtons, ActionIcons, Actions, Content, Media, MediaContent, PrimaryAction } from "@smui/card";
	import IconButton from "@smui/icon-button";


    let { semester }: { semester: SemesterResponse } = $props();
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
            <IconButton>
                <Icon class="material-icons" >edit</Icon>
            </IconButton>
            <IconButton style="color: var(--error)">
                <Icon class="material-icons" >delete</Icon>
            </IconButton>
        </ActionIcons>
    </Actions>
</Card>

<style>
    .label {
        color: color-mix(in srgb, currentColor 60%, transparent);
    }
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

	.year {
		font-size: 0.9rem;
		opacity: 0.7;
	}

	.details {
		display: grid;
		grid-template-columns: 1fr 1fr;
		gap: 1rem;
	}

	label {
		font-size: 0.75rem;
		text-transform: uppercase;
		letter-spacing: 0.5px;
		opacity: 0.7;
	}

	p {
		margin: 0.15rem 0 0;
		font-size: 0.95rem;
	}
</style>