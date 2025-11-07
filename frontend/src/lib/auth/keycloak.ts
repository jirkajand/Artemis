// https://willystwdev.com/posts/implementing-keycloak-authentication-sveltekit/
// https://www.keycloak.org/securing-apps/javascript-adapter

import { browser } from "$app/environment";
import Keycloak from "keycloak-js";

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
    console.log("🔑 Keycloak already initialized");
    return keycloak;
  }
  try {
    const authenticated = await keycloak.init({
      onLoad: "login-required", // auto-login
      checkLoginIframe: false,
    });

    if (!authenticated) {
      await keycloak.login();
    } else {
      console.log("🔐 Keycloak initialized and user is authenticated");
    }

    // Optional: schedule token refresh
    scheduleTokenRefresh();

  } catch (err) {
    console.error("Failed to initialize Keycloak:", err);
  }
  
  return keycloak;
}

function scheduleTokenRefresh() {
  setInterval(async () => {
    if (keycloak.token && keycloak.isTokenExpired(30)) {
      try {
        await keycloak.updateToken(60);
        console.log("🔄 Token refreshed");
      } catch (error) {
        console.error("Failed to refresh token:", error);
        await keycloak.login();
      }
    }
  }, 300000);
}
