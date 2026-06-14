/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Desenhaveis;

import java.util.Scanner;

/**
 *
 * @author felip
 */
public class Jogador extends Personagem {
    private final int percepcao;
    private boolean temBastao;
    private boolean temArma;
    private boolean temKit;
    private int municao;
    
    public Jogador(int x, int y, int hp, int dano, int velocidade, int percepcao) {
        super(x, y, 'P', hp, dano, velocidade);
        this.percepcao = percepcao;
        this.temBastao = false;
        this.temArma = false;
        this.temKit = false;
        this.municao = 0;
    }
    
    @Override
    public Desenhavel mover(Desenhavel[][] tabuleiro, Scanner teclado) {
        char direcao;
        int oldX = getX();
        int oldY = getY();
        int newX = oldX;
        int newY = oldY;
        Desenhavel colisao = null;
        
        do {
            direcao = teclado.next().charAt(0);
            switch(direcao) {
                case 'd':
                    newX = oldX + 1;
                    break;
                case 'w':
                    newY = oldY - 1;
                    break;
                case 'a':
                    newX = oldX - 1;
                    break;
                case 's':
                    newY = oldY + 1;
                    break;
                case '0':
                    System.exit(0);
                    break;
                default:
                    break;
            }
        } while (direcao != 'a' && direcao != 'd' && direcao != 'w' && direcao != 's');
        
        if (newX >= 0 && newX < tabuleiro[0].length &&
            newY >= 0 && newY < tabuleiro.length &&
            !(tabuleiro[newY][newX] instanceof Parede)) {
            
            colisao = tabuleiro[newY][newX];
            
            tabuleiro[newY][newX] = this;
            tabuleiro[oldY][oldX] = new Blank(oldX, oldY);
            setX(newX);
            setY(newY);
        }
        
        return colisao;
    }
}
