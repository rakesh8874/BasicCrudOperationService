package com.dbms.basiccrudoperationservice.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="userData")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String userEmail;
    private String firstName;
    private String lastName;
    private long contactNo;
    private String gender;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    @JsonManagedReference
    private Address address;

}
