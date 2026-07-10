package com.example.offerops.models;


import jakarta.persistence.*;
import lombok.*;

import javax.lang.model.element.Name;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends  BaseModel{

 @Column(unique = true)
String email;

 String password;

 @Column(nullable = false)
 String name;

 @OneToMany(
         mappedBy = "user",
         fetch = FetchType.LAZY,
         cascade = CascadeType.ALL
 )
 private List<JobApplication> jobApplication;

}
