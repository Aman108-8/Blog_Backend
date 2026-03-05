package in.AY.Blog.Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.AY.Blog.Backend.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
