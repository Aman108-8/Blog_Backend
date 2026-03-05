package in.AY.Blog.Backend.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryDto {
	
	private Integer id;
	@NotBlank
	@Size(min=3, message="minimum Character 10")
	private String CategoryTitle;
	@NotBlank
	@Size(min = 4, message="minimum Character 10")
	private String CategoryDescription;
	
	public CategoryDto() {
		// TODO Auto-generated constructor stub
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategoryTitle() {
		return CategoryTitle;
	}
	public void setCategoryTitle(String categoryTitle) {
		CategoryTitle = categoryTitle;
	}
	public String getCategoryDescription() {
		return CategoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		CategoryDescription = categoryDescription;
	}

}
