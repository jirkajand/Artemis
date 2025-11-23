// https://willystwdev.com/posts/implementing-keycloak-authentication-sveltekit/
// https://www.keycloak.org/securing-apps/javascript-adapter

import { browser } from "$app/environment";
import Keycloak from "keycloak-js";
import type { KeycloakOIDCProfile } from "./keycloak-types";

// Create a Keycloak instance
export const keycloak = new Keycloak({
  url: import.meta.env.VITE_KEYCLOAK_URL,
  realm: import.meta.env.VITE_KEYCLOAK_REALM,
  clientId: import.meta.env.VITE_KEYCLOAK_CLIENT_ID,
});

// Initialize Keycloak (login if not logged in)
export async function initKeycloak() {
  if(browser === false) {
    // Skip initialization on the server side
    return keycloak;
  }
  if (keycloak.didInitialize) {
    return keycloak;
  }
  try {
    const authenticated = await keycloak.init({
      onLoad: "check-sso",
      pkceMethod: "S256",
      checkLoginIframe: true,
      silentCheckSsoRedirectUri: `${location.origin}/silent-check-sso.html`,
    });

    keycloak.didInitialize = true;

    if (authenticated) {
      keycloak.onTokenExpired = async () => {
        try {
          await keycloak.updateToken(60)
        } catch(e) {
          console.error("Failed to refresh token");
          keycloak.login();
        };
      };
    }

  } catch (err) {
    console.error("Failed to initialize Keycloak:", err);
  }
  
  return keycloak;
}

export async function getUserInfo(fetchApi: typeof fetch = fetch): Promise<KeycloakOIDCProfile> {
  // Fetch userinfo using the accessToken
  if(!keycloak.token) {
    throw new Error("No token available");
  }
  const userInfoUrl = `${import.meta.env.VITE_KEYCLOAK_URL}/realms/${import.meta.env.VITE_KEYCLOAK_REALM}/protocol/openid-connect/userinfo`;
  const res = await fetchApi(
    userInfoUrl,
    {
      headers: {
        Authorization: `Bearer ${keycloak.token}`
      }
    }
  );
  if (!res.ok) {
    throw new Error(`Failed to fetch user info: ${res.status}`);
  }
  const user: KeycloakOIDCProfile = await res.json();
  return user;
}
