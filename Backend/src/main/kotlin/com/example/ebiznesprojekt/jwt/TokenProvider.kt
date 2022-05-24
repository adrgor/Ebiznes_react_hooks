package com.example.ebiznesprojekt.jwt

import com.example.ebiznesprojekt.configuration.AppProperties
import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class TokenProvider(val appProperties: AppProperties) {

    @Value("\${spring.app.auth.tokenSecret}")
    lateinit var tokenSecret: String

    @Value("\${spring.app.auth.tokenExpirationMsec}")
    lateinit var tokenExpirationMsec: String

    val logger = LoggerFactory.getLogger(TokenProvider::class.java)

    fun createToken(authentication: Authentication): String {
        val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal

        val signingKey = SecretKeySpec(
            tokenSecret.toByteArray(),
            SignatureAlgorithm.HS512.jcaName
        )

        return Jwts.builder()
            .setSubject(userPrincipal.id.toString())
            .signWith(signingKey)
            .compact()
    }

    fun getUserIdFromToken(token: String): Long {
        val claims = Jwts.parserBuilder()
            .setSigningKey(tokenSecret.toByteArray())
            .build()
            .parseClaimsJws(token)
            .body

        return claims.subject.toLong()

    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(tokenSecret.toByteArray())
                .build()
                .parseClaimsJws(token)
            return true
        } catch (ex: SignatureException) {
            logger.error("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            logger.error("Invalid JWT token");
        } catch (ex: ExpiredJwtException) {
            logger.error("Expired JWT token");
        } catch (ex: UnsupportedJwtException) {
            logger.error("Unsupported JWT token");
        } catch (ex: IllegalArgumentException) {
            logger.error("JWT claims string is empty.");
        } catch(ex: Exception) {
            logger.error("Could not validate JWT token")
        }
        return false;
    }
}