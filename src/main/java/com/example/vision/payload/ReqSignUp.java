package com.example.vision.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonFormat(shape = JsonFormat.Shape.ARRAY)
//@JsonPropertyOrder({"username", "password", "projectId", "rolesId"})
public class ReqSignUp {
    private Long id;
    @NotNull
    private String username;
    @NotNull
    @Pattern(regexp = "^(?:(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*)[^\\s]{8,}$", message = "saved")
    private String password;

    private Long projectId;

    // @NotNull
    private List<Integer> rolesId;
}