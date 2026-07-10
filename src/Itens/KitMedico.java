/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Itens;

import Desenhaveis.Jogador;

/**
 *
 * @author felip
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
