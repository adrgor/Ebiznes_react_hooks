package com.example.ebiznesprojekt.oauth2

import com.example.ebiznesprojekt.configuration.AppProperties
import com.example.ebiznesprojekt.jwt.TokenProvider
import com.example.ebiznesprojekt.utlis.CookieUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuth2AuthenticationSuccessHandler: SimpleUrlAuthenticationSuccessHandler() {
    val REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri"

    @Autowired
    lateinit var tokenProvider: TokenProvider

    @Autowired
    lateinit var appProperties: AppProperties

    @Autowired
    lateinit var httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        var targetUrl = determineTargetUrl(request, response, authentication)
        if(response!!.isCommitted) {
            logger.debug("Response has already been comitted. Unable to redirect to $targetUrl")
        }
        clearAuthenticationAttributes(request, response)

        redirectStrategy.sendRedirect(request, response, targetUrl)
    }

    override fun determineTargetUrl(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?): String? {
        val redirectUri = CookieUtils.getCookie(request!!, REDIRECT_URI_PARAM_COOKIE_NAME)?.value

        val token = tokenProvider.createToken(authentication!!)

        return UriComponentsBuilder.fromUriString(redirectUri!!)
            .queryParam("token", token)
            .build().toUriString()
    }

    protected fun clearAuthenticationAttributes(request: HttpServletRequest?, response: HttpServletResponse?) {
        super.clearAuthenticationAttributes(request)
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response)
    }

}