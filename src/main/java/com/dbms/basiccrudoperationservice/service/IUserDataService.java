package com.dbms.basiccrudoperationservice.service;

import com.dbms.basiccrudoperationservice.domain.EmailData;
import com.dbms.basiccrudoperationservice.domain.UserData;

public interface IUserDataService {

    UserData addUserData(UserData userData) throws Exception;

    UserData getUserByEmailId(String  userEmail);
    UserData updateUserData(UserData userData, String userEmail, int id) throws Exception;
    boolean deleteUserData(String userEmail, int userId) throws Exception;

    UserData loginUser(String userEmail, String userPassword) throws Exception;

    int generateOtp(String userEmail);


}
