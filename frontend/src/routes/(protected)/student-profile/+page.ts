import { getCountryFlag, getCountryName, getGenderIcon } from '$lib/helpers/studentUtils';
import type { PageParentData } from "./$types";
import { redirect } from "@sveltejs/kit";

export const load = async ({ parent }) => {
	const { clients } = await parent() as PageParentData;
	const { management, settings } = clients;

	try {
		const { type, id } = await management.getCurrentStudentForNavbar();
		const studentDetail =
			type === "LOCAL"
				? await management.getLocalStudentById({ localStudentId: id })
				: await management.getInternationalStudentById({ internationalStudentId: id });
		console.log(studentDetail);
		const faculty = await settings.getFacultyById({id: studentDetail.facultyId})

		const student = {
			type,
		...studentDetail,
			faculty,
			name: `${studentDetail.firstName} ${studentDetail.lastName}`,
			countryFlag: getCountryFlag(studentDetail?.countryCode) ?? "",
			countryName: getCountryName(studentDetail?.countryCode) ?? "",
			genderIcon: getGenderIcon(studentDetail?.gender)
		};

		return { management, student };

	} catch (err) {
		console.error("Student fetch failed:", err);
		throw redirect(302, "/");
	}
};
