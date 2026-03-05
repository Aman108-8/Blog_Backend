package in.AY.Blog.Backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.AY.Blog.Backend.payloads.ApiException;
import in.AY.Blog.Backend.payloads.JwtAuthRequest;
import in.AY.Blog.Backend.payloads.UserDto;
import in.AY.Blog.Backend.security.JwtAuthResponse;
import in.AY.Blog.Backend.security.JwtTokenHelper;
import in.AY.Blog.Backend.services.UserServices;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController 
{
	//Generate JWT after successful authentication
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	//Load user from database after authentication
	@Autowired
	private UserDetailsService userDetailsService;
	
	//It is responsible for: Verifying email, Verifying password, Calling CustomUserDetailService, Comparing password using BCrypt
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	UserServices userServices;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> CreateToken(@RequestBody JwtAuthRequest request)
	{
		this.authenticate(request.getEmail(), request.getPassword());
		//You load user again to: Get authorities, Get username, Pass into JWT generator
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
		//Inside this: Username added as subject, Issue time added, Expiration added, Signed with secret key, Returned as compact JWT string
		String token = this.jwtTokenHelper.generateToken(userDetails);
		//now set token
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK); 
	}

	
	  private void authenticate(String email, String password) 
	  {
		  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
		  
		  try 
		  {
			  this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		  } 
		  catch (BadCredentialsException e) 
		  {
			throw new ApiException("login not succefully");
		  }
		  
	  }
	  
	  //register new user api
	  @PostMapping("/register")
	  public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto)
	  {
		  UserDto registrerUser = this.userServices.registerNewUser(userDto);
		  return new ResponseEntity<UserDto>(registrerUser, HttpStatus.CREATED);
	  }
	 
}
