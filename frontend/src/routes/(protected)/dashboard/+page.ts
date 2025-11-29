import type { PageLoad } from './$types';
import { getCountryFlag, getCountryName, getGenderIcon, getAllCountryNames } from '$lib/helpers/studentUtils';

export const load: PageLoad = async ({ parent, url }) => {
	const { clients } = await parent();
	const { settings, management } = clients;

	const page = Number(url.searchParams.get('page') ?? 1);
	const perPage = 20;

	const country = url.searchParams.get('country') || undefined;
	const faculty = url.searchParams.get('faculty') || undefined;
	const semester = url.searchParams.get('semester') || undefined;

	try {
		const [internationalRes, faculties] = await Promise.all([
			management.getAllInternationalStudentsAnonymous({
				page: page - 1,
				size: perPage,
				containAssigned: false,
				countryCode: country,
				facultyId: faculty,
				semesterId: semester
			}),
			settings.getAllFaculties()
		]);

		const facultyMap = new Map((faculties ?? []).map(f => [f.id, f]));
		const students = (internationalRes?.students ?? []).map(s => ({
			...s,
			faculty: facultyMap.get(s.facultyId),
			countryFlag: getCountryFlag(s.countryCode ?? ''),
			countryName: getCountryName(s.countryCode ?? ''),
			genderIcon: getGenderIcon(s.gender ?? '')
		}));

		const countries = Object.entries(getAllCountryNames()).map(([code, name]) => ({ value: code, label: name }));

		return {
			students,
			faculties: faculties ?? [],
			filterValues: {
				countries,
				destinationFaculties: (faculties ?? []).map(f => ({ id: f.id, label: f.shortName }))
			},
			activeFilters: { country: country ?? '', faculty: faculty ?? '', semester: semester ?? '' },
			pagination: { page, perPage, total: internationalRes?.pageable?.totalElements ?? 0 },
			management
		};
	} catch (error) {
		console.error(error);
		return {
			students: [],
			faculties: [],
			filterValues: { countries: [], destinationFaculties: [] },
			activeFilters: { country: '', faculty: '', semester: '' },
			pagination: { page: 1, perPage: 0, total: 0 },
			management
		};
	}
};
