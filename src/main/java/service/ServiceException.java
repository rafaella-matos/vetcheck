package service;

import java.util.Map;
import java.util.TreeMap;

/**
 * Repreenta um erro lançado em um serviço de controle
 */
public class ServiceException {
	private String message;
	private Map<String, Object> properties;

	/**
	 * Inicializa uma instância de {ServiceException}
	 * @param message A mensagem do erro
	 */
	public ServiceException(String message) {
		this.message = message;
		this.properties = new TreeMap<String, Object>();
	}

	/**
	 * Obtém a mensagem do erro
	 * @return A mensagem do erro
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Obtém as propriedades do erro
	 * @return As propriedades do erro
	 */
	public Map<String, Object> getProperties() {
		return this.properties;
	}

	/**
	 * Adiciona uma propriedade ao erro atual
	 * @param property A propriedade a ser adicioada
	 * @param value O valor da propriedade a ser adicionada
	 * @return O erro, para cascateamento de chamadas
	 */
	public ServiceException add(String property, Object value) {
		this.properties.put(property, value);
		return this;
	}
}
