package com.ibas.brta.vehims.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * To represent or manage the currently authenticated user within an application.
 *
 * @author ashshakur.rahaman
 * @version 1.0 08/20/24
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {

}
