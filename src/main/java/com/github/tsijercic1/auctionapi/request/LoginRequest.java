package com.github.tsijercic1.auctionapi.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(@NotBlank String email, @NotBlank String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return getEmail() + "\n" + getPassword() + "\n";
    }
}
