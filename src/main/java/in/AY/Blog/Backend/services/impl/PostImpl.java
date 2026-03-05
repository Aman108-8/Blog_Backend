package in.AY.Blog.Backend.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import in.AY.Blog.Backend.entities.Category;
import in.AY.Blog.Backend.entities.Post;
import in.AY.Blog.Backend.entities.User;
import in.AY.Blog.Backend.exception.ResourceNotFoundException;
import in.AY.Blog.Backend.payloads.PostDto;
import in.AY.Blog.Backend.payloads.PostResponse;
import in.AY.Blog.Backend.payloads.UserDto;
import in.AY.Blog.Backend.repositories.CategoryRepo;
import in.AY.Blog.Backend.repositories.PostRepo;
import in.AY.Blog.Backend.repositories.UserRepo;
import in.AY.Blog.Backend.services.PostService;

@Service
public class PostImpl implements PostService{

	@Autowired
	private PostRepo pr;
	@Autowired
	private ModelMapper mm;
	
	@Autowired
	private UserRepo ur;
	@Autowired
	private CategoryRepo cr;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.ur.findById(userId).orElseThrow(() ->new ResourceNotFoundException("User", "User Id", userId));
		Category category = this.cr.findById(categoryId).orElseThrow(() ->new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		Post post = this.mm.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		
		Post newPost = pr.save(post);
		return mm.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.pr.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","post Id", postId));
		post.setTitle(postDto.getTitle());
	    post.setContent(postDto.getContent());
	    post.setImageName(postDto.getImageName());
	    
		pr.save(post);
		return this.mm.map(post, PostDto.class);
	}

	@Override
	public void DeletePost(Integer postId) {
		Post posts = this.pr.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","post Id", postId));
		pr.delete(posts);
	}

	@Override
	public PostResponse getAllPost(Integer pageSize, Integer pageNumber,String sortBy, String sortDir) {
		Sort sort =(sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending());
		
		/*if(sortDir.equalsIgnoreCase("asc"))
			sort = Sort.by(sortBy).ascending();
		else
			sort = Sort.by(sortBy).descending();*/
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
				
		Page<Post> pagePosts = this.pr.findAll(p);
		List<Post> allPost = pagePosts.getContent();
		List<PostDto> postDto = allPost.stream().map(post->this.mm.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResp = new PostResponse();
		
		postResp.setContent(postDto);
		postResp.setPageNumber(pagePosts.getNumber());
		postResp.setPageSize(pagePosts.getSize());
		postResp.setTotalElement(pagePosts.getTotalElements());
		postResp.setTotalPage(pagePosts.getTotalPages());
		postResp.setLastPage(pagePosts.isLast());
		
		return postResp;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post posts = this.pr.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		PostDto postDto = mm.map(posts, PostDto.class);
		return postDto;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category cat = cr.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		List<Post> posts = this.pr.findByCategory(cat);
		List<PostDto> postDtos = posts.stream().map(post-> this.mm.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getpostByUser(Integer userId) {
		User user = ur.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
		List<Post> posts= pr.findByUser(user);
		List<PostDto> postDtos = posts.stream()
		        .map(post -> this.mm.map(post, PostDto.class))
		        .collect(Collectors.toList());

		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPost(String search) {
		List<Post> posts = this.pr.searchByTitle("%"+search+"%");
		List<PostDto> postDto = posts.stream()
				.map(post-> this.mm.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDto;
	}

}
