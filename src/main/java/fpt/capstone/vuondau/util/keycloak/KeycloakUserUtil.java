package fpt.capstone.vuondau.util.keycloak;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.common.ApiException;
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
import java.util.Optional;

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

    public Boolean create(Account account) {
        CredentialRepresentation credentialRepresentation = preparePasswordRepresentation(account.getPassword());
        UserRepresentation userRepresentation = prepareUserRepresentation(account, credentialRepresentation);
        Response response = keycloak.realm(realm).users().create(userRepresentation);
        if (response.getStatus() == HttpStatus.OK.value()) {
            return true;
        }
        return false;
    }

    public Boolean update(Account account) {
        CredentialRepresentation credentialRepresentation = preparePasswordRepresentation(account.getPassword());
        try {
            UserRepresentation userRepresentation = prepareUserRepresentation(account, credentialRepresentation);
            UserResource userResource = Optional.ofNullable(getUserResource(account))
                    .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("User not found in Keycloak!!"));
            keycloak.realm(realm).users().get(userResource.toRepresentation().getId()).update(userRepresentation);
        } catch (Exception e) {
            throw ApiException.create(HttpStatus.CONFLICT).withMessage(e.getMessage());
        }
        return true;
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
        newUser.setCredentials(Collections.singletonList(credentialRepresentation));
        newUser.setEnabled(true);
        return newUser;
    }
}
