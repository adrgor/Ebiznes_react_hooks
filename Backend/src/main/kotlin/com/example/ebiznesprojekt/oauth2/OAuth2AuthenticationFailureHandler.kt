package com.example.ebiznesprojekt.oauth2

import com.example.ebiznesprojekt.utlis.CookieUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuth2AuthenticationFailureHandler: SimpleUrlAuthenticationFailureHandler() {
    val REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri"

    @Autowired
    lateinit var httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository

    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        var targetUrl = CookieUtils.getCookie(request!!, REDIRECT_URI_PARAM_COOKIE_NAME)!!.value

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("error", exception!!.localizedMessage)
            .build().toUriString()

        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequest(request, response)

        redirectStrategy.sendRedirect(request, response, targetUrl)
    }
}