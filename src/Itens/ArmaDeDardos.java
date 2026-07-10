/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Itens;

/**
 *
 * @author felip
 */
public class ArmaDeDardos extends Arma {
    public ArmaDeDardos() {
        super();
    }
    
    @Override
    public boolean usar() {
        if (this.getMunicao() <= 0) return false;
        
        this.setMunicao(this.getMunicao() - 1);
        return true;
    }
}
