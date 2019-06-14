package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.DAOControler;


public class TelaFinal extends JFrame implements ActionListener{

	private JPanel painel;
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JButton jogarNovamente;
	private JButton salvar;
	private JButton ranking;
	private JLabel labelJog1;
	private JLabel labelJog2;
	private JTextField fieldJog1;
	private JTextField fieldJog2;
	private JLabel imagemTopo;
	private int pontuacao;
	private int[] numeroAcertos;
	private boolean empate;
	
	public TelaFinal(int ... acertos) {
		super("Cadastro de Usuario");
		this.numeroAcertos = acertos;
		iniciarComponentes();
		adicionarComponentes();
		verificaEmpate();
		configurarTela();
	}
	
	private void iniciarComponentes() {
		painel = new JPanel();
		layout = new GridBagLayout(); 
		constraints = new GridBagConstraints();
		jogarNovamente = new JButton("Novo Jogo");
		jogarNovamente.setBackground(Color.cyan);
		jogarNovamente.setPreferredSize(new Dimension(120, 20));
		jogarNovamente.addActionListener(this);
		salvar = new JButton("Salvar");
		salvar.setBackground(Color.green);
		salvar.setPreferredSize(new Dimension(120, 20));
		salvar.addActionListener(this);
		ranking = new JButton("Ranking");
		ranking.setBackground(Color.red);
		ranking.setPreferredSize(new Dimension(120, 20));
		ranking.addActionListener(this);
		labelJog1 = new JLabel("Primeiro Jogador:");
		labelJog2 = new JLabel("Segundo Jogador:");
		fieldJog1 = new JTextField();
		fieldJog2 = new JTextField();
		fieldJog1.setPreferredSize(new Dimension(120, 20));
		fieldJog2.setPreferredSize(new Dimension(120, 20));
		imagemTopo = new JLabel();
		imagemTopo.setPreferredSize(new Dimension(583, 270));
		ImageIcon aux = new ImageIcon(getClass().getResource("../imagens/fimJogo.jpg"));
		aux.setImage(aux.getImage().getScaledInstance(583, 250, 90));
		imagemTopo.setIcon(aux);
		empate = false;
	}
	
	private void adicionarComponentes() {
		painel.setLayout(layout);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 4;
		constraints.gridheight = 1;
		painel.add(imagemTopo, constraints);
		constraints.insets = new Insets(10, 0, 20, 0);
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.gridy = 1;
		painel.add(labelJog1, constraints);
		constraints.gridx = 1;
		painel.add(fieldJog1, constraints);
		constraints.gridx = 0;
		constraints.gridy = 2;
		painel.add(labelJog2, constraints);
		constraints.gridx = 1;
		painel.add(fieldJog2, constraints);
		constraints.insets = new Insets(0, 40, 10, 0);
		constraints.gridx = 0;
		constraints.gridy = 3;
		painel.add(jogarNovamente, constraints);
		constraints.gridx = 1;
		painel.add(salvar, constraints);
		constraints.gridx = 2;
		constraints.insets = new Insets(0, 40, 10, 10);
		painel.add(ranking, constraints);
		add(painel);
	}
	
	private void verificaEmpate() {
		if(numeroAcertos.length > 1) {
			empate = true;
		}else {
			labelJog2.setEnabled(false);
			fieldJog2.setEnabled(false);
			labelJog1.setText("Nome do Vencedor:");
		}
	}
	
	private void configurarTela() {
		setResizable(false);
		setSize(600, 450);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("../imagens/interrogacao.jpg")).getImage().getScaledInstance(200, 200, 100));
	}
	
	@Override
	public void actionPerformed(ActionEvent clicado) {
		if(clicado.getSource() == salvar) {
			salvarDados();
		}else if(clicado.getSource() == ranking) {
			new TelaRanking();
		}else if(clicado.getSource() == jogarNovamente) {
			new TelaInicial();	
			dispose();
		}
	}

	private void salvarDados() {
		String nome1 = "";
		String nome2 = "";
		try {
			if(empate) {
				nome1 = fieldJog1.getText();
				nome2 = fieldJog2.getText();
				if(dadosValidos(nome1, nome2)) {
					DAOControler.getIntancia().salvaDados(nome1, numeroAcertos[0]);
					DAOControler.getIntancia().salvaDados(nome2, numeroAcertos[1]);
					janelaMensagem("Dados salvos com sucesso!");
					fieldJog1.setEnabled(false);
					fieldJog2.setEnabled(false);
				}else {
					janelaMensagem("Dados Invalidos!");
				}
			}else {
				nome1 = fieldJog1.getText();
				if(dadosValidos(nome1)) {
					DAOControler.getIntancia().salvaDados(nome1, numeroAcertos[0]);
					janelaMensagem("Dados salvos com sucesso!");
					fieldJog1.setEnabled(false);
				}else {
					janelaMensagem("Dados Invalidos!");
				}
			}
		}catch(Exception e) {
			janelaMensagem("Ocorreu um erro no salvamento!");
			System.exit(0);
		}
	}	
	
	private void janelaMensagem(String mensagem) {
		JOptionPane.showMessageDialog(null,mensagem);
	}
	
	private boolean dadosValidos(String ... valores) {
		boolean retorno = true;
		String aux = "";
		int i = 0;
		while(retorno && i<valores.length){
			aux = valores[i];
			aux = aux.replaceAll("[^a-zA-Zà-úÀ-Ú0-9]", "");
			if(aux.isEmpty()) {
				retorno = false;
			}
			i++;
		}
		return retorno;
	}
}
