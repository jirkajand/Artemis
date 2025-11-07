import { Configuration, FetchError, SettingsServiceApi, UserManagementApi, type HealthCheckResponse } from "$lib/api";
import { initKeycloak, keycloak } from "$lib/auth/keycloak";
import { error, redirect } from "@sveltejs/kit";

import type { LayoutLoad } from "./$types";

export const prerender = true;
export const ssr = false;

export const load: LayoutLoad = async ({ fetch }) => {
  console.log("Layout load started");
  await initKeycloak();
  console.log("Layout load finished");

  if (!keycloak.authenticated) {
    console.warn("User not authenticated, redirecting to login...");
    throw redirect(302, await keycloak.createLoginUrl());
  }

  console.log("✅ Keycloak authenticated, token:", keycloak.token?.substring(0, 10) + "...");

  const config = new Configuration({
    accessToken: async () => `Bearer ${keycloak.token ?? ""}`,
    fetchApi: fetch
  });

  const clients = {
    management: new UserManagementApi(config),
    settings: new SettingsServiceApi(config)
  };

  return { clients };
};