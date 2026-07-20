package Desenhaveis;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
 */
public interface Combatente {
    public abstract int atacar( Personagem alvo, TipoAtaque tipo );
    public abstract boolean defender();
}
