package fpt.capstone.vuondau.util.keycloak;

import fpt.capstone.vuondau.entity.common.ApiException;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KeycloakClientUtil {
    @Value("${keycloak.app-client}")
    private String appClientId;

    protected ClientResource getClientResource(RealmResource resource) {
        ClientRepresentation clientRepresentation = resource.clients().findByClientId(appClientId).stream().findFirst().orElse(null);
        if (clientRepresentation != null) {
            return resource.clients().get(clientRepresentation.getId());
        }
        return null;
    }

}
