package in.AY.Blog.Backend.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.AY.Blog.Backend.payloads.ApiResponse;
import in.AY.Blog.Backend.payloads.UserDto;
import in.AY.Blog.Backend.services.UserServices;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController 
{
	@Autowired
	private UserServices us;
	
	//post
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto CreateUserDto = this.us.createUser(userDto);
		return new ResponseEntity<>(CreateUserDto, HttpStatus.CREATED);
	}
	
	//put
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uId){
		UserDto updatedUser = this.us.updateUser(userDto, uId);
		return ResponseEntity.ok(updatedUser);
	}
	
	//Admin Delete
	@PreAuthorize("hasRole('ADMIN')")
	//delete
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uId){
		this.us.deleteUser(uId); 
		//return new ResponseEntity(Map.of("Message","User Deleted Successfully"), HttpStatus.OK );
		return new ResponseEntity<>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK );
	}
	
	//get
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUer(){
		return ResponseEntity.ok(this.us.getAllUsers());
	}
	
	//get
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getAllUer(@PathVariable Integer userId){
		return ResponseEntity.ok(this.us.getUserById(userId));
	}
}
