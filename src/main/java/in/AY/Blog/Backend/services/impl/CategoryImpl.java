package in.AY.Blog.Backend.services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.AY.Blog.Backend.entities.Category;
import in.AY.Blog.Backend.exception.ResourceNotFoundException;
import in.AY.Blog.Backend.payloads.CategoryDto;
import in.AY.Blog.Backend.repositories.CategoryRepo;
import in.AY.Blog.Backend.services.CategoryService;

@Service
public class CategoryImpl implements CategoryService{

	@Autowired
	private CategoryRepo cr;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = this.DtoToCategoty(categoryDto);
		Category SaveCat = this.cr.save(cat);
		return categoryToDto(SaveCat);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		//Category cat = this.DtoToCategoty(categoryDto);
		Category cat = this.cr.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCat = this.cr.save(cat);
		return categoryToDto(updatedCat);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.cr.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		cr.deleteById(categoryId);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
	    Category cat = this.cr.findById(categoryId)
	            .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

	    return modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> AllCategory= this.cr.findAll();
		List<CategoryDto> AllCategoryDto =AllCategory.stream()
				.map((cat)-> this.modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		return AllCategoryDto;
	}

	public Category DtoToCategoty(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		return category;
		
	}
	public CategoryDto categoryToDto(Category category) {
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		return categoryDto;
		
	}
}
