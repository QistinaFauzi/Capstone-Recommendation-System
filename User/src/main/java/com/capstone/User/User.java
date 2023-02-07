package com.capstone.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="mizan_user")
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
