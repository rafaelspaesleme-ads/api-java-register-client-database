package br.com.xyz.webclient.services.dockers.microservices;

import br.com.xyz.webclient.services.dockers.RegisterConteiner;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class DisableConteinerMicroservice {

    public void runnerConteiner(RegisterConteiner registerConteiner) {
        disableConteiner(registerConteiner);
    }

    private void disableConteiner(RegisterConteiner registerConteiner) {

        System.out.println(
                registerConteiner.getImageDocker() +
                        registerConteiner.getNameConteiner() +
                        registerConteiner.getPortConteiner() +
                        registerConteiner.getUsernameDatabase() +
                        registerConteiner.getPasswordDatabase() +
                        registerConteiner.getNameDatabase()
        );

        String commandStopDocker = "docker stop " + registerConteiner.getNameConteiner() + "";
        String commandStartDocker = "docker start " + registerConteiner.getNameConteiner() + "";

        String[] env = {"PATH=/bin:/usr/bin/"};

        try {
            if (!registerConteiner.getActive()){
                Runtime.getRuntime().exec(commandStopDocker, env);
            } else {
                Runtime.getRuntime().exec(commandStartDocker, env);
            }
        } catch (IOException ex) {
            Logger.getLogger(DisableConteinerMicroservice.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}