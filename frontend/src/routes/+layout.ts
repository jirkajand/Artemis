import { Configuration, FetchError, SettingsServiceApi, UserManagementApi, type HealthCheckResponse } from "$lib/api";
import { initKeycloak, keycloak } from "$lib/auth/keycloak";
import { error, redirect } from "@sveltejs/kit";
import type { LayoutLoad } from "./$types";
import type { Key } from "readline";
import type { KeycloakProfile } from "keycloak-js";
import type { KeycloakOIDCProfile } from "$lib/auth/keycloak-types";

export const prerender = true;
export const ssr = false;

export const load: LayoutLoad = async ({ fetch }) => {
  await initKeycloak();


  console.log("Keycloak token:", keycloak.token?.substring(0, 20) + "...");

  console.log("Setting openapi clients without token");
  const config = new Configuration({
    // skip accessToken
    accessToken: undefined,
    fetchApi: fetch,
    basePath: import.meta.env.VITE_API_BASE_URL
  });

  const clients = {
    management: new UserManagementApi(config),
    settings: new SettingsServiceApi(config)
  };

  return { clients };
};