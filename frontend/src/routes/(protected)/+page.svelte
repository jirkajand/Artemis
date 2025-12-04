<script lang="ts">
    import { keycloak } from '$lib/auth/keycloak';
    import Button, { Label } from '@smui/button';
    import Card, { Content } from '@smui/card';

    let { data } = $props();
    let { settingsHealth, managementHealth, studentDetail } = $derived(data);

    const formatKey = (key: string) => key.charAt(0).toUpperCase() + key.slice(1).replace(/([A-Z])/g, ' $1');
    const isUp = (status: string) => status?.toUpperCase() === 'UP';
</script>

<div class="dashboard-page">
    <!-- Welcome Banner -->
    <header class="welcome-banner">
        <div class="text-content">
            <h1>Hello, {studentDetail.firstName}</h1>
            <p>Welcome back to Artemis. Your system status is active and stable.</p>
        </div>
        <div class="actions">
            <Button variant="raised" class="logout-btn" on:click={() => keycloak.logout()}>
                <Label>Logout</Label>
            </Button>
        </div>
    </header>

    <h2 class="section-title">System Health & Services</h2>
    <div class="services-grid">
        <Card class="service-card">
            <Content>
                <div class="card-header">
                    <h3>User Management</h3>
                    <span class="status-badge" class:online={isUp(managementHealth?.status)}>
                        {managementHealth?.status || 'UNKNOWN'}
                    </span>
                </div>
                <div class="card-body">
                    {#if managementHealth}
                        {#each Object.entries(managementHealth) as [key, value]}
                            {#if key !== 'status'}
                                <div class="detail-row">
                                    <span class="key">{formatKey(key)}</span>
                                    <span class="value">{String(value)}</span>
                                </div>
                            {/if}
                        {/each}
                    {:else}
                        <p class="no-data">No health data available</p>
                    {/if}
                </div>
            </Content>
        </Card>

        <!-- Settings Service Card -->
        <Card class="service-card">
            <Content>
                <div class="card-header">
                    <h3>Settings Service</h3>
                    <span class="status-badge" class:online={isUp(settingsHealth?.status)}>
                        {settingsHealth?.status || 'UNKNOWN'}
                    </span>
                </div>
                <div class="card-body">
                    {#if settingsHealth}
                        {#each Object.entries(settingsHealth) as [key, value]}
                            {#if key !== 'status'}
                                <div class="detail-row">
                                    <span class="key">{formatKey(key)}</span>
                                    <span class="value">{String(value)}</span>
                                </div>
                            {/if}
                        {/each}
                    {:else}
                        <p class="no-data">No health data available</p>
                    {/if}
                </div>
            </Content>
        </Card>
    </div>
</div>

<style lang="scss">
    .dashboard-page {
        max-width: 1200px;
        margin: 0 auto;
        padding: 2rem;
        font-family: 'Inter', sans-serif;
        color: var(--on-background);
        min-height: 100vh;
        display: flex;
        flex-direction: column;
        gap: 2.5rem;
    }

    /* --- Welcome Banner --- */
    .welcome-banner {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 2.5rem;
        border-radius: 16px;
        background: var(--primary);
        color: var(--on-primary);
        box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
        gap: 2rem;
        transition: transform 0.2s ease, box-shadow 0.2s ease;

        &:hover {
            transform: translateY(-2px);
            box-shadow: 0 12px 28px rgba(0,0,0,0.25);
        }

        h1 {
            margin: 0 0 0.5rem 0;
            font-size: 2.2rem;
            font-weight: 700;
        }

        p {
            margin: 0;
            opacity: 0.9;
            font-size: 1.1rem;
        }

        .actions {
            display: flex;
            align-items: center;
        }

        :global(.logout-btn) {
            background-color: var(--surface);
            color: var(--primary);
            font-weight: 600;

            &:hover {
                background-color: rgba(255, 255, 255, 0.85);
            }
        }
    }

    /* --- Section Title --- */
    .section-title {
        font-size: 1.5rem;
        color: var(--secondary);
        font-weight: 700;
        text-transform: uppercase;
        letter-spacing: 0.05em;
        margin-bottom: 1.5rem;
    }

    /* --- Services Grid --- */
    .services-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
        gap: 2rem;
    }

    :global(.service-card) {
        border-radius: 16px !important;
        background-color: var(--surface) !important;
        color: var(--on-surface) !important;
        transition: transform 0.2s ease, box-shadow 0.2s ease;

        &:hover {
            transform: translateY(-3px);
            box-shadow: 0 12px 20px rgba(0,0,0,0.15);
        }

        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1rem;
            padding-bottom: 0.75rem;
            border-bottom: 1px solid var(--neutral-bg);

            h3 {
                margin: 0;
                font-size: 1.3rem;
                color: var(--primary);
            }
        }

        .status-badge {
            font-size: 0.75rem;
            font-weight: 700;
            padding: 0.25rem 0.75rem;
            border-radius: 50px;
            text-transform: uppercase;
            letter-spacing: 0.05em;
            color: var(--on-primary);
            background-color: var(--error);
            transition: all 0.2s ease;

            &.online {
                background-color: var(--secondary);
                color: var(--on-secondary);
            }
        }

        .card-body {
            padding-top: 0.5rem;

            .detail-row {
                display: flex;
                justify-content: space-between;
                padding: 0.5rem 0;
                font-size: 0.95rem;

                .key {
                    color: var(--on-surface);
                    opacity: 0.8;
                    font-weight: 500;
                }

                .value {
                    font-family: monospace;
                    background: var(--neutral-bg);
                    padding: 0.2rem 0.5rem;
                    border-radius: 6px;
                    color: var(--on-surface);
                }
            }

            .no-data {
                color: var(--on-surface);
                opacity: 0.6;
                font-style: italic;
            }
        }
    }

    /* --- Responsive --- */
    @media (max-width: 768px) {
        .welcome-banner {
            flex-direction: column;
            text-align: center;
            gap: 1.5rem;
        }
    }
</style>
