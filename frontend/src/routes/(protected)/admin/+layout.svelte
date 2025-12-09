<script lang="ts">
	import Tab, { Label } from "@smui/tab";
	import TabBar from "@smui/tab-bar";
	import type { LayoutProps } from "./$types";

  let { children }: LayoutProps = $props();

    const tabDict: Record<string, any> = {
        'Semester': { slug: 'semester'},
        'Faculties': { slug: 'faculty'},
    }
    const tabKeys = Object.keys(tabDict);

</script>

<TabBar style="margin-bottom: 1.5rem" tabs={tabKeys} active={tabKeys[0]}>
    {#snippet tab(tab)}
      <Tab {tab} href={`/admin/${tabDict[tab].slug}`}>
        <Label>{tab}</Label>
      </Tab>
    {/snippet}
</TabBar>

{@render children()}