import getUnicodeFlagIcon from 'country-flag-icons/unicode';
import countries from "i18n-iso-countries";
import en from "i18n-iso-countries/langs/en.json";
import type { PageParentData } from "./$types";
// Register locale once on the server
countries.registerLocale(en);

// Helper functions (moved out of the component)
function getCountryFlag(countryCode: string) {
	if (!countryCode) return '🌍';
	return getUnicodeFlagIcon(countryCode) || '🌍';
}

function getCountryName(countryCode: string){
	return countries.getName(countryCode, "en") || countryCode;
}

function getGenderIcon(gender: string) {
	if (!gender) return '⚧️';
	return gender.toLowerCase() === 'male' ? '♂️' : gender.toLowerCase() === 'female' ? '♀️' : '⚧️';
}

export const load = async ({ parent }) => {
	const data = await parent() as PageParentData;
	const { settings,  management } = data.clients
	const { students } = await management.getAllInternationalStudentsAnonymous({size: 999})
	const faculties = await settings.getAllFaculties();
	const studentsTransformed = students?.map((student: any) => {
		const faculty = faculties.find((f: any) => f.id === student.facultyId);

		return {
			...student,
			faculty: faculty || {},
			countryFlag: getCountryFlag(student.countryCode),
			countryName: getCountryName(student.countryCode),
			genderIcon: getGenderIcon(student.gender)
		};
	}) || [];

	return {
		faculties,
		management,
		students: studentsTransformed
	};
};