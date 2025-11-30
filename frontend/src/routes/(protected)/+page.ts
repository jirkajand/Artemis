import { error, type Load } from "@sveltejs/kit";
import type { PageParentData } from "./$types";


export const load: Load = async ({ parent }) => {
    const parentData = await parent() as PageParentData;

    const { management, settings } = parentData.clients;

    try {
        const health = management.getHealthCheck();
        return { health };
    } catch (err: any) {
        const status = err.status ?? err.response?.status ?? 500;
        const errorMessage = err.body?.message ?? err.message ?? "Unknown error";
        throw error(status, `Health check failed: ${errorMessage}`);
    }
};
