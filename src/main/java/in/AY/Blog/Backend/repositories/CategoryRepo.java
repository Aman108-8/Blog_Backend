package in.AY.Blog.Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.AY.Blog.Backend.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
	
}
