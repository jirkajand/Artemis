import { Configuration, FetchError, SettingsServiceApi, UserManagementApi, type HealthCheckResponse } from "$lib/api";
import { initKeycloak, keycloak } from "$lib/auth/keycloak";
import { error, redirect } from "@sveltejs/kit";
import type { LayoutLoad } from "./$types";

export const prerender = true;
export const ssr = false;

export const load: LayoutLoad = async ({ fetch }) => {
  await initKeycloak();

  if (!keycloak.authenticated) {
    throw redirect(302, "/login");
  }
  const config = new Configuration({
    accessToken: async () => `Bearer ${keycloak.token ?? ""}`,
    fetchApi: fetch,
    basePath: import.meta.env.VITE_API_BASE_URL
  });

  const clients = {
    management: new UserManagementApi(config),
    settings: new SettingsServiceApi(config)
  };

  return { clients };
};