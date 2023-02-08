package com.capstone.Web;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue
    private int userid;
    private String username;
    private String email;
    private String type;
    private String password;
    @Transient
    private String verifypassword;
}
