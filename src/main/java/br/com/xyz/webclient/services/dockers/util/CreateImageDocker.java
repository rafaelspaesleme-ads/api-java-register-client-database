package br.com.xyz.webclient.services.dockers.util;

import br.com.xyz.webclient.services.dockers.RegisterConteiner;
import br.com.xyz.webclient.services.dockers.microservices.RegisterConteinerMicroservice;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class CreateImageDocker {
    public void builderImagePostgres(String imageDocker) {
        createImageDocker(imageDocker);
    }

    private void createImageDocker(String imageDocker) {

        String commandDocker = "docker build -t "+imageDocker+" .";

        String[] env = {"PATH=/bin:/usr/bin/"};

        try {
            Runtime.getRuntime().exec(commandDocker, env);
        } catch (IOException ex) {
            Logger.getLogger(RegisterConteinerMicroservice.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
