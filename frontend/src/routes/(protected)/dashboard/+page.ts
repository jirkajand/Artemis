// +page.ts
import { error, type Load } from '@sveltejs/kit';
import type { PageParentData } from './$types';
import { keycloak } from '$lib/auth/keycloak';

export const load: Load = async ({ parent }) => {
	const parentData = await parent() as PageParentData;
	console.log('Authenticated: ' + keycloak.authenticated);
	const { management } = parentData.clients;

	try {
		return await management.getAllInternationalStudentsAnonymous();

	} catch (err: any) {
		const status = err.status ?? err.response?.status ?? 500;
		const errorMessage = err.body?.message ?? err.message ?? 'Unknown error';
		throw error(status, `Fetch failed: ${errorMessage}`);
	}
};
