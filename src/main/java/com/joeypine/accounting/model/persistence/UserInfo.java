package com.joeypine.accounting.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private  Long id;
    private String username;
    private String password;
    private String salt;
    private LocalDate createTime;
    private LocalDate updateTime;

}
