package com.turkcell.rentACar.api.controllers;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.entities.concretes.Role;
import com.turkcell.rentACar.entities.concretes.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokensController {

	private final UserService userService;

	@GetMapping("/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authorizationHeader = request.getHeader(AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String email = decodedJWT.getSubject();
				User user = userService.getUserByEmail(email).getData();
				String access_token = JWT.create().withSubject(user.getEmail())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.withIssuer(request.getRequestURI().toString())
						.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algorithm);

				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);

			} catch (Exception e) {
				response.setHeader("Error", e.getMessage());
				response.setStatus(FORBIDDEN.value());

				Map<String, String> error = new HashMap<>();
				error.put("error_message", e.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}

		} else {
			throw new BusinessException(BusinessMessages.REFRESH_TOKEN_IS_MISSING);

		}
	}
}
