package br.com.xyz.webclient.services;

import br.com.xyz.webclient.entity.Client;
import br.com.xyz.webclient.entity.Port;
import br.com.xyz.webclient.repositories.PortRepository;
import org.springframework.stereotype.Service;

@Service
public class PortService {

    private static int PRIMARY_PORT = 5432;

    private final PortRepository portRepository;

    public PortService(PortRepository portRepository) {
        this.portRepository = portRepository;
    }

    public Port registerPort(Client client) {
        Integer countLastPort = (PRIMARY_PORT+(Integer.valueOf(String.valueOf(client.getId()))));
        System.out.println("AQUIIIII: "+PRIMARY_PORT);
        Port port = new Port(
                null,
                PRIMARY_PORT,
                countLastPort,
                (countLastPort+1)
        );

        return portRepository.save(port);
    }

}
