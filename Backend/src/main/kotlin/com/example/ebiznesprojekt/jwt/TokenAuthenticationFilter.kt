package com.example.ebiznesprojekt.jwt

import com.example.ebiznesprojekt.oauth2.CustomUserDetailsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TokenAuthenticationFilter: OncePerRequestFilter() {
    @Autowired
    lateinit var tokenProvider: TokenProvider

    @Autowired
    lateinit var customUserDetailsService: CustomUserDetailsService

    private val logger: Logger = LoggerFactory.getLogger(TokenAuthenticationFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = getJwtFromRequest(request)

        if(jwt?.isNotBlank()!! && tokenProvider.validateToken(jwt)) {
            val userId: Long = tokenProvider.getUserIdFromToken(jwt)

            val userDetails: UserDetails = customUserDetailsService.loadUserById(userId)
            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

            SecurityContextHolder.getContext().authentication = authentication
            filterChain.doFilter(request, response)
        } else {
            response.sendError(401)
            filterChain.doFilter(request, response)
        }
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        if(bearerToken.isNotBlank() && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7)
        }
        return null
    }
}