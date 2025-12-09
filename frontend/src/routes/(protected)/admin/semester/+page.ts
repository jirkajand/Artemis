import type { Load } from "@sveltejs/kit";
import type { PageParentData } from "./$types";

export const load: Load = async ({ parent }) => {
    const parentData = await parent() as PageParentData;

    const { clients } = parentData;
    const { settings } = clients;

    const semesters = await settings.getAllSemesters();

    return { semesters, settingsClient: settings };
}