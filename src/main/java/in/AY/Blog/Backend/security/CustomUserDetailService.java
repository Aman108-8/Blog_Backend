package in.AY.Blog.Backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.AY.Blog.Backend.entities.User;
import in.AY.Blog.Backend.exception.ResourceNotFoundException;
import in.AY.Blog.Backend.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	private UserRepo ur;

	
	  @Override 
	  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	  { 
		  //loading user from database by UserName User
		  User user = this.ur.findByEmail(username).orElseThrow(()-> new
		  ResourceNotFoundException("User", "User id", username));
		  
		  return user; 
	  }
	 

	/*@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = this.ur.findByName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

		return user; // if your User implements UserDetails
	}*/

}
