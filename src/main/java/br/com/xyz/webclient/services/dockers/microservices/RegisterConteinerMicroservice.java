package br.com.xyz.webclient.services.dockers.microservices;

import br.com.xyz.webclient.services.dockers.RegisterConteiner;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class RegisterConteinerMicroservice {

    private String domainServer = "rplproject.com";
    private String ipServer = "174.138.111.1";

    public void runnerConteiner(RegisterConteiner registerConteiner) {
        createConteiner(registerConteiner);
    }

    private void createConteiner(RegisterConteiner registerConteiner) {

        String commandDocker = "docker run --name " + registerConteiner.getNameConteiner() + " -d -p " + registerConteiner.getPortConteiner() + ":5432 -e DB_NAME=" + registerConteiner.getNameDatabase() + " -e DB_USER=" + registerConteiner.getUsernameDatabase() + " -e DB_PASSWD=" + registerConteiner.getPasswordDatabase() + " rafaelspaesleme/imagedatabase:latest";

        String[] env = {"PATH=/bin:/usr/bin/"};

        try {
            Runtime.getRuntime().exec(commandDocker, env);
            createTemplateSubdomainNginx(registerConteiner);
        } catch (IOException ex) {
            Logger.getLogger(RegisterConteinerMicroservice.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createTemplateSubdomainNginx(RegisterConteiner registerConteiner) {
        String commandBash = "touch /etc/templates/" + registerConteiner.getNameDatabase();

        String[] env = {"PATH=/bin:/usr/bin/"};

        try {
            Runtime.getRuntime().exec(commandBash, env);
            sendTemplateSubdomainNginx(registerConteiner);
        } catch (IOException ex) {
            Logger.getLogger(RegisterConteinerMicroservice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendTemplateSubdomainNginx(RegisterConteiner registerConteiner) {
        String commandBash = "cp /etc/templates/TemplateConfigSubdomainNginx /etc/templates/" + registerConteiner.getNameDatabase();

        String[] env = {"PATH=/bin:/usr/bin/"};

        try {
            Runtime.getRuntime().exec(commandBash, env);
            sedSubdomain(registerConteiner);
        } catch (IOException ex) {
            Logger.getLogger(RegisterConteinerMicroservice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sedSubdomain(RegisterConteiner registerConteiner) {
        String commandBash = "sed -e 's|SUB_DOMAIN|" + registerConteiner.getNameDatabase() + "|' -i /etc/templates/" + registerConteiner.getNameDatabase();
        executeBashCommandSedSubDomain(registerConteiner, commandBash);
        sedDomain(registerConteiner);
    }

    private void sedDomain(RegisterConteiner registerConteiner) {
        String commandBash = "sed -e 's|DOMAIN|" + domainServer + "|' -i /etc/templates/" + registerConteiner.getNameDatabase();
        executeBashCommandSedDomain(registerConteiner, commandBash);
        sedPort(registerConteiner);
    }

    private void sedPort(RegisterConteiner registerConteiner) {
        String commandBash = "sed -e 's|PORT_CONTEINER_DB|" + registerConteiner.getPortConteiner() + "|' -i /etc/templates/" + registerConteiner.getNameDatabase();
        executeBashCommandSedPort(registerConteiner, commandBash);
        reallocatingSubdomain(registerConteiner);
    }

    private void reallocatingSubdomain(RegisterConteiner registerConteiner) {
        String commandBash = "mv /etc/templates/" + registerConteiner.getNameDatabase() + " /etc/nginx/sites-available";

        String[] env = {"PATH=/bin:/usr/bin/"};

        try {
            Runtime.getRuntime().exec(commandBash, env);
            removeSubdomainNginxDefault(registerConteiner);
        } catch (IOException ex) {
            Logger.getLogger(RegisterConteinerMicroservice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeSubdomainNginxDefault(RegisterConteiner registerConteiner) {
        String commandBash = "rm /etc/nginx/sites-enabled/default";

        String[] env = {"PATH=/bin:/usr/bin/"};

        try {
            Runtime.getRuntime().exec(commandBash, env);
            enabledReverseProxySubdomain(registerConteiner);
        } catch (IOException ex) {
            Logger.getLogger(RegisterConteinerMicroservice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enabledReverseProxySubdomain(RegisterConteiner registerConteiner) {
        String commandBash = "ln -s /etc/nginx/sites-available/" + registerConteiner.getNameDatabase() + " /etc/nginx/sites-enabled/" + registerConteiner.getNameDatabase();

        String[] env = {"PATH=/bin:/usr/bin/"};

        try {
            Runtime.getRuntime().exec(commandBash, env);
            restartServerNginx(registerConteiner);
        } catch (IOException ex) {
            Logger.getLogger(RegisterConteinerMicroservice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void restartServerNginx(RegisterConteiner registerConteiner) {
        String commandBash = "service nginx restart";

        String[] env = {"PATH=/bin:/usr/bin/"};

        try {
            Runtime.getRuntime().exec(commandBash, env);
            registerSubdomain(registerConteiner);
        } catch (IOException ex) {
            Logger.getLogger(RegisterConteinerMicroservice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void registerSubdomain(RegisterConteiner registerConteiner) {
        String commandBash = "doctl compute domain records create " + domainServer + " --record-type 'A' --record-data '" + ipServer + "' --record-name '" + registerConteiner.getNameDatabase() + "' --record-ttl 3600";
        executeBashCommand(commandBash);
    }

    private static boolean executeBashCommand(String command) {
        boolean success = false;
        System.out.println("Executing BASH command:\n   " + command);
        Runtime r = Runtime.getRuntime();
        // Use bash -c so we can handle things like multi commands separated by ; and
        // things like quotes, $, |, and \. My tests show that command comes as
        // one argument to bash, so we do not need to quote it to make it one thing.
        // Also, exec may object if it does not have an executable file as the first thing,
        // so having bash here makes it happy provided bash is installed and in path.
        String[] commands = {"bash", "-c", command};
        try {
            Process p = r.exec(commands);

            p.waitFor();
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";

            while ((line = b.readLine()) != null) {
                System.out.println(line);
            }

            b.close();
            success = true;
        } catch (Exception e) {
            System.err.println("Failed to execute bash with command: " + command);
            e.printStackTrace();
        }
        return success;
    }

    private static boolean executeBashCommandSedSubDomain(RegisterConteiner registerConteiner, String command) {
        boolean success = false;
        System.out.println("Executing BASH command:\n   " + command);
        Runtime r = Runtime.getRuntime();
        // Use bash -c so we can handle things like multi commands separated by ; and
        // things like quotes, $, |, and \. My tests show that command comes as
        // one argument to bash, so we do not need to quote it to make it one thing.
        // Also, exec may object if it does not have an executable file as the first thing,
        // so having bash here makes it happy provided bash is installed and in path.
        String[] commands = {"bash", "-c", command};
        try {
            Process p = r.exec(commands);

            p.waitFor();
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";

            while ((line = b.readLine()) != null) {
                System.out.println(line);
            }

            b.close();
            success = true;
        } catch (Exception e) {
            System.err.println("Failed to execute bash with command: " + command);
            e.printStackTrace();
        }
        return success;
    }

    private static boolean executeBashCommandSedDomain(RegisterConteiner registerConteiner, String command) {
        boolean success = false;
        System.out.println("Executing BASH command:\n   " + command);
        Runtime r = Runtime.getRuntime();
        // Use bash -c so we can handle things like multi commands separated by ; and
        // things like quotes, $, |, and \. My tests show that command comes as
        // one argument to bash, so we do not need to quote it to make it one thing.
        // Also, exec may object if it does not have an executable file as the first thing,
        // so having bash here makes it happy provided bash is installed and in path.
        String[] commands = {"bash", "-c", command};
        try {
            Process p = r.exec(commands);

            p.waitFor();
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";

            while ((line = b.readLine()) != null) {
                System.out.println(line);
            }

            b.close();
            success = true;
        } catch (Exception e) {
            System.err.println("Failed to execute bash with command: " + command);
            e.printStackTrace();
        }
        return success;
    }

    private static boolean executeBashCommandSedPort(RegisterConteiner registerConteiner, String command) {
        boolean success = false;
        System.out.println("Executing BASH command:\n   " + command);
        Runtime r = Runtime.getRuntime();
        // Use bash -c so we can handle things like multi commands separated by ; and
        // things like quotes, $, |, and \. My tests show that command comes as
        // one argument to bash, so we do not need to quote it to make it one thing.
        // Also, exec may object if it does not have an executable file as the first thing,
        // so having bash here makes it happy provided bash is installed and in path.
        String[] commands = {"bash", "-c", command};
        try {
            Process p = r.exec(commands);

            p.waitFor();
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";

            while ((line = b.readLine()) != null) {
                System.out.println(line);
            }

            b.close();
            success = true;
        } catch (Exception e) {
            System.err.println("Failed to execute bash with command: " + command);
            e.printStackTrace();
        }
        return success;
    }
}
