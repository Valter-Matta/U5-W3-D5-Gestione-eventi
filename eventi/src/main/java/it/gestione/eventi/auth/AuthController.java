package it.gestione.eventi.auth;

import it.gestione.eventi.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping ("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final PasswordEncoder passwordEncoder;
	private final AppUserService appUserService;

	@PostMapping ("/register")
	public ResponseEntity<String> register (@RequestBody RegisterRequest registerRequest) {
		appUserService.registerUser(
			registerRequest.getUsername(),
			registerRequest.getPassword()
		);

		return ResponseEntity.ok("Registrazione avvenuta con successo");
	}

	@PostMapping ("/login")
	public ResponseEntity<AuthResponse> login (@RequestBody LoginRequest loginRequest) {
		String token = appUserService.authenticateUser(
			loginRequest.getUsername(),
			loginRequest.getPassword()
		);
		return ResponseEntity.ok(new AuthResponse(token));
	}

	@PostMapping("/admin/register")
	@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<String> configRole(@RequestBody RegisterAdminRequest roleQuest){
		appUserService.registerAdminUser(
			roleQuest.getUsername(),
			roleQuest.getPassword(),
			Set.of(roleQuest.getRole())
		);

		return ResponseEntity.ok("Registrazione avvenuta con successo");
	}
}
