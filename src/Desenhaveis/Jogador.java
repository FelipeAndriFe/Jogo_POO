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
    public char mover(char[][] tabuleiro, Scanner teclado) {
        char direcao = teclado.next().charAt(0);
        
        int oldX = getX();
        int oldY = getY();
        int newX = oldX;
        int newY = oldY;
        char colisao = '0';
        
        switch(direcao) {
            case 'd':
                newX = oldX + getVelocidade();
                break;
            case 'w':
                newY = oldY - getVelocidade();
                break;
            case 'a':
                newX = oldX - getVelocidade();
                break;
            case 's':
                newY = oldY + getVelocidade();
                break;
        }
        
        if (newX >= 0 && newX < tabuleiro[0].length &&
            newY >= 0 && newY < tabuleiro.length &&
            tabuleiro[newY][newX] != '1') {
            
            colisao = tabuleiro[newY][newX];
            
            tabuleiro[newY][newX] = getSimbolo();
            tabuleiro[oldY][oldX] = '0';
            setX(newX);
            setY(newY);
        }
        
        return colisao;
    }
}
