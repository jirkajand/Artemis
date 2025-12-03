import getUnicodeFlagIcon from 'country-flag-icons/unicode';
import countries from "i18n-iso-countries";
import en from "i18n-iso-countries/langs/en.json";
const DEFAULT_PROFILE_PICTURE = 'https://icons.veryicon.com/png/o/education-technology/alibaba-cloud-iot-business-department/image-load-failed.png';
countries.registerLocale(en);

export function getCountryFlag(countryCode: string) {
	if (!countryCode) return '🌍';
	return getUnicodeFlagIcon(countryCode) || '🌍';
}

export function getCountryName(countryCode: string){
	return countries.getName(countryCode, "en") || countryCode;
}

export function getGenderIcon(gender: string) {
	if (!gender) return '⚧️';
	return gender?.toLowerCase() === 'male' ? '♂️' :
		gender?.toLowerCase() === 'female' ? '♀️' : '⚧️';
}

export function getAllCountryNames(){
	return countries.getNames("en", {select: "alias"})
}

export async function fetchBlob(fetcher: () => Promise<any>, fallbackUrl = DEFAULT_PROFILE_PICTURE): Promise<string> {
	try {
		const data = await fetcher();
		if (data instanceof Blob) {
			return URL.createObjectURL(data);
		} else if (typeof data === 'string' && data.trim() !== '') {
			return data; // already a valid URL
		} else {
			console.warn('Fetched resource is invalid, using fallback.');
			return fallbackUrl;
		}
	} catch (err) {
		console.warn('Resource fetch failed, using fallback.', err);
		return fallbackUrl;
	}
}

