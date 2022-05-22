package com.example.ebiznesprojekt.configuration

import com.example.ebiznesprojekt.jwt.TokenAuthenticationFilter
import com.example.ebiznesprojekt.oauth2.CustomOAuth2UserService
import com.example.ebiznesprojekt.oauth2.HttpCookieOAuth2AuthorizationRequestRepository
import com.example.ebiznesprojekt.oauth2.OAuth2AuthenticationFailureHandler
import com.example.ebiznesprojekt.oauth2.OAuth2AuthenticationSuccessHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class SecurityConfiguration: WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository

    @Autowired
    lateinit var customOAuth2UserService: CustomOAuth2UserService

    @Autowired
    lateinit var oAuth2AuthenticationFailureHandler: OAuth2AuthenticationFailureHandler

    @Autowired
    lateinit var oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler

    @Autowired
    lateinit var tokenAuthenticationFilter: TokenAuthenticationFilter

//    @Bean
//    fun tokenAuthenticationFilter(): TokenAuthenticationFilter? {
//        return TokenAuthenticationFilter()
//    }


    override fun configure(http: HttpSecurity?) {
        if(http != null) {
            http.cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/", "/auth/**", "/oauth2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                    .baseUri("/oauth2/authorize")
                    .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                .and()
                .userInfoEndpoint()
                    .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)

                http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        }
    }
}