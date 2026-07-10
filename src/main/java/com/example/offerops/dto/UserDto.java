package com.example.offerops.dto;


import com.example.offerops.models.User;
import lombok.*;
import org.jspecify.annotations.NullMarked;

import javax.lang.model.element.ModuleElement;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;
    private  String email;
    private Long id;

    public  static UserDto UserDtoBuilder(User user){

     return UserDto.builder()
             .id(user.getId())
             .email(user.getEmail())
             .name(user.getName())
             .build();
    }

}
