package Sistemas;

import Desenhaveis.Blank;
import Desenhaveis.Caixa;
import Desenhaveis.Desenhavel;
import Desenhaveis.DesenhavelFactory;
import Desenhaveis.Dinossauro;
import Desenhaveis.Jogador;
import Desenhaveis.Personagem;
import GUI.PanelManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import sobrevivencia_jurassica.Sobrevivencia_Jurassica;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
 */
public class Jogo {
    private Tabuleiro tabuleiro;
    private final List<Personagem> ativos;
    private final MenuManager menuManager;
    private final String mapa;
    private final DesenhavelFactory factory;
    private PanelManager manager;
    private int playerOldX;
    private int playerOldY;
    private int dinoOldX;
    private int dinoOldY;
    private int oldPercepcao;
    private boolean combateAtivo = false;
    
    public Jogo(Tabuleiro t, List<Personagem> a, MenuManager m, String mapa, DesenhavelFactory factory) {
       this.tabuleiro = t;
       this.ativos = a;
       this.menuManager = m;
       this.mapa = mapa;
       this.factory = factory;
   }
    
    public void start() {
        int escolhaMenu = menuManager.menuBoasVindas();

        if (escolhaMenu == 2) System.exit(0);
        
        if (escolhaMenu == 1) {
            Desenhavel[][] matriz = new Desenhavel[10][10];
            List<Personagem> novosAtivos = new ArrayList<>();

            boolean carregou = SaveManager.carregarJogo(matriz, novosAtivos, factory);

            if (!carregou) {
                JOptionPane.showMessageDialog(
                        null,
                        "Nenhum save encontrado!"
                );

                sobrevivencia_jurassica.Sobrevivencia_Jurassica.main(new String[0]);
                return;
            }

            ativos.clear();
            ativos.addAll(novosAtivos);

            this.tabuleiro = new Tabuleiro(matriz, 10, 10);

        } else {
            escolherDificuldade();
        }

        manager = new PanelManager(tabuleiro, (Jogador) ativos.get(0), this);

        for (Personagem obj : ativos) {
            if (obj instanceof Dinossauro dino) {
                dino.setTabuleiro(tabuleiro);
                dino.setJogo(this);

                if (dino instanceof Runnable r) {
                    Thread t = new Thread(r);
                    t.start();
                }
            }
        }
    }
    
    private void escolherDificuldade() {
        int dificuldade = menuManager.menuDificuldade();
        Jogador player = (Jogador) ativos.get(0);

        switch (dificuldade) {
            case 0:
                oldPercepcao = 3; // Fácil
                break;
            case 1:
                oldPercepcao = 2; // Médio
                break;
            case 2:
                oldPercepcao = 1; // Difícil
                break;
            default:
                oldPercepcao = 0;
                break;
        }
        player.setPercepcao(oldPercepcao);
    }

    public void iniciarCombate(Jogador player, Dinossauro dino, int OldX, int OldY) {
        
        pausarThreads();
        
        playerOldX = OldX;
        playerOldY = OldY;

        dinoOldX = dino.getX();
        dinoOldY = dino.getY();

        manager.iniciarCombate(player, dino);
    }
    
    public void matarDinossauro(Jogador player, Dinossauro dino) {

        ativos.remove(dino);
        tabuleiro.setTile( new Blank(dino.getX(), dino.getY()), dino.getX(), dino.getY() );
        
        if (ativos.size() == 1) {
            finalizarJogo(true);
            return;
        }
        
        playerOldX = player.getX();
        playerOldY = player.getY();
        
        player.setX(playerOldX);
        player.setY(playerOldY);
        
        tabuleiro.setTile(player, playerOldX, playerOldY);
        
        continuarThreads();
        manager.voltarMapa();
    }
    
    public void fugir(Jogador player, Dinossauro dino) {
        
        tabuleiro.setTile(new Blank(player.getX(), player.getY() ), player.getX(), player.getY());
        tabuleiro.setTile(new Blank(dino.getX(), dino.getY() ), dino.getX(), dino.getY());

        player.setX(playerOldX);
        player.setY(playerOldY);

        dino.setX(dinoOldX);
        dino.setY(dinoOldY);

        tabuleiro.setTile(player, playerOldX, playerOldY);
        tabuleiro.setTile(dino, dinoOldX, dinoOldY);
        
        continuarThreads();
        manager.voltarMapa();
    }

