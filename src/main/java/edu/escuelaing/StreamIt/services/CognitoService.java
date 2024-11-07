package edu.escuelaing.StreamIt.services;

import jakarta.enterprise.context.ApplicationScoped;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType;

import java.util.Map;

@ApplicationScoped
public class CognitoService {

    private final CognitoIdentityProviderClient cognitoClient;

    public CognitoService() {
        cognitoClient = CognitoIdentityProviderClient.builder().region(Region.of("us-east-1")).build();
    }

    public void createUser(String name,String email, String password) {
        cognitoClient.signUp(signUpRequest -> signUpRequest
            .clientId("j1gakm7ngpdf5cu8eor8c8g8o")
            .username(email)
            .password(password)
            .userAttributes(
                    AttributeType.builder().name("name").value(name).build(),
                    AttributeType.builder().name("email").value(email).build(),
                    AttributeType.builder().name("phone_number").value("+57312564789").build()
            )
        );
    }

    public String authenticateUser(String email, String password) {
        AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                .userPoolId("us-east-1_6N20v59bv")
                .clientId("j1gakm7ngpdf5cu8eor8c8g8o")
                .authFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .authParameters(Map.of(
                        "USERNAME", email,
                        "PASSWORD", password
                ))
                .build();

        AdminInitiateAuthResponse authResponse = cognitoClient.adminInitiateAuth(authRequest);

        return authResponse.authenticationResult().idToken();  // El JWT
    }

    public String loginUser(String username, String password) {
        AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                .authFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .clientId("j1gakm7ngpdf5cu8eor8c8g8o") // Cognito App Client ID
                .userPoolId("us-east-1_6N20v59bv") // Cognito User Pool ID
                .authParameters(Map.of("USERNAME", username, "PASSWORD", password))
                .build();

        AdminInitiateAuthResponse authResponse = cognitoClient.adminInitiateAuth(authRequest);

        return authResponse.authenticationResult().idToken(); // Token para acceder a las APIs protegidas
    }
}


