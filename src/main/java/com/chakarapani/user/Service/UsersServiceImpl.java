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
    public ResponseEntity<Object> getAllUsers(Map<String, String> headers) {
        List<Users> allUsers = usersRepository.findAll();
        if (headers.get("x-correlation-id") == null || headers.get("country") == null) {
            return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST, headerMissingValue);
        }
        return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, allUsers);
    }

    @Override
    public ResponseEntity<Object> saveUser(Map<String, String> headers, @NotNull Users user) {
        String localMessage;
        if (user.getDateOfBirth() == null) {
            return Response.generateResponse(Message.FAILURE, headers, HttpStatus.NOT_ACCEPTABLE, "Please Date of Birth is Must");
        } else if (headers.get("x-correlation-id") == null || headers.get("country") == null) {
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
    public ResponseEntity<Object> getUserByUserName(Map<String, String> headers, @NotNull String username) {
        if (username.equals("")) {
            return Response.generateResponse(Message.FAILURE, headers, HttpStatus.NOT_ACCEPTABLE, "Please enter the username");
        } else if (headers.get("x-correlation-id") == null || headers.get("country") == null) {
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
    public ResponseEntity<Object> getUserByEmail(Map<String, String> headers, @NotNull String email) {
        if (email.equals("")) {
            return Response.generateResponse(Message.FAILURE, headers, HttpStatus.NOT_ACCEPTABLE, "Please enter the email");
        } else if (headers.get("x-correlation-id") == null || headers.get("country") == null) {
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
    public ResponseEntity<Object> deleteUserbyUsername(Map<String, String> headers, @NotNull String username) {
        if (username.equals("")) {
            return Response.generateResponse(Message.FAILURE, headers, HttpStatus.NOT_ACCEPTABLE, "Please enter the username");
        } else if (headers.get("x-correlation-id") == null || headers.get("country") == null) {
            return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST, headerMissingValue);
        } else {
            Users user = usersRepository.findByUsername(username);
            if (user == null) {
                return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK, "Username doesn't exist");
            } else {
                usersRepository.deleteById(user.getId());
                return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.FOUND, "Deleted the user " + user.getUsername());
            }
        }
    }

    @Override
    public ResponseEntity<Object> deleteAllUsers(Map<String, String> headers) {
        if (headers.get("x-correlation-id") == null || headers.get("country") == null) {
            return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST, headerMissingValue);
        } else {
            usersRepository.deleteAll();
            return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.FOUND, "Cleared Database");
        }
    }
}
