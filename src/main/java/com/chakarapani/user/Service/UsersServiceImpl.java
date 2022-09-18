package com.chakarapani.user.Service;

import com.chakarapani.base.Entity.Users;
import com.chakarapani.base.Enums.Message;
import com.chakarapani.base.Response.Response;
import com.chakarapani.user.Repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.chakarapani.base.Constants.Constants.*;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public ResponseEntity<Object> getAllUsers(@NotNull Map<String, String> headers) {
		String xCorrId = headers.get(HEADERCORRELEATIONTITLE);
		String country = headers.get(HEADERCOUNTRYTITLE);
		List<Users> allUsers = usersRepository.findAll();
		if (xCorrId == null || country == null) {
			log.error(HEADERSMISSINGVALUE);
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST, HEADERSMISSINGVALUE);
		}
		log.info(allUsers.toString(), StructuredArguments.entries(headers));
		return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, allUsers);
	}

	@Override
	public ResponseEntity<Object> saveUser(@NotNull Map<String, String> headers, @NotNull Users user) {
		String xCorrId = headers.get(HEADERCORRELEATIONTITLE);
		String country = headers.get(HEADERCOUNTRYTITLE);
		String localMessage;
		if (user.getDateOfBirth() == null) {
			log.error("Date of birth is a must for Registering User");
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.NOT_ACCEPTABLE, "Please Date of Birth is Must");
		} else if (xCorrId == null || country == null) {
			log.error(HEADERSMISSINGVALUE);
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST, HEADERSMISSINGVALUE);
		} else {
			try {
				log.info(user.toString(), StructuredArguments.entries(headers));
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
				log.error(localMessage);
				return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, localMessage);
			}
		}
	}

	@Override
	public ResponseEntity<Object> getUserByUserName(@NotNull Map<String, String> headers, @NotNull String username) {
		String xCorrId = headers.get(HEADERCORRELEATIONTITLE);
		String country = headers.get(HEADERCOUNTRYTITLE);
		if (username.equals("")) {
			log.error("Username is missing for getUserByUsername");
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.NOT_ACCEPTABLE, "Please enter the username");
		} else if (xCorrId == null || country == null) {
			log.error(HEADERSMISSINGVALUE);
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST, HEADERSMISSINGVALUE);
		} else {
			Users user = usersRepository.findByUsername(username);
			if (user == null) {
				log.error("The User is not found in the database");
				return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, "User doesn't exit");
			} else {
				log.info(user.toString(), StructuredArguments.entries(headers));
				return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, user);
			}
		}
	}

	@Override
	public ResponseEntity<Object> getUserByEmail(@NotNull Map<String, String> headers, @NotNull String email) {
		String xCorrId = headers.get(HEADERCORRELEATIONTITLE);
		String country = headers.get(HEADERCOUNTRYTITLE);
		if (email.equals("")) {
			log.error("Email is missing for getUserByEmail");
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.NOT_ACCEPTABLE, "Please enter the email");
		} else if (xCorrId == null || country == null) {
			log.error(HEADERSMISSINGVALUE);
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST, HEADERSMISSINGVALUE);
		} else {
			Users user = usersRepository.findByEmail(email);
			if (user == null) {
				log.error("User is not present in the database");
				return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, "User doesn't exit");
			} else {
				log.info(user.toString(), StructuredArguments.entries(headers));
				return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, user);
			}
		}
	}

	@Override
	public ResponseEntity<Object> deleteUserbyUsername(@NotNull Map<String, String> headers, @NotNull String username) {
		String xCorrId = headers.get(HEADERCORRELEATIONTITLE);
		String country = headers.get(HEADERCOUNTRYTITLE);
		if (username.equals("")) {
			log.error("Username is missing for deleteUserbyUsername");
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.NOT_ACCEPTABLE, "Please enter the username");
		} else if (xCorrId == null || country == null) {
			log.error(HEADERSMISSINGVALUE);
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST, HEADERSMISSINGVALUE);
		} else {
			Users user = usersRepository.findByUsername(username);
			if (user == null) {
				log.error("User is not present in the database");
				return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, "User doesn't exit");
			} else {
				log.info("User is deleted in the database, Username: " + user.getUsername(), StructuredArguments.entries(headers));
				usersRepository.deleteById(user.getId());
				return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, "Deleted the user " + user.getUsername());
			}
		}
	}

	@Override
	public ResponseEntity<Object> deleteAllUsers(@NotNull Map<String, String> headers) {
		String xCorrId = headers.get(HEADERCORRELEATIONTITLE);
		String country = headers.get(HEADERCOUNTRYTITLE);
		if (xCorrId == null || country == null) {
			log.error(HEADERSMISSINGVALUE);
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST, HEADERSMISSINGVALUE);
		} else {
			log.info("All the users have been deleted in the database", StructuredArguments.entries(headers));
			usersRepository.deleteAll();
			return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, "Cleared Database");
		}
	}
}
