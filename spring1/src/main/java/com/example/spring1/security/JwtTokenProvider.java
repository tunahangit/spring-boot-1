package com.example.spring1.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
	
	@Value("${spring1.app.secret}")  // Değerleri application_properties içerisinden verilecek.
	private String APP_SECRET; // token 
	
	@Value("${spring1.expires.in}")
	private long EXPIRES_IN;   // geçerlilik süresi
	
	
	public String generateJwtToken(Authentication auth) {
		JwtUserDetails 	userDetails=(JwtUserDetails) auth.getPrincipal();
		Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
		return Jwts.builder()
				.setSubject(Long.toString(userDetails.getId()))
				.setIssuedAt(new Date())
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
	}
	
	Long getUserIdFromJwt(String token ) {  // yukarıdaki işlemi tersinden yapıyoruz.
		Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject()); 
	}
	
	boolean validateToken(String token ) { // user token ile geldiğinde(frontend) token bizim oluşturduğumuz token mi ?
		try {
			// APP_SECRET kullanarak parse edebiliyorsak bu bizim uygulamamızın oluşturmuş olduğu bir keydir.
			Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);  
		   return  !isTokenExpired(token);   //süresi dolduysa(true) false , süresi dolmadıysa(false) true dönecek.
		}catch(SignatureException e ){  // Parse edilirken alınacak muhtemel hatalar. Catch olursa token hatalı demektir.
			return false;
		}catch(MalformedJwtException e) {
			return false;
		}catch(ExpiredJwtException e) {
			return false;
		}catch(UnsupportedJwtException e) {
			return false;
		}catch(IllegalArgumentException e) {
			return false;
		}
	}

	private boolean isTokenExpired(String token) {
		 Date expiration = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
		return expiration.before(new Date());
	}
	

}
