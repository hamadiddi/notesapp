package com.hamadiddi.notesapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "Hamadi Iddi",
            email = "dev@hamadiddi.com",
            url = "hamadiddi.com"
        ),
        description = "An API for a Note taking application",
        title = "NOTES APP",
        version = "1.0",
        termsOfService = "Terms of Service"
    )
)
public class OpenApiConfig {

}
