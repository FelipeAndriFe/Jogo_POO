package Sistemas;

import javax.swing.JOptionPane;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
 */
public class MenuManager {
    
    public int menuBoasVindas() {
        String[] opcoes = {"Novo Jogo", "Continuar Jogo", "Sair"};
        return JOptionPane.showOptionDialog(
                null, 
                "Bem-vindo ao Sobrevivência Jurássica!", 
                "Menu Principal", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.INFORMATION_MESSAGE, 
                null, 
                opcoes, 
                opcoes[0]
        );
    }
    
    public int menuDificuldade() {
        String[] opcoes = {"Fácil", "Médio", "Difícil"};
        return JOptionPane.showOptionDialog(
                null, 
                "Escolha a dificuldade:", 
                "Dificuldade", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                opcoes, 
                opcoes[0]
        );
    }
    
    public int menuTermino() {
        String[] opcoes = {"Novo Jogo", "Reiniciar Jogo", "Sair"};
        return JOptionPane.showOptionDialog(
                null, 
                "Sua jornada acabou. O que deseja fazer?", 
                "Fim de Jogo", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                opcoes, 
                opcoes[0]
        );
    }
}