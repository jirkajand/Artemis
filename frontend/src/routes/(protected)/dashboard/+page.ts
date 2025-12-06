import type { PageLoad } from './$types';
import { getCountryFlag, getCountryName, getGenderIcon, getAllCountryNames } from '$lib/helpers/studentUtils';
import { redirect } from '@sveltejs/kit';

export const load: PageLoad = async ({ parent, url }) => {
	const { clients: { settings, management } } = await parent();
	let { type: studentType } = await management.getCurrentStudentForNavbar();

	if (studentType === 'LOCAL') {
		throw redirect(302, '/dashboard/local');
	} else if (studentType === 'INTERNATIONAL'){
		throw redirect(302, '/dashboard/international')
	} else {
		throw redirect(302, '/')
	}
};