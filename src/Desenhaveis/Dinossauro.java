/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Desenhaveis;

import Sistemas.Tabuleiro;

/**
 *
 * @author felip
 */
public class Dinossauro extends Personagem implements Combatente {
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
    public int atacar(Personagem alvo) {
        //IMPLEMENTAR
    }
    
    @Override
    public boolean defender() {
        //IMPLEMENTAR
        //OBS: se os dinossauros nao defenderem deixa esse metodo so com "return false;"
    }
    
    @Override
    public Desenhavel mover(Tabuleiro tabuleiro) {
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

            if (newX >= 0 && newX < tabuleiro.getColunas() &&
                newY >= 0 && newY < tabuleiro.getLinhas() &&
                (tabuleiro.getTile(newX, newY) instanceof Blank || tabuleiro.getTile(newX, newY) instanceof Jogador || tabuleiro.getTile(newX, newY) instanceof Caixa)) {

                colisao = tabuleiro.getTile(newX, newY);

                if (tabuleiro.getTile(newX, newY) instanceof Blank || tabuleiro.getTile(newX, newY) instanceof Caixa) {
                    tabuleiro.setTile(this, newX, newY);
                }
                tabuleiro.setTile(new Blank(oldX, oldY), oldX, oldY);
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
