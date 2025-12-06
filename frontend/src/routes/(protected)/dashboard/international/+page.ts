import type { PageLoad } from './$types';
import { redirect } from '@sveltejs/kit';
import { fetchBlob } from '$lib/helpers/studentUtils';

const NO_BUDDY_PLACEHOLDER = '/questionmark.jpg';

export const load: PageLoad = async ({ parent }) => {
	const {
		clients: { settings, management }
	} = await parent();

	try {
		const { type, id } = await management.getCurrentStudentForNavbar();
		if (type !== 'INTERNATIONAL') throw redirect(302, '/dashboard/local');

		const [studentRes, profilePicture] = await Promise.all([
			management.getInternationalStudentById({ internationalStudentId: id }),
			fetchBlob(() =>
				management.getCurrentStudentProfilePicture({ studentId: id })
			)
		]);

		const buddy = studentRes.assignedBuddy;

		const [faculty, buddyFaculty, buddyProfilePicture] = await Promise.all([
			settings.getFacultyById({ id: studentRes.facultyId }).then((v) => v ?? null),
			buddy
				? settings.getFacultyById({ id: buddy.facultyId }).then((v) => v ?? null)
				: Promise.resolve(null),
			buddy
				? fetchBlob(() =>
					management.getCurrentStudentProfilePicture({
						studentId: buddy.id
					})
				).catch(() => NO_BUDDY_PLACEHOLDER)
				: Promise.resolve(NO_BUDDY_PLACEHOLDER)
		]);

		return {
			student: {
				...studentRes,
				profilePicture,
				faculty,
				assignedBuddy: {
					...studentRes.assignedBuddy,
					faculty: buddyFaculty,
					profilePicture: buddyProfilePicture
				}
			},
			management
		};
	} catch (e) {
		console.error('Data load failed:', e);
		redirect(302, "/")
	}
};
