package com.mahendra.jpal.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlMixed;
import java.util.UUID;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long addressId;
    private String doorNo;
    private String streetName;

    private String locality;
    private String district;

    private String state;
    private String country;
    private long pincode;

    //bidirectional mapping with Student class
    //this way it makes sure that only foreign key is created in student table and it's not needed in address table
    @OneToOne(mappedBy = "address")
    @JsonIgnore  // Used to avoid getting recursive student and address in the output.
    private Student student;
}
