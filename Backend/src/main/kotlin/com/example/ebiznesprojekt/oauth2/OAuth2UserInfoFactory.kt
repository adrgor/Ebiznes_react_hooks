package com.example.ebiznesprojekt.oauth2

import com.example.ebiznesprojekt.models.AuthProvider

object OAuth2UserInfoFactory {
    fun getOAuth2UserInfo(registrationId: String, attributes: Map<String?, Any?>?): OAuth2UserInfo {
        return if (registrationId.equals(AuthProvider.google.toString(), ignoreCase = true)) {
            GoogleOAuth2UserInfo(attributes)
        } else if (registrationId.equals(AuthProvider.github.toString(), ignoreCase = true)) {
            GithubOAuth2UserInfo(attributes)
        } else {
            throw Exception("Sorry! Login with $registrationId is not supported yet.")
        }
    }
}
