package com.dbms.basiccrudoperationservice.repository;

import com.dbms.basiccrudoperationservice.domain.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {

 UserData findByUserEmail(String userEmail);

}
