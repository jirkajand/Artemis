import { UserManagementApi, type HealthCheckResponse } from "$lib/api";
import type { LayoutLoad } from "./$types";

export const prerender = true;
export const ssr = false;

export const load: LayoutLoad = async () => {

    const client = new UserManagementApi();
    // const health = await client.getHealthCheck();
    const health: HealthCheckResponse = { status: "mock" }

    return {
        test: 'hello',
        world: 'world',
        health
    };
};