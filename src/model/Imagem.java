package model;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

public class Imagem {
	private List<ImageIcon> listaImagem;
	
	public Imagem(){
		listaImagem = new LinkedList<ImageIcon>();
		addImagens();
		duplicarListaImagem();
		Collections.shuffle(listaImagem);
	}
	
	public ImageIcon retornaImagem() {
		return listaImagem.remove(0);
	}
	
	private void addImagens() {
		insereImagem(new ImageIcon(getClass().getResource("/imagens/donald.jpg")));
		insereImagem(new ImageIcon(getClass().getResource("/imagens/frajola.jpg")));
		insereImagem(new ImageIcon(getClass().getResource("/imagens/gaguinho.jpg")));
		insereImagem(new ImageIcon(getClass().getResource("/imagens/jerry.jpg")));
		insereImagem(new ImageIcon(getClass().getResource("/imagens/patolino.jpg")));
		insereImagem(new ImageIcon(getClass().getResource("/imagens/piupiu.jpg")));
		insereImagem(new ImageIcon(getClass().getResource("/imagens/taz.png")));
		insereImagem(new ImageIcon(getClass().getResource("/imagens/tom.jpg")));
	}
	
	private void insereImagem(ImageIcon caminho) {
		ImageIcon icon = caminho;
		icon.setImage(caminho.getImage().getScaledInstance(120, 120, 100));
		listaImagem.add(icon);
	}
	
	private void duplicarListaImagem() {
		int tam = listaImagem.size();
		for(int i = 0; i < tam; i++) {
			listaImagem.add(listaImagem.get(i));
		}
	}
	
}
