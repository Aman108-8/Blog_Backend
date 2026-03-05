package in.AY.Blog.Backend.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component	// Create this class as a Bean and manage it inside Spring Container.
public class JwtAuthenticationFilter extends OncePerRequestFilter
{

	//This service loads user data from DB.
	//internally it call loadUserByUsername(String username) which return UserDetails
	@Autowired
	private UserDetailsService userDetailsService;
	
	//This is your custom class. It handles: Extract username from token, Validate token, Check expiration, Verify signature
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	//This method runs for every HTTP request. This is the actual filter logic.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Browser sends header like: Authorization: Bearer eyJhbGc and get token
		String requestToken = request.getHeader("Authorization");
		
		//Bearer 2352523dewe
		System.out.println("Token: "+requestToken);
		
		// Extract Username From Token
		String userName = null;
		String token = null;
		
		//check req is not null
		if(requestToken != null && requestToken.startsWith("Bearer ")) 
		{
			//get the token;
			token = requestToken.substring(7);
			try 
			{
				//get usernam from token Inside this method: JWT is decoded, Signature is verified, Claims are extracted, sub (subject) field is returned (usually username/email)
				userName = this.jwtTokenHelper.getEmailFromToken(token);
			}
			catch(IllegalArgumentException e) 
			{
				System.out.println("jwt unable to get token");
			}
			//Means: Token structure invalid, Token tampered, Wrong format
			catch(MalformedJwtException e) 
			{
				System.out.println("invalid Jwt");
			}
		}
		else {
			System.out.println("Jwt token is not begin with Bearer");
		}
		
		//once we get the token, now validate
		//username not null. User not already authenticated.
		if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null) 
		{
			//This fetches: Username, Password, Roles/Authorities From your DB.
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			
			//What validateToken checks: Username inside token matches DB username, Token not expired, Signature correct, Token not tampered
			if(this.jwtTokenHelper.validateToken(token, userDetails))
			{
				//This is Spring Security’s object representing authenticated user.
				//Principal → userDetails, Credentials → null (because already verified via JWT), Authorities → roles (ROLE_USER, ROLE_ADMIN)
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				//This stores: IP address, Session ID, Other request info
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//This request is authenticated.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else 
			{
				System.out.println("Invalid jwt token");
			}
		}
		else 
		{
			System.out.println("username is null or context is null");
		}
		
		//If you don’t call this:
		//→ Request stops here
		//→ Controller never executes
		//This passes control to: Next filter → DispatcherServlet → Controller
		filterChain.doFilter(request, response);
	}

}
