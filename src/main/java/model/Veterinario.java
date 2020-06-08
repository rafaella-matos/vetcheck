package model;
import java.io.Serializable;

/**
 * Representa um veterinário
 */
public class Veterinario implements Serializable  {

	private static final long serialVersionUID = 4;
	private int id;
	private String nome;
	private String senha;
	private String email;
	private String telefone;
	private String crmv;

	/**
	 * Obtém o identificador do veterinário
	 * @return O identificador do veterinário
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Configura o identificador do veterinário
	 * @param id O identificador do veterinário
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtém o telefone do veterinário
	 * @return O telefone do veterinário
	 */
	public String getTelefone() {
		return this.telefone;
	}

	/**
	 * Configura o telefone do veterinário
	 * @param telefone O telefone do veterinário
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * Obtém o CRMV do veterínário
	 * @return O CRMV do veterínário
	 */
	public String getCrmv() { return this.crmv; }

	/**
	 * Configura o CRMV do veterínário
	 * @param crmv O CRMV do veterínário
	 */
	public void setCrmv(String crmv) {
		this.crmv = crmv;
	}

	/**
	 * Obtém o email do veterinário
	 * @return O email do veterinário
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Configura o email do veterinário
	 * @param email O email do veterinário
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Obtém o nome do veterinário
	 * @return O nome do veterinário
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Configura o nome do veterinário
	 * @param nome O nome do veterinário
	 */
	public void setNome(String nome) { this.nome = nome; }

	/**
	 * Obtém a senha do veterinário
	 * @return A senha do veterinário
	 */
	public String getSenha() {
		return this.senha;
	}

	/**
	 * Configura a senha do veterinário
	 * @param senha A senha do veterinário
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * Inicializa uma instância de {Veterinario}
	 */
	public Veterinario() {
		System.out.println("OPA");
	}

	/**
	 * Permite comprar instâncias de objetos
	 * @param obj O veterinário a ser comparado
	 * @return True caso sejam equivalentes, False caso contrário
	 */
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Veterinario) obj).getId());
	}
}



