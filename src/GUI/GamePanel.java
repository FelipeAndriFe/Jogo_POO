/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Desenhaveis.Jogador;
import Sistemas.Tabuleiro;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author felip
 */
public class GamePanel extends JPanel implements Runnable {
    private final int originalTileSize = 16;
    private final int scale = 4;
    private final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 10;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;
    
    private KeyHandler keyH = new KeyHandler();
    
    private Tabuleiro tabuleiro;
    private Jogador jogador;
    
    private Thread gameThread;
    
    public GamePanel(Tabuleiro tabuleiro, Jogador jogador) {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
        this.tabuleiro = tabuleiro;
        this.jogador = jogador;
    }
    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        this.requestFocusInWindow();
    }

    @Override
    public void run() {
        while(gameThread != null) {
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
        if (keyH.getUp() == true) {
            jogador.setDirecao('w');
            jogador.mover(tabuleiro);
        } else if (keyH.getDown() == true) {
            jogador.setDirecao('s');
            jogador.mover(tabuleiro);
        } else if (keyH.getLeft() == true) {
            jogador.setDirecao('a');
            jogador.mover(tabuleiro);
        } else if (keyH.getRight() == true) {
            jogador.setDirecao('d');
            jogador.mover(tabuleiro);
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; 
        
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                BufferedImage image = tabuleiro.getTile(j, i).getImage();
                g2.drawImage(image, j * tileSize, i * tileSize, tileSize, tileSize, null);
            }
        }
        
        g2.dispose();
    }
}
