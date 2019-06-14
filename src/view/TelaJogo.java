package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.ContratoNiveis;
import model.Imagem;
import model.Posicao;

public class TelaJogo extends JFrame implements ActionListener {
	private int nivel;
	private boolean jogadorXJogador;
	private JPanel painel;
	private JButton sair;
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private ImageIcon[][] imagens;
	private JButton[][] cartas;
	private final int TAM_JOGO = 16;
	private int cartasViradas;
	private final int COLUNAS = 4;
	private final int LINHAS = 4;
	private int[] carta1;
	private int[] carta2;
	private boolean acertoJogador;
	private int vitoriasJog1;
	private int vitoriasJog2;
	private JLabel labelJog1;
	private JLabel labelJog2;
	private JLabel mensagem;
	private boolean jogador1Ativo;
	private Maquina maquina;
	private boolean maquinaAcertou;

	public TelaJogo(int... valores) {
		super("E hora do show");
		iniciarComponentes(valores);
		adicionarComponentes();
		configurarTela();
		new ThreadParada().start();
	}

	private void iniciarComponentes(int... valores) {
		if (valores != null) {
			if (valores[0] == ContratoNiveis.JOGADOR_X_MAQUINA) {
				jogadorXJogador = false;
				nivel = valores[1];
				maquina = new Maquina();
			} else {
				jogadorXJogador = true;
				nivel = -1;
				maquina = null;
			}
		}
		painel = new JPanel();
		sair = new JButton("Sair");
		sair.setBackground(Color.red);
		sair.addActionListener(this);
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		imagens = new ImageIcon[LINHAS][COLUNAS];
		cartas = new JButton[LINHAS][COLUNAS];
		cartasViradas = 0;
		carta1 = new int[] { -1, -1 };
		carta2 = new int[] { -1, -1 };
		acertoJogador = false;
		vitoriasJog1 = 0;
		vitoriasJog2 = 0;
		if(maquina != null) {
			labelJog1 = new JLabel("Acertos Jogador : 0");
			labelJog2 = new JLabel("Acertos Maquina : 0");
		}else {
			labelJog1 = new JLabel("Acertos Jogador 1: 0");
			labelJog2 = new JLabel("Acertos Jogador 2: 0");
		}
		mensagem = new JLabel("Vamos comecar!");
		labelJog1.setPreferredSize(new Dimension(220, 20));
		labelJog2.setPreferredSize(new Dimension(220, 20));
		mensagem.setPreferredSize(new Dimension(220, 20));
		mensagem.setForeground(Color.BLUE);
		jogador1Ativo = true;
		maquinaAcertou = false;

	}

	private void adicionarComponentes() {
		painel.setLayout(layout);
		adicionarBotoes();
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 5;
		painel.add(labelJog1, constraints);
		constraints.gridy = 7;
		painel.add(labelJog2, constraints);
		constraints.gridy = 8;
		painel.add(mensagem, constraints);
		constraints.gridx = 1;
		constraints.gridy = 9;
		painel.add(sair, constraints);
		add(painel);
	}

	private void adicionarBotoes() {
		constraints.insets = new Insets(5, 5, 5, 5);
		Imagem imagem = new Imagem();
		for (int i = 0; i < LINHAS; i++) {
			for (int j = 0; j < COLUNAS; j++) {
				imagens[i][j] = imagem.retornaImagem();
				cartas[i][j] = new JButton();
				cartas[i][j].setPreferredSize(new Dimension(120, 120));
				cartas[i][j].setIcon(imagens[i][j]);
				constraints.gridx = j;
				constraints.gridy = i;
				painel.add(cartas[i][j], constraints);
			}
		}
	}

	private void addClique() {
		for (int i = 0; i < LINHAS; i++) {
			for (int j = 0; j < COLUNAS; j++) {
				if (cartas[i][j].isEnabled()) {
					cartas[i][j].addActionListener(this);
				}
			}
		}
	}

	private void removerClique() {
		for (int i = 0; i < LINHAS; i++) {
			for (int j = 0; j < COLUNAS; j++) {
				if (cartas[i][j].isEnabled()) {
					cartas[i][j].removeActionListener(this);
				}
			}
		}
	}

	private void removerIcone() {
		for (int i = 0; i < LINHAS; i++) {
			for (int j = 0; j < COLUNAS; j++) {
				if (cartas[i][j].isEnabled()) {
					cartas[i][j].setIcon(null);
				}
			}
		}
	}

