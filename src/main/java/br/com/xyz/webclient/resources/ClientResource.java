package br.com.xyz.webclient.resources;

import br.com.xyz.webclient.dto.ClientDTO;
import br.com.xyz.webclient.entity.Client;
import br.com.xyz.webclient.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/cliente")
public class ClientResource {

    private final ClientService ClientService;

    public ClientResource(ClientService clientService) {
        this.ClientService = clientService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody ClientDTO clientDTO) {
        Client client = ClientService.fromDTO(clientDTO);
        System.out.println(clientDTO.getNameConteiner());
        System.out.println(client);
        client = ClientService.insert(client);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> disable(@Valid @RequestBody ClientDTO clientDTO, @PathVariable Long id) {
        Client client = ClientService.fromDTO(clientDTO);
        System.out.println("ID>>>>>> "+id);
        System.out.println("SITUACAO>>>>> "+client.getActive());
        System.out.println("NAME>>>>> "+client.getNameConteiner());
        client.setId(id);
        ClientService.disable(client);
        return ResponseEntity.noContent().build();
    }

}
