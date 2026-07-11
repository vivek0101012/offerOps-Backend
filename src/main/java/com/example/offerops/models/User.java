package com.example.offerops.models;


import jakarta.persistence.*;
import lombok.*;
import com.example.offerops.constant.AuthProvider;
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

 @Enumerated(EnumType.STRING)
 @Column(columnDefinition = "varchar(20) default 'LOCAL'")
 private AuthProvider provider;
 String password;

 @Column(nullable = false)
 String name;

 @OneToMany(
         mappedBy = "user",
         fetch = FetchType.LAZY,
         cascade = CascadeType.ALL
 )
 private List<JobApplication> jobApplication;
 @PrePersist
 public void setDefaults() {
  if (this.provider == null) this.provider = AuthProvider.LOCAL;
 }
}
