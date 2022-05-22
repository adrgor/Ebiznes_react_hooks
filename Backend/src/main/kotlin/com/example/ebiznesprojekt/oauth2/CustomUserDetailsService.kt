package com.example.ebiznesprojekt.oauth2

import com.example.ebiznesprojekt.jwt.UserPrincipal
import com.example.ebiznesprojekt.models.User
import com.example.ebiznesprojekt.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CustomUserDetailsService: UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Transactional
    override fun loadUserByUsername(email: String?): UserDetails {
        val user: User? = userRepository.findByEmail(email!!)

        return UserPrincipal.create(user!!)
    }

    @Transactional
    fun loadUserById(id: Long): UserDetails {
        val user: User = userRepository.findById(id).orElseThrow()
        return UserPrincipal.create(user)
    }
}