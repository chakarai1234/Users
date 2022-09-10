package com.chakarapani.user.Service;

import com.chakarapani.base.Entity.Users;
import com.chakarapani.base.Enums.Message;
import com.chakarapani.base.Response.Response;
import com.chakarapani.user.Repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {

	@Value("${header-missing-value}")
	String headerMissingValue;

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public ResponseEntity<Object> getAllUsers(@NotNull Map<String, String> headers) {
		String xCorrId = headers.get("x-correlation-id");
		String country = headers.get("country");
		List<Users> allUsers = usersRepository.findAll();
		if (xCorrId == null || country == null) {
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST, headerMissingValue);
		}
		return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, allUsers);
	}

	@Override
	public ResponseEntity<Object> saveUser(@NotNull Map<String, String> headers, @NotNull Users user) {
		String xCorrId = headers.get("x-correlation-id");
		String country = headers.get("country");
		String localMessage;
		if (user.getDateOfBirth() == null) {
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.NOT_ACCEPTABLE,
					"Please Date of Birth is Must");
		} else if (xCorrId == null || country == null) {
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST, headerMissingValue);
		} else {
			try {
				usersRepository.save(user);
				return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, user);
			} catch (Exception e) {
				if (e.getMessage().contains("EMAILCONSTRAINT")) {
					localMessage = "Email already exists";
				} else if (e.getMessage().contains("USERNAMECONSTRAINT")) {
					localMessage = "Username already exists";
				} else {
					localMessage = "";
				}
				return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, localMessage);
			}
		}
	}

	@Override
	public ResponseEntity<Object> getUserByUserName(@NotNull Map<String, String> headers, @NotNull String username) {
		String xCorrId = headers.get("x-correlation-id");
		String country = headers.get("country");
		if (username.equals("")) {
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.NOT_ACCEPTABLE,
					"Please enter the username");
		} else if (xCorrId == null || country == null) {
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST, headerMissingValue);
		} else {
			Users user = usersRepository.findByUsername(username);
			if (user == null) {
				return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, "User doesn't exit");
			} else {
				return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, user);
			}
		}
	}

	@Override
	public ResponseEntity<Object> getUserByEmail(@NotNull Map<String, String> headers, @NotNull String email) {
		String xCorrId = headers.get("x-correlation-id");
		String country = headers.get("country");
		if (email.equals("")) {
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.NOT_ACCEPTABLE,
					"Please enter the email");
		} else if (xCorrId == null || country == null) {
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST, headerMissingValue);
		} else {
			Users user = usersRepository.findByEmail(email);
			if (user == null) {
				return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, "User doesn't exit");
			} else {
				return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, user);
			}
		}
	}

	@Override
	public ResponseEntity<Object> deleteUserbyUsername(@NotNull Map<String, String> headers, @NotNull String username) {
		String xCorrId = headers.get("x-correlation-id");
		String country = headers.get("country");
		if (username.equals("")) {
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.NOT_ACCEPTABLE,
					"Please enter the username");
		} else if (xCorrId == null || country == null) {
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST, headerMissingValue);
		} else {
			Users user = usersRepository.findByUsername(username);
			if (user == null) {
				return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, "User doesn't exit");
			} else {
				usersRepository.deleteById(user.getId());
				return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK,
						"Deleted the user " + user.getUsername());
			}
		}
	}

	@Override
	public ResponseEntity<Object> deleteAllUsers(@NotNull Map<String, String> headers) {
		String xCorrId = headers.get("x-correlation-id");
		String country = headers.get("country");
		if (xCorrId == null || country == null) {
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST, headerMissingValue);
		} else {
			usersRepository.deleteAll();
			return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, "Cleared Database");
		}
	}
}
