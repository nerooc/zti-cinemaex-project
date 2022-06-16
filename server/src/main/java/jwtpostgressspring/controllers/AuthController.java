package jwtpostgressspring.controllers;


import jwtpostgressspring.models.ERole;
import jwtpostgressspring.models.Role;
import jwtpostgressspring.models.User;
import jwtpostgressspring.payload.request.IsVerifiedRequest;
import jwtpostgressspring.payload.request.LoginRequest;
import jwtpostgressspring.payload.request.RegisterRequest;
import jwtpostgressspring.payload.response.JwtResponse;
import jwtpostgressspring.payload.response.MessageResponse;
import jwtpostgressspring.repository.RoleRepository;
import jwtpostgressspring.repository.UserRepository;
import jwtpostgressspring.security.jwt.JwtUtils;
import jwtpostgressspring.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		System.out.println(loginRequest.getEmail());
		System.out.println(loginRequest.getPassword());

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		System.out.println(authentication);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				userDetails.getEmail(),
				roles));
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getName(),
				signUpRequest.getSurname(),
				signUpRequest.getNewsletter());

//		Set<String> strRoles = new HashSet<String>();
//		strRoles.add("admin");

		Set<Role> roles = new HashSet<>();

//		if (strRoles == null) {
//			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//			roles.add(userRole);
//		} else {
//			strRoles.forEach(role -> {
//				switch (role) {
//					case "admin":
//						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//						roles.add(adminRole);
//
//						break;
//					case "mod":
//						Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//						roles.add(modRole);
//
//						break;
//					default:
//						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//						roles.add(userRole);
//				}
//			});
//		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}


	@GetMapping("/is-verified")
	public ResponseEntity<?> isVerified(@RequestHeader("token") String token) {
		HashMap<String, Boolean> map = new HashMap<>();
		map.put("auth", jwtUtils.validateJwtToken(token));

		return ResponseEntity.ok(map);
	}
}