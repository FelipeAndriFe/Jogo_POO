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
public class Caixa extends Desenhavel {
    private final int conteudo;
    
    @Override
    public BufferedImage pegarImage() {
        BufferedImage i = null;
        try {
            i = ImageIO.read(getClass().getResourceAsStream("/Imagens/Caixa.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }
    
    public Caixa(int x, int y, int conteudo) {
        super(x, y, 'X');
        this.conteudo = conteudo;
        this.setImage(this.pegarImage());
    }
    
    public int getConteudo() {
        return conteudo;
    }
}
