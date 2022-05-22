package com.example.ebiznesprojekt.oauth2

import com.example.ebiznesprojekt.jwt.UserPrincipal
import com.example.ebiznesprojekt.models.AuthProvider
import com.example.ebiznesprojekt.models.User
import com.example.ebiznesprojekt.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import javax.naming.AuthenticationException

@Component
class CustomOAuth2UserService: DefaultOAuth2UserService() {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        try {
            return processOAuth2User(userRequest, oAuth2User)
        } catch(ex: AuthenticationException) {
            throw ex
        } catch (ex: Exception) {
            throw InternalAuthenticationServiceException(ex.message)
        }
    }

    private fun processOAuth2User(userRequest: OAuth2UserRequest?, oAuth2User: OAuth2User): OAuth2User {
        val oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(userRequest!!.clientRegistration.registrationId,
                                                                    oAuth2User.attributes)

        if(oAuth2UserInfo.email!!.isEmpty()) {
            throw Exception("Email not found from OAuth2 provider")
        }

        var user: User? = userRepository.findByEmail(oAuth2UserInfo.email!!)

        if (user != null) {
            if(!user!!.provider!!.equals(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()))) {
                throw Exception("Looks like you're signed up with " +
                        user.provider + " account. Please use your " + user.provider +
                        " account to login.");
            }

            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(userRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user!!, oAuth2User.attributes)
    }

    private fun updateExistingUser(existingUser: User, oAuth2UserInfo: OAuth2UserInfo): User {
        if(oAuth2UserInfo.name != null)
            existingUser.name = oAuth2UserInfo.name
        else
            existingUser.name = oAuth2UserInfo.attributes!!["login"] as String
        existingUser.imageUrl = oAuth2UserInfo.imageUrl
        return userRepository.save(existingUser)
    }

    private fun registerNewUser(userRequest: OAuth2UserRequest?, oAuth2UserInfo: OAuth2UserInfo): User {
        var user = User()
        user.provider = AuthProvider.valueOf(userRequest!!.clientRegistration.registrationId)
        user.providerId = oAuth2UserInfo.id
        if(oAuth2UserInfo.name != null)
            user.name = oAuth2UserInfo.name
        else
            user.name = oAuth2UserInfo.attributes!!["login"] as String?
        user.email = oAuth2UserInfo.email
        user.imageUrl = oAuth2UserInfo.imageUrl
        return userRepository.save(user)
    }
}