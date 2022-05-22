package com.example.ebiznesprojekt.oauth2

import com.example.ebiznesprojekt.utlis.CookieUtils
import com.example.ebiznesprojekt.utlis.CookieUtils.deleteCookie
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.stereotype.Component
import org.springframework.util.SerializationUtils
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.reflect.cast


@Component
class HttpCookieOAuth2AuthorizationRequestRepository: AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    val OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request"
    val REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri"
    private val cookieExpireSeconds = 180


    override fun loadAuthorizationRequest(request: HttpServletRequest?): OAuth2AuthorizationRequest {
        val oAuthCookie = CookieUtils.getCookie(request!!, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
        return OAuth2AuthorizationRequest::class.cast(SerializationUtils.deserialize(
            Base64.getUrlDecoder().decode(oAuthCookie?.value)
        ))
    }

    override fun saveAuthorizationRequest(
        authorizationRequest: OAuth2AuthorizationRequest?,
        request: HttpServletRequest?,
        response: HttpServletResponse?
    ) {
        if(authorizationRequest == null) {
            deleteCookie(request!!, response!!, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
            deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME)
            return
        }
        CookieUtils.addCookie(response!!, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, CookieUtils.serialize(authorizationRequest), cookieExpireSeconds)
        val redirectUri = request!!.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME)

        if(redirectUri.isNotBlank()) {
            CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUri, cookieExpireSeconds)
        }
    }

    override fun removeAuthorizationRequest(request: HttpServletRequest?): OAuth2AuthorizationRequest {
        return this.loadAuthorizationRequest(request);
    }

    fun removeAuthorizationRequestCookies(request: HttpServletRequest?, response: HttpServletResponse?) {
        deleteCookie(request!!, response!!, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
        deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME)
    }
}