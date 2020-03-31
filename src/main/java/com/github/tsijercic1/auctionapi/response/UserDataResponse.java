package com.github.tsijercic1.auctionapi.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataResponse {
    private Long id;
    private String email, name, surname, token;

    public UserDataResponse(Long id, String username, String email, String name, String surname, String token) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.token = token;
    }

}
