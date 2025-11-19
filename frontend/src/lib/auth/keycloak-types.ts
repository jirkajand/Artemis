export interface KeycloakOIDCProfile {
  sub: string;

  email?: string;
  email_verified?: boolean;
  preferred_username?: string;
  given_name?: string;
  family_name?: string;
  name?: string;

  realm_access?: {
    roles: string[];
  };
  resource_access?: Record<string, { roles: string[] }>;

  [key: string]: unknown;
}

