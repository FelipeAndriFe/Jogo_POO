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
public abstract class Personagem extends Desenhavel {
    private int hp;
    private int dano;
    private int velocidade;
    
    public Personagem(int x, int y, char simbolo, int hp, int dano, int velocidade) {
        super(x, y, simbolo);
        this.hp = hp;
        this.dano = dano;
        this.velocidade = velocidade;
    }
    
    public int getHp() {
        return hp;
    }
    
    public int getDano() {
        return dano;
    }
    
    public int getVelocidade() {
        return velocidade;
    }
    
    public void setHp(int novoHp) {
        this.hp = novoHp;
    }
    
    public abstract char mover(char[][] tabuleiro, Scanner teclado);
}
