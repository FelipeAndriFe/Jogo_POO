package GUI;

import Desenhaveis.Dinossauro;
import Desenhaveis.Jogador;
import Sistemas.Jogo;
import Sistemas.SaveManager;
import Sistemas.Tabuleiro;
import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
 */
public class PanelManager extends JFrame {
    private final CardLayout layout;
    private final JPanel telas;
    private final GamePanel gamePanel;
    private CombatPanel combatPanel;
    private final Jogo jogo;

    public PanelManager(Tabuleiro tabuleiro, Jogador jogador, Jogo jogo) {
        this.jogo = jogo;
        
        layout = new CardLayout();
        telas = new JPanel(layout);

        gamePanel = new GamePanel(tabuleiro, jogador, jogo);
        telas.add(gamePanel, "MAPA");
        add(telas);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {

                int resposta = JOptionPane.showConfirmDialog(
                        PanelManager.this,
                        "Deseja realmente sair do jogo?",
                        "Sair",
                        JOptionPane.YES_NO_OPTION
                );

                if (resposta == JOptionPane.YES_OPTION) {
                    SaveManager.salvar(tabuleiro, jogador);

                    dispose();
                    System.exit(0);
                }
            }
        });
        
        setVisible(true);
        gamePanel.startGameThread();
    }

    public void iniciarCombate(Jogador jogador, Dinossauro dino){
        gamePanel.setcombateAtivo(true);
        
        if (combatPanel != null)
            telas.remove(combatPanel);
        
        combatPanel = new CombatPanel(jogador, dino, jogo);
        telas.add(combatPanel, "COMBATE");
        
        layout.show(telas, "COMBATE");
     
    }

    public void voltarMapa(){
        gamePanel.setcombateAtivo(false);
        gamePanel.resetInput();

        layout.show(telas, "MAPA");
        gamePanel.requestFocusInWindow();
        
    }
    
    public GamePanel getGamePanel() {
        return this.gamePanel;
    }
}