package Sistemas;

import Desenhaveis.Desenhavel;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
 */
public class Tabuleiro {
    private final Desenhavel[][] tabuleiro;
    private final int linhas;
    private final int colunas;
    
    public Tabuleiro(Desenhavel[][] t, int l, int c) {
        this.tabuleiro = t;
        this.linhas = l;
        this.colunas = c;
    }
    
    public synchronized void setTile(Desenhavel d, int x, int y) {
        tabuleiro[y][x] = d;
    }
    
    public synchronized Desenhavel getTile(int x, int y) {
        return tabuleiro[y][x];
    }
    
    public int getLinhas() {
        return linhas;
    }
    
    public int getColunas() {
        return colunas;
    }
}
