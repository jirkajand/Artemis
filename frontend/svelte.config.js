import adapter from '@sveltejs/adapter-static';
import { vitePreprocess } from '@sveltejs/vite-plugin-svelte';

// https://svelte.dev/docs/kit/adapter-static

/** @type {import('@sveltejs/kit').Config} */
const config = {
	// Consult https://svelte.dev/docs/kit/integrations
	// for more information about preprocessors
	preprocess: vitePreprocess(),

	kit: {	
		adapter: adapter({
			fallback: '200.html'	// SPA fallback page
		})
	}
};

export default config;
