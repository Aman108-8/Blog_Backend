package in.AY.Blog.Backend.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import in.AY.Blog.Backend.entities.Category;
import in.AY.Blog.Backend.entities.Comment;
import in.AY.Blog.Backend.entities.User;


public class PostDto 
{
	private Integer postId;
	private String title;
	private String content;
	private String imageName;
	private Date addDate;
	private CategoryDto category;
	private UserDto user;
	private Set<CommentDto> CommentDto = new HashSet<>();
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public CategoryDto getCategory() {
		return category;
	}
	public void setCategory(CategoryDto category) {
		this.category = category;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public Set<CommentDto> getComment() {
		return CommentDto;
	}
	public void setComment(Set<CommentDto> CommentDto) {
		this.CommentDto = CommentDto;
	}
	
	
}
