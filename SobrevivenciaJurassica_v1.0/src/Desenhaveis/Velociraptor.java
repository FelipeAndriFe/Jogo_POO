package Desenhaveis;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
 */
public class Velociraptor extends Dinossauro implements Runnable {
    
    @Override
    public BufferedImage pegarImage() {
        BufferedImage i = null;
        try {
            i = ImageIO.read(getClass().getResourceAsStream("/Imagens/Velociraptor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }
    
    public Velociraptor(int x, int y, char simbolo, int hp, int dano, int velocidade, boolean tomaDanoDeSoco, boolean tomaDanoDeArma) {
        super(x, y, simbolo, hp, dano, velocidade, tomaDanoDeSoco, tomaDanoDeArma);
    }
    
    @Override
    public void run() {
        try {
            while (this.getHp() > 0) {
                if (!jogo.getCombateAtivo()) super.mover(getTabuleiro());
                Thread.sleep(1500);
            }
        } catch (InterruptedException e) {
            System.out.println("A Thread do Velociraptor foi interrompida.");
        }
    }
}
