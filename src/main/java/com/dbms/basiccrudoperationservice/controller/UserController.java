package com.dbms.basiccrudoperationservice.controller;

import com.dbms.basiccrudoperationservice.domain.EmailData;
import com.dbms.basiccrudoperationservice.domain.UserData;
import com.dbms.basiccrudoperationservice.security.IJWTTokenGenerator;
import com.dbms.basiccrudoperationservice.service.IUserDataService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("user-service")
@CrossOrigin("http://localhost:4200/")
public class UserController {

    private final IUserDataService userDataService;
    private final IJWTTokenGenerator ijwtTokenGenerator;
    @Autowired
    public UserController(IUserDataService userDataService, IJWTTokenGenerator ijwtTokenGenerator) {
        this.userDataService = userDataService;
        this.ijwtTokenGenerator = ijwtTokenGenerator;
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUserDetails(@RequestBody UserData userData) {
        try {
            return new ResponseEntity<>(userDataService.addUserData(userData), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.ok("User Already Exist On Given Email Id");
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserData> getCurrentUser(HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String userEmail = claims.getSubject();
        return new ResponseEntity<>(userDataService.getUserByEmailId(userEmail), HttpStatus.OK);
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<?> updateUserDetails(@RequestBody UserData userData, @PathVariable int userId, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String userEmail = claims.getSubject();
        try {
            return new ResponseEntity<>(userDataService.updateUserData(userData, userEmail, userId), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.ok("User Doesn't Exist");
        }
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String userEmail = claims.getSubject();
        try {
            return new ResponseEntity<>(userDataService.deleteUserData(userEmail,userId), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.ok("User Doesn't Exist");
        }
    }

    @PostMapping("/login")
    ResponseEntity<?> loginUser(@RequestBody UserData userData){
        UserData loginUser = null;
        try {
            loginUser = userDataService.loginUser(userData.getUserEmail(), userData.getPassword());
        if(loginUser != null){
            Map<String, String> map = ijwtTokenGenerator.generateJWTToken(loginUser);
            return  new ResponseEntity<>(map, HttpStatus.OK);
        }
        } catch (Exception e) {
             return ResponseEntity.ok("User Doesn't Exist");
        }
        return new ResponseEntity<>("Invalid User Id Or Password", HttpStatus.NOT_FOUND);
    }

     @GetMapping("/getOtp/{userEmail}")
      public ResponseEntity<?> getRegistrationOTP(@PathVariable String userEmail){
        return new ResponseEntity<>(userDataService.generateOtp(userEmail), HttpStatus.CREATED);
       }

}
