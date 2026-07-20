package Desenhaveis;

import Itens.ArmaDeDardos;
import Itens.Bastao;
import Itens.KitMedico;
import Sistemas.Tabuleiro;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
 */
public class Jogador extends Personagem implements Combatente {
    private int percepcao;
    private Bastao bastao;
    private ArmaDeDardos arma;
    private KitMedico kit;
    private char direcao;
    private int dado;
    private static Jogador instance;
    
    @Override
    public BufferedImage pegarImage() {
        BufferedImage i = null;
        try {
            i = ImageIO.read(getClass().getResourceAsStream("/Imagens/Jogador.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }
    
    private Jogador(int x, int y, int hp, int dano, int velocidade, int percepcao) {
        super(x, y, 'P', hp, dano, velocidade);
        this.percepcao = percepcao;
        this.bastao = null;
        this.arma = null;
        this.kit = new KitMedico();
        this.setImage(this.pegarImage());
    }
    
    public static Jogador getInstance(int x, int y, int hp, int dano, int velocidade, int percepcao) {
        if (instance == null) {
            instance = new Jogador(x, y, hp, dano, velocidade, percepcao);
        }
        return instance;
    }
    
    public static void resetInstance() {
        Jogador.instance = null;
    }
    
    public void pegarKit() {
        kit.setQuantidade(kit.getQuantidade() + 1);    
    }
    
    public void pegarArmaDeDardos() {
        if (arma == null) {
            arma = new ArmaDeDardos();
        } else {
            arma.setMunicao(arma.getMunicao() + 1);
        }
    }
    
    public void pegarBastao() {
        if (bastao == null) {
            bastao = new Bastao();
        }
    }
    
    public boolean temArmaDeDardos() {
        return arma != null;
    }

    public boolean temBastao() {
        return bastao != null;
    }
    
    public int getPercepcao() {
        return percepcao;
    }
    
    public void setPercepcao(int percepcao) {
        this.percepcao = percepcao;
    }
    
    public boolean getKit() {
        return kit.getQuantidade() > 0;
    }
    
    public boolean curar() {
        return kit.usar(this);
    }
    
    public int getDado() {
        return dado;
    }
    
    @Override
    public int atacar(Personagem alvo, TipoAtaque tipo) {
        Random rand = new Random();
        dado = rand.nextInt(6) + 1;

        switch (tipo) {
            
            case MAO:
                if ( ( (Dinossauro) alvo ).getTomaDanoDeSoco() == true ) {
                    System.out.println("Dado do player: " + dado);
                    if (dado == 6)
                        return 2;
                    else if (dado > 2)
                        return 1;
                } else {
                    showMessageDialog(null, "Ele é muito forte para matar no soco.");
                    System.out.println("Ele é muito forte para matar no soco.");
                }
                break;

            case BASTAO:
                System.out.println("Dado do player: " + dado);
                if (dado > 4)
                    return 2;
                else if (dado > 1)
                    return 1;

                break;

            case DARDO:
                if (((Dinossauro) alvo).getTomaDanoDeArma() == true) {

                    if ( arma.usar() == false ) {
                        showMessageDialog(null, "Sem munição!");
                        return 0;
                    }

                    return 2;

                } else {
                    showMessageDialog(null, "Ele é muito rápido para acertar o tiro.");
                }

                break;
        }

        return 0;
    }
    
    @Override
    public boolean defender() {
        Random rand = new Random();
        int dado = rand.nextInt(4) + 1;
        System.out.println( "Dado de defesa do player: " + dado );
        if ( dado > getPercepcao() ) {
            System.out.println( "Player tomou 1 de dano.\n" );
            return false;
        } else {
            System.out.println( "Player nao tomou dano.\n" );
            return true;
        }
    }
    
    public void setDirecao(char direcao) {
        this.direcao = direcao;
    }
    
    @Override
    public Desenhavel mover(Tabuleiro tabuleiro) {
        int oldX = getX();
        int oldY = getY();
        int newX = oldX;
        int newY = oldY;
        Desenhavel colisao = null;
        
        do {
            switch(direcao) {
                case 'd':
                    newX = oldX + 1;
                    break;
                case 'w':
                    newY = oldY - 1;
                    break;
                case 'a':
                    newX = oldX - 1;
                    break;
                case 's':
                    newY = oldY + 1;
                    break;
                default:
                    break;
            }
        } while (direcao != 'a' && direcao != 'd' && direcao != 'w' && direcao != 's');
        
        if (newX >= 0 && newX < tabuleiro.getColunas() &&
            newY >= 0 && newY < tabuleiro.getLinhas() &&
            !(tabuleiro.getTile(newX, newY) instanceof Parede)) {
            
            colisao = tabuleiro.getTile(newX, newY);
                
            tabuleiro.setTile(this, newX, newY);
            tabuleiro.setTile(new Blank(oldX, oldY), oldX, oldY);
            setX(newX);
            setY(newY);
        }
        
        return colisao;
    }

    public void resetarInventario() {
        this.bastao = null;
        this.arma = null;
        this.kit = new KitMedico();
    }
}
