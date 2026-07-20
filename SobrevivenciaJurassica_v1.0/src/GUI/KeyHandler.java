package GUI;

import Itens.Bastao;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
 */
public class KeyHandler implements KeyListener {
    private boolean up, down, left, right;
    private boolean debugAtivo = false;
    private boolean curar;
    private boolean sair;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        if (code == KeyEvent.VK_W) up = true;
        if (code == KeyEvent.VK_S) down = true;
        if (code == KeyEvent.VK_A) left = true;
        if (code == KeyEvent.VK_D) right = true;
        if (code == KeyEvent.VK_F5) debugAtivo = !debugAtivo;
        if (code == KeyEvent.VK_H) curar = true;
        if (code == KeyEvent.VK_ESCAPE) sair = true;
        verificarComando(code);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        
        if (code == KeyEvent.VK_W) up = false;
        if (code == KeyEvent.VK_S) down = false;
        if (code == KeyEvent.VK_A) left = false;
        if (code == KeyEvent.VK_D) right = false;
        if (code == KeyEvent.VK_H) curar = false;
        if (code == KeyEvent.VK_ESCAPE) sair = false;
    }
    
    public void resetKeys() {
        up = false;
        down = false;
        left = false;
        right = false;
        curar = false;
        sair = false;
    }
    
    private final int[] KONAMI = {
        KeyEvent.VK_UP,
        KeyEvent.VK_UP,
        KeyEvent.VK_DOWN,
        KeyEvent.VK_DOWN,
        KeyEvent.VK_LEFT,
        KeyEvent.VK_RIGHT,
        KeyEvent.VK_LEFT,
        KeyEvent.VK_RIGHT,
        KeyEvent.VK_B,
        KeyEvent.VK_A
    };

    private List<Integer> sequencia = new ArrayList<>();
    private boolean konamiAtivado = false;
    
    private void verificarComando(int code) {

        sequencia.add(code);

        if (sequencia.size() > KONAMI.length) sequencia.remove(0);
        
        if (sequencia.size() == KONAMI.length) {
            boolean comandoRealizado = true;

            for (int i = 0; i < KONAMI.length; i++) {
                if (sequencia.get(i) != KONAMI[i]) {
                    comandoRealizado = false;
                    break;
                }
            }

            if (comandoRealizado) konamiAtivado = true;
        }
    }
    
    public boolean getDebugAtivo() {
        return debugAtivo;
    }
    
    public boolean getUp() {
        return up;
    }
    
    public boolean getDown() {
        return down;
    }
    
    public boolean getLeft() {
        return left;
    }
    
    public boolean getRight() {
        return right;
    }
    
    public boolean getCurar() {
        return curar;
    }

    public boolean getSair() {
        return sair;
    }
    
    public boolean ativaCodigo() {
        if (konamiAtivado) {
            konamiAtivado = false;
            return true;
        }
        
        return false;
    }
}
