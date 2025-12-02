import { redirect } from '@sveltejs/kit';
import { getCountryFlag, getCountryName, getGenderIcon } from '$lib/helpers/studentUtils';
import type { PageParentData } from './$types';

const formatBuddyInfo = (buddy: any) =>
	buddy
		? `${buddy.firstName} ${buddy.lastName} | Phone: ${buddy.phoneNumber} | Email: ${buddy.email}`
		: '';

export const load = async ({ parent }) => {
	const { clients } = await parent() as PageParentData;
	const { management, settings } = clients;

	try {
		const { type, id } = await management.getCurrentStudentForNavbar();

		const profilePicturePromise = management.getCurrentStudentProfilePicture({ studentId: id })
			.catch(err => {
				console.warn('Profile picture fetch failed, using placeholder', err);
				return 'https://icons.veryicon.com/png/o/education-technology/alibaba-cloud-iot-business-department/image-load-failed.png';
			});

		const studentDetailPromise = type === 'LOCAL'
			? management.getLocalStudentById({ localStudentId: id })
			: management.getInternationalStudentById({ internationalStudentId: id });

		const [profilePicture, studentDetail] = await Promise.all([
			profilePicturePromise,
			studentDetailPromise
		]);

		let faculties = await settings.getAllFaculties()
		let faculty = null;
		if (studentDetail.facultyId) {
			faculty = faculties.find(f => f.id === studentDetail.facultyId);
		}

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

		return { faculties, student, management };

	} catch (err) {
		console.error('Student fetch failed:', err);
		throw redirect(302, '/');
	}
};