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
            
            colisao = ativos.get(0).mover(tabuleiro, teclado);
            if (colisao instanceof Dinossauro) ativos.remove((Personagem) colisao);
            imprimir(tabuleiro);
            
            for (int i = 0; i < ativos.size(); i++) {
                colisao = ativos.get(i).mover(tabuleiro, teclado);
                if (colisao instanceof Dinossauro) ativos.remove((Personagem) colisao);
                if (colisao instanceof Jogador) ativos.remove(i);
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
    
    public static void combate(Personagem atacante, Personagem defesor) {
        
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
