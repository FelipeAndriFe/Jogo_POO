package Sistemas;

import Desenhaveis.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
 */
public class SaveManager {

    public static void salvar(Tabuleiro tabuleiro, Jogador jogador) {
        salvarTabuleiro(tabuleiro);
        salvarStatus(jogador);
    }

    private static void salvarTabuleiro(Tabuleiro tabuleiro) {
        try(FileWriter escritor = new FileWriter("saveTabuleiro.txt")) {
            
            for (int i = 0; i < tabuleiro.getLinhas(); i++) {
                for (int j = 0; j < tabuleiro.getColunas(); j++) {
                    escritor.write(tabuleiro.getTile(j, i).getSimbolo());
                    escritor.write(' ');
                }
                escritor.write("\n");
            }
            
        } catch (IOException e) {
            System.out.println("Erro ao manipular o arquivo de save.");
        }
    }

    private static void salvarStatus(Jogador jogador) {
        try(FileWriter escritor = new FileWriter("saveStatus.txt")) {
            
            String dados = jogador.getHp() + " " + 
               jogador.getPercepcao() + " " +
               (jogador.getKit() ? "1" : "0") + " " +
               (jogador.temBastao() ? "1" : "0") + " " +
               (jogador.temArmaDeDardos() ? "1" : "0");
            
            escritor.write(dados);
            
        } catch (IOException e) {
            System.out.println("Erro ao manipular o arquivo de save.");
        }
    }
    
    public static boolean carregarJogo(Desenhavel[][] matriz, List<Personagem> ativos, DesenhavelFactory factory) {
        File tabFile = new File("saveTabuleiro.txt");
        File statusFile = new File("saveStatus.txt");

        if (!tabFile.exists() || !statusFile.exists()) return false;

        Jogador.resetInstance();

        try (Scanner scanTab = new Scanner(tabFile)) {
            
            int conteudo = 0;

            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    char simbolo = scanTab.next().charAt(0);
                    
                    factory.createObject(x, y, simbolo, ativos, matriz, conteudo);

                    if (simbolo == 'X') conteudo++;
                }
            }
        } catch (FileNotFoundException e) {
            
            return false;
        }

        Jogador jogador = (Jogador) ativos.get(0);

        try (Scanner scanStatus = new Scanner(statusFile)) {
            
            int hp = scanStatus.nextInt();
            int percepcao = scanStatus.nextInt();

            boolean kit = scanStatus.nextInt() == 1;
            boolean bastao = scanStatus.nextInt() == 1;
            boolean arma = scanStatus.nextInt() == 1;

            jogador.setHp(hp);
            jogador.setPercepcao(percepcao);

            jogador.resetarInventario();

            if (kit) jogador.pegarKit();
            if (bastao) jogador.pegarBastao();
            if (arma) jogador.pegarArmaDeDardos();

        } catch (FileNotFoundException e) {
            return false;
        }
        
        return true;
    }
}