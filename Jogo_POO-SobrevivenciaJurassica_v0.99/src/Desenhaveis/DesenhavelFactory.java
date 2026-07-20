/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Desenhaveis;

import java.util.List;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
 */
public class DesenhavelFactory {
    public void createObject(int x, int y, char simbolo, List<Personagem> ativos, Desenhavel[][] tabuleiro, int conteudo) {
        switch (simbolo) {
            case 'P':
                tabuleiro[y][x] = Jogador.getInstance(x, y, 5, 1, 1, 3);
                ativos.add(0, (Personagem)tabuleiro[y][x]);
                break;
            case '1':
                tabuleiro[y][x] = new Parede(x,y);
                break;
            case 'C':
                tabuleiro[y][x] = new Compsognato(x, y, 'C', 1, 1, 1, true, true);
                ativos.add((Personagem)tabuleiro[y][x]);
                break;
            case 'V':
                tabuleiro[y][x] = new Velociraptor(x, y, 'V', 2, 1, 2, true, false);
                ativos.add((Personagem)tabuleiro[y][x]);
                break;
            case 'R':
                tabuleiro[y][x] = new Rex(x, y, 'R', 3, 1, 0, false, true);
                ativos.add((Personagem)tabuleiro[y][x]);
                break;
            case 'T':
                tabuleiro[y][x] = new Troodonte(x, y, 'T', 2, 1, 1, true, true);
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