	private void fimJogo() {
		if (cartasViradas == TAM_JOGO) {
			if(jogadorXJogador) {
				if (vitoriasJog1 > vitoriasJog2) {
					JOptionPane.showMessageDialog(null, "O jogador 1 ganhou!");
					new TelaFinal(vitoriasJog1);
				}	
				else if (vitoriasJog2 > vitoriasJog1) {
					JOptionPane.showMessageDialog(null, "O jogador 2 ganhou!");					
					new TelaFinal(vitoriasJog2);
				}
				else {
					JOptionPane.showMessageDialog(null, "Empate!");
					new TelaFinal(vitoriasJog1, vitoriasJog2);
				}
			}else {
				if (vitoriasJog1 > vitoriasJog2) {
					JOptionPane.showMessageDialog(null, "O jogador 1 ganhou!");
					new TelaFinal(vitoriasJog1);
				}	
				else if (vitoriasJog2 > vitoriasJog1) {
					JOptionPane.showMessageDialog(null, "A maquina ganhou!");					
					new TelaInicial();
				}
				else {
					JOptionPane.showMessageDialog(null, "Empate!");
					new TelaFinal(vitoriasJog1);
				}
			}
			dispose();
		}
	}

	private void adicionarIcone(int pos1, int pos2) {
		cartas[pos1][pos2].setIcon(imagens[pos1][pos2]);
	}

