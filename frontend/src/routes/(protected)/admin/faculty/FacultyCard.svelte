<script lang="ts">
	import type { FacultyResponse } from "$lib/api";
	import { Icon } from "@smui/button";
	import Card, { ActionIcons, Actions, Content } from "@smui/card";
	import IconButton from "@smui/icon-button";

    let { 
		faculty,
		onedit,
		ondelete
	}: {
		faculty: FacultyResponse,
		onedit: () => void,
		ondelete: () => void
	} = $props();

	function getRGBFromCSSColor(color: string) {
		const canvas = document.createElement("canvas");
		canvas.width = canvas.height = 1;
		const ctx = canvas.getContext("2d");
		if (!ctx) return { r: 0, g: 0, b: 0 };

		// Fill the canvas with the color
		ctx.fillStyle = color;
		ctx.fillRect(0, 0, 1, 1);

		// Read the resulting RGB
		const [r, g, b] = ctx.getImageData(0, 0, 1, 1).data;
		return { r, g, b };
	}

	function getContrastColor(color: string | undefined) {
		if (!color) return "black";
		const { r, g, b } = getRGBFromCSSColor(color);

		// luminance formula
		const luminance = (0.299*r + 0.587*g + 0.114*b) / 255;
		return luminance > 0.5 ? "black" : "white";
	}
</script>

<Card >
    <Content >
        <div class="semester-card">
            <div class="header">
                <h3>{faculty.facultyNameInternational} ({faculty.shortName})</h3>
            </div>

            <div class="details">
                <span class="label">Local Name</span>
                <p>{faculty.facultyNameLocal}</p>
                
                <span class="label">Color</span>
                <p 
					style="padding: 0.2rem;
					border-radius: 0.2rem; background: {faculty.color};
					color: {getContrastColor(faculty.color)}"
				>{faculty.color}</p>
            </div>
        </div>
    </Content>
    <Actions>
        <ActionIcons>
            <IconButton onclick={onedit}>
                <Icon class="material-icons" >edit</Icon>
            </IconButton>
            <IconButton style="color: var(--error)" onclick={ondelete}>
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