package fpt.capstone.vuondau.util.keycloak;

import fpt.capstone.vuondau.entity.common.ApiException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KeycloakRealmUtil {
    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;

    public KeycloakRealmUtil(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    protected RealmResource getRealmReSource() {
        return Optional.ofNullable(keycloak.realm(realm)).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Realm not found!!"));
    }
}
