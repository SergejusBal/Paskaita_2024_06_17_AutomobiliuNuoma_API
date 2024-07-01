package com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.MSQ_Connection;

import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataType.Client;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataBaseRepository.ClientDataBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientsService {

    private final ClientDataBaseRepository dataBaseRepository;

    @Autowired
    public ClientsService(ClientDataBaseRepository dataBaseRepository) {
        this.dataBaseRepository = dataBaseRepository;
    }

    public String getAllClients(){
        return dataBaseRepository.getAllClients();
    }

    public String getClientRentals(Integer id){
        return dataBaseRepository.getClientRentals(id);
    }

    public String getClientsById(Integer id){
        return dataBaseRepository.getClientsById(id);
    }

    public String createClient(Client client){

        if(
                client.getFirstName() == null || client.getLastName() == null ||
                client.getEmail() == null || client.getPhone() == null
        )
        {
            return "Invalid Input";
        }

        return dataBaseRepository.createClient(client);
    }

    public String getAllActiveClients(){
        return dataBaseRepository.getAllActiveClients();
    }

    public String alterClient(Client client,Integer id){
        if(
                client.getFirstName() == null || client.getLastName() == null ||
                        client.getEmail() == null || client.getPhone() == null
        )
        {
            return "Invalid Input";
        }
        return dataBaseRepository.alterClient(client,id);
    }


    public String deleteClient (Integer id){
        dataBaseRepository.deleteRentalByClientID(id);
        return dataBaseRepository.deleteClient(id);
    }


}
