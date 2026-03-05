package in.AY.Blog.Backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import in.AY.Blog.Backend.security.CustomUserDetailService;
import in.AY.Blog.Backend.security.JwtAuthenticationEntryPoint;
import in.AY.Blog.Backend.security.JwtAuthenticationFilter;

@Configuration	//This class defines Spring Beans.
@EnableWebSecurity	//This enables Spring Security for your project.
@EnableMethodSecurity
//@EnableWebMvc
public class SecurityConfig
{
	public static final String[] PUBLIC_URLS= {
			"/api/v1/auth/**",
			"/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**"
	};
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	//What happens when unauthorized user tries to access protected API.
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	//This is your custom filter that: Extracts JWT, Validates JWT, Sets authentication in context
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	//Basic Authentication
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())	//Because JWT is stateless. CSRF protection is needed for session-based auth.
            .authorizeHttpRequests(auth -> auth		//Any other request → must be authenticated
                    .requestMatchers(PUBLIC_URLS).permitAll()
                    .requestMatchers(HttpMethod.GET, "/**").permitAll()
                    .anyRequest().authenticated()
            )
            //If user is not authenticated: Instead of default login page, Spring calls your custom:
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )
            //It tells Spring: Do NOT create HttpSession. Do NOT store authentication in session. Every request must carry JWT.
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        //Run your JWT filter BEFORE Spring’s default login filter
        //becoz You are not using form login, You authenticate via JWT, So your filter must run first
        http.addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
	
	//This tells Spring: When authenticating a user: Use customUserDetailService, Use BCryptPasswordEncoder
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		//auth.userDetailsService(customUserDetailService).passwordEncoder(null);
		auth.userDetailsService(customUserDetailService)
	    .passwordEncoder(passwordEncoder());
	}
	
	//This is used: When saving user password, When verifying login password
	//BCrypt automatically:Hashes password, Adds salt, Makes brute force difficult
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
	    return configuration.getAuthenticationManager();
	}
	
}
