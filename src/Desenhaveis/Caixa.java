/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Desenhaveis;

/**
 *
 * @author felip
 */
public class Caixa extends Desenhavel {
    private final int conteudo;
    
    public Caixa(int x, int y, int conteudo) {
        super(x, y, 'X');
        this.conteudo = conteudo;
    }
    
    public int getConteudo() {
        return conteudo;
    }
}
