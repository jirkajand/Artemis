import { fetchBlob, getCountryFlag, getCountryName, getGenderIcon } from '$lib/helpers/studentUtils';
import type { PageParentData } from "./$types";
import { redirect, error } from "@sveltejs/kit";

export const load = async ({ parent }) => {
	const data = await parent() as PageParentData;
	const { management, settings } = data.clients;

	let students;
	let faculties;
	let {type: studentType} = await management.getCurrentStudentForNavbar()

	if (studentType !== "LOCAL") {
		throw redirect(302, "/");
	}

	try {
		// fetch students
		const res = await management.getAssignedInternationalStudentsForLocalStudent();
		if (!res?.students) throw new Error("Missing students data");

		//profile picture
		students = await Promise.all(
			res.students.map(async (student) => ({
				...student,
				profilePicture: await fetchBlob(() => management.getCurrentStudentProfilePicture({ studentId: student.id }))
			}))
		);

		// fetch faculties
		faculties = await settings.getAllFaculties();
		if (!faculties) throw new Error("Failed to load faculties");


	} catch (e) {
		console.error("Student fetch failed:", e);
		throw redirect(302, "/");
	}

	// Transform
	const studentsTransformed = students.map((student: any) => {
		const faculty = faculties.find((f: any) => f.id === student.facultyId);

		return {
			...student,
			faculty: faculty || {},
			name: `${student.firstName} ${student.lastName}`,
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
