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
public class Dinossauro extends Personagem {
    private final boolean tomaDanoDeSoco;
    private final boolean tomaDanoDeArma;
    
    public Dinossauro(int x, int y, char simbolo, int hp, int dano, int velocidade, boolean tomaDanoDeSoco, boolean tomaDanoDeArma) {
        super(x, y, simbolo, hp, dano, velocidade);
        this.tomaDanoDeSoco = tomaDanoDeSoco;
        this.tomaDanoDeArma = tomaDanoDeArma;
    }
    
    @Override
    public char mover(char[][] tabuleiro, Scanner teclado) {
        int direcao = (int) (Math.random() * 4);
        int oldX = getX();
        int oldY = getY();
        int newX = oldX;
        int newY = oldY;
        char colisao = '0';
        
        switch(direcao) {
            case 0:
                newX = oldX + getVelocidade();
                break;
            case 1:
                newY = oldY - getVelocidade();
                break;
            case 2:
                newX = oldX - getVelocidade();
                break;
            case 3:
                newY = oldY + getVelocidade();
                break;
        }
        
        if (newX >= 0 && newX < tabuleiro[0].length &&
            newY >= 0 && newY < tabuleiro.length &&
            (tabuleiro[newY][newX] == '0' || tabuleiro[newY][newX] == 'P')) {
            
            colisao = tabuleiro[newY][newX];
            
            tabuleiro[newY][newX] = getSimbolo();
            tabuleiro[oldY][oldX] = '0';
            setX(newX);
            setY(newY);
        }
        
        return colisao;
    }
}
