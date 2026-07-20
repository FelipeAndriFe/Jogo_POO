package Desenhaveis;

import Sistemas.Tabuleiro;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
 */
public abstract class Personagem extends Desenhavel {
    private int hp;
    private final int dano;
    private final int velocidade;
    private final int maxHp;
    
    public Personagem(int x, int y, char simbolo, int hp, int dano, int velocidade) {
        super(x, y, simbolo);
        this.hp = hp;
        this.dano = dano;
        this.velocidade = velocidade;
        this.maxHp = hp;
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
        if (novoHp > maxHp) {
            this.hp = maxHp;
        } else {
            this.hp = novoHp;
        }
    }
    
    public abstract Desenhavel mover(Tabuleiro tabuleiro);
}
