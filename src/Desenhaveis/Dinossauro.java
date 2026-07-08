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
    
    public boolean getTomaDanoDeSoco() {
        return tomaDanoDeSoco;
    }
    
    public boolean getTomaDanoDeArma() {
        return tomaDanoDeArma;
    }
    
    @Override
    public Desenhavel mover(Desenhavel[][] tabuleiro, Scanner teclado) {
        int oldX = getX();
        int oldY = getY();
        int newX = oldX;
        int newY = oldY;
        Desenhavel colisao = null;
        
        for (int i = 0; i < getVelocidade(); i++) {
            int direcao = (int) (Math.random() * 4);
            switch(direcao) {
                case 0:
                    newX = oldX + 1;
                    break;
                case 1:
                    newY = oldY - 1;
                    break;
                case 2:
                    newX = oldX - 1;
                    break;
                case 3:
                    newY = oldY + 1;
                    break;
            }

            if (newX >= 0 && newX < tabuleiro[0].length &&
                newY >= 0 && newY < tabuleiro.length &&
                (tabuleiro[newY][newX] instanceof Blank || tabuleiro[newY][newX] instanceof Jogador || tabuleiro[newY][newX] instanceof Caixa)) {

                colisao = tabuleiro[newY][newX];

                if (tabuleiro[newY][newX] instanceof Blank || tabuleiro[newY][newX] instanceof Caixa) tabuleiro[newY][newX] = this;
                tabuleiro[oldY][oldX] = new Blank(oldX, oldY);
                setX(newX);
                setY(newY);

                if (colisao instanceof Jogador) return colisao;
                
                oldX = newX;
                oldY = newY;
            }
        }
        
        return colisao;
    }
}
