package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import dao.DAOControler;
import model.Jogador;

public class TelaRanking  extends JFrame implements ActionListener{
	private JPanel painel;
	private JScrollPane rolagem;
	private JButton fechar;
	
	public TelaRanking() {
		super("Ranking");
		iniciarComponentes();
		configurarTela();
		try {
			montarTela();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Ocorreu um erro ao montar o ranking!");
			System.exit(0);
		}
	}
	
	private void iniciarComponentes() {
		painel = new JPanel();
		rolagem = new JScrollPane();
		rolagem.setViewportView(painel);
		fechar = new JButton("Fechar");
		fechar.setBackground(Color.red);
		fechar.addActionListener(this);
		fechar.setPreferredSize(new Dimension(200, 50));
	}
	
	private void configurarTela() {
		setResizable(false);
		setSize(600, 400);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("../imagens/interrogacao.jpg")).getImage().getScaledInstance(200, 200, 100));
	}
	
	private void montarTela() throws Exception {
		List<Jogador> listaDados = DAOControler.getIntancia().retornaDados();
		int i = 0;
		GridBagLayout layout = new GridBagLayout();
		painel.setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = i;
		constraints.insets = new Insets(10, 0, 20, 0);
		painel.add(fechar, constraints);
		constraints.insets = new Insets(0, 0, 10, 0);
		for(Jogador meu : listaDados) {
			i++;
			constraints.gridy = i;
			JLabel label = new JLabel(meu.getNome() + ": " + meu.getVitorias() + " vitorias");
			label.setPreferredSize(new Dimension(600, 20));
			label.setHorizontalAlignment(SwingConstants.LEFT );
			painel.add(label, constraints);
			
		}
		add(rolagem);
	}

	@Override
	public void actionPerformed(ActionEvent clicado) {
		if(clicado.getSource() == fechar) {
			dispose();
		}
	}
}
