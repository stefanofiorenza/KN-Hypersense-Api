package com.knits.coreplatform.security;

/**
 * Constants for Spring Security authorities.
 * @version 2.0
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    /**Authorities to be used while creating roles.
     * Example 1: Admin will have all four authorities.
     * Example 2: Analytic will have only PERMISSION_READ.
     */
    public static final String PERMISSION_CREATE = "ROLE_PERMISSION_CREATE";
    public static final String PERMISSION_UPDATE = "ROLE_PERMISSION_UPDATE";
    public static final String PERMISSION_READ = "ROLE_PERMISSION_READ";
    public static final String PERMISSION_DELETE = "ROLE_PERMISSION_DELETE";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
