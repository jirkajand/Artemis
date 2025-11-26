import { getCountryFlag, getCountryName, getGenderIcon } from '$lib/helpers/studentUtils';
import type { PageParentData } from "./$types";
import { keycloak } from '$lib/auth/keycloak';


export const load = async ({ parent, depends }) => {
	const data = await parent() as PageParentData;
	keycloak.refreshToken
	const { management, settings } = data.clients
	const { students } = await management.getAssignedInternationalStudentsForLocalStudent()
	console.log(students);
	const faculties = await settings.getAllFaculties()
	const studentsTransformed = students?.map((student: any) => {
		const faculty = faculties.find((f: any) => f.id === student.facultyId);

		return {
			...student,
			faculty: faculty || {},
			name: student?.firstName.concat(" ", student?.lastName),
			countryFlag: getCountryFlag(student?.countryCode),
			countryName: getCountryName(student?.countryCode),
			genderIcon: getGenderIcon(student?.gender),
		};
	}) || [];

	return {
		faculties,
		management,
		students: studentsTransformed
	};
};