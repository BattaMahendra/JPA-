package com.mahendra.jpal.entity;


import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlMixed;
import java.util.UUID;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID addressId;
    private String doorNo;
    private String streetName;

    private String locality;
    private String district;

    private String state;
    private String country;
    private long pincode;

    //bi-directional mapping with Student class
    //this way it makes sure that only foreign key is created in student table and it's not needed in address table
    @OneToOne(mappedBy = "address")
    private Student student;
}
