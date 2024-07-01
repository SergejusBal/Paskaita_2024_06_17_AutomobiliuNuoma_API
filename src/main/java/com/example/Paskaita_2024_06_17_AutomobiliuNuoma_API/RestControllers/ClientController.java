package com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.RestControllers;

import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataType.Client;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.MSQ_Connection.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {
    private final ClientsService clientsService;

    @Autowired
    public ClientController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping("/clients")
    public String getAllClients() {
        return clientsService.getAllClients();
    }

    @GetMapping("/clients/{id}")
    public String getClientById(@PathVariable Integer id) {
        return clientsService.getClientsById(id);
    }

    @GetMapping("/clients/active-rentals")
    public String getAllActiveClients() {
        return clientsService.getAllActiveClients();
    }

    @GetMapping("/clients/{id}/rental")
    public String getClientRentals(@PathVariable Integer id) {
        return clientsService.getClientRentals(id);
    }

    @PostMapping("/clients")
    public String createClients(@RequestBody Client client) {
        return clientsService.createClient(client);
    }

    @PutMapping("/clients/{id}")
    public String alterClient(@RequestBody Client client, @PathVariable Integer id) {
        return clientsService.alterClient(client, id);
    }

    @DeleteMapping("/clients/{id}")
    public String deleteClient(@PathVariable Integer id) {
        return clientsService.deleteClient(id);
    }
}
