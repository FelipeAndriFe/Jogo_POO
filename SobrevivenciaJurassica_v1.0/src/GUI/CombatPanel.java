package GUI;

import Desenhaveis.Dinossauro;
import Desenhaveis.Jogador;
import Desenhaveis.TipoAtaque;
import Sistemas.Jogo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
 */
public final class CombatPanel extends JPanel {

    private final Jogo jogo;
    private final Jogador jogador;
    private final Dinossauro inimigo;

    private JLabel dados;
    private JLabel danoD;
    private JLabel danoL;
    private JLabel vidaPlayer;
    private JLabel vidaDino;

    private JButton atacar;
    private JButton fugir;
    private JButton curar;

    public CombatPanel(Jogador jogador, Dinossauro inimigo, Jogo jogo) {
        this.jogo = jogo;
        this.jogador = jogador;
        this.inimigo = inimigo;

        this.setLayout(new BorderLayout(10, 10));

        criarPainelVida();
        
        add(criarImagem(jogador.getImage()), BorderLayout.WEST);
        add(criarImagem(inimigo.getImage()), BorderLayout.EAST);

        criarPainelCentro();
        
        criarPainelBotoes();
        
        atacar.addActionListener( e -> abrirMenuAtaque() );

        curar.addActionListener( e -> {
            jogador.curar();
            atualizarVida();
        });

        fugir.addActionListener( e -> {
            showMessageDialog(this, "Covarde");
            jogo.fugir(jogador, inimigo);
        });

    }
    
    public void atualizarVida() {
        vidaPlayer.setText("Vida do Player: " + jogador.getHp());
        vidaDino.setText("Vida do Dinossauro: " + inimigo.getHp());
    }

    public void abrirMenuAtaque() {
        JPopupMenu menu = new JPopupMenu();
        Font fonteMenu = new Font("Arial", Font.PLAIN, 24);

        if (jogador.temBastao() == true){
            JMenuItem bastao = new JMenuItem("Bastão Elétrico");
            bastao.setFont( fonteMenu );
            
            bastao.addActionListener( e -> {  
                    atacarComBastao();
            } );

            menu.add(bastao);
        } else {
            JMenuItem mao = new JMenuItem("Mão");
            mao.setFont(fonteMenu);
            
            mao.addActionListener( e -> {
                    atacarComMao();
            } );
            
            menu.add(mao);
        }


        if (jogador.temArmaDeDardos() == true){

            JMenuItem arma = new JMenuItem("Arma de Dardos");
            arma.setFont( fonteMenu );
            
            arma.addActionListener( e -> {
                    atacarComDardos();
            } );
            
            menu.add(arma);
        }

        menu.show(atacar,0,atacar.getHeight());

    }

    public void atacarComMao() {
        int dano = jogador.atacar(inimigo, TipoAtaque.MAO);
        fimTurno(dano);
    }

    public void atacarComBastao() {
        int dano = jogador.atacar(inimigo, TipoAtaque.BASTAO);
        fimTurno(dano);
    }

    public void atacarComDardos() {
        int dano = jogador.atacar(inimigo, TipoAtaque.DARDO);
        fimTurno(dano);
    }

    public void fimTurno(int dano) {
        inimigo.setHp(inimigo.getHp() - dano);

        dados.setText("Dado rolado: " + jogador.getDado());
        danoD.setText("Dano dado: " + dano);
        
        if (inimigo.getHp() <= 0){
            showMessageDialog(this, "Dinossauro morto!");
            
            jogo.matarDinossauro(jogador, inimigo);
            
            return;
        }
 
        if (jogador.defender() == false){
            danoL.setText("Dano levado: " +  1);
            jogador.setHp(jogador.getHp()-1);
        } else {
            danoL.setText("Dano levado: " + 0 );
        }

        atualizarVida();

        if (jogador.getHp() <= 0) {
            jogo.finalizarJogo(false);
            return;
        }

    }

    public void criarPainelVida() { 
        vidaPlayer = new JLabel();
        vidaDino = new JLabel();
        atualizarVida();

        Font fonteVida = new Font("Comic Sans", Font.ITALIC, 20);
        
        vidaPlayer.setHorizontalAlignment(SwingConstants.LEFT);
        vidaPlayer.setFont( fonteVida );
        vidaDino.setHorizontalAlignment(SwingConstants.RIGHT);
        vidaDino.setFont( fonteVida );
        
        JPanel painelVida = new JPanel(new GridLayout(1, 2, 1, 0));
        painelVida.add( vidaPlayer );
        painelVida.add( vidaDino );
        
        add(painelVida, BorderLayout.NORTH);
    }

    public void criarPainelCentro() { 
        Font fonteCentro = new Font("Arial", Font.BOLD, 18);
        
        JPanel painelCentro = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0); 

        dados = new JLabel("Dado de Dano: --", SwingConstants.CENTER);
        dados.setFont(fonteCentro);
        painelCentro.add(dados, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);

        danoD = new JLabel("Dano dado: --", SwingConstants.CENTER);
        danoD.setFont(fonteCentro);
        painelCentro.add(danoD, gbc);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 0, 10, 0);

        danoL = new JLabel("Dano levado: --", SwingConstants.CENTER);
        danoL.setFont(fonteCentro);
        painelCentro.add(danoL, gbc);

        add(painelCentro, BorderLayout.CENTER);
    }

    public void criarPainelBotoes() {
        atacar = new JButton("Atacar");
        curar = new JButton("Curar");
        fugir = new JButton("Fugir");

        Dimension tamanhoBotao = new Dimension(100, 50);
        atacar.setPreferredSize( tamanhoBotao );
        curar.setPreferredSize( tamanhoBotao );
        fugir.setPreferredSize( tamanhoBotao );

        Font fonteBotoes = new Font("Arial", Font.ITALIC, 20);
        atacar.setFont( fonteBotoes );
        curar.setFont( fonteBotoes );
        fugir.setFont( fonteBotoes );
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        painelBotoes.add( atacar );
        painelBotoes.add( curar );
        painelBotoes.add( fugir );
        
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    public JPanel criarImagem(BufferedImage imagem) {
        JPanel painel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (imagem != null) {
                    g.drawImage(imagem, 0, 0, 150, 150, null);
                }
            }
        };

        painel.setPreferredSize(new Dimension(150, 150));
        return painel;
    }
    
}