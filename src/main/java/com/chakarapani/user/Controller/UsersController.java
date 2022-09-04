package com.chakarapani.user.Controller;

import com.chakarapani.base.Entity.Users;
import com.chakarapani.user.Service.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@SuppressWarnings("unused")
public class UsersController {

    @Autowired
    private UsersServiceImpl usersService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers(@RequestHeader Map<String, String> headers) {
        return usersService.getAllUsers(headers);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveUser(@RequestHeader Map<String, String> headers, @RequestBody Users user) {
        return usersService.saveUser(headers, user);
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUserByUserName(@RequestHeader Map<String, String> headers, @RequestParam("username") String username) {
        return usersService.getUserByUserName(headers, username);
    }

    @GetMapping("/email")
    public ResponseEntity<Object> getUserByEmail(@RequestHeader Map<String, String> headers, @RequestParam("email") String email) {
        return usersService.getUserByEmail(headers, email);
    }

    @DeleteMapping("/user")
    public ResponseEntity<Object> deleteUserByUserName(@RequestHeader Map<String, String> headers, @RequestParam("username") String username) {
        return usersService.deleteUserbyUsername(headers, username);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Object> deleteAllUsers(@RequestHeader Map<String, String> headers) {
        return usersService.deleteAllUsers(headers);
    }
}
