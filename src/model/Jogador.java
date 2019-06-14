package model;

public class Jogador implements Comparable<Jogador> {
	private int vitorias;
	private String nome;
	
	public Jogador(String nome, int vitorias ){
		this.vitorias = vitorias;
		this.nome = nome;
	}

	public int getVitorias() {
		return vitorias;
	}

	public String getNome() {
		return nome;
	}
	
	@Override
	public int compareTo(Jogador outro) {
		if(this.vitorias < outro.getVitorias()) {
			return 1;
		}else if(this.vitorias > outro.getVitorias()) {
			return -1;
		}
		return 0;
	}
	
}
