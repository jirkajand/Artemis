<script lang="ts">
	import { page } from "$app/state";
	import Button from "@smui/button";

    // define list of nav pages
    interface NavPage {
        name: string;
        icon: string;
        href: string;
    }
    const navPages: NavPage[] = [
        { name: "Dashboard", icon: "dashboard", href: "/dashboard" },
        { name: "My Students", icon: "school", href: "/my-students" },
        // { name: "Events", icon: "event", href: "/events" },
        // { name: "Statistics", icon: "bar_chart", href: "/statistics" },
        { name: "Administration", icon: "admin_panel_settings", href: "/admin" },
        // { name: "E-mailing", icon: "mail", href: "/emailing" },
    ];

    let isOpen = $state(false);

</script>

<Button id="menu-button" onclick={() => isOpen = !isOpen}>
    <span class="material-icons">menu</span>
</Button>
<aside class="sidebar" class:open="{isOpen}">
    <a class="logo" href="/">
        <img  src="/logo/hradec_kralove-logo-colour.png" alt="Logo" />
    </a>
    <nav>
        <ul>
            {#each navPages as section}
                <li class:active="{page.url.pathname.includes(section.href)}">
                    <a href="{section.href}">
                        <span class="material-icons">{section.icon}</span>{section.name}
                    </a>
                </li>
            {/each}
        </ul>
    </nav>
    <section class="semester">
        <span>Current Semester:</span>
        <span>2025/2026 - Winter semester</span>
    </section>
    <section class="footer">
        <span>2025 ©</span>
        <img src="/favicon.svg" alt="App Icon" width="16" height="16" />
        <span>ARTEMIS</span>
    </section>
</aside>

<style>
    :global(#menu-button) {
        position: absolute;
        display: none;
        z-index: 1100;
    }
    .sidebar {
		display: flex;
		flex-flow: column;
		gap: 1rem;
		padding-top: 0.5rem;
		height: 100%;
		width: 14rem;
		background-color: var(--nav-bg);
        color: var(--on-nav-bg);
		overflow-x: hidden; /* Disable horizontal scroll */
        scrollbar-width: none;
	}
    .sidebar::-webkit-scrollbar {
        display: none; /* Disable scrollbar for WebKit browsers */
    }
    .sidebar .logo {
        padding: 0 0.5rem;
    }
    .sidebar .logo img {
        width: 100%;
        max-width: 200px;
        display: block;
    }
    .sidebar .material-icons {
        color: var(--on-nav-bg);
        margin-right: 0.5rem;
    }
    nav ul {
        list-style: none;
        padding: 1rem 0.5rem;
        margin: 0;
    }
    nav ul li {
        margin-right: 0.75rem;
    }
    nav ul li a {
        text-decoration: none;
        font-size: 1rem;
        color: var(--on-nav-bg);
        display: block;
        padding: 0.75rem 0;

        border-bottom: 2px solid var(--secondary);
    }
    nav ul li.active a {
        border-bottom-color: var(--primary);
    }
    nav ul li a:hover {
        border-bottom-color: var(--primary);
    }
    .semester {
        display: flex;
        flex-flow: column;
        font-size: 0.9rem;
        text-align: center;
    }
    .footer {
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 0.5rem;
        font-size: 0.9rem;
        text-align: center;
        padding: 1rem;
        margin-top: auto;
    }

    @media (max-width: 768px) and (min-width: 481px) {
        .sidebar {
            width: 4.5rem;
            align-items: center;
        }
        .sidebar .logo {
            padding: 0 0.25rem;
        }
        .sidebar .logo img {
            max-width: 50px;
        }
        nav ul li {
            margin-right: 0;
        }
        nav ul li a {
            font-size: 0;
            padding: 0.5rem 0;
            border-bottom: none;
        }
        nav ul li a .material-icons {
            margin-right: 0;
            font-size: 1.5rem;
        }
        .semester, .footer {
            font-size: 0;
        }
    }

    @media (max-width: 480px) {
        .sidebar {
            display: none;
            position: absolute;
            bottom: 0;
            left: 0;
            width: 100%;
            height: 100%;
            align-items: center;
            padding: 0.5rem;

            z-index: 1000;
        }
        .sidebar nav {
            width: 100%;
        }
        .sidebar.open {
            display: flex;
        }
        :global(#menu-button) {
            display: block;
        }
    }
</style>