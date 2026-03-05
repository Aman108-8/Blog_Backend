package in.AY.Blog.Backend.services;

import java.util.List;

import in.AY.Blog.Backend.entities.Post;
import in.AY.Blog.Backend.payloads.PostDto;
import in.AY.Blog.Backend.payloads.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	void DeletePost(Integer postId);
	
	PostDto getPostById(Integer postId);
	
	List<PostDto> getPostByCategory(Integer categoryId);
	
	List<PostDto> getpostByUser(Integer userID);
	
	List<PostDto> searchPost(String search);

	PostResponse getAllPost(Integer pageSize, Integer pageNumber,String sortBy, String sortDir);
}
