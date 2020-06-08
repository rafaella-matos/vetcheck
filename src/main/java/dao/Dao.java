package dao;
import java.util.List;

/**
 * Interface Dao
 * 
 * @author Hugo de Paula
 *
 */public interface Dao<T, K> {
	public List<T> listar(String filtro);
	public T localizarPelaChave(K chave);
	public void adicionar(T p);
	public void atualizar(T p);
	public void remover(T p);
	public K obterChave();
}
