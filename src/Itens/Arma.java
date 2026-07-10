/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Itens;

/**
 *
 * @author felip
 */
public abstract class Arma extends Item {
    private int municao;
    
    public Arma() {
        this.municao = 1;
    }
    
    public int getMunicao() {
        return municao;
    }
    
    public void setMunicao(int x) {
        if (x < 0) {
            municao = 0;
        } else {
            municao = x;
        }
    }
    
    public abstract boolean usar();
}
