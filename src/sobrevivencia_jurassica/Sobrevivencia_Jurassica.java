/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sobrevivencia_jurassica;

import java.util.ArrayList;
import java.util.List;
import Desenhaveis.Desenhavel;
import Desenhaveis.Dinossauro;
import Desenhaveis.Jogador;
import Desenhaveis.Personagem;
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
        Scanner teclado = new Scanner(System.in);
        
        int linhas = 10;
        int colunas = 10;
        char[][] tabuleiro = new char[linhas][colunas];
        
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                tabuleiro[i][j] = '0';
            }
        }
        tabuleiro[4][4] = '1';
        tabuleiro[5][4] = '1';
        tabuleiro[6][4] = '1';
        tabuleiro[3][4] = '1';
        
        Personagem jogador = new Jogador(2, 2, 3, 1, 1, 3);
        Personagem raptor = new Dinossauro(8, 8, 'V', 2, 1, 2, true, false);
        Personagem comp = new Dinossauro(8, 4, 'C', 1, 1, 1, true, true);
        
        List<Personagem> ativos = new ArrayList<>();
        ativos.add(jogador);
        ativos.add(raptor);
        ativos.add(comp);
        
        for (int i = 0; i < ativos.size(); i++) {
            Personagem obj = ativos.get(i);
            tabuleiro[obj.getY()][obj.getX()] = obj.getSimbolo();
        }
        
        imprimir(tabuleiro);
        while(true) {
            jogador.mover(tabuleiro, teclado);
            imprimir(tabuleiro);
            
            for (int i = 0; i < ativos.size(); i++) {
                char colisao = ativos.get(i).mover(tabuleiro, teclado);
            }
            imprimir(tabuleiro);
        }
    }
    
    public static void imprimir(char[][] tabuleiro) {
        for (int i = 0; i < tabuleiro[0].length; i++) {
            for (int j = 0; j < tabuleiro.length; j++) {
                System.out.printf("%c ", tabuleiro[i][j]);
            }
            System.out.print("\n");
        }
    }
}
