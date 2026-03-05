package in.AY.Blog.Backend.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.AY.Blog.Backend.entities.Comment;
import in.AY.Blog.Backend.entities.Post;
import in.AY.Blog.Backend.exception.ResourceNotFoundException;
import in.AY.Blog.Backend.payloads.CommentDto;
import in.AY.Blog.Backend.repositories.CommentRepo;
import in.AY.Blog.Backend.repositories.PostRepo;
import in.AY.Blog.Backend.services.CommentService;

@Service
public class CommentImpl implements CommentService{

	@Autowired
	CommentRepo cr;
	
	@Autowired
	PostRepo pr;
	
	@Autowired
	ModelMapper mm;
	
	@Override
	public CommentDto addComment(CommentDto commentDto, Integer postId) {
		Post post = pr.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		Comment comment = mm.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = cr.save(comment);
		return mm.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = cr.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment Id", commentId));
		cr.delete(comment);
	}

}