	private void configurarTela() {
		setResizable(false);
		setSize(650, 700);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("../imagens/interrogacao.jpg")).getImage().getScaledInstance(200, 200, 100));
	}

	@Override
	public void actionPerformed(ActionEvent clicado) {
		for (int i = 0; i < LINHAS; i++) {
			for (int j = 0; j < COLUNAS; j++) {
				if (clicado.getSource() == cartas[i][j]) {
					if (cartas[i][j].getIcon() == null) {
						adicionarIcone(i, j);
						if (carta1[0] == -1) {
							carta1[0] = i;
							carta1[1] = j;
						} else {
							carta2[0] = i;
							carta2[1] = j;
							if (jogadorXJogador)
								validarAcerto();
							else
								jogarComMaquina();
						}
					}
				}
			}
		}
		if (clicado.getSource() == sair) {
			dispose();
		}
	}

	private void validarAcerto() {
		if (cartas[carta1[0]][carta1[1]].getIcon() == imagens[carta2[0]][carta2[1]]) {
			acertoJogador = true;
			mensagem.setForeground(Color.blue);
			cartas[carta1[0]][carta1[1]].setEnabled(false);
			cartas[carta2[0]][carta2[1]].setEnabled(false);
			cartasViradas += 2;
			if (jogador1Ativo)
				mensagem.setText("Acertou! Jogador 1 continua!");
			else
				mensagem.setText("Acertou! Jogador 2 continua!");
		} else {
			acertoJogador = false;
			mensagem.setForeground(Color.red);
			if (jogador1Ativo) {
				jogador1Ativo = false;
				mensagem.setText("Errou! Vez jogador 2!");
			} else {
				jogador1Ativo = true;
				mensagem.setText("Errou! Vez jogador 1!");
			}
		}
		if (acertoJogador) {
			if (jogador1Ativo) {
				vitoriasJog1++;
				labelJog1.setText("Acertos Jogador 1: " + vitoriasJog1);
			} else {
				vitoriasJog2++;
				labelJog2.setText("Acertos Jogador 2: " + vitoriasJog2);
			}
		}
		pausarJogo();
	}

	private void jogarComMaquina() {		
		maquina.adicionaPosicao(carta1[0], carta1[1], cartas[carta1[0]][carta1[1]].getIcon());
		maquina.adicionaPosicao(carta2[0], carta2[1], cartas[carta2[0]][carta2[1]].getIcon());
		
		if (cartas[carta1[0]][carta1[1]].getIcon() == imagens[carta2[0]][carta2[1]]) {
			mensagem.setText("Jogador Acertou! Jogador Continua!");
			vitoriasJog1 ++;
			mensagem.setForeground(Color.blue);
			cartas[carta1[0]][carta1[1]].setEnabled(false);
			cartas[carta2[0]][carta2[1]].setEnabled(false);
			cartasViradas += 2;
			labelJog1.setText("Acertos Jogador  : " + vitoriasJog1);
			maquina.adicionaUsados(carta1[0], carta1[1], cartas[carta1[0]][carta1[1]].getIcon());
			maquina.adicionaUsados(carta2[0], carta2[1], cartas[carta2[0]][carta2[1]].getIcon());
			pausarJogo();
		} else {
			mensagem.setText("Jogador errou! Maquina Joga!");
			mensagem.setForeground(Color.red);
			maquinaAcertou = true;
			new ThreadParada().start();
		}
	}
	
	private void pausarJogo() {
		new ThreadParada().start();
		carta1[0] = -1;
		carta1[1] = -1;
		carta2[0] = -1;
		carta2[1] = -1;
	}

	private void maquinaJoga(int linha1, int coluna1, int linha2, int coluna2) {
		cartas[linha1][coluna1].setIcon(imagens[linha1][coluna1]);
		cartas[linha2][coluna2].setIcon(imagens[linha2][coluna2]);
	}

	private class ThreadParada extends Thread {
		int[] jogadaMaq = null;
		public void run() {
			try {
				removerClique();
				sleep(1500);
				fimJogo();
				removerIcone();
				sleep(800);
				if(maquinaAcertou) {
						if (maquina != null) {
							jogadaMaq = maquina.retornaJogada();
							carta1[0] = jogadaMaq[0];
							carta1[1] = jogadaMaq[1];
							carta2[0] = jogadaMaq[2];
							carta2[1] = jogadaMaq[3];
							adicionarIcone(carta1[0], carta1[1]);
							adicionarIcone(carta2[0], carta2[1]);
							maquina.adicionaPosicao(carta1[0], carta1[1], cartas[carta1[0]][carta1[1]].getIcon());
							maquina.adicionaPosicao(carta2[0], carta2[1], cartas[carta2[0]][carta2[1]].getIcon());
							if(cartas[carta1[0]][carta1[1]].getIcon() != imagens[carta2[0]][carta2[1]]) {
								maquinaAcertou = false;
								mensagem.setText("Maquina Errou! Vez o Jogador!");
								mensagem.setForeground(Color.red);
							}else {
								maquinaAcertou = true;
								mensagem.setText("Maquina Acertou! Maquina Continua!");
								vitoriasJog2 ++;
								mensagem.setForeground(Color.blue);
								cartas[carta1[0]][carta1[1]].setEnabled(false);
								cartas[carta2[0]][carta2[1]].setEnabled(false);
								cartasViradas += 2;
								labelJog2.setText("Acertos Maquina  : " + vitoriasJog2);
								maquina.adicionaUsados(carta1[0], carta1[1], cartas[carta1[0]][carta1[1]].getIcon());
								maquina.adicionaUsados(carta2[0], carta2[1], cartas[carta2[0]][carta2[1]].getIcon());
							}
							pausarJogo();
						}else {
							maquinaAcertou = false;
						}
				}
				addClique();
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao iniciar!");
			}
		}
	}

	private class Maquina {
		private List<Posicao> memoria;
		private List<Posicao> usados;
		private int tamanhoMaximo;

		public Maquina() {
			memoria = new LinkedList<>();
			usados = new LinkedList<>();
			if (nivel == ContratoNiveis.NIVEL_MEDIO) {
				tamanhoMaximo = 8;
			} else if (nivel == ContratoNiveis.NIVEL_DIFICIL) {
				tamanhoMaximo = 16;
			} else {
				tamanhoMaximo = 0;
			}
		}
		
		public void adicionaUsados(int linha, int coluna, Icon icone) {
			Posicao pos = new Posicao(linha, coluna, icone);
			usados.add(pos);
			memoria.remove(pos);
		}

		public int[] retornaJogada() {
			int linha1, coluna1, linha2, coluna2;
			Instant inicio = Instant.now();
			Instant fim;
			Duration duracao;
			Posicao posicao[] = verificaAcerto();
			if (posicao == null) {
				linha1 = gerarPosicao();
				linha2 = gerarPosicao();
				coluna1 = gerarPosicao();
				coluna2 = gerarPosicao();
				while (linha1 == linha2 && coluna1 == coluna2 || usados.contains(new Posicao(linha1, coluna1, null)) || usados.contains(new Posicao(linha2, coluna2, null))) {
					linha1 = gerarPosicao();
					linha2 = gerarPosicao();
					coluna1 = gerarPosicao();
					coluna2 = gerarPosicao();	
					fim = Instant.now();
					duracao = Duration.between(inicio, fim);
					if(duracao.getSeconds() >= 20) {
						JOptionPane.showMessageDialog(null, "Desculpe, Não consegui fazer uma Jogada!");
						new TelaInicial();
						dispose();
						break;
					}
				}
				return new int[] { linha1, coluna1, linha2, coluna2 };
			} else {
				return new int[] { posicao[0].getLinha(), posicao[0].getColuna(), posicao[1].getLinha(),
						posicao[1].getColuna() };
			}
		}
		
		private int gerarPosicao() {
			Random gerador = new Random();
			return gerador.nextInt(3);
		}

		public void adicionaPosicao(int linha, int coluna, Icon imagem) {
			Posicao nova = new Posicao(linha, coluna, imagem);
			if (tamanhoMaximo > memoria.size() && tamanhoMaximo != 0) {
				if (!memoria.contains(nova))
					memoria.add(nova);
			}
		}

		private Posicao[] verificaAcerto() {
			Posicao pos = null;
			if( memoria.size() != 0) {
				pos = memoria.get(0);
			}
			for (int i = 1; i < memoria.size(); i++) {
				if (pos.getImagem() == memoria.get(i).getImagem()) {
					return new Posicao[] { pos, memoria.get(i) };
				}
			}
			return null;
		}
	}
}
