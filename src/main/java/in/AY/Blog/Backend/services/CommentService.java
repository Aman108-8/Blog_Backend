package in.AY.Blog.Backend.services;

import in.AY.Blog.Backend.entities.Comment;
import in.AY.Blog.Backend.payloads.CommentDto;

public interface CommentService {
	public CommentDto addComment(CommentDto commentDto, Integer psotId);
	public void deleteComment(Integer id);
}
