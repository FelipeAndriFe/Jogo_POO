package GUI;

import Desenhaveis.Caixa;
import Desenhaveis.Desenhavel;
import Desenhaveis.Dinossauro;
import Desenhaveis.Jogador;
import Sistemas.Jogo;
import Sistemas.SaveManager;
import Sistemas.Tabuleiro;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
 */
public class GamePanel extends JPanel implements Runnable {
    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 10;
    private final int maxScreenRow = 10;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;
    private boolean combateAtivo = false;
    
    private final KeyHandler keyH = new KeyHandler();
    
    private final Jogo jogo;
    private final Tabuleiro tabuleiro;
    private final Jogador jogador;
    
    private Thread gameThread;
    
    public GamePanel(Tabuleiro tabuleiro, Jogador jogador, Jogo jogo) {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight + 50));
        this.setBackground(new Color(50, 152, 57));
        this.setDoubleBuffered(true);

        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.jogo = jogo;
        this.tabuleiro = tabuleiro;
        this.jogador = jogador;
    }
    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setcombateAtivo(boolean bool) {
        this.combateAtivo = bool;
    }
    
    public void resetInput() {
        keyH.resetKeys();
    }
    
    @Override
    public void run() {
        while (gameThread != null) {
            update();
            
            repaint();
            
            try {
                Thread.sleep(100); // 10 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void update() {
        if (keyH.ativaCodigo()) {
            jogador.pegarBastao();
            jogador.pegarArmaDeDardos();
            jogador.pegarKit();
            jogador.setHp(5);

            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Interessante..."
            );
            return;
        }
        
        if (combateAtivo == true) return;
        int oldX = jogador.getX();
        int oldY = jogador.getY();
        Desenhavel colisao = null;

        if (keyH.getCurar()) {
            boolean curou = jogador.curar();
            if (curou) {
                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Vida restaurada!"
                );
            } else {
                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Você não possui kits médicos."
                );
            }
            keyH.resetKeys();
            return;
        }

        if (keyH.getSair()) {
            int resposta = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja sair do jogo?",
                "Sair",
                javax.swing.JOptionPane.YES_NO_OPTION
            );
            if (resposta == javax.swing.JOptionPane.YES_OPTION) {
                SaveManager.salvar(tabuleiro, jogador);
                System.exit(0);
            }
            keyH.resetKeys();
            return;
        }
        
        if (keyH.getUp() == true) {
            jogador.setDirecao('w');
            colisao = jogador.mover(tabuleiro);
        } else if (keyH.getDown() == true) {
            jogador.setDirecao('s');
            colisao = jogador.mover(tabuleiro);
        } else if (keyH.getLeft() == true) {
            jogador.setDirecao('a');
            colisao = jogador.mover(tabuleiro);
        } else if (keyH.getRight() == true) {
            jogador.setDirecao('d');
            colisao = jogador.mover(tabuleiro);
        }
        
        
        verificarColisao(colisao, oldX, oldY);
    }
    
    public void verificarColisao( Desenhavel colisao, int OldX, int OldY ) {
        if (colisao instanceof Dinossauro dino) {
            jogo.iniciarCombate(jogador, dino, OldX, OldY);
            return;
        }
        if (colisao instanceof Caixa caixa){
            this.resetInput();
            jogo.abrirCaixa(jogador, caixa);
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        int playerX = jogador.getX();
        int playerY = jogador.getY();
        int percepcao = jogador.getPercepcao();
        
        boolean debug = keyH.getDebugAtivo();
        
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                
                boolean visivel = false;
                
                if (debug) {
                    visivel = true;
                } else {
                    int distX = Math.abs(j - playerX);
                    int distY = Math.abs(i - playerY);

                    switch (percepcao) {
                        case 3:
                            visivel = (distX <= percepcao) && (distY <= percepcao);
                            break;
                        case 2:
                            visivel = (distX + distY <= 2);
                            break;
                        default:
                            visivel = (i == playerY && distX <= percepcao) || (j == playerX && distY <= percepcao);
                            break;
                    }
                }
                
                if (visivel) {
                    BufferedImage image = tabuleiro.getTile(j, i).getImage();
                    g2.drawImage(image, j * tileSize, i * tileSize, tileSize, tileSize, null);
                } else {
                    g2.setColor(Color.BLACK);
                    g2.fillRect(j * tileSize, i * tileSize, tileSize, tileSize);
                }
            }
        }
        
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0, screenHeight, screenWidth, 50);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 14));

        String info = "HP: " + jogador.getHp() + "/5" + " | Percepcao: " + jogador.getPercepcao() + " | Debug - F5" + " | Cura - H" + " | Sair - Esc";

        g2.drawString(info, 10, screenHeight + 18);

        StringBuilder itens = new StringBuilder("Itens: ");

        boolean primeiro = true;

        if (jogador.getKit()) {
            itens.append("Kit Medico");
            primeiro = false;
        }

        if (jogador.temBastao()) {
            if (!primeiro) itens.append(" | ");
            itens.append("Bastão Eletrico");
            primeiro = false;
        }

        if (jogador.temArmaDeDardos()) {
            if (!primeiro) itens.append(" | ");
            itens.append("Arma de Dardos");
            primeiro = false;
        }

        if (primeiro) {
            itens.append("Nenhum");
        }

        g2.drawString(itens.toString(), 10, screenHeight + 38);

        g2.dispose();
    }
}
