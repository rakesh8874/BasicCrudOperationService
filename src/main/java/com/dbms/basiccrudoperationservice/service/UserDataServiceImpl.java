package com.dbms.basiccrudoperationservice.service;

import com.dbms.basiccrudoperationservice.domain.EmailData;
import com.dbms.basiccrudoperationservice.domain.UserData;
import com.dbms.basiccrudoperationservice.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserDataServiceImpl implements IUserDataService{

    private final UserDataRepository userDataRepository;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    public UserDataServiceImpl(UserDataRepository userDataRepository, JavaMailSender javaMailSender) {
        this.userDataRepository = userDataRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public UserData addUserData(UserData userData) throws Exception {
        UserData isExist = userDataRepository.findByUserEmail(userData.getUserEmail());
        if(isExist == null) {
            return this.userDataRepository.save(userData);
        }

        throw new Exception("User Already Exist");
    }

    @Override
    public UserData getUserByEmailId(String userEmail) {
        return userDataRepository.findByUserEmail(userEmail);
    }

    @Override
    public UserData updateUserData(UserData userData, String useEmail, int id) throws Exception{
        UserData isExist = userDataRepository.findByUserEmail(useEmail);
        if(isExist != null) {
            isExist.setUserEmail(userData.getUserEmail());
            isExist.setFirstName(userData.getFirstName());
            isExist.setLastName(userData.getLastName());
            isExist.setPassword(userData.getPassword());
            isExist.setAddress(userData.getAddress());
            userDataRepository.save(isExist);
            return isExist;
        }
        throw  new Exception("User Doesn't Exist");
    }

    @Override
    public boolean deleteUserData(String userEmail, int userId) throws Exception{
        UserData user = userDataRepository.findByUserEmail(userEmail);
        if(user != null) {
            userDataRepository.deleteById(user.getId());
            return true;
        }
       throw new Exception("User Doesn't Exist");
    }

    @Override
    public UserData loginUser(String userEmail, String userPassword)throws Exception {
        UserData userData = userDataRepository.findByUserEmail(userEmail);
        if(userData != null) {
            if (userEmail.equals(userData.getUserEmail()) && userPassword.equals(userData.getPassword())) {
                return userData;
            } else {
                return null;
            }
        }
        throw  new Exception("User Doesn't Exist");
    }

    @Override
    public int generateOtp(String email) {
        int otp = ThreadLocalRandom.current().nextInt(1000, 9999);
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("todo.tracker8874@gmail.com");
            simpleMailMessage.setTo(email);
            simpleMailMessage.setText("Your Registration OTP : "+otp+" ");
            simpleMailMessage.setSubject("OTP Received");
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e){
            e.printStackTrace();
        }
        return otp;
    }


    }

