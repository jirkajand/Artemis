import { Configuration, UserManagementApi } from "$lib/api";
import { initKeycloak, keycloak } from "$lib/auth/keycloak";
import type { LayoutLoad } from "./$types";

export const prerender = false;
export const ssr = false;

export const load: LayoutLoad = async ({ fetch }) => {

  // Initialize Keycloak
  await initKeycloak();

  // Setup API clients with access token if authenticated
  const config = new Configuration({
    accessToken: keycloak.authenticated 
      ? () => `Bearer ${keycloak.token}`
      : undefined,
    fetchApi: fetch,
    basePath: import.meta.env.VITE_API_BASE_URL
  });

  const clients = {
    management: new UserManagementApi(config)
  };

  return { clients };
};