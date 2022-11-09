package fpt.capstone.vuondau.entity.request;

import java.io.Serializable;
import java.util.Map;

public class RegisterKeycloakUserRequest implements Serializable {

    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private String firstname;
    private String lastName;
    private Map<String, String> clientRoles;
    private Boolean enabled;

}
