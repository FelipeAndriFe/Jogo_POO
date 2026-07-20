package Itens;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
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
