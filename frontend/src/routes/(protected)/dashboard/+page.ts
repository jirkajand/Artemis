import type { PageLoad } from './$types';
import { getCountryFlag, getCountryName, getGenderIcon } from '$lib/helpers/studentUtils';

export const load: PageLoad = async ({ parent, url }) => {
	const { clients } = await parent();
	const { settings, management } = clients;

	const page = Number(url.searchParams.get('page') ?? 1);
	const perPage = 20;

	try {
		// Fetch only the current page of students
		const [internationalRes, faculties] = await Promise.all([
			management.getAllInternationalStudentsAnonymous({
				page: page - 1, // usually zero-based index
				size: perPage
			}),
			settings.getAllFaculties()
		]);

		const studentsData = internationalRes?.students ?? [];
		const total = internationalRes?.pageable?.totalElements ?? 0;

		const facultiesData = faculties ?? [];
		const facultyMap = new Map(facultiesData.map((f) => [f.id, f]));

		// Fetch assigned students
		let assignedIds = new Set<number>();
		try {
			const assignedRes = await management.getAssignedInternationalStudentsForLocalStudent();
			assignedIds = new Set(assignedRes?.students?.map((s) => s.id) ?? []);
		} catch {
			console.warn('Failed to fetch assigned students');
		}

		const filteredStudents = studentsData
			.filter((s) => !assignedIds.has(s.id))
			.map((s) => ({
				...s,
				faculty: facultyMap.get(s.facultyId) ?? {},
				countryFlag: getCountryFlag(s.countryCode ?? ''),
				countryName: getCountryName(s.countryCode ?? ''),
				genderIcon: getGenderIcon(s.gender ?? '')
			}));

		return {
			faculties: facultiesData,
			management,
			students: filteredStudents,
			pagination: { page, perPage, total }
		};
	} catch (e) {
		console.error('Error loading students or faculties:', e);
		return {
			faculties: [],
			management,
			students: [],
			pagination: { page: 1, perPage, total: 0 }
		};
	}
};
