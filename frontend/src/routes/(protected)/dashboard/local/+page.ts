import type { PageLoad } from './$types';
import { getCountryFlag, getCountryName, getGenderIcon, getAllCountryNames } from '$lib/helpers/studentUtils';
import { redirect } from '@sveltejs/kit';

const PER_PAGE = 20;

const getEmptyState = (management: any) => ({
	students: [],
	faculties: [],
	filterValues: { countries: [], destinationFaculties: [], semesters: [] },
	activeFilters: { country: '', faculty: '', semester: '', containAssigned: false },
	pagination: { page: 1, perPage: 0, total: 0 },
	management
});

export const load: PageLoad = async ({ parent, url }) => {
	const { clients: { settings, management } } = await parent();
	let { type: studentType } = await management.getCurrentStudentForNavbar();

	if (studentType !== 'LOCAL') {
		throw redirect(302, '/dashboard/international');
	}

	const page = Math.max(1, Number(url.searchParams.get('page')) || 1);

	const containAssigned = url.searchParams.get('containAssigned') === 'true';

	const filters = {
		countryCode: url.searchParams.get('country') || undefined,
		facultyId: url.searchParams.get('faculty') || undefined,
		semesterId: url.searchParams.get('semester') || undefined,
		containAssigned
	};

	try {
		const [studentRes, faculties, semesters] = await Promise.all([
			management.getAllInternationalStudentsAnonymous({
				page: page - 1,
				size: PER_PAGE,
				...filters
			}),
			settings.getAllFaculties().then(res => res ?? []),
			settings.getAllSemesters().then(res => res ?? [])
		]);

		const facultyMap = new Map(faculties.map(f => [f.id, f]));

		const students = (studentRes?.students ?? []).map(s => ({
			...s,
			faculty: facultyMap.get(s.facultyId),
			countryFlag: getCountryFlag(s.countryCode ?? ''),
			countryName: getCountryName(s.countryCode ?? ''),
			genderIcon: getGenderIcon(s.gender ?? '')
		}));

		const filterValues = {
			countries: Object.entries(getAllCountryNames()).map(([value, label]) => ({ value, label })),
			destinationFaculties: faculties.map(f => ({ id: f.id, label: f.shortName })),
			semesters: semesters.map(s => ({ id: s.id, label: s.semesterName }))
		};

		return {
			students,
			faculties,
			filterValues,
			activeFilters: {
				containAssigned: filters.containAssigned,
				country: filters.countryCode,
				faculty: filters.facultyId,
				semester: filters.semesterId
			},
			pagination: {
				page,
				perPage: PER_PAGE,
				total: studentRes?.pageable?.totalElements ?? 0
			},
			management
		};

	} catch (e) {
		console.error('Data load failed:', e);
		return getEmptyState(management);
	}
};