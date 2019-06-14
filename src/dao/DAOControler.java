	package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import model.Jogador;

public class DAOControler {
	private static DAOControler instancia = null;
	
	private DAOControler() {}
	
	public static DAOControler getIntancia() {
		if(instancia == null) {
			instancia = new DAOControler();
		}
		return instancia;
	}
	
	public void salvaDados(String nome, int vitorias) throws Exception {
		OutputStream arquivo; 
		OutputStreamWriter leitor;
		BufferedWriter escritor;
		
		List<Jogador> jogadores;
		jogadores = retornaDados();
		
		arquivo = new FileOutputStream("src/dao/registros.txt", false);
		leitor = new OutputStreamWriter(arquivo);
		escritor = new BufferedWriter(leitor);
		
		jogadores.add(new Jogador(nome, vitorias));
		Collections.sort(jogadores);
		for(Jogador meu : jogadores) {
			escritor.newLine();
			escritor.write(meu.getNome() + ":" + meu.getVitorias());
		}		
		escritor.close();
		leitor.close();
		escritor.close();
	}
	
	public List retornaDados() throws Exception{
		List<Jogador> dadosJogadores = new ArrayList<>();
		String linha = "";
		String[] dados = new String[2];
		InputStream arquivo; 
		InputStreamReader leitor;
		BufferedReader leitorLinhas;
		arquivo = new FileInputStream("src/dao/registros.txt");
		leitor = new InputStreamReader(arquivo);
		leitorLinhas = new BufferedReader(leitor);
		
		linha = leitorLinhas.readLine() ;
		do{
			linha = leitorLinhas.readLine();
			if(linha != null) {
				dados = linha.split(":");
				dadosJogadores.add(new Jogador(dados[0], Integer.parseInt(dados[1])));
			}
		}while(linha != null);
		
		leitorLinhas.close();
		leitor.close();
		arquivo.close();
		return dadosJogadores;
	}
}
