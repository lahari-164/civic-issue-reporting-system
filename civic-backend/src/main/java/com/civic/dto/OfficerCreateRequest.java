package com.civic.dto;

import lombok.Data;

@Data
public class OfficerCreateRequest {
    private String name;
    private String email;
    private String password;
    private String category;
}