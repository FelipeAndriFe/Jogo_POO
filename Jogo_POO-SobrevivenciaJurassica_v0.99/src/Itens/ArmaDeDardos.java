package Itens;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
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
