<script lang="ts">
    import Card, { Content, PrimaryAction, Media, Actions, ActionButtons } from '@smui/card';
    import Button, { Label } from '@smui/button';
    import Badge from '@smui-extra/badge';
    import type { PageProps } from '../../../.svelte-kit/types/src/routes/$types';
    import Icon from '@smui/select/icon';

    let { data }: PageProps = $props();

    const openCard = () => console.log('Student card opened');
</script>

<div class="card-display">
    <Card class="custom-card mdc-elevation--z12">
        <!-- Badges -->
        <div class="badge-container">
            <Badge class="gender-badge" position="inset" align="top-start">♂️</Badge>
            <Badge class="country-badge" position="inset" align="top-end">🏴‍☠️</Badge>
        </div>

        <!-- Main content -->
        <PrimaryAction on:click={openCard}>
            <Media
                    class="card-media"
                    aspectRatio="16x9"
                    style="background-image: url('https://i.pinimg.com/736x/6f/36/1f/6f361f08d9837f91fecbd336cd311bce.jpg');"
            />
            <Content class="card-content">
                <div class="line">
                    <Icon class="material-icons">person</Icon>
                    <h2 class="card-title">Really long student name</h2>
                </div>

                <div class="line">
                    <Icon class="material-icons">school</Icon>
                    <h3 class="card-subtitle">Faculty</h3>
                </div>

                <div class="line">
                    <Icon class="material-icons">info</Icon>
                    <div class="bio-wrapper">
                        <p class="card-bio">Some bio information, hobbies...</p>
                    </div>
                </div>
            </Content>
        </PrimaryAction>

        <!-- Actions -->
        <Actions class="actions-flex">
            <ActionButtons>
                <Button variant="raised">
                    <Label>Assign student</Label>
                </Button>
            </ActionButtons>
        </Actions>
    </Card>
</div>

<style>
    .card-display {
        max-width: 400px;
        margin: 1rem auto;
        position: relative;
    }

    /* Card styling */
    :global(.custom-card) {
        position: relative;
        border-radius: 16px;
        overflow: hidden;
        background-color: var(--mdc-theme-surface);
        border: 1px solid var(--mdc-theme-outline, #e0e0e0);
        transition: transform 0.25s ease, box-shadow 0.25s ease;
        z-index: 0;
    }

    :global(.custom-card:hover) {
        transform: translateY(-6px) scale(1.02);
        box-shadow: 0 18px 35px rgba(0, 0, 0, 0.12);
    }

    /* Badges */
    :global(.smui-badge) {
        z-index: 5;
        font-size: clamp(20px, 2vw, 28px);
        padding: 0.4em 0.6em;
        margin: 0.5vw;
        background-color: rgba(255, 255, 255, 0.9) !important;
        border-radius: 50%;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        color: var(--mdc-theme-on-surface);
    }

    :global(.country-badge) {
        font-size: clamp(22px, 2.2vw, 30px);
    }

    /* Card media */
    :global(.card-media) {
        height: 200px;
        object-fit: cover;
    }

    /* Content styling */
    .card-content {
        display: flex;
        flex-direction: column;
        align-items: center;
        text-align: center;
        color: var(--mdc-theme-on-surface);
        padding: 1rem;
        width: 100%;
        padding: 0.5rem 1rem;
    }

    .line {
        display: flex;
        border-bottom: 1px solid black;
        align-items: center;
        padding: 0.5rem 0;
        width: 100%;
        position: relative; /* needed for absolute positioning of icon */
    }

    .line Icon {
        position: absolute;
        left: 0;          /* stick to the left */
        flex-shrink: 0;   /* prevent shrinking */
    }
    .line h2,
    .line h3,
    .line h4,
    .line p {
        margin: 0 auto;   /* centers the text */
        text-align: center;
    }

    .card-title {
        margin: 0;
        font-weight: 700;
        color: var(--mdc-theme-on-surface);
    }

    .card-subtitle {
        margin: 0;
        font-weight: 500;
        color: var(--mdc-theme-on-surface);
    }

    .card-bio {
        /* Existing styles... */
        font-size: 0.875rem;
        line-height: 1.4;
        color: var(--mdc-theme-on-surface);
        margin: 0;
        max-width: 90%;
        overflow: hidden;
        display: -webkit-box; /* Required for multi-line ellipsis */
        -webkit-line-clamp: 4; /* Limit to 3 lines */
        -webkit-box-orient: vertical;
    }

    /* Actions */
    :global(.actions-flex) {
        display: flex;
        justify-content: center;
        padding: 0.75rem 0 1rem;
    }

    .bio-wrapper {
        display: flex;
        align-items: center;    /* vertical center */
        justify-content: center;/* optional: horizontal center */
        min-height: 5rem;
        flex: 1;
    }
</style>
