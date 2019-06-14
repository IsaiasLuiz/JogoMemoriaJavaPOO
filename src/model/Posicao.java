package model;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Posicao {
	private int linha;
	private int coluna;
	private Icon imagem;
	
	public Posicao(int linha, int coluna, Icon imagem) {
		this.linha = linha;
		this.coluna = coluna;
		this.imagem = imagem;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	public Icon getImagem() {
		return imagem;
	}
	
	@Override
	public boolean equals(Object pos) {
		boolean aux = false;
		if(pos != null) {
			if(pos instanceof Posicao) {
				if(((Posicao) pos).getColuna() == this.coluna && ((Posicao)pos).getLinha() == this.linha) {
					aux = true;
				}
			}
		}		
		return aux;
	}
}
