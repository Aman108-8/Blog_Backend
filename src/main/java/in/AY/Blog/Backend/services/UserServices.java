package in.AY.Blog.Backend.services;

import java.util.List;

import in.AY.Blog.Backend.payloads.UserDto;

public interface UserServices {
	
	UserDto registerNewUser(UserDto user);
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user, Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
}
