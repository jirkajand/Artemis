import { getCountryFlag, getCountryName, getGenderIcon } from '$lib/helpers/studentUtils';
import type { PageParentData } from "./$types";
import { redirect, error } from "@sveltejs/kit";
import { keycloak } from '$lib/auth/keycloak';

export const load = async ({ parent }) => {
	const data = await parent() as PageParentData;
	const { management, settings } = data.clients;

	let studentDetail;
	let faculties;

	try {
		// fetch students
		studentDetail = await management.getCurrentStudentDetail()
		faculties = await settings.getAllFaculties();
		if (!faculties) throw new Error("Failed to load faculties");
		let { roles } = keycloak.resourceAccess.account

	} catch (e) {
		console.error("Student fetch failed:", e);
		throw redirect(302, "/");
	}

		const faculty = faculties.find((f: any) => f.id === studentDetail.facultyId);

		const student = {
			...studentDetail,
			faculty: faculty || {},
			name: `${studentDetail.firstName} ${studentDetail.lastName}`,
			countryFlag: getCountryFlag(studentDetail?.countryCode) ?? '',
			countryName: getCountryName(studentDetail?.countryCode) ?? '',
			genderIcon: getGenderIcon(studentDetail?.gender)
		};
	console.log(student);
	return {
		management,
		student
	};
};
