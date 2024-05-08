package com.github.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.client.ClientHttpConfiguration;
import com.github.domain.Pet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Scanner;

@Service
@AllArgsConstructor
public class PetService {

    private final ObjectMapper mapper;

    private final ClientHttpConfiguration client;

    public void importPets() throws IOException, InterruptedException {
        System.out.println("Digite o id ou nome do abrigo:");
        String idOuNome = new Scanner(System.in).nextLine();

        System.out.println("Digite o nome do arquivo CSV:");
        String nomeArquivo = new Scanner(System.in).nextLine();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " +nomeArquivo);
        }
        String line;
        while ((line = reader.readLine()) != null) {
            String[] campos = line.split(",");
            String tipo = campos[0];
            String nome = campos[1];
            String raca = campos[2];
            int idade = Integer.parseInt(campos[3]);
            String cor = campos[4];
            Float peso = Float.parseFloat(campos[5]);

            String uri = "http://localhost:8080/abrigos/" + idOuNome + "/pets";
            Pet pet = Pet.of(nome, raca, tipo.toUpperCase(), cor, peso, idade);
            HttpResponse<String> response = client.requestPOST(uri, pet);
            int statusCode = response.statusCode();
            String responseBody = response.body();
            if (statusCode == 200) {
                System.out.println("Pet cadastrado com sucesso: " + nome);
            } else if (statusCode == 404) {
                System.out.println("Id ou nome do abrigo não encontado!");
                break;
            } else if (statusCode == 400 || statusCode == 500) {
                System.out.println("Erro ao cadastrar o pet: " + nome);
                System.out.println(responseBody);
                break;
            }
        }
        reader.close();
    }

    public void getPets() throws IOException, InterruptedException {
        System.out.println("Digite o id ou nome do abrigo:");
        String idOuNome = new Scanner(System.in).nextLine();

        String uri = "http://localhost:8080/abrigos/" +idOuNome +"/pets";
        HttpResponse<String> response = client.requestGET(uri);
        int statusCode = response.statusCode();
        if (statusCode == 404 || statusCode == 500) {
            System.out.println("ID ou nome não cadastrado!");
        }
        String responseBody = response.body();

        Pet[] pets = mapper.readValue(responseBody, Pet[].class);
        System.out.println("Pets cadastrados:");
        Arrays.stream(pets).forEach(pet -> {
            String id = pet.getId();
            String type = pet.getType();
            String name = pet.getName();
            String breed = pet.getBreed();
            int age = pet.getAge();
            System.out.println(id + " - " + type + " - " + name + " - " + breed + " - " + age + " ano(s)");
        });
    }

}
