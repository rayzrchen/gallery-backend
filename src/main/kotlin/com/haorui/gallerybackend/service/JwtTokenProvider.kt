package com.haorui.gallerybackend.service

import com.haorui.gallerybackend.model.Role
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest


@Component
class JwtTokenProvider(
    @Value("\${security.jwt.token.secret-key}")
    private var secretKey: String? = null,

    @Value("\${security.jwt.token.expire-length}")
    private val validityInMilliseconds: Long = 3_600_000,

    private val myUserDetails: MyUserDetails,

    ) {
    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey!!.toByteArray())
    }

    fun createToken(username: String, roles: List<Role>): String {
        val claims = Jwts.claims().setSubject(username)
        claims["auth"] = roles.stream()
            .map { s: Role ->
                SimpleGrantedAuthority(
                    s.authority
                )
            }
            .filter(Objects::nonNull)
            .collect(Collectors.toList())
        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)
        return Jwts.builder() //
            .setClaims(claims) //
            .setIssuedAt(now) //
            .setExpiration(validity) //
            .signWith(SignatureAlgorithm.HS256, secretKey) //
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = myUserDetails.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String?): String {
        if (token == null) {
            return ""
        }
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

    fun validateToken(token: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            throw RuntimeException("Expired or invalid JWT token")
        }
    }

}
