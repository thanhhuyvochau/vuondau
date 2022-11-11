package fpt.capstone.vuondau.util.KeycloakUtils;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.util.ObjectUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Component
public class KeycloakUserUtil {
    private final Keycloak keycloak;
    private final KeycloakRealmUtil keycloakRealmUtil;
    @Value("${keycloak.realm}")
    private String realm;

    public KeycloakUserUtil(Keycloak keycloak, KeycloakRealmUtil keycloakRealmUtil) {
        this.keycloak = keycloak;
        this.keycloakRealmUtil = keycloakRealmUtil;
    }

    public Boolean saveAccount(Account account) {
        CredentialRepresentation credentialRepresentation = preparePasswordRepresentation(account.getPassword());
        UserRepresentation userRepresentation = prepareUserRepresentation(account, credentialRepresentation);
        Response response = keycloak.realm(realm).users().create(userRepresentation);
        if (response.getStatus() == HttpStatus.OK.value()) {
            return true;
        }
        return false;
    }

    protected UserResource getUserResource(Account account) {
        return keycloakRealmUtil.getRealmReSource().users().get(account.getKeycloakId());
    }

    private CredentialRepresentation preparePasswordRepresentation(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }

    private UserRepresentation prepareUserRepresentation(Account account, CredentialRepresentation credentialRepresentation) {
        UserRepresentation newUser = ObjectUtil.copyProperties(account, new UserRepresentation(), UserRepresentation.class, true);
//        newUser.setEmail(account.getEmail());
//        newUser.setFirstName(account.getFirstName());
//        newUser.setLastName(account.getLastName());
//        newUser.setUsername(account.getUsername());
        newUser.setCredentials(Collections.singletonList(credentialRepresentation));
        newUser.setEnabled(true);
        return newUser;
    }
}
