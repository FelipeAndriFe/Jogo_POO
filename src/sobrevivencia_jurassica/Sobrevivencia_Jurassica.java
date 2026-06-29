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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

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
        
        switch(mapa) {
            case 0:
                mapFile = new File("template1.txt");
                break;
            case 1:
                mapFile = new File("template1.txt");
                break;
            case 2:
                mapFile = new File("template1.txt");
                break;
        }
        
        try(Scanner readFile = new Scanner(mapFile)) { 
            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < colunas; j++) {
                    String token = readFile.next();
                    char lido = token.charAt(0);

                    createObject(j, i, lido, ativos, tabuleiro, conteudo);
                    if (lido == 'X') conteudo++;
                }
            }
        } catch(FileNotFoundException e) {
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

                    createObject(j, i, lido, ativos, tabuleiro, conteudo);
                    if (lido == 'X') conteudo++;
                }
            }
        }

        imprimir(tabuleiro);
        while(true) {
            Desenhavel colisao;
            int flag;
            colisao = ativos.get(0).mover(tabuleiro, teclado);
            // caso dinossauro bata no player
            if (colisao instanceof Dinossauro) {
                flag = combate((Jogador) ativos.get(0), (Dinossauro) colisao, teclado);
                if( flag == 2) {
                        ativos.remove((Personagem) colisao);
                } else if ( flag == 1 ) {
                        System.exit(0);
                } else {
                    System.out.println("Covarde.");        
                    System.out.println("Para onde deseja se mover? ");
                    ativos.get(0).mover(tabuleiro, teclado);
                    ((Dinossauro) colisao).mover(tabuleiro, teclado); 
                }
            }
            imprimir(tabuleiro);
            
            for (int i = 0; i < ativos.size(); i++) {        
                if ( ativos.size() == 1 ) {
                    System.out.println("Parabens Ganhastes o Jogo!!!");
                    System.exit(0);
                }
                colisao = ativos.get(i).mover(tabuleiro, teclado);
                // caso o dinossauro bata no player
                if (colisao instanceof Dinossauro) {
                    flag = combate((Jogador) ativos.get(0), (Dinossauro) colisao, teclado);
                    if( flag == 2) {
                        ativos.remove((Personagem) colisao);
                    } else if ( flag == 1 ) {
                        System.exit(0);
                    } else {
                        System.out.println("Covarde."); 
                        System.out.println("Para onde deseja se mover? ");
                        ativos.get(0).mover(tabuleiro, teclado);
                        ((Dinossauro) colisao).mover(tabuleiro, teclado);
                        continue;
                    }
                }
                // caso o player bata no dinossauro
                if (colisao instanceof Jogador) {
                    flag = combate((Jogador) colisao, (Dinossauro) ativos.get(i), teclado);
                    if( flag == 2) {
                        ativos.remove(i);
                    } else if ( flag == 1 ) {
                        System.exit(0);
                    } else {
                        System.out.println("Covarde.");            
                        System.out.println("Para onde deseja se mover? ");
                        ((Jogador) colisao).mover(tabuleiro, teclado);
                        ativos.get(i).mover(tabuleiro, teclado);
                        continue;
                    }
                }
            }
            imprimir(tabuleiro);
            
        }
    }
    
    public static void imprimir(Desenhavel[][] tabuleiro) {
        for (int i = 0; i < tabuleiro[0].length; i++) {
            for (int j = 0; j < tabuleiro.length; j++) {
                System.out.printf("%c ", tabuleiro[i][j].getSimbolo());
            }
            System.out.print("\n");
        }
    }
    
    public static int combate(Jogador player, Dinossauro dino, Scanner teclado) {
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        int morte = 0;
        while( morte == 0 ) {
            System.out.println("Vida Dinossauro: " + dino.getHp() );
            System.out.println("Vida Player: " + player.getHp() );
            
            System.out.println("""
                               1. Atacar
                               2. Fugir
                               Qual acao deseja fazer?""");
            int acao = teclado.nextInt();
            switch(acao) {
                case 1:
                    ataque(player, dino, teclado);
                    if ( dino.getHp() < 1 ) {
                        System.out.println("Dinossauro morto.");
                        morte = 2;
                        break;
                    }
                            
                    defesa(player, dino);
                    if ( player.getHp() < 1 ){
                        System.out.println("Player morreu.");
                        morte = 1;
                        break;
                    }
                    break;
                    
                case 2:
                    return 0;
                    
                default:
                    System.out.println("Digitou errado, logo foi burro, logo perdeu a vez\n\nOtario.\n\n"); 
                    defesa(player, dino);
                    if ( player.getHp() < 1 ){
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
    
    public static void ataque ( Jogador player, Dinossauro dino, Scanner teclado ) {
        Random rand = new Random();
        int dado1 = rand.nextInt((6 - 1) + 1) + 1;
        System.out.println("""
            1. Mao
            2. Bastao Eletrico
            3. Arma de Dardos
            Qual arma deseja usar?""");
        int decisao = teclado.nextInt();
        switch(decisao) {
            case 1:
                if ( dino.getTomaDanoDeSoco() ) {
                    System.out.println("Dado do player: " + dado1);
                    if ( dado1 > 4 ) {
                        System.out.println("Dano dado: 2\n");
                        dino.setHp(dino.getHp() - player.getDano() * 2);
                    } else if ( dado1 > 2 ) {
                        System.out.println("Dano dado: 1\n");
                        dino.setHp(dino.getHp() - player.getDano());
                    } else 
                        System.out.println("Nenhum dano dado\n");

                    System.out.println("Vida Dinossauro: " + dino.getHp() );
                    System.out.println("Vida Player: " + player.getHp() );
                } else {
                    System.out.println("Ele e muito forte para matar no soco \nOh nao\n");
                }
                
                break;
                            
            case 2:
                if ( player.getBastao() ) {
                    System.out.println("Dado do player: " + dado1);
                    if ( dado1 > 4 ) {
                        System.out.println("Dano dado: 2\n");
                        dino.setHp(dino.getHp() - player.getDano() * 2);
                    } else if ( dado1 > 1 ) {
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

            case 3:
                if ( player.getArma() ) {
                    if ( dino.getTomaDanoDeArma() ) {
                        System.out.println(player.getMunicao());
                        dino.setHp(dino.getHp() - player.getDano() * 2);
                        player.setMunicao();
                        System.out.println(player.getMunicao());
                        System.out.println("Vida Dinossauro: " + dino.getHp() );
                        System.out.println("Vida Player: " + player.getHp() );
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
    
    public static void defesa ( Jogador player, Personagem dino ) {
        Random rand = new Random();
        int dado2 = rand.nextInt((6 - 1) + 1) + 1;
        System.out.println("Dado de defesa do player: " + dado2);
        if ( dado2 > player.getPercepcao() ) {
            player.setHp( player.getHp() - 1 );
            System.out.println("Player tomou 1 de dano.\n");
        } else
            System.out.println("Player nao tomou dano.\n");
    }

    
    public static void createObject(int x, int y, char simbolo, List<Personagem> ativos, Desenhavel[][] tabuleiro, int conteudo) {
        switch(simbolo) {
            case 'P':
                tabuleiro[y][x] = new Jogador(x, y, 3, 1, 1, 3);
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
}
