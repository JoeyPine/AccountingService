package com.joeypine.accounting.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {
    private  Long id;
    private String username;
    private String password;
    private String salt;
}
