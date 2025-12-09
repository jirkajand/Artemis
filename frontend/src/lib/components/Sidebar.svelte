<script lang="ts">
	import { page } from "$app/state";
	import type { ResponseStudentNavbar, SemesterResponse } from "$lib/api";
	import Button from "@smui/button";
	import { onMount } from "svelte";

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

    const roleAcessiblePages: Record<string, string[] | null> = {
        "ADMIN": null,
        "TUTOR": ["/dashboard", "/my-students", "/admin"],
        "LOCAL": ["/dashboard", "/my-students"],
        "INTERNATIONAL": ["/dashboard"],
    };

    let { 
        currentSemester,
        userNavbarData
    } : {
        currentSemester?: SemesterResponse;
        userNavbarData: Promise<ResponseStudentNavbar>;
    } = $props();

    let isOpen = $state(false);
    let roles = $state<string[]>(["ADMIN"]); // default role
    let filteredNavPages = $derived(getAvailablePages(roles));

    function getAvailablePages(roles: string[]): NavPage[] {
        const accessiblePages = new Set<string>();
        // if any of the roles have null (i.e., ADMIN), return all pages
        for (const role of roles) {
            if(!roleAcessiblePages.hasOwnProperty(role))
                continue;
            if (roleAcessiblePages[role] === null)
                return navPages;
            roleAcessiblePages[role].forEach(page => accessiblePages.add(page));
        }
        return navPages.filter(page => accessiblePages.has(page.href));
    }

    onMount(async () => {
        // use both roles and type as roles
        const userData = await userNavbarData;
        const combinedRoles = [
            ...(userData.roles ?? []),
            ...(userData.type ? [userData.type] : []),
        ];
        if(combinedRoles.length === 0) {
            combinedRoles.push("ADMIN"); // default to admin if no roles found (only visual, no real effect)
        }
        roles = combinedRoles;
    });

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
            {#each filteredNavPages as section}
                <li class:active="{page.url.pathname.includes(section.href)}">
                    <a href="{section.href}">
                        <span class="material-icons">{section.icon}</span>{section.name}
                    </a>
                </li>
            {/each}
        </ul>
    </nav>
    <section class="semester">
        {#if currentSemester}
        <span>Current Semester:</span>
        <span>{`${currentSemester.year} ${currentSemester.semesterName}`}</span>
        {/if}
    </section>
    <section class="roles">
        {`${roles.join(", ")}`}
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
    .roles {
        text-align: center;
        font-size: 0.8rem;
        color: color-mix(in srgb, currentColor 60%, transparent);
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