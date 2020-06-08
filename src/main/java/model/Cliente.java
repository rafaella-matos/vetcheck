package model;
import java.io.Serializable;

/**
 * Representa um cliente
 */
public class Cliente implements Serializable  {

	private static final long serialVersionUID = 2;
	private Integer id;
	private String nome;
	private String endereco;
	private String email;
	private String telefone;

	/**
	 * Obtém o identificador do cliente
	 * @return O identificador do cliente
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Configura o identificador do cliente
	 * @param id O identificador do cliente
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtém o nome do cliente
	 * @return O nome do cliente
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Configura o nome do cliente
	 * @param name O nome do cliente
	 */
	public void setNome(String name) { this.nome = name; }

	/**
	 * Obtém o endereço do cliente
	 * @return O endereço do cliente
	 */
	public String getEndereco() { return this.endereco; }

	/**
	 * Configura o endereço do cliente
	 * @param adress O endereço do cliente
	 */
	public void setEndereco(String adress) { this.endereco = adress; }

	/**
	 * Obtém o email do cliente
	 * @return O email do cliente
	 */
	public String getEmail() { return this.email; }

	/**
	 * Configura o email do cliente
	 * @param email O email do cliente
	 */
	public void setEmail(String email) { this.email = email; }

	/**
	 * Obtém o telefone do cliente
	 * @return O telefone do cliente
	 */
	public String getTelefone() { return this.telefone; }

	/**
	 * Configura o telefone do cliente
	 * @param telefone O telefone do cliente
	 */
	public void setTelefone(String telefone) { this.telefone = telefone; }

	/**
	 * Inicializa uma nova instância de {Cliente}
	 */
	public Cliente() { }

	/**
	 * Inicializa uma nova instância de {Cliente}
	 * @param id O identificador do cliente
	 * @param nome O nome do cliente
	 * @param endereco O endereço do cliente
	 * @param email O email do cliente
	 * @param telefone O telefone do cliente
	 */
	public Cliente(int id, String nome, String endereco, String email, String telefone) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.endereco = endereco;
		this.telefone = telefone;
	}

	/**
	 * Permite comprar instâncias diferentes de um objeto
	 * @param obj O cliente a ser comparado
	 * @return True caso sejam equivalentes, False caso contrário
	 */
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Cliente) obj).getId());
	}
}



