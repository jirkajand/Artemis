<script lang="ts">
	import Tab, { Label } from "@smui/tab";
	import TabBar from "@smui/tab-bar";
	import type { LayoutProps } from "./$types";
	import { page } from "$app/state";

  let { children }: LayoutProps = $props();

    const tabDict: Record<string, any> = {
        'Semester': { slug: 'semester'},
        'Faculties': { slug: 'faculty'},
    }
    const tabKeys = Object.keys(tabDict);
    // dynamicaly determine active tab based on current page
    const activeTab = tabKeys.find(key => page.url.pathname.includes(tabDict[key].slug)) || tabKeys[0];

</script>

<TabBar style="margin-bottom: 1.5rem" tabs={tabKeys} active={activeTab}>
    {#snippet tab(tab)}
      <Tab {tab} href={`/admin/${tabDict[tab].slug}`}>
        <Label>{tab}</Label>
      </Tab>
    {/snippet}
</TabBar>

{@render children()}