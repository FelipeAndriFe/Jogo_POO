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
import java.util.Random;

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
        System.out.println();
        
        Random rand = new Random();
        int dado = rand.nextInt(6) + 1;
        
        int opcao = 1;
        System.out.println(opcao++ + ". Mao");

        int opcaoBastao = -300996;
        int opcaoArma = -260407;

        if (bastao != null) {
            opcaoBastao = opcao;
            System.out.println(opcao++ + ". Bastao eletrico");
        }
        if (arma != null) {
            opcaoArma = opcao;
            System.out.println(opcao++ + ". Arma de dardos");
        }
        
        System.out.println("Que arma deseja usar? ");
        
        int decisao = this.teclado.nextInt();
        
        if ( decisao == 1 ) {
            if ( ( (Dinossauro) alvo ).getTomaDanoDeSoco() == true) {
                System.out.println("Dado do player: " + dado);
                if ( dado == 6 ) {
                    return 2;
                } else if ( dado > 2 ) {
                    return 1;
                }
            } else {
                System.out.println("Ele e muito forte para matar no soco \nOh nao\n");
            }
        } else if ( decisao == opcaoBastao ) {
            System.out.println("Dado do player: " + dado);
            if ( dado > 4 ) {
                return 2;
            } else if ( dado > 1 ) {
                return 1;
            }
        } else if ( decisao == opcaoArma ) {
            if ( ( (Dinossauro)alvo ).getTomaDanoDeArma() == true ) {
                if ( this.arma.usar() == false ) {
                    System.out.println("Sem Municao.");
                } else {
                    return 2;
                }
            } else
                System.out.println("Ele e muito rapido para acertar o tiro \nOh nao\n");
        } else {
            System.out.println("Digitou errado, perdeu a vez\n");
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
