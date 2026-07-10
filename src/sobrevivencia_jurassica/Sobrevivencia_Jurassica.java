/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sobrevivencia_jurassica;

import Desenhaveis.Blank;
import Desenhaveis.Caixa;
import java.util.ArrayList;
import java.util.List;
import Desenhaveis.Desenhavel;
import Desenhaveis.Dinossauro;
import Desenhaveis.Jogador;
import Desenhaveis.Parede;
import Desenhaveis.Personagem;
import Sistemas.Jogo;
import Sistemas.MenuManager;
import Sistemas.Tabuleiro;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Eduardo Ramos, Felipe Ferreira e Rafael Bermudes
 */
public class Sobrevivencia_Jurassica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int linhas = 10;
        int colunas = 10;
        Desenhavel[][] tabuleiro = new Desenhavel[linhas][colunas];
        int conteudo = 0;
        List<Personagem> ativos = new ArrayList<>();
        File mapFile = null;
        Scanner teclado = new Scanner(System.in);
        
        int mapa = (int) (Math.random() * 3);
        
        switch (mapa) {
            case 0:
                mapFile = new File("template1.txt");
                break;
            case 1:
                mapFile = new File("template2.txt");
                break;
            case 2:
                mapFile = new File("template3.txt");
                break;
        }
        
        try (Scanner readFile = new Scanner(mapFile)) { 
            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < colunas; j++) {
                    String token = readFile.next();
                    char lido = token.charAt(0);

                    createObject(j, i, lido, ativos, tabuleiro, conteudo, teclado);
                    if (lido == 'X') conteudo++;
                }
            }
        } catch (FileNotFoundException e) {
            char[][] template = {{'0', '0', '0', '0', 'T', '0', '1', '0', '0', '0'}, 
                                {'0', 'P', '0', '0', '0', '0', '1', '0', 'R', '0'}, 
                                {'0', '0', '0', '0', '0', '0', '1', '0', '0', '0'}, 
                                {'0', '0', '0', '0', 'C', 'X', '1', '0', 'V', '0'}, 
                                {'0', '0', '0', '0', '1', '1', '1', '0', '0', '0'}, 
                                {'0', 'T', '0', '0', '1', 'X', '0', '0', '0', '0'}, 
                                {'0', '0', '0', '1', '1', '0', '0', '0', '0', '0'}, 
                                {'0', '0', '0', '1', '0', 'T', '0', '1', '0', 'T'}, 
                                {'C', '0', '0', '0', '0', '0', '0', '1', '0', '0'}, 
                                {'X', 'C', '0', '0', '0', 'V', '0', '1', '0', 'X'}};
            
            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < colunas; j++) {
                    char lido = template[i][j];

                    createObject(j, i, lido, ativos, tabuleiro, conteudo, teclado);
                    if (lido == 'X') conteudo++;
                }
            }
        }
        
        Tabuleiro tab = new Tabuleiro(tabuleiro, linhas, colunas);

        MenuManager menuManager = new MenuManager();
        
        Jogo jogo = new Jogo(tab, ativos, menuManager, teclado);
        
        jogo.start();
    }
    
    /*
    public static int combate (Jogador player, Dinossauro dino, Scanner teclado) {
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        int morte = 0;
        while (morte == 0) {
            System.out.println("Vida Dinossauro: " + dino.getHp() );
            System.out.println("Vida Player: " + player.getHp() );
            
            System.out.println("""
                               1. Atacar
                               2. Fugir
                               Qual acao deseja fazer?""");
            char acao = teclado.next().charAt(0);
            switch (acao) {
                case '1':
                    ataque(player, dino, teclado);
                    if (dino.getHp() < 1) {
                        System.out.println("Dinossauro morto.");
                        morte = 2;
                        break;
                    }
                            
                    defesa(player, dino);
                    if (player.getHp() < 1){
                        System.out.println("Player morreu.");
                        morte = 1;
                        break;
                    }
                    break;
                    
                case '2':
                    return 0;
                    
                default:
                    System.out.println("Digitou errado, logo foi burro, logo perdeu a vez\n\nOtario.\n\n"); 
                    defesa(player, dino);
                    if (player.getHp() < 1){
                        System.out.println("Player morreu.");
                        morte = 1;
                        break;
                    }
                    break;
            }
            
        }
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        return morte;
    }
    
    public static void ataque(Jogador player, Dinossauro dino, Scanner teclado) {
        Random rand = new Random();
        int dado1 = rand.nextInt((6 - 1) + 1) + 1;
        System.out.println("""
            1. Mao
            2. Bastao eletrico
            3. Arma de dardos
            Qual arma deseja usar?""");
        char decisao = teclado.next().charAt(0);
        switch (decisao) {
            case '1':
                if (dino.getTomaDanoDeSoco()) {
                    System.out.println("Dado do player: " + dado1);
                    if (dado1 > 4) {
                        System.out.println("Dano dado: 2\n");
                        dino.setHp(dino.getHp() - player.getDano() * 2);
                    } else if (dado1 > 2) {
                        System.out.println("Dano dado: 1\n");
                        dino.setHp(dino.getHp() - player.getDano());
                    } else 
                        System.out.println("Nenhum dano dado\n");

                    System.out.println("Vida Dinossauro: " + dino.getHp());
                    System.out.println("Vida Player: " + player.getHp());
                } else {
                    System.out.println("Ele e muito forte para matar no soco \nOh nao\n");
                }
                
                break;
                            
            case '2':
                if (player.getBastao()) {
                    System.out.println("Dado do player: " + dado1);
                    if (dado1 > 4) {
                        System.out.println("Dano dado: 2\n");
                        dino.setHp(dino.getHp() - player.getDano() * 2);
                    } else if (dado1 > 1) {
                        System.out.println("Dano dado: 1\n");
                        dino.setHp(dino.getHp() - player.getDano());
                    } else 
                        System.out.println("Nenhum dano dado\n");

                    System.out.println("Vida Dinossauro: " + dino.getHp() );
                    System.out.println("Vida Player: " + player.getHp() );

                } else {
                    System.out.println("Tentou usar arma que nao tem, logo perdeu a vez\n\nBurro.\n\n");
                }
                break;

            case '3':
                if (player.getArma()) {
                    if (dino.getTomaDanoDeArma()) {
                        System.out.println(player.getMunicao());
                        dino.setHp(dino.getHp() - player.getDano() * 2);
                        player.setMunicao(-1);
                        System.out.println(player.getMunicao());
                        System.out.println("Vida Dinossauro: " + dino.getHp());
                        System.out.println("Vida Player: " + player.getHp());
                    } else {
                        System.out.println("Ele e muito rapido para acertar o tiro \nOh nao\n");
                    }
                } else {
                    System.out.println("Tentou usar arma que nao tem, logo perdeu a vez\n\nBurro.\n\n");
                }
                break;

            default:
                System.out.println("Digitou errado, logo foi burro, logo perdeu a vez\n\nOtario.\n\n");
                break;
        }
    }
    
    public static void defesa(Jogador player, Personagem dino) {
        Random rand = new Random();
        int dado2 = rand.nextInt((4 - 1) + 1) + 1;
        System.out.println("Dado de defesa do player: " + dado2);
        if (dado2 > player.getPercepcao()) {
            player.setHp(player.getHp() - 1);
            System.out.println("Player tomou 1 de dano.\n");
        } else
            System.out.println("Player nao tomou dano.\n");
    }
    
    */
    
    public static void createObject(int x, int y, char simbolo, List<Personagem> ativos, Desenhavel[][] tabuleiro, int conteudo, Scanner teclado) {
        switch (simbolo) {
            case 'P':
                tabuleiro[y][x] = new Jogador(x, y, 5, 1, 1, 3, teclado);
                ativos.add(0, (Personagem)tabuleiro[y][x]);
                break;
            case '1':
                tabuleiro[y][x] = new Parede(x,y);
                break;
            case 'C':
                tabuleiro[y][x] = new Dinossauro(x, y, 'C', 1, 1, 1, true, true);
                ativos.add((Personagem)tabuleiro[y][x]);
                break;
            case 'V':
                tabuleiro[y][x] = new Dinossauro(x, y, 'V', 2, 1, 2, true, false);
                ativos.add((Personagem)tabuleiro[y][x]);
                break;
            case 'R':
                tabuleiro[y][x] = new Dinossauro(x, y, 'R', 3, 1, 0, false, true);
                ativos.add((Personagem)tabuleiro[y][x]);
                break;
            case 'T':
                tabuleiro[y][x] = new Dinossauro(x, y, 'T', 2, 1, 1, true, true);
                ativos.add((Personagem)tabuleiro[y][x]);
                break;
            case 'X':
                tabuleiro[y][x] = new Caixa(x, y, conteudo);
                break;
            default:
                tabuleiro[y][x] = new Blank(x,y);
                break;
        }
    }
    
    /*
    
    public static void abrirCaixa(Jogador player, Caixa caixa) {
        int conteudo = caixa.getConteudo();
    
        switch (conteudo) {
            case 0: // Kit médico
                player.setKit(true);
                System.out.println("Voce encontrou um kit medico.");
                break;
            case 1: // Bastão elétrico
                player.setBastao(true);
                System.out.println("Voce encontrou um bastao eletrico.");
                break;
            case 2: // Arma de dardos
                    player.setArma(true);
                    player.setMunicao(1); 
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
    
    public static boolean exibirMenu(Jogador jogador, boolean debugAtivo, Scanner teclado) {

            

            char opcao;
            
            do {
                opcao = teclado.next().charAt(0);
                switch (opcao) {
                    case '1':
                        break;

                    case '2': // Botão Cura
                        if (jogador.getKit()) {
                            jogador.setHp(jogador.getHp() + 5);
                            jogador.setKit(false);
                            System.out.println("Vida restaurada.");
                            if ( jogador.getHp() > 5 ) jogador.setHp(5);
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
    
    public static void imprimir(Desenhavel[][] tabuleiro, Jogador jogador, boolean debugAtivo) {
        int playerX = jogador.getX();
        int playerY = jogador.getY();
        int percepcao = jogador.getPercepcao();

        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                if (debugAtivo) {
                    System.out.printf("%c ", tabuleiro[i][j].getSimbolo());
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
                        System.out.printf("%c ", tabuleiro[i][j].getSimbolo());
                    } else {
                        System.out.print("? "); 
                    }
                }
            }
            System.out.print("\n");
        }
    }

    */
}