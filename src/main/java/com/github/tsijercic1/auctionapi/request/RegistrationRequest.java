package com.github.tsijercic1.auctionapi.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * class which gets populated on registration
 */
@Data
public class RegistrationRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @Override
    public String toString() {
        return getEmail() + "\n" + getPassword() + "\n" + getName() + "\n" + getSurname() + "\n";
    }
}
