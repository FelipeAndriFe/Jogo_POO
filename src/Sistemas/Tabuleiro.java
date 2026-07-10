/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sistemas;

import Desenhaveis.Desenhavel;

/**
 *
 * @author felip
 */
public class Tabuleiro {
    private Desenhavel[][] tabuleiro;
    private int linhas;
    private int colunas;
    
    public Tabuleiro(Desenhavel[][] t, int l, int c) {
        this.tabuleiro = t;
        this.linhas = l;
        this.colunas = c;
    }
    
    public void setTile(Desenhavel d, int x, int y) {
        tabuleiro[y][x] = d;
    }
    
    public Desenhavel getTile(int x, int y) {
        return tabuleiro[y][x];
    }
    
    public int getLinhas() {
        return linhas;
    }
    
    public int getColunas() {
        return colunas;
    }
}
