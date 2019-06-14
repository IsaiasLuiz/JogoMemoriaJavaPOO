package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.ContratoNiveis;

public class TelaInicial extends JFrame implements ActionListener{
	private JPanel painel;
	private JRadioButton jogadorXJogador;
	private JRadioButton jogadorXMaquina;
	private JRadioButton nivelFacil;
	private JRadioButton nivelMedio;
	private JRadioButton nivelDificil;
	private JLabel jogadores;
	private JLabel nivel;
	private JLabel imagemTopo;
	private JButton avancar;
	private JButton ranking;
	private ButtonGroup grupoJogadores;
	private ButtonGroup grupoNiveis;
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	public TelaInicial() {
		super("Definicoes de Jogo");
		iniciarComponentes();
		inserirComponentes();
		configurarTela();
	}
	
	private void iniciarComponentes() {
		painel = new JPanel();
		grupoJogadores = new ButtonGroup();
		grupoNiveis = new ButtonGroup();
		jogadorXJogador = new JRadioButton("Jogador x Jogador");
		jogadorXJogador.setSelected(true);
		jogadorXJogador.addActionListener(this);
		jogadorXMaquina = new JRadioButton("Jogador x Maquina");
		jogadorXMaquina.addActionListener(this);
		grupoJogadores.add(jogadorXJogador);
		grupoJogadores.add(jogadorXMaquina);
		nivelFacil = new JRadioButton("Facil");
		nivelFacil.setSelected(true);
		nivelMedio = new JRadioButton("Medio");
		nivelDificil = new JRadioButton("Dificil");
		grupoNiveis.add(nivelFacil);
		grupoNiveis.add(nivelMedio);
		grupoNiveis.add(nivelDificil);
		jogadores = new JLabel("Jogadores:");
		nivel = new JLabel("Nivel:");
		nivelInvisivel();
		avancar = new JButton("Avancar");
		avancar.setBackground(Color.green);
		avancar.addActionListener(this);
		ranking = new JButton("Ranking");
		ranking.addActionListener(this);
		ranking.setBackground(Color.CYAN);
		imagemTopo = new JLabel("topo com imagem de fundo");
		imagemTopo.setPreferredSize(new Dimension(583,280));
		ImageIcon aux = new ImageIcon(getClass().getResource("../imagens/garfield.jpg"));
		aux.setImage(aux.getImage().getScaledInstance(583, 250, 100));
		imagemTopo.setIcon(aux);
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
	}
	
	private void inserirComponentes(){		
		painel.setLayout(layout);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 5;
		constraints.gridheight = 1;
		painel.add(imagemTopo, constraints);
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 1;
		painel.add(jogadores, constraints);
		constraints.gridx = 1;
		painel.add(jogadorXJogador, constraints);
		constraints.gridx = 2;
		painel.add(jogadorXMaquina, constraints);
		constraints.gridx = 0;
		constraints.gridy = 2;
		painel.add(nivel, constraints);
		constraints.gridx = 1;
		painel.add(nivelFacil, constraints);
		constraints.gridx = 2;
		painel.add(nivelMedio, constraints);
		constraints.gridx = 3;
		painel.add(nivelDificil, constraints);
		constraints.gridx = 1;
		constraints.gridy = 3;
		painel.add(avancar, constraints);
		constraints.gridx = 3;
		constraints.gridy = 3;
		painel.add(ranking, constraints);
		add(painel);
	}
	
	private void configurarTela() {
		setResizable(false);
		setSize(600, 400);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("../imagens/interrogacao.jpg")).getImage().getScaledInstance(200, 200, 100));
	}
	
	private void nivelInvisivel() {
		nivelFacil.setEnabled(false);
		nivelMedio.setEnabled(false);
		nivelDificil.setEnabled(false);
		nivel.setEnabled(false);
	}
	
	private void nivelVisivel() {
		nivelFacil.setEnabled(true);
		nivelMedio.setEnabled(true);
		nivelDificil.setEnabled(true);
		nivel.setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent clicado) {
		if(clicado.getSource() == jogadorXJogador) {
			nivelInvisivel();
		}else if(clicado.getSource() == jogadorXMaquina) {
			nivelVisivel();
		}else if(clicado.getSource() == avancar) {
			new TelaJogo(definirModoJogo());
			dispose();
		}else if(clicado.getSource() == ranking) {
			new TelaRanking();
		}
	}
	
	private int[] definirModoJogo() {
		if(jogadorXJogador.isSelected()) {
			return new int[] {ContratoNiveis.JOGADOR_X_JOGADOR}; 
		}else {
			if(nivelFacil.isSelected()) {
				return new int[] {ContratoNiveis.JOGADOR_X_MAQUINA, ContratoNiveis.NIVEL_FACIL}; 
			}else if(nivelMedio.isSelected()) {
				return new int[] {ContratoNiveis.JOGADOR_X_MAQUINA, ContratoNiveis.NIVEL_MEDIO}; 
			}else {
				return new int[] {ContratoNiveis.JOGADOR_X_MAQUINA, ContratoNiveis.NIVEL_DIFICIL}; 
			}
		}
	}
}
