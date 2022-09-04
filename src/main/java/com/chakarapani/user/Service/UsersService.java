package com.chakarapani.user.Service;

import com.chakarapani.base.Entity.Users;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UsersService {

    ResponseEntity<Object> getAllUsers(Map<String, String> headers);

    ResponseEntity<Object> saveUser(Map<String, String> headers, Users user);

    ResponseEntity<Object> getUserByUserName(Map<String, String> headers, String username);

    ResponseEntity<Object> getUserByEmail(Map<String, String> headers, String email);

    ResponseEntity<Object> deleteUserbyUsername(Map<String, String> headers, String username);

    ResponseEntity<Object> deleteAllUsers(Map<String, String> headers);

}
