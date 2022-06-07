package com.example.jwt;

import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
public class JwtApi {

	@GetMapping("/time")
	@Operation(summary = "Get time", security = @SecurityRequirement(name = "bearerAuth"))
	public String time(HttpServletRequest request) {
		var encryptedUsername = request.getAttribute(JwtConstant.REQUEST_DOCUMENT_ID);
		return "Now is " + LocalTime.now() + ", doc id " + encryptedUsername;
	}

	@Autowired
	private JwtService jwtService;

	@GetMapping("/auth")
	public String auth(@RequestParam("docId") String docId) {
		var jwtData = new JwtData();
		jwtData.setDocumentId(docId);
		return jwtService.create(jwtData);
	}

}
