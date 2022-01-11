package com.example.backend.controller;

import com.example.backend.domain.Client;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.peristence.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public Iterable<Client> getClient() {
        return clientRepository.findAll();
    }

    @PostMapping
    public Client createEmployee(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    // get employee by id rest api
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not exist with id :" + id));
        return ResponseEntity.ok(client);
    }

    // update employee rest api

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not exist with id :" + id));

        client.setName(clientDetails.getName());
        client.setClientId(clientDetails.getClientId());

        Client updatedClient = clientRepository.save(client);
        return ResponseEntity.ok(updatedClient);
    }

    // delete employee rest api
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteClient(@PathVariable Long id) {
        Client employee = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not exist with id :" + id));

        clientRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
