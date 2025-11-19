<script lang="ts">
    import TopAppBar, {Row, Section, Title} from "@smui/top-app-bar";
    import LayoutGrid, {Cell} from "@smui/layout-grid";
    import StudentCard from "$lib/components/StudentCard.svelte";
    import Select, {Option} from "@smui/select";
    import Tab, {Label} from '@smui/tab';
    import Icon from "@smui/select/icon";

    let {data} = $props();


    const cards = Array(20).fill(0);

    // Select state
    const semesters = ['2023/2024', '2024/2025', '2025/2026'];
    const faculties = ["FIM", "PDF", "PřF"]
    let semesterSelectValue = $state('')
    let facultySelectValue =  $state('')
</script>

<div class="dashboard-container">
    <!-- Top App Bar -->
    <TopAppBar variant="static">
        <Row>
            <Section>
                <Title>Dashboard</Title>
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

    <LayoutGrid class="wrap-grid">
        {#each cards as _, i (i)}
            <Cell span={3} align="middle">
                <StudentCard/>
            </Cell>
        {/each}
    </LayoutGrid>
</div>

<style>
    .dashboard-container {
        padding: 1rem;
        font-size: 16px;
    }

    .controls {
        display: flex;
        gap: 1rem;
        align-items: center;
        margin: 1rem 0;
        flex: 1;
        justify-content: flex-end;
    }

    /* StudentCard sizing */
    :global(.card-display) {
        max-width: 300px;
        width: 100%;
        margin: 0 auto;
    }

    /* TopAppBar styling */
    :global(.mdc-top-app-bar__section) {
        padding: 0.5rem 1rem;
    }
</style>
