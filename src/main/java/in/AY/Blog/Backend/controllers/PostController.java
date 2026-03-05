package in.AY.Blog.Backend.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.AY.Blog.Backend.config.AppConstants;
import in.AY.Blog.Backend.payloads.ApiResponse;
import in.AY.Blog.Backend.payloads.PostDto;
import in.AY.Blog.Backend.payloads.PostResponse;
import in.AY.Blog.Backend.services.FileService;
import in.AY.Blog.Backend.services.PostService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService ps;
	
	@Autowired
	private FileService fs;  
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto, 
			@PathVariable("userId") Integer userId, 
			@PathVariable("categoryId") Integer categoryId)
	{
		PostDto createPost = ps.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(
			@PathVariable("userId") Integer userId)
	{
		List<PostDto> getUser = ps.getpostByUser(userId);
		return new ResponseEntity<List<PostDto>>(getUser, HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(
			@PathVariable("categoryId") Integer categoryId)
	{
		List<PostDto> getCategory = ps.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(getCategory, HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir)
	{
		
		PostResponse posts = ps.getAllPost(pageSize, pageNumber, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId){
		PostDto post= ps.getPostById(postId);
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@PathVariable("postId") Integer postId, @RequestBody PostDto postDto){
		PostDto posts = this.ps.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(posts, HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable("postId") Integer postId){
		this.ps.DeletePost(postId);
		return new ApiResponse("Selected Post Deleted", true);
	}
	
	@GetMapping("/posts/search/{search}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("search") String search){
		List<PostDto> result = ps.searchPost(search);
		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image, 
			@PathVariable("postId") Integer postId) throws IOException
	{
		String fileName= this.fs.uploadImage(path, image);
		PostDto postDto = this.ps.getPostById(postId);
		postDto.setImageName(fileName);
		PostDto updatePost = ps.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
	@GetMapping(value = "posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException{
		InputStream resource = this.fs.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
}
