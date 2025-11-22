<script lang="ts">
    import TopAppBar, {Row, Section, Title} from "@smui/top-app-bar";
    import StudentCard from "$lib/components/StudentCard.svelte";
    import Select, {Option} from "@smui/select";
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

    <!-- Custom Grid -->
    <div class="card-grid">
        {#each cards as _, i (i)}
            <StudentCard/>
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

    :global(.mdc-top-app-bar__section) {
        padding: 0.5rem 1rem;
    }

    .card-grid {
        display: grid;
        gap: 1rem;
        padding: 2rem 0;
        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    }


    @media(min-width: 1280px) {
        .card-grid {
            padding: 3rem;
            gap: 2rem;
        }
    }




</style>
