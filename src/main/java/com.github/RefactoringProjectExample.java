package com.github;

import com.github.client.ClientHttpConfiguration;
import com.github.service.PetService;
import com.github.service.ShelterService;

import java.util.Scanner;

public class RefactoringProjectExample {

    public static void main(String[] args) {
        ClientHttpConfiguration client = new ClientHttpConfiguration();
        PetService petService = new PetService(client);
        ShelterService shelterService = new ShelterService(client);

        System.out.println("##### BOAS VINDAS AO SISTEMA ADOPET CONSOLE #####");
        try {
            int opcaoEscolhida = 0;
            while (opcaoEscolhida != 5) {
                System.out.println("\nDIGITE O NÚMERO DA OPERAÇÃO DESEJADA:");
                System.out.println("1 -> Listar abrigos cadastrados");
                System.out.println("2 -> Cadastrar novo abrigo");
                System.out.println("3 -> Listar pets do abrigo");
                System.out.println("4 -> Importar pets do abrigo");
                System.out.println("5 -> Sair");

                String textoDigitado = new Scanner(System.in).nextLine();
                opcaoEscolhida = Integer.parseInt(textoDigitado);

                if (opcaoEscolhida == 1) {
                    shelterService.getShelters();
                } else if (opcaoEscolhida == 2) {
                    shelterService.addShelter();
                } else if (opcaoEscolhida == 3) {
                    petService.getPets();
                } else if (opcaoEscolhida == 4) {
                    petService.importPets();
                } else if (opcaoEscolhida == 5) {
                    break;
                } else {
                    System.out.println("NÚMERO INVÁLIDO!");
                    opcaoEscolhida = 0;
                }
            }
            System.out.println("Finalizando o programa...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    
}
