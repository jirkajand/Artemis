import { Configuration, SettingsServiceApi, UserManagementApi } from "$lib/api";
import { getUserInfo, initKeycloak, keycloak } from "$lib/auth/keycloak";
import { redirect } from "@sveltejs/kit";
import type { LayoutLoad } from "./$types";
import type { KeycloakOIDCProfile } from "$lib/auth/keycloak-types";

export const prerender = true;
export const ssr = false;

export const load: LayoutLoad = async ({ fetch }) => {

  // Initialize Keycloak and check authentication
  await initKeycloak();

  if (!keycloak.authenticated) {
    throw redirect(302, "/login");
  }

  // Fetch userinfo using the accessToken
  let user: KeycloakOIDCProfile | null = null;
  try {
    user = await getUserInfo(fetch);
  } catch (err) {
    console.error("Failed to fetch user info:", err);
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

  return { user, clients };
};