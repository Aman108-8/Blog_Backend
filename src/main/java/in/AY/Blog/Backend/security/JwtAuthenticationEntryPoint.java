package in.AY.Blog.Backend.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component	//Create an object of this class and manage it as a Bean then it will be ready to inject in security config exception handling
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint	//Start authentication process when user is unauthenticated.
{
	//it will execute when unauthorize person try to access our authroirize resources
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// This sends HTTP response: (SC_UNAUTHORIZED)401 code and message: access denied
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
	}

}


/*
Where is it called internally?
1. Inside Spring Security filter chain:
a. ExceptionTranslationFilter
	That filter catches:
	i) AuthenticationException
	ii) AccessDeniedException

b. If user is not authenticated → it calls:
i.) authenticationEntryPoint.commence(...)
ii) So your method is triggered from inside Spring Security.

c. commence method automatically called by Spring when:

❌ No token
❌ Invalid token
❌ Expired token
❌ User not authenticated

d.AuthenticationException authException
- This contains: Why authentication failed.


*/