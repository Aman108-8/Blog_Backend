package in.AY.Blog.Backend.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.AY.Blog.Backend.config.AppConstants;
import in.AY.Blog.Backend.entities.Role;
import in.AY.Blog.Backend.entities.User;
import in.AY.Blog.Backend.exception.ResourceNotFoundException;
import in.AY.Blog.Backend.payloads.UserDto;
import in.AY.Blog.Backend.repositories.RoleRepo;
import in.AY.Blog.Backend.repositories.UserRepo;
import in.AY.Blog.Backend.services.UserServices;

@Service
public class UserImpl implements UserServices{

	@Autowired
	private UserRepo ur;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RoleRepo rr;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		//String encode = passwordEncoder.encode(user.getPassword());
		//user.setPassword(encode);
		User savedUser = this.ur.save(user);
		return this.UsertoDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.ur.findById(userId)
				.orElseThrow((()-> new ResourceNotFoundException("User", " Id ",userId)));
		
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		
		User updateUser = this.ur.save(user);
		UserDto userDto1 = this.UsertoDto(updateUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = ur.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", " Id ", userId));
		
		return this.UsertoDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users= ur.findAll();
		
		List<UserDto> userDtos = (List<UserDto>) users.stream().map(user->this.UsertoDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.ur.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," Id ", userId));
		
		this.ur.delete(user);
	}

	private User dtoToUser(UserDto userDto) 
	{
		//Conversion from DTO object to sql Object
		User user = this.modelMapper.map(userDto, User.class);
		
		/*User user = new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());*/
		return user;
	}
	
	private UserDto UsertoDto(User user) 
	{
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		//set roles
		Role role = this.rr.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = this.ur.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}
}
