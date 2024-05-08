package com.github.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.client.ClientHttpConfiguration;
import com.github.domain.Shelter;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Scanner;

@AllArgsConstructor
public class ShelterService {

    private final ObjectMapper mapper;

    private final ClientHttpConfiguration client;

    public void addShelter() throws IOException, InterruptedException {
        System.out.println("Digite o nome do abrigo:");
        String name = new Scanner(System.in).nextLine();
        System.out.println("Digite o telefone do abrigo:");
        String phone = new Scanner(System.in).nextLine();
        System.out.println("Digite o email do abrigo:");
        String email = new Scanner(System.in).nextLine();

        String uri = "http://localhost:8080/abrigos";
        Shelter shelter = Shelter.of(name, phone, email);
        HttpResponse<String> response = client.requestPOST(uri, shelter);
        int statusCode = response.statusCode();
        String responseBody = response.body();
        if (statusCode == 200) {
            System.out.println("Abrigo cadastrado com sucesso!");
            System.out.println(responseBody);
        } else if (statusCode == 400 || statusCode == 500) {
            System.out.println("Erro ao cadastrar o abrigo:");
            System.out.println(responseBody);
        }
    }

    public void getShelters() throws IOException, InterruptedException {
        String uri = "http://localhost:8080/abrigos";
        HttpResponse<String> response = client.requestGET(uri);
        String responseBody = response.body();

        Shelter[] shelters = mapper.readValue(responseBody, Shelter[].class);
        System.out.println("Abrigos cadastrados:");
        Arrays.stream(shelters).forEach(shelter -> {
            String id = shelter.getId();
            String name = shelter.getName();
            System.out.println(id + " - " + name);
        });
    }

}
