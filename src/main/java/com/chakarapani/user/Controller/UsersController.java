package com.chakarapani.user.Controller;

import com.chakarapani.base.Entity.Users;
import com.chakarapani.user.Service.UsersServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.chakarapani.base.Constants.Constants.*;

@RestController
@RequestMapping(USERSPATH)
@SuppressWarnings("unused")
@CrossOrigin(value = GATEWAYURL)
public class UsersController {

	@Autowired
	private UsersServiceImpl usersService;

	@GetMapping("/all")
	@ApiResponses(value = {
			@ApiResponse(description = SUCCESSMESSAGE, useReturnTypeSchema = true, responseCode = RESPONSE200,
					content = @Content(mediaType = CONTENTTYPE))
	})
	@Parameters(value = {
			@Parameter(name = HEADERCORRELEATIONTITLE, in = ParameterIn.HEADER, required = true, example = HEADERCORRELATIONEXAMPLE),
			@Parameter(name = HEADERCOUNTRYTITLE, in = ParameterIn.HEADER, required = true, example = HEADERCOUNTRYEXAMPLE)
	})
	public ResponseEntity<Object> getAllUsers(@RequestHeader Map<String, String> headers) {
		return usersService.getAllUsers(headers);
	}

	@PostMapping("/save")
	@ApiResponses(value = {
			@ApiResponse(description = SUCCESSMESSAGE, useReturnTypeSchema = true, responseCode = RESPONSE200,
					content = @Content(mediaType = CONTENTTYPE))
	})
	@Parameters(value = {
			@Parameter(name = HEADERCORRELEATIONTITLE, in = ParameterIn.HEADER, required = true, example = HEADERCORRELATIONEXAMPLE),
			@Parameter(name = HEADERCOUNTRYTITLE, in = ParameterIn.HEADER, required = true, example = HEADERCOUNTRYEXAMPLE)
	})
	public ResponseEntity<Object> saveUser(@RequestHeader Map<String, String> headers, @RequestBody Users user) {
		return usersService.saveUser(headers, user);
	}

	@GetMapping("/user")
	@ApiResponses(value = {
			@ApiResponse(description = SUCCESSMESSAGE, useReturnTypeSchema = true, responseCode = RESPONSE200,
					content = @Content(mediaType = CONTENTTYPE))
	})
	@Parameters(value = {
			@Parameter(name = HEADERCORRELEATIONTITLE, in = ParameterIn.HEADER, required = true, example = HEADERCORRELATIONEXAMPLE),
			@Parameter(name = HEADERCOUNTRYTITLE, in = ParameterIn.HEADER, required = true, example = HEADERCOUNTRYEXAMPLE)
	})
	public ResponseEntity<Object> getUserByUserName(@RequestHeader Map<String, String> headers, @RequestParam("username") String username) {
		return usersService.getUserByUserName(headers, username);
	}

	@GetMapping("/email")
	@ApiResponses(value = {
			@ApiResponse(description = SUCCESSMESSAGE, useReturnTypeSchema = true, responseCode = RESPONSE200,
					content = @Content(mediaType = CONTENTTYPE))
	})
	@Parameters(value = {
			@Parameter(name = HEADERCORRELEATIONTITLE, in = ParameterIn.HEADER, required = true, example = HEADERCORRELATIONEXAMPLE),
			@Parameter(name = HEADERCOUNTRYTITLE, in = ParameterIn.HEADER, required = true, example = HEADERCOUNTRYEXAMPLE)
	})
	public ResponseEntity<Object> getUserByEmail(@RequestHeader Map<String, String> headers, @RequestParam("email") String email) {
		return usersService.getUserByEmail(headers, email);
	}

	@DeleteMapping("/user")
	@ApiResponses(value = {
			@ApiResponse(description = SUCCESSMESSAGE, useReturnTypeSchema = true, responseCode = RESPONSE200,
					content = @Content(mediaType = CONTENTTYPE))
	})
	@Parameters(value = {
			@Parameter(name = HEADERCORRELEATIONTITLE, in = ParameterIn.HEADER, required = true, example = HEADERCORRELATIONEXAMPLE),
			@Parameter(name = HEADERCOUNTRYTITLE, in = ParameterIn.HEADER, required = true, example = HEADERCOUNTRYEXAMPLE)
	})
	public ResponseEntity<Object> deleteUserByUserName(@RequestHeader Map<String, String> headers,
	                                                   @RequestParam("username") String username) {
		return usersService.deleteUserbyUsername(headers, username);
	}

	@DeleteMapping("/clear")
	@ApiResponses(value = {
			@ApiResponse(description = SUCCESSMESSAGE, useReturnTypeSchema = true, responseCode = RESPONSE200,
					content = @Content(mediaType = CONTENTTYPE))
	})
	@Parameters(value = {
			@Parameter(name = HEADERCORRELEATIONTITLE, in = ParameterIn.HEADER, required = true, example = HEADERCORRELATIONEXAMPLE),
			@Parameter(name = HEADERCOUNTRYTITLE, in = ParameterIn.HEADER, required = true, example = HEADERCOUNTRYEXAMPLE)
	})
	public ResponseEntity<Object> deleteAllUsers(@RequestHeader Map<String, String> headers) {
		return usersService.deleteAllUsers(headers);
	}
}
