<script lang="ts">
    import TopAppBar, {Row, Section, Title} from "@smui/top-app-bar";
    import StudentCard from "$lib/components/StudentCard.svelte";
    import Select, {Option} from "@smui/select";
    import Icon from "@smui/select/icon";
    import { Label } from '@smui/common';
    import type { PageProps } from "./$types";
    import AnonymisedStudentCard from '$lib/components/AnonymisedStudentCard.svelte'

    let { data } = $props();
    let { students } = data;
    console.log(students);
     const cards = Array(20).fill(0);
    let dashboardPage = ['Buddy Matching', 'Manage my mentees'];
    let selected = $state(dashboardPage[0]);
    // Select state
    const semesters = ['2023/2024', '2024/2025', '2025/2026'];
    const faculties = ["FIM", "PDF", "PřF"]
    import SegmentedButton, { Segment } from '@smui/segmented-button';
    let semesterSelectValue = $state('')
    let facultySelectValue =  $state('')
</script>

<div class="dashboard-container">
    <!-- Top App Bar -->
    <TopAppBar variant="static">
        <Row>
            <Section>
                <Title>Artemis dashboard</Title>
            </Section>
            <Section class="segment-section">
                <SegmentedButton segments={dashboardPage} singleSelect bind:selected>
                    {#snippet segment(segment)}
                        <Segment {segment}>
                            <Label>{segment}</Label>
                        </Segment>
                    {/snippet}
                </SegmentedButton>
            </Section>
            <Section>
                <div class="controls">
                    <Select
                            variant="outlined"
                            bind:value={semesterSelectValue}
                            label="Semester"
                    >
                        {#snippet leadingIcon()}
                            <Icon class="material-icons">event</Icon>
                        {/snippet}
                        <Option value=""/>
                        {#each semesters as semester}
                            <Option value={semester}>{semester}</Option>
                        {/each}
                    </Select>
                    <Select
                            variant="outlined"
                            bind:value={facultySelectValue}
                            label="Destination Faculty"
                    >
                        {#snippet leadingIcon()}
                            <Icon class="material-icons">school</Icon>
                        {/snippet}
                        <Option value=""/>
                        {#each faculties as faculty}
                            <Option value={faculty}>{faculty}</Option>
                        {/each}
                    </Select>
                </div>
            </Section>

        </Row>
    </TopAppBar>

    <!-- Custom Grid -->
    <div class="card-grid">
        {#each cards as _, i (i)}
            <AnonymisedStudentCard/>
        {/each}
    </div>
</div>

<style>
    .controls {
        display: flex;
        gap: 1rem;
        align-items: center;
        margin: 1rem 0;
        flex: 1;
        justify-content: flex-end;
    }

    :global(.mdc-top-app-bar) {
        border-radius: 10px;
    }

    :global(.mdc-top-app-bar__section) {
        padding: 0.5rem 1rem;
        flex: 1;
    }
    :global(.mdc-top-app-bar__title){
        font-size: 1.75rem;
    }


    :global(.segment-section) {
        justify-content: center;
    }

    :global(.mdc-segmented-button__segment--selected) {
        background-color: var(--mdc-theme-on-primary) !important;
    }


    .card-grid {
        display: grid;
        gap: 1rem;
        padding: 2rem 0;
        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    }


    @media(min-width: 1280px) {
        .card-grid {
            padding: 2.5rem;
            gap: 2rem;
        }
    }

    @keyframes subtle-pulse {
        0% {
            transform: scale(1.005);
            box-shadow: 0 0 4px rgba(0,0,0,0.08);
        }
        50% {
            transform: scale(1.03);
            box-shadow: 0 10px 16px rgba(0,0,0,0.16);
        }
        100% {
            transform: scale(1);
            box-shadow: 0 0 4px rgba(0,0,0,0.08);
        }
    }



    :global(.mdc-segmented-button) {
        display: flex;
        justify-content: center;
        align-items: center;
        border-radius: 12px;
        overflow: visible;
        padding: 4px;
        gap: 0.5rem;
    }


    :global(.mdc-segmented-button__segment) {
        border-radius: 15px !important;
        transition:
                background-color 0.18s ease,
                transform 0.18s ease,
                box-shadow 0.18s ease;
        padding: 0.75rem 1.4rem;
        font-size: 1rem;
    }

    :global(.mdc-segmented-button__segment--selected) {
        background: var(--mdc-theme-on-primary) !important;
        color: white;
        transform: scale(1.08);
        animation: subtle-pulse 2s ease-in-out infinite;
        box-shadow: 0 2px 8px rgba(0,0,0,0.12);
        border-color: transparent !important;
        padding: 1.45rem 1.4rem !important;
        z-index: 10;
    }

    :global(.mdc-segmented-button__label) {
        font-weight: 500;
    }




</style>
