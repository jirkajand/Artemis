import getUnicodeFlagIcon from 'country-flag-icons/unicode';
import countries from "i18n-iso-countries";
import en from "i18n-iso-countries/langs/en.json";

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

