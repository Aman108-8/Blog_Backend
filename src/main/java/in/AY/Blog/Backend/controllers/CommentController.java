package in.AY.Blog.Backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.AY.Blog.Backend.entities.Comment;
import in.AY.Blog.Backend.payloads.ApiResponse;
import in.AY.Blog.Backend.payloads.CommentDto;
import in.AY.Blog.Backend.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService cs;
	
	@PostMapping("/posts/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commDto, @PathVariable("postId") Integer postId){
		CommentDto commentDto = cs.addComment(commDto, postId);
		return new ResponseEntity<CommentDto>(commentDto, HttpStatus.OK);
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId){
		cs.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", true),HttpStatus.OK);
	}
}
