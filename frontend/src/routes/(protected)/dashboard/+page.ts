import type { PageLoad } from './$types';
import { getCountryFlag, getCountryName, getGenderIcon } from '$lib/helpers/studentUtils';

export const load: PageLoad = async ({ parent }) => {
	const { clients } = await parent();
	const { settings, management } = clients;

	try {
		// Fetch international students and faculties in parallel
		const [internationalRes, faculties] = await Promise.all([
			management.getAllInternationalStudentsAnonymous({ size: 999 }),
			settings.getAllFaculties()
		]);

		const studentsData = internationalRes?.students ?? [];
		const facultiesData = faculties ?? [];
		const facultyMap = new Map(facultiesData.map((f) => [f.id, f]));

		// Try fetching assigned students, but ignore failures
		let assignedIds = new Set<number>();
		try {
			const assignedRes = await management.getAssignedInternationalStudentsForLocalStudent();
			assignedIds = new Set(assignedRes?.students?.map((s) => s.id) ?? []);
		} catch (e) {
			console.warn('Failed to fetch assigned students, showing all international students', e);
		}

		const students = studentsData.map((s) => ({
			...s,
			faculty: facultyMap.get(s.facultyId) ?? {},
			countryFlag: getCountryFlag(s.countryCode ?? ''),
			countryName: getCountryName(s.countryCode ?? ''),
			genderIcon: getGenderIcon(s.gender ?? '')
		}));

		// Filter out assigned only if we successfully fetched them
		const filteredStudents = assignedIds.size > 0
			? students.filter((s) => !assignedIds.has(s.id))
			: students;

		return {
			faculties: facultiesData,
			management,
			students: filteredStudents
		};

	} catch (e) {
		console.error('Error loading international students or faculties:', e);
		return {
			faculties: [],
			management,
			students: []
		};
	}
};
