package br.com.xyz.webclient.services;

import br.com.xyz.webclient.dto.ClientDTO;
import br.com.xyz.webclient.entity.Client;
import br.com.xyz.webclient.entity.Port;
import br.com.xyz.webclient.repositories.PortRepository;
import br.com.xyz.webclient.repositories.ClientRepository;
import br.com.xyz.webclient.services.dockers.RegisterConteiner;
import br.com.xyz.webclient.services.dockers.microservices.DisableConteinerMicroservice;
import br.com.xyz.webclient.services.dockers.microservices.RegisterConteinerMicroservice;
import br.com.xyz.webclient.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private static int PRIMARY_PORT = 5432;

    private final ClientRepository clientRepository;
    private final RegisterConteinerMicroservice registerConteinerMicroservice;
    private final DisableConteinerMicroservice disableConteinerMicroservice;
    private final PortService portService;

    public ClientService(ClientRepository clientRepository,
                         RegisterConteinerMicroservice registerConteinerMicroservice,
                         DisableConteinerMicroservice disableConteinerMicroservice,
                         PortRepository portRepository,
                         PortService portService) {
        this.clientRepository = clientRepository;
        this.registerConteinerMicroservice = registerConteinerMicroservice;
        this.portService = portService;
        this.disableConteinerMicroservice = disableConteinerMicroservice;
    }

    private Client search(Long id) {
        Optional<Client> objeto = clientRepository.findById(id);
        return objeto.orElseThrow(() ->
                new ObjectNotFoundException("Objeto não encontrado na base de dados. Tipo: "
                        .concat(Client.class.getName())));
    }

    public Client insert(Client objectClient) {
        objectClient.setId(null);
        Client client = clientRepository.save(objectClient);
        Port port = portService.registerPort(client);
        System.out.println("OOOOO: " + port.getLastPort());
        if (port.getLastPort() != null) {
            client.setPortConteiner(port.getNewPort());
            client = clientRepository.save(client);
        } else {
            client.setPortConteiner((PRIMARY_PORT + 1));
            client = clientRepository.save(client);
        }
        createConteiner(client);
        return client;
    }

    public Client disable(Client client) {
        Client registerNewClient = search(client.getId());
        updateData(registerNewClient, client);
        disableConteiner(registerNewClient);
        return clientRepository.save(registerNewClient);
    }

    public Client fromDTO(ClientDTO clientDTO) {
        return new Client(
                clientDTO.getId(),
                clientDTO.getImageDocker(),
                clientDTO.getNameConteiner(),
                clientDTO.getPortConteiner(),
                clientDTO.getUsernameDatabase(),
                clientDTO.getPasswordDatabase(),
                clientDTO.getNameDatabase(),
                clientDTO.getActive());
    }

    private void createConteiner(Client client) {
        RegisterConteiner registerConteiner = new RegisterConteiner(
                client.getImageDocker(),
                client.getNameConteiner(),
                client.getPortConteiner(),
                client.getUsernameDatabase(),
                client.getPasswordDatabase(),
                client.getNameDatabase(),
                client.getActive()
        );

        registerConteinerMicroservice.runnerConteiner(registerConteiner);
    }

    private void disableConteiner(Client client) {
        RegisterConteiner registerConteiner = new RegisterConteiner(
                client.getImageDocker(),
                client.getNameConteiner(),
                client.getPortConteiner(),
                client.getUsernameDatabase(),
                client.getPasswordDatabase(),
                client.getNameDatabase(),
                client.getActive()
        );

        disableConteinerMicroservice.runnerConteiner(registerConteiner);
    }

    private void updateData(Client registerNewClient, Client client) {
        registerNewClient.setActive(client.getActive());
    }
}
