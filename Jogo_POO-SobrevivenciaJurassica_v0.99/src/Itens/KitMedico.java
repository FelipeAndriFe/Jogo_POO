package Itens;

import Desenhaveis.Jogador;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
 */
public class KitMedico extends Item {
    private int quantidade;
    private final int cura;
    
    public KitMedico() {
        quantidade = 0;
        cura = 5;
    }
    
    public int getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(int x) {
        if (x < 0) {
            quantidade = 0;
        } else {
            quantidade = x;
        }
    }
    
    public boolean usar(Jogador jogador) {
        if (quantidade <= 0) return false;
        
        jogador.setHp(jogador.getHp() + cura);
        quantidade--;
        return true;
    }
}
