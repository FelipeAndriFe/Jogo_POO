/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Desenhaveis;

import Itens.ArmaDeDardos;
import Itens.Bastao;
import Itens.KitMedico;
import Sistemas.Tabuleiro;
import java.util.Scanner;

/**
 *
 * @author felip
 */
public class Jogador extends Personagem implements Combatente {
    private int percepcao;
    private Bastao bastao;
    private ArmaDeDardos arma;
    private KitMedico kit;
    private Scanner teclado;
    
    public Jogador(int x, int y, int hp, int dano, int velocidade, int percepcao, Scanner teclado) {
        super(x, y, 'P', hp, dano, velocidade);
        this.percepcao = percepcao;
        this.bastao = null;
        this.arma = null;
        this.kit = new KitMedico();
        this.teclado = teclado;
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
    
    public int getPercepcao() {
        return percepcao;
    }
    
    public void setPercepcao(int percepcao) {
        this.percepcao = percepcao;
    }
    
    public boolean curar() {
        return kit.usar(this);
    }
    
    @Override
    public int atacar(Personagem alvo) {
        //IMPLEMENTAR
        //DICA: o jogador agora tem o atributo "teclado" q tu pode chamar
    }
    
    @Override
    public boolean defender() {
        //IMPLEMENTAR
    }
    
    @Override
    public Desenhavel mover(Tabuleiro tabuleiro) {
        char direcao;
        int oldX = getX();
        int oldY = getY();
        int newX = oldX;
        int newY = oldY;
        Desenhavel colisao = null;
        
        do {
            direcao = teclado.next().charAt(0);
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
}
