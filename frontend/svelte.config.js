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
			// No "fallback" => this is NOT pure SPA mode
			// Each route becomes its own .html file
		}),

		prerender: {
			entries: ['*'] // prerender all routes into static HTML
		}
	}
};

export default config;
