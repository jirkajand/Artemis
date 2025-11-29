import facultiesData from '$lib/helpers/testData/faculties.json';
import studentsData from '$lib/helpers/testData/students.json';
import { GenderEnum } from '$lib/api';

type GenderKey = keyof typeof GenderEnum;

// Helper function to pick a random element from an array
const getRandomElement = <T>(array: T[]): T | undefined => {
	if (!array || array.length === 0) {
		return undefined;
	}
	const randomIndex = Math.floor(Math.random() * array.length);
	return array[randomIndex];
};

// Helper function to generate a random date between two years
const getRandomDate = (startYear: number, endYear: number): Date => {
	const startTimestamp = new Date(startYear, 0, 1).getTime();
	const endTimestamp = new Date(endYear, 11, 31).getTime();

	const randomTime = startTimestamp + Math.random() * (endTimestamp - startTimestamp);
	return new Date(randomTime);
};

export const load = async ({ parent }) => {
	const parentData = await parent();
	const { settings, management } = parentData.clients;

	// 1. Concurrently create all faculties
	const facultyPromises = facultiesData.map(f =>
		settings.createFaculty({
			facultyCreateRequest: f
		})
	);
	const createdFaculties = await Promise.all(facultyPromises);

	// Define a reasonable birth range (e.g., 20 to 35 years old)
	const MIN_BIRTH_YEAR = 1990;
	const MAX_BIRTH_YEAR = 2005;

	// 2. Concurrently register all students
	const studentPromises = studentsData.map(s => {
		const genderKey = s.gender as GenderKey;
		const randomFaculty = getRandomElement(createdFaculties);

		// Ensure faculty exists and get a random birth date
		const facultyId = randomFaculty?.id;
		const dateOfBirth = getRandomDate(MIN_BIRTH_YEAR, MAX_BIRTH_YEAR);

		if (!facultyId) {
			console.error(`Skipping student ${s.firstName}: No faculty ID found.`);
			return Promise.resolve();
		}

		return management.registerInternationalStudent({
			registerInternationalStudentRequest: {
				firstName: s.firstName,
				lastName: s.lastName,
				bio: `Bio for ${s.firstName}`,
				email: `${s.firstName.toLowerCase()}.${s.lastName.toLowerCase()}${Date.now()}@example.com`,
				password: 'SecurePassword123!',
				phoneNumber: '+1234567890',
				facultyId: facultyId,
				dateOfBirth: dateOfBirth, // Randomly generated date
				homeUniversity: s.homeUniversity,
				gender: GenderEnum[genderKey],
				termsAndConditionsChecked: true,
				countryISO: s.countryISO
			}
		}).catch(e => console.error(`Student creation failed for ${s.firstName}:`, e));
	});

	await Promise.all(studentPromises);

	return {};
};