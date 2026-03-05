package in.AY.Blog.Backend.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.AY.Blog.Backend.payloads.ApiException;
import in.AY.Blog.Backend.payloads.ApiResponse;

@RestControllerAdvice	//Through this we can handle global Exception Handling. and wher we are working with api then adding restControllerAdvice otherwise only COntrolllerAdvice

public class GlobalExceptionHandler 
{
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourcesNotFoundException(ResourceNotFoundException rex){
		 String message = rex.getMessage();
		 ApiResponse apiResp = new ApiResponse(message, false);
		 return new ResponseEntity<ApiResponse>(apiResp, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
		//remove fiels, messages then map the handlerMessage
		Map<String, String> resp = new HashMap();
		//first we get the message from Exception then get all errors then we get list of all errors after this we transverse then we fetch the error
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String field = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			resp.put(field, message);
		});
		return new ResponseEntity<Map<String,String>>(resp, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> invalidLogin(ApiException rex){
		 String message = rex.getMessage();
		 ApiResponse apiResp = new ApiResponse(message, false);
		 return new ResponseEntity<ApiResponse>(apiResp, HttpStatus.BAD_REQUEST);
	}
}