import { Configuration, SettingsServiceApi, UserManagementApi } from "$lib/api";
import { initKeycloak, keycloak } from "$lib/auth/keycloak";
import { redirect } from "@sveltejs/kit";
import type { LayoutLoad } from "./$types";

export const prerender = true;
export const ssr = false;

export const load: LayoutLoad = async ({ fetch }) => {

  // Initialize Keycloak and check authentication
  await initKeycloak();

  if (!keycloak.authenticated) {
    throw redirect(302, "/login");
  }

  // Setup API clients with access token if authenticated
  const config = new Configuration({
    accessToken: () => `Bearer ${keycloak.token}`,
    fetchApi: fetch,
    basePath: import.meta.env.VITE_API_BASE_URL
  });

  const clients = {
    management: new UserManagementApi(config),
    settings: new SettingsServiceApi(config)
  };

  // Fetch user navbar data
  const userNavbarData = clients.management.getCurrentStudentForNavbar();

  return { clients, userNavbarData };
};