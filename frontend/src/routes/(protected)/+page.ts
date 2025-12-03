import { error, redirect, type Load } from "@sveltejs/kit";
import type { PageParentData } from "./$types";
import type { fromAction } from "svelte/attachments";


export const load: Load = async ({ parent }) => {
    const parentData = await parent() as PageParentData;

    const { clients, userNavbarData } = parentData;
    const { management, settings } = clients;

    const userData = await userNavbarData;
    if(userData.type == "LOCAL")
        throw redirect(302, "/dashboard");

    try {
        const studentDetail = await management.getCurrentStudentDetail()
        const [managementHealth, settingsHealth] = await Promise.all([management.getHealthCheck(), settings.getHealthCheck1()]);
        return { settingsHealth, managementHealth, studentDetail, management, settings};
    } catch (err: any) {
        const status = err.status ?? err.response?.status ?? 500;
        const errorMessage = err.body?.message ?? err.message ?? "Unknown error";
        throw error(status, `Health check failed: ${errorMessage}`);
    }
};
