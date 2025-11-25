import { getCountryFlag, getCountryName, getGenderIcon } from '$lib/helpers/studentUtils';
import type { PageParentData } from './$types';

export const load = async ({ parent, depends }) => {
	const data = await parent() as PageParentData;
	const { settings, management } = data.clients;

	const [
		{ students: allInternationalStudents },
		{ students: assignedStudentsArray },
		faculties
	] = await Promise.all([
		management.getAllInternationalStudentsAnonymous({ size: 999 }),
		management.getAssignedInternationalStudentsForLocalStudent(),
		settings.getAllFaculties()
	]);

	const existingBuddiesIds = new Set(assignedStudentsArray?.map(({ id }) => (id)));

	const availableStudents = allInternationalStudents?.filter(
		({ id }) => !existingBuddiesIds.has(id)
	) || [];

	const facultyMap = new Map(faculties.map(f => [f.id, f]));

	const studentsTransformed = availableStudents.map((student: any) => {
		const faculty = facultyMap.get(student.facultyId);

		return {
			...student,
			faculty: faculty || {},
			countryFlag: getCountryFlag(student.countryCode),
			countryName: getCountryName(student.countryCode),
			genderIcon: getGenderIcon(student.gender)
		};
	});

	return {
		faculties,
		management,
		students: studentsTransformed
	};
};