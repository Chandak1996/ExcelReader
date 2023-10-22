package com.demo.excellerader.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowerDto {
    private String firstName;
    private String lastName;
    private String gender;
    private String country;
    private Integer age;
    private String dob;
}
