package model;

/**
 * Representa um login
 */
public class Login {
    private String email;
    private String senha;

    /**
     * Obtém o email do login
     * @return O email do login
     */
    public String getEmail() { return this.email; }

    /**
     * Configura o email do login
     * @param email O email do login
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Obtém a senha do login
     * @return A senha do login
     */
    public String getSenha() { return this.senha; }

    /**
     * Configura a senha do login
     * @param senha A senha do login
     */
    public void setSenha(String senha) { this.senha = senha; }
}
