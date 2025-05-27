package dev.ilya_anna.announcement_service.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import dev.ilya_anna.announcement_service.exceptions.SignOutMarkValidationException;
import dev.ilya_anna.announcement_service.exceptions.UserNotFoundException;
import dev.ilya_anna.announcement_service.services.JwtService;
import dev.ilya_anna.announcement_service.services.SignOutService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
  @Autowired
  private JwtService jwtService;

  @Autowired
  private SignOutService signOutService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) throws ServletException, IOException {
  //get authorization header
  String authorizationHeader = request.getHeader("Authorization");
    try {
      //authorize user only if authorization header is jwt
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        // get jwt
        String token = authorizationHeader.substring(7);

        //validate jwt and retrieve data
        DecodedJWT decodedJWT = jwtService.verifyAccessToken(token);
        String userId = decodedJWT.getClaim("userId").asString();
        LocalDateTime issuedAt = decodedJWT.getIssuedAt().toInstant()
            .atZone(ZoneId.systemDefault()).toLocalDateTime();
        
        //validate relevance
        signOutService.validateSignOutMark(userId, issuedAt);
        

        //add user to security context
        UserDetails userDetails = JwtUserDetails.builder()
          .userId(decodedJWT.getClaim("userId").asString())
          .roles(decodedJWT.getClaim("roles").asList(String.class))
        .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities()
        );
        authentication.setAuthenticated(true);
        if (SecurityContextHolder.getContext().getAuthentication() == null ||
                !SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
      filterChain.doFilter(request, response);
    } catch (JWTVerificationException e) {
        response.getWriter().write("Invalid authorization JWT");
    } catch (UserNotFoundException e) {
        response.getWriter().write(e.getMessage());
    } catch (SignOutMarkValidationException e) {
        response.getWriter().write(e.getMessage());
    }
  }
}
