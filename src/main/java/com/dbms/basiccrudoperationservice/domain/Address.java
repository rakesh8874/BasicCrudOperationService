package com.dbms.basiccrudoperationservice.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="userAddress")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String town;
    private String city;
    private String state;
    private String country;
    private int zipcode;

    @OneToOne(mappedBy = "address")
    @JsonBackReference
    private UserData userData;

}
