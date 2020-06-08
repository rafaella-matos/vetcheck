package model;
import java.io.Serializable;

/**
 * Representa uma consulta animal
 */
public class ConsultaAnimal implements Serializable  {

	private static final long serialVersionUID = 3;
	private int id;
	private int idAnimal;
	private String dadosConsulta;

	/**
	 * Obtém o identificador da consulta animal
	 * @return O identificador da consulta animal
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Configura o identificador da consulta animal
	 * @param id O identificador da consulta animal
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtém o identificador do animal
	 * @return O identificador do animal
	 */
	public int getIdAnimal() { return this.idAnimal; }

	/**
	 * Configura o identificador do animal
	 * @param idAnimal O identificador do animal
	 */
	public void setIdAnimal(int idAnimal) { this.idAnimal = idAnimal; }

	/**
	 * Obtém os dados da consulta do animal
	 * @return Os dados da consulta do animal
	 */
	public String getDadosConsulta() { return this.dadosConsulta; }

	/**
	 * Configura os dados da consulta do animal
	 * @param dadosConsulta Os dados da consulta do animal
	 */
	public void setDadosConsulta(String dadosConsulta) { this.dadosConsulta = dadosConsulta; }

	/**
	 * Inicializa uma nova instância de {ConsultaAnimal}
	 * @param id O identificador da consulta do animal
	 * @param idAnimal O identificador do animal
	 * @param dadosConsulta Os dados da consulta
	 */
	public ConsultaAnimal(int id, int idAnimal, String dadosConsulta) {
		this.id = id;
		this.idAnimal = idAnimal;
		this.dadosConsulta = dadosConsulta;
	}

	/**
	 * Permite comparar instâncias de dois objetos
	 * @param obj A consutla a ser comparada
	 * @return True caso sejam equivalentes, False caso contrário
	 */
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((ConsultaAnimal) obj).getId());
	}
}



