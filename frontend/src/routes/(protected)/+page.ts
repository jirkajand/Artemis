import { redirect, type Load } from "@sveltejs/kit";


export const load: Load = async ({ parent }) => {
    redirect(302, '/dashboard');
};
