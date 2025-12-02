import { error, type Load } from "@sveltejs/kit";
import type { PageParentData } from "./$types";


export const load: Load = async ({ parent }) => {
    const parentData = await parent() as PageParentData;

    const { management, settings } = parentData.clients;

    try {
        const studentDetail = await management.getCurrentStudentDetail()
        const [managementHealth, settingsHealth] = await Promise.all([management.getHealthCheck(), settings.getHealthCheck1()]);
        return { settingsHealth, managementHealth, studentDetail};
    } catch (err: any) {
        const status = err.status ?? err.response?.status ?? 500;
        const errorMessage = err.body?.message ?? err.message ?? "Unknown error";
        throw error(status, `Health check failed: ${errorMessage}`);
    }
};
