package com.daniellopes.startboot.controller;

import com.daniellopes.startboot.model.Client;
import com.daniellopes.startboot.repository.ClientRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "Clients")
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;


    @ApiOperation(value = "List all clients")
    @GetMapping
    public List<Client> list() {
        return clientRepository.findAll();
    }
    @ApiOperation(value = "Get a client")
    @GetMapping("/{id}")
    public Client get(@PathVariable Long id) {
        Optional<Client> currentClient = clientRepository.findById(id);
        if(currentClient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found!");
        }

        return currentClient.get();
    }

    @ApiOperation(value = "Create a client")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @ApiOperation(value = "Update a client")
    @PutMapping
    public Client edit(@RequestBody Client client) {
        Optional<Client> currentClient = clientRepository.findById(client.id);
        if(currentClient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found!");
        }

        Client editedClient = currentClient.get();
        editedClient.name = client.name;
        clientRepository.save(editedClient);
        return editedClient;
    }

    @ApiOperation(value = "Delete a client")
    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        Optional<Client> currentClient = clientRepository.findById(id);
        if(currentClient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found!");
        }

        clientRepository.delete(currentClient.get());
    }
}
