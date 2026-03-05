package in.AY.Blog.Backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.AY.Blog.Backend.entities.User;
import java.util.List;


public interface UserRepo extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);
	Optional<User> findByName(String username);
}
