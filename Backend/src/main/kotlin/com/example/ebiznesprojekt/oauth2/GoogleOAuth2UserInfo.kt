package com.example.ebiznesprojekt.oauth2

class GoogleOAuth2UserInfo(attributes: Map<String?, Any?>?) :
    OAuth2UserInfo(attributes) {
    override val id: String?
        get() = attributes?.get("sub") as String?
    override val name: String?
        get() = attributes?.get("name") as String?
    override val email: String?
        get() = attributes?.get("email") as String?
    override val imageUrl: String?
        get() = attributes?.get("picture") as String?
}
