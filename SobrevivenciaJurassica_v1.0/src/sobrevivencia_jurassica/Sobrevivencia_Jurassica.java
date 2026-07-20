package sobrevivencia_jurassica;

import java.util.ArrayList;
import java.util.List;
import Desenhaveis.Desenhavel;
import Desenhaveis.DesenhavelFactory;
import Desenhaveis.Dinossauro;
import Desenhaveis.Jogador;
import Desenhaveis.Personagem;
import Sistemas.Jogo;
import Sistemas.MenuManager;
import Sistemas.Tabuleiro;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
 */
public class Sobrevivencia_Jurassica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int linhas = 10;
        int colunas = 10;
        Desenhavel[][] tabuleiro = new Desenhavel[linhas][colunas];
        int conteudo = 0;
        List<Personagem> ativos = new ArrayList<>();
        DesenhavelFactory factory = new DesenhavelFactory();
        
        int mapaEscolha = (int) (Math.random() * 3);
        
        String mapa = "template1.txt";

        switch (mapaEscolha) {
            case 0:
                mapa = "template1.txt";
                break;
            case 1:
                mapa = "template2.txt";
                break;
            case 2:
                mapa = "template3.txt";
                break;
        }

        File mapFile = new File(mapa);
        
        Jogador.resetInstance();
        
        try (Scanner readFile = new Scanner(mapFile)) { 
            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < colunas; j++) {
                    String token = readFile.next();
                    char lido = token.charAt(0);

                    factory.createObject(j, i, lido, ativos, tabuleiro, conteudo);
                    if (lido == 'X') conteudo++;
                }
            }
        } catch (FileNotFoundException e) {
            char[][] template = {{'0', '0', '0', '0', 'T', '0', '1', '0', '0', '0'}, 
                                {'0', 'P', '0', '0', '0', '0', '1', '0', 'X', '0'}, 
                                {'0', '0', '0', '0', '0', '0', '1', '0', '0', '0'}, 
                                {'0', '0', 'C', '0', '0', 'X', '1', '0', 'V', '0'}, 
                                {'0', '0', '0', '0', '1', '1', '1', '0', '0', '0'}, 
                                {'0', 'T', '0', '0', '1', 'X', '0', 'T', '0', '0'}, 
                                {'0', '0', '0', '1', '1', '0', '0', '0', '0', 'T'}, 
                                {'0', 'C', '0', '1', '0', 'T', '0', '1', '0', '0'}, 
                                {'0', '0', '0', '0', '0', '0', '0', '1', '0', '0'}, 
                                {'X', '0', '0', '0', '0', 'V', '0', '1', '0', 'R'}};
            
            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < colunas; j++) {
                    char lido = template[i][j];

                    factory.createObject(j, i, lido, ativos, tabuleiro, conteudo);
                    if (lido == 'X') conteudo++;
                }
            }
        }
        
        Tabuleiro tab = new Tabuleiro(tabuleiro, linhas, colunas);

        MenuManager menuManager = new MenuManager();
        
        Jogo jogo = new Jogo(tab, ativos, menuManager, mapa, factory);
        
        for (Personagem p : ativos) {
            if (p instanceof Dinossauro dino) {
                dino.setJogo(jogo);
            }
        }
        
        jogo.start();
    }
}