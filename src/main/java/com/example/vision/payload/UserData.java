package com.example.vision.payload;

import com.example.vision.entity.Role;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserData {

    private Long id;
    @NotNull
    private String username;

    private boolean activate;

    private Role roleId;
}
