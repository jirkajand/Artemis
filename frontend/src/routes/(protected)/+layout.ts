import { Configuration, SettingsServiceApi, UserManagementApi } from "$lib/api";
import { initKeycloak, keycloak } from "$lib/auth/keycloak";
import { redirect } from "@sveltejs/kit";
import type { LayoutLoad } from "./$types";
import type { KeycloakOIDCProfile } from "$lib/auth/keycloak-types";

export const prerender = true;
export const ssr = false;

export const load: LayoutLoad = async ({ fetch }) => {
  await initKeycloak();

  if (!keycloak.authenticated) {
    throw redirect(302, "/login");
  }

  // Fetch userinfo using the accessToken
  let user: KeycloakOIDCProfile | null = null;
  try {
    const userInfoUrl = `${import.meta.env.VITE_KEYCLOAK_URL}/realms/${import.meta.env.VITE_KEYCLOAK_REALM}/protocol/openid-connect/userinfo`;
    const res = await fetch(
      userInfoUrl,
      {
        headers: {
          Authorization: `Bearer ${keycloak.token}`
        }
      }
    );
    user = await res.json();
  } catch (err) {
    console.error("Failed to fetch user info:", err);
  }

  // Setup API clients with access token
  const config = new Configuration({
    accessToken: async () => `Bearer ${keycloak.token}`,
    fetchApi: fetch,
    basePath: import.meta.env.VITE_API_BASE_URL
  });

  const clients = {
    management: new UserManagementApi(config),
    settings: new SettingsServiceApi(config)
  };

  return { user, clients };
};