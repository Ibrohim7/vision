package com.example.vision.payload;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Pattern;


@Data
public class ReqSignIn {
    @NotNull
    private String username;
    @NotNull
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "blablabla")
    private String password;
}
