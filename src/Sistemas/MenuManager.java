/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sistemas;

import Desenhaveis.Jogador;
import java.util.Scanner;

/**
 *
 * @author felip
 */
public class MenuManager {
    public void menuBoasVindas() {
        System.out.println("Bem-vindo ao Sobrevivencia Jurassica!");
        System.out.println("1. Novo Jogo");
        System.out.println("2. Sair");
    }
    
    //Nao usado por enquanto
    public void menuTermino() {
        System.out.println("Sessao encerrada.");
        System.out.println("1. Novo Jogo");
        System.out.println("2. Reiniciar Fase");
        System.out.println("3. Sair");
    }
    
    public void menuDificuldade() {
        System.out.println("""
                            1. Facil
                            2. Medio
                            3. Dificil
                            """);
    }
    
    public boolean menuAcoes(Jogador jogador, boolean debugAtivo, Scanner teclado) {
        System.out.println("\nEscolha uma opcao:");
        System.out.println("1. Mover");
        System.out.println("2. Cura");
        System.out.println("3. DEBUG");
        System.out.println("4. Sair");
        
        char opcao;
            
        do {
            opcao = teclado.next().charAt(0);
            switch (opcao) {
                case '1':
                    break;

                case '2': // Botão Cura
                    boolean curou = jogador.curar();
                    if (curou) {
                        System.out.println("Vida restaurada.");
                    } else {
                        System.out.println("Sem kit medico disponivel.");
                    }    
                    break;

                case '3': // DEBUG
                    System.out.println("Modo debug alterado para: " + (debugAtivo ? "DESLIGADO" : "LIGADO"));
                    debugAtivo = !debugAtivo;
                    break;

                case '4': // Sair
                    System.out.println("Jogo encerrado.");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opcao invalida");
                    break;
            }
        } while (opcao != '1');
            
        return debugAtivo;
    }
}
