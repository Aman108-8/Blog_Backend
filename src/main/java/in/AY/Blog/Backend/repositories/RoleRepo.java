package in.AY.Blog.Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.AY.Blog.Backend.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
