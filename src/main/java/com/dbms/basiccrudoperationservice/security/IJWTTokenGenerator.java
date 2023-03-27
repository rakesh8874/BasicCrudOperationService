package com.dbms.basiccrudoperationservice.security;

import com.dbms.basiccrudoperationservice.domain.UserData;

import java.util.Map;

public interface IJWTTokenGenerator {

    Map<String, String> generateJWTToken(UserData userData);

}
