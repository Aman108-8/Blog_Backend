package in.AY.Blog.Backend.payloads;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDto {
	private int id;
	
	@NotEmpty
	@Size(max = 10, message="minimum Character 10")
	private String name;
	
	@Email(message = "your email Address is not valid")
	private String email;
	
	@NotNull
	@Size(min =8, message="password must be more than 3 chacrcter and include .,#,0-9,A-Z")
	@Pattern(regexp = "^[A-Z0-9]+$")
	private String password;
	
	private String about;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}

	private Set<RoleDto> roles = new HashSet<>();

	public Set<RoleDto> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleDto> roles) {
		this.roles = roles;
	}
	
}