    public void abrirCaixa(Jogador player, Caixa caixa) {
        int conteudo = caixa.getConteudo();
    
        switch (conteudo) {
            case 0: // Kit médico
                player.pegarKit();
                showMessageDialog(null, "Você encontrou um kit médico.");
                System.out.println("Você encontrou um kit médico.");
                break;
                
            case 1: // Bastão elétrico
                player.pegarBastao();
                showMessageDialog(null, "Você encontrou um bastão elétrico.");
                System.out.println("Você encontrou um bastão elétrico.");
                break;
                
            case 2: // Arma de dardos e compsognato surpresa
                player.pegarArmaDeDardos();
                showMessageDialog(null, "Você encontrou uma arma de dardos.");
                System.out.println("Você encontrou uma arma de dardos.");
                    
                Random rand = new Random();
                int surpresa = rand.nextInt(3) + 1;

                if (surpresa <= player.getPercepcao()) {
                    showMessageDialog(null, "Um compsognato selvagem apareceu, mas você desviou!");
                    System.out.println("Um compsognato selvagem apareceu, mas você desviou!");
                } else {
                    showMessageDialog(null, "Um compsognato selvagem apareceu, você foi atacado! -1 de vida");
                    System.out.println("Um compsognato selvagem apareceu, você foi atacado! -1 de vida");
                    player.setHp(player.getHp() - 1);
                }
                break;
                
            case 3: // Arma de dardos e compsognato surpresa
                player.pegarArmaDeDardos();
                showMessageDialog(null, "Você encontrou uma arma de dardos.");
                System.out.println("Você encontrou uma arma de dardos.");
                
                Random rand2 = new Random();
                surpresa = rand2.nextInt(3) + 1;

                if (surpresa <= player.getPercepcao()) {
                    showMessageDialog(null, "Um compsognato selvagem apareceu, mas você desviou!");
                    System.out.println("Um compsognato selvagem apareceu, mas você desviou!");
                } else {
                    showMessageDialog(null, "Um compsognato selvagem apareceu, você foi atacado! -1 de vida");
                    System.out.println("Um compsognato selvagem apareceu, você foi atacado! -1 de vida");
                    player.setHp(player.getHp() - 1);
                }
                break;
        }
    }
    
    public void finalizarJogo(boolean venceu) {
        if (manager != null) manager.getGamePanel().setcombateAtivo(true);

        if (venceu) {
            JOptionPane.showMessageDialog(
                manager,
                "Parabéns! Você lutou bravamente e venceu o jogo!",
                "Vitória",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                manager,
                "Você morreu. Fim de jogo!",
                "Game Over",
                JOptionPane.ERROR_MESSAGE
            );
        }

        int escolha = menuManager.menuTermino();

        switch (escolha) {
            case 0:
                if (manager != null) manager.dispose();
                Sobrevivencia_Jurassica.main(new String[0]);
                break;
            case 1:
                reiniciarPartida();
                break;
            default:
                System.exit(0);
        }
    }
    
    private void reiniciarPartida() {
        try {
            int linhas = 10;
            int colunas = 10;

            ativos.clear();

            Desenhavel[][] novoTabuleiro = new Desenhavel[linhas][colunas];

            File mapFile = new File(mapa);
            Scanner readFile = new Scanner(mapFile);

            int conteudo = 0;
            Jogador.resetInstance();

            for (int y = 0; y < linhas; y++) {
                for (int x = 0; x < colunas; x++) {
                    
                    char lido = readFile.next().charAt(0);
                    
                    factory.createObject(
                        x, y, lido,
                        ativos,
                        novoTabuleiro,
                        conteudo
                    );

                    if (lido == 'X') conteudo++;
                }
            }

            readFile.close();

            this.tabuleiro = new Tabuleiro(novoTabuleiro, linhas, colunas);

            Jogador player = (Jogador) ativos.get(0);
            if (oldPercepcao == 0) oldPercepcao = 2;
            player.setPercepcao(oldPercepcao);
    
            for (Personagem p : ativos) {
                if (p instanceof Dinossauro dino) {
                    dino.setTabuleiro(tabuleiro);
                    dino.setJogo(this);
                }
            }

            if (manager != null) manager.dispose();

            manager = new PanelManager(tabuleiro, player, this);

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(
                null,
                "Erro ao reiniciar o mapa."
            );
        }
    }
    
    public synchronized void pausarThreads() {
        combateAtivo = true;
    }

    public synchronized void continuarThreads() {
        combateAtivo = false;
    }

    public synchronized boolean getCombateAtivo() {
        return combateAtivo;
    }
}