import { redirect } from '@sveltejs/kit';
import { getCountryFlag, getCountryName, getGenderIcon } from '$lib/helpers/studentUtils';
import type { PageParentData } from './$types';
import { fetchBlob } from '$lib/helpers/studentUtils'; // import your helper

const formatBuddyInfo = (buddy: any) =>
	buddy
		? `${buddy.firstName} ${buddy.lastName} | Phone: ${buddy.phoneNumber} | Email: ${buddy.email}`
		: '';

export const load = async ({ parent }) => {
	const { clients } = await parent() as PageParentData;
	const { management, settings } = clients;

	try {
		const { type, id } = await management.getCurrentStudentForNavbar();

		const profilePicturePromise = fetchBlob(() => management.getCurrentStudentProfilePicture({ studentId: id }));

		const studentDetailPromise = type === 'LOCAL'
			? management.getLocalStudentById({ localStudentId: id })
			: management.getInternationalStudentById({ internationalStudentId: id });

		const [profilePicture, studentDetail] = await Promise.all([
			profilePicturePromise,
			studentDetailPromise
		]);

		const faculties = await settings.getAllFaculties();
		const faculty = studentDetail.facultyId
			? faculties.find(f => f.id === studentDetail.facultyId)
			: null;

		const student = {
			...studentDetail,
			type,
			faculty,
			profilePicture,
			buddyInfo: formatBuddyInfo(studentDetail?.assignedBuddy) ?? '',
			countryFlag: getCountryFlag(studentDetail?.countryCode) ?? '',
			countryName: getCountryName(studentDetail?.countryCode) ?? '',
			genderIcon: getGenderIcon(studentDetail?.gender)
		};
		console.log(student);

		return { faculties, student, management };

	} catch (err) {
		console.error('Student fetch failed:', err);
		throw redirect(302, '/');
	}
};
