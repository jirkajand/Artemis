import { error, redirect } from '@sveltejs/kit';
import { getCountryFlag, getCountryName, getGenderIcon } from '$lib/helpers/studentUtils';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ parent }) => {
	const { clients } = await parent();
	const { settings, management } = clients;

	try {
		const [internationalRes, assignedRes, faculties] = await Promise.all([
			management.getAllInternationalStudentsAnonymous({ size: 999 }),
			management.getAssignedInternationalStudentsForLocalStudent(),
			settings.getAllFaculties()
		]);

		if (!internationalRes?.students || !assignedRes?.students || !faculties) {
			throw error(500, 'Incomplete data received');
		}

		const assignedIds = new Set(assignedRes.students.map((s) => s.id));
		const facultyMap = new Map(faculties.map((f) => [f.id, f]));

		const students = internationalRes.students
			.filter((s) => !assignedIds.has(s.id))
			.map((s) => ({
				...s,
				faculty: facultyMap.get(s.facultyId) ?? {},
				countryFlag: getCountryFlag(s.countryCode ?? ''),
				countryName: getCountryName(s.countryCode ?? ''),
				genderIcon: getGenderIcon(s.gender ?? ''),
			}));

		return { faculties, management, students };

	} catch (e) {
		console.error(e);
		throw redirect(302, '/');
	}
};
