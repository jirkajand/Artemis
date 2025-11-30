import type { PageLoad } from './$types';
import { getCountryFlag, getCountryName, getGenderIcon, getAllCountryNames } from '$lib/helpers/studentUtils';

export const load: PageLoad = async ({ parent, url }) => {
	const { clients: { settings, management } } = await parent();

	const page = Math.max(1, Number(url.searchParams.get('page') ?? 1));
	const perPage = 20;

	const country = url.searchParams.get('country') || undefined;
	const faculty = url.searchParams.get('faculty') || undefined;
	const semester = url.searchParams.get('semester') || undefined;

	try {
		const [internationalRes, faculties, semesters] = await Promise.all([
			management.getAllInternationalStudentsAnonymous({
				page: page - 1,
				size: perPage,
				containAssigned: false,
				countryCode: country,
				facultyId: faculty,
				semesterId: semester
			}),
			settings.getAllFaculties().then(res => res ?? []),
			settings.getAllSemesters().then(res => res ?? [])
		]);

		const facultyMap = new Map(faculties.map(f => [f.id, f]));

		const students = (internationalRes?.students ?? []).map(s => ({
			...s,
			faculty: facultyMap.get(s.facultyId),
			countryFlag: getCountryFlag(s.countryCode ?? ''),
			countryName: getCountryName(s.countryCode ?? ''),
			genderIcon: getGenderIcon(s.gender ?? '')
		}));

		const countries = Object.entries(getAllCountryNames()).map(([value, label]) => ({ value, label }));

		return {
			students,
			faculties,
			filterValues: {
				countries,
				destinationFaculties: faculties.map(({ id, shortName }) => ({ id, label: shortName })),
				semesters: semesters.map(({ id, semesterName }) => ({ id, label: semesterName }))
			},
			activeFilters: { country: country ?? '', faculty: faculty ?? '', semester: semester ?? '' },
			pagination: { page, perPage, total: internationalRes?.pageable?.totalElements ?? 0 },
			management
		};

	} catch (e) {
		console.error(e);
		return {
			students: [],
			faculties: [],
			filterValues: { countries: [], destinationFaculties: [], semesters: [] },
			activeFilters: { country: '', faculty: '', semester: '' },
			pagination: { page: 1, perPage: 0, total: 0 },
			management
		};
	}
};