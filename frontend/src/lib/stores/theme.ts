import { writable } from "svelte/store";

export enum Theme {
    Default = 0,
    Light = 1,
    Dark = 2
}

export const theme = createThemeStore();

function createThemeStore() {
    const { subscribe, set, update } = writable(Theme.Default);

    return {
        set,
        subscribe,
        setTheme: (newTheme: Theme) => set(newTheme),
        toggleTheme: () => update(currentTheme => {
            let nextTheme = currentTheme + 1;

            if(nextTheme > Theme.Dark) 
                nextTheme = Theme.Default;

            return nextTheme;
        }),
        getLabel: (currentTheme: Theme) => {
            switch(currentTheme) {
                case Theme.Default:
                    return "Default";
                case Theme.Light:
                    return "Light";
                case Theme.Dark:
                    return "Dark";
                default:
                    return "Unknown";
            }}
    };
}