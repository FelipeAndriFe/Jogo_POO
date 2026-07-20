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
public class Blank extends Desenhavel {
    @Override
    public BufferedImage pegarImage() {
        BufferedImage i = null;
        try {
            i = ImageIO.read(getClass().getResourceAsStream("/Imagens/Blank.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }
    
    public Blank(int x, int y) {
        super(x, y, '0');
        this.setImage(this.pegarImage());
    }
}
