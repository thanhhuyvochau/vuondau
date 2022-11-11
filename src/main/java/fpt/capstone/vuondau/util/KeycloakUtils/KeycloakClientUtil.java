package fpt.capstone.vuondau.util.KeycloakUtils;

import fpt.capstone.vuondau.entity.common.ApiException;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class KeycloakClientUtil {
    @Value("${keycloak.app-client}")
    private String appClientId;

    protected ClientRepresentation getClientRepresentation(RealmResource resource) {
        return resource.clients().findByClientId(appClientId)
                .stream().findFirst()
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("App client not found!"));
    }

    protected ClientResource getClientResource(RealmResource resource) {
        return resource.clients().get(appClientId);
    }

}
