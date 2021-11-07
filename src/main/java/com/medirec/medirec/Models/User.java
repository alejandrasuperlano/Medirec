package com.medirec.medirec.Models;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Table(name = "USER", uniqueConstraints = @UniqueConstraint(columnNames = "userEmail"))
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "userFirstName")
    @NotBlank
    private String userFirstName;

    @Column(name = "userLastName")
    @NotBlank
    private String userLastName;

    @Column(name = "userDocType")
    @NotBlank
    private String userDocType;

    @Column(name = "userDoc")
    @NotBlank
    private String userDoc;

    @Column(name = "userEmail")
    @NotBlank
    private String userEmail;

    @Column(name = "userPassword")
    @NotBlank
    private String userPassword;

    @Column(name = "userBirthDay")
    private Date userBirthDay;

    @Column(name = "userGender")
    private String userGender;

    @Column(name = "userAddress")
    private String userAddress;

    @Column(name = "userTutorial")
    @NotNull // 1 = done, 0 = not yet
    private int userTutorial;


    // Register constructor
    public User(String userFirstName, String userLastName, String userDocType, String userDoc, String userEmail, String userPassword) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userDocType = userDocType;
        this.userDoc = userDoc;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public User(){
        
    }


}
