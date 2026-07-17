/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sistemas;

import Desenhaveis.Blank;
import Desenhaveis.Caixa;
import Desenhaveis.Desenhavel;
import Desenhaveis.Dinossauro;
import Desenhaveis.Jogador;
import Desenhaveis.Personagem;
import GUI.GamePanel;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JFrame;

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
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Sobrevivencia Jurassica");
        
        GamePanel gamePanel = new GamePanel(tabuleiro, (Jogador) ativos.get(0));
        window.add(gamePanel);
        
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
        
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
            
            Jogador player = (Jogador) ativos.get(0);
            int playerX = player.getX();
            int playerY = player.getY();
            
            debugAtivo = menuManager.menuAcoes( player, debugAtivo, teclado);
            colisao = ativos.get(0).mover(tabuleiro);
            
            if (colisao instanceof Caixa) abrirCaixa((Jogador) ativos.get(0), (Caixa) colisao);
            
            // caso player bata no dinossauro
            if (colisao instanceof Dinossauro) {
                
                Dinossauro dino = (Dinossauro) colisao;
                int dinoX = dino.getX();
                int dinoY = dino.getY();
                
                flag = combate( player, dino, teclado );

                if ( flag == 2 ) {
                        ativos.remove( dino );
                } else if (flag == 1) {
                        start();
                        return;
                } else {
                    System.out.println("Covarde.");        
                    voltarPosicao(player, dino, playerX, playerY, dinoX, dinoY);
                }
            }
            
            imprimir(tabuleiro, (Jogador) ativos.get(0), debugAtivo);
            
            for (int i = 0; i < ativos.size(); i++) {        
                if (ativos.size() == 1) {
                    System.out.println("Parabens Ganhastes o Jogo!!!");
                    return;
                }
                
                int dinoX = 0;
                int dinoY = 0;
                    
                if (i == 0) {
                    debugAtivo = menuManager.menuAcoes( player, debugAtivo, teclado);
                    playerX = player.getX();
                    playerY = player.getY();
                } 
                if ( ativos.get(i) instanceof Dinossauro ){
                    dinoX = ativos.get(i).getX();
                    dinoY = ativos.get(i).getY();
                }
                colisao = ativos.get(i).mover(tabuleiro);
                
                if (i == 0) {
                    if (colisao instanceof Caixa) abrirCaixa((Jogador) ativos.get(0), (Caixa) colisao);
                }
                
                // caso o player bata no dinossauro
                if (colisao instanceof Dinossauro) {
                    
                    Dinossauro dino = (Dinossauro) colisao;
                    dinoX = dino.getX();
                    dinoY = dino.getY();
                    
                    flag = combate( player, dino, teclado );
                    if (flag == 2) {
                        ativos.remove( dino );
                    } else if (flag == 1) {
                        start();
                        return;
                    } else {
                        System.out.println("Covarde.");        
                        voltarPosicao(player, dino, playerX, playerY, dinoX, dinoY);
                    }
                }
                // caso o dinossauro bata no player
                if (colisao instanceof Jogador) {
                    
                    Dinossauro dino = (Dinossauro) ativos.get(i);
                    
                    flag = combate( player, dino, teclado );
                    if (flag == 2) {
                        ativos.remove( dino );
                    } else if (flag == 1) {
                        start();
                        return;
                    } else {
                        System.out.println("Covarde.");      
                        voltarPosicao(player, dino, playerX, playerY, dinoX, dinoY);
                    }
                }
            }
            imprimir(tabuleiro, (Jogador) ativos.get(0), debugAtivo);
        }
    }
    
    
    
    public static int combate(Jogador player, Personagem dino, Scanner teclado) {
        //IMPLEMENTAR
        //A ideia é a funcao ficar chamando os metodos "atacar()" e "defender()"
        //O codigo antigo ta comentado 
        //OBS: se o jogador morrer deve ser dado um "return" no metodo start() pro programa voltar pro main
        System.out.println();
        
        final int FUGIU = 0;
        final int PLAYER_MORREU = 1;
        final int DINO_MORREU = 2;
        while (true) {
            System.out.println("Vida Dinossauro: " + dino.getHp() );
            System.out.println("Vida Player: " + player.getHp() );
            
            System.out.println("""
                               1. Atacar
                               2. Fugir
                               Qual acao deseja fazer?""");
            char acao = teclado.next().charAt(0);
            switch ( acao ) {
                case '1':
                    int dano = player.atacar( dino );
                    System.out.println("Dano dado: " + dano );
                    dino.setHp( dino.getHp() - dano );
                    
                    if (dino.getHp() < 1) {
                        System.out.println("Dinossauro morto.");
                        return DINO_MORREU;
                    }
                            
                    if ( player.defender() == false )
                        player.setHp( player.getHp() - 1 );
                    
                    if ( player.getHp() < 1 ){
                        System.out.println("Player morreu.");
                        return PLAYER_MORREU;
                    }
                    break;
                    
                case '2':
                    return FUGIU;
                    
                default:
                    System.out.println("Digitou errado, perdeu a vez\n"); 
                    if ( player.defender() == false )
                        player.setHp( player.getHp() - 1 );
                    if ( player.getHp() < 1 ) {
                        System.out.println("Player morreu.");
                        return PLAYER_MORREU;
                    }
                    break;
            }
            
        }
    }
    
    public void voltarPosicao(Jogador player, Dinossauro dino, int playerX, int playerY, int dinoX, int dinoY) {

        tabuleiro.setTile( new Blank( player.getX(), player.getY() ), player.getX(), player.getY() );
        tabuleiro.setTile( new Blank( dino.getX(), dino.getY() ), dino.getX(), dino.getY() );

        player.setX(playerX);
        player.setY(playerY);

        dino.setX(dinoX);
        dino.setY(dinoY);

        tabuleiro.setTile(player, playerX, playerY);
        tabuleiro.setTile(dino, dinoX, dinoY);
        
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
