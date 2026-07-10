/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sistemas;

import Desenhaveis.Caixa;
import Desenhaveis.Desenhavel;
import Desenhaveis.Jogador;
import Desenhaveis.Personagem;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author felip
 */
public class Jogo {
    private Tabuleiro tabuleiro;
    private List<Personagem> ativos;
    private MenuManager menuManager;
    private Scanner teclado;
    
    public Jogo(Tabuleiro t, List<Personagem> a, MenuManager m, Scanner teclado) {
        this.tabuleiro = t;
        this.ativos = a;
        this.menuManager = m;
        this.teclado = teclado;
    }
    
    public void start() {
        boolean debugAtivo = false;
        
        menuManager.menuBoasVindas();
        
        char escolha = teclado.next().charAt(0);
               
        switch (escolha) {
            case '1':
                menuManager.menuDificuldade();

                char dificuldade = teclado.next().charAt(0);

                switch (dificuldade) {
                    case '1':
                        ((Jogador) ativos.get(0)).setPercepcao(3);
                        break;

                    case '2':
                        ((Jogador) ativos.get(0)).setPercepcao(2);
                        break;

                    case '3':
                        ((Jogador) ativos.get(0)).setPercepcao(1);
                        break;

                    default:
                        break;
                }
            break;
            
            case '2':
                System.exit(0);
                break;
                
            default:
                System.out.println("Digitou errado. Tchau.");
                System.exit(0);
        }
        
        imprimir(tabuleiro, (Jogador) ativos.get(0), debugAtivo);
            
        while (true) {
            Desenhavel colisao;
            int flag;
            debugAtivo = menuManager.menuAcoes((Jogador) ativos.get(0), debugAtivo, teclado);
            colisao = ativos.get(0).mover(tabuleiro);
            
            if (colisao instanceof Caixa) abrirCaixa((Jogador) ativos.get(0), (Caixa) colisao);
            
            /*
            // caso player bata no dinossauro
            if (colisao instanceof Dinossauro) {
                flag = combate((Jogador) ativos.get(0), (Dinossauro) colisao, teclado);
                if (flag == 2) {
                        ativos.remove((Personagem) colisao);
                } else if (flag == 1) {
                        System.exit(0);
                } else {
                    System.out.println("Covarde.");        
                    System.out.println("Para onde deseja se mover? ");
                    ativos.get(0).mover(tabuleiro);
                    ((Dinossauro) colisao).mover(tabuleiro); 
                }
            }
            */
            
            imprimir(tabuleiro, (Jogador) ativos.get(0), debugAtivo);
            
            for (int i = 0; i < ativos.size(); i++) {        
                if (ativos.size() == 1) {
                    System.out.println("Parabens Ganhastes o Jogo!!!");
                    return;
                }
                
                if (i == 0) {
                    debugAtivo = menuManager.menuAcoes((Jogador) ativos.get(0), debugAtivo, teclado);;
                }
                colisao = ativos.get(i).mover(tabuleiro);
                
                if (i == 0) {
                    if (colisao instanceof Caixa) abrirCaixa((Jogador) ativos.get(0), (Caixa) colisao);
                }
                
                /*
                // caso o player bata no dinossauro
                if (colisao instanceof Dinossauro) {
                    flag = combate((Jogador) ativos.get(0), (Dinossauro) colisao, teclado);
                    if (flag == 2) {
                        ativos.remove((Personagem) colisao);
                    } else if (flag == 1) {
                        System.exit(0);
                    } else {
                        System.out.println("Covarde."); 
                        System.out.println("Para onde deseja se mover? ");
                        ativos.get(0).mover(tabuleiro);
                        ((Dinossauro) colisao).mover(tabuleiro);
                        continue;
                    }
                }
                // caso o dinossauro bata no player
                if (colisao instanceof Jogador) {
                    flag = combate((Jogador) colisao, (Dinossauro) ativos.get(i), teclado);
                    if (flag == 2) {
                        ativos.remove(i);
                    } else if (flag == 1) {
                        System.exit(0);
                    } else {
                        System.out.println("Covarde.");            
                        System.out.println("Para onde deseja se mover? ");
                        ((Jogador) colisao).mover(tabuleiro);
                        ativos.get(i).mover(tabuleiro);
                        continue;
                    }
                }
                */
            }
            imprimir(tabuleiro, (Jogador) ativos.get(0), debugAtivo);
        }
    }
    
    public static int combate(Personagem atacante, Personagem defensor) {
        //IMPLEMENTAR
        //A ideia é a funcao ficar chamando os metodos "atacar()" e "defender()"
        //O codigo antigo ta comentado 
        //OBS: se o jogador morrer deve ser dado um "return" no metodo start() pro programa voltar pro main
    }
    
    public static void abrirCaixa(Jogador player, Caixa caixa) {
        int conteudo = caixa.getConteudo();
    
        switch (conteudo) {
            case 0: // Kit médico
                player.pegarKit();
                System.out.println("Voce encontrou um kit medico.");
                break;
                
            case 1: // Bastão elétrico
                player.pegarBastao();
                System.out.println("Voce encontrou um bastao eletrico.");
                break;
                
            case 2: // Arma de dardos
                    player.pegarArmaDeDardos();
                    System.out.println("Voce encontrou uma arma de dardos.");
                break;
                
            case 3: // Compsognato surpresa
                Random rand = new Random();
                int surpresa = rand.nextInt(3) + 1;
                
                if (surpresa <= player.getPercepcao()) {
                    System.out.println("Um compsognato selvagem apareceu, mas voce desviou!");
                } else {
                    System.out.println("Um compsognato selvagem apareceu, voce foi atacado! -1 de vida");
                    player.setHp(player.getHp() - 1);
                }
                break;
        }
    }
    
    public static void imprimir(Tabuleiro tabuleiro, Jogador jogador, boolean debugAtivo) {
        int playerX = jogador.getX();
        int playerY = jogador.getY();
        int percepcao = jogador.getPercepcao();

        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                if (debugAtivo) {
                    System.out.printf("%c ", tabuleiro.getTile(j, i).getSimbolo());
                } else {
                    boolean visivel = false;
                    int distX = Math.abs(j - playerX);
                    int distY = Math.abs(i - playerY);

                    if (percepcao == 3) {
                        visivel = (distX <= percepcao) && (distY <= percepcao);
                    } else if (percepcao == 2) {
                        boolean cruz = (i == playerY && distX <= 2) || (j == playerX && distY <= 2);
                        boolean diagonal = (distX == distY) && (distX <= 2);
                        visivel = cruz || diagonal;
                    } else {
                        visivel = (i == playerY && distX <= percepcao) || (j == playerX && distY <= percepcao);
                    }

                    if (visivel) {
                        System.out.printf("%c ", tabuleiro.getTile(j, i).getSimbolo());
                    } else {
                        System.out.print("? "); 
                    }
                }
            }
            System.out.print("\n");
        }
    }
}
