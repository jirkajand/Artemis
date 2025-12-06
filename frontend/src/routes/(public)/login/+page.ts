import { redirect, type Load } from "@sveltejs/kit";
import { keycloak } from "$lib/auth/keycloak";
import type { PageParentData } from "./$types";

export const load: Load = async ({ parent }) => {
    const parentData = await parent() as PageParentData;
    
    // if user is logged in redirect to home page
    if (keycloak.authenticated) {
        throw redirect(302, "/dashboard");
    }
};