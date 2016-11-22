package eagles.sabor_mel.dao;

import eagles.sabor_mel.model.Produto;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Tiago Lima Villalobos
 */
public class ProdutoDAO extends DAO<Produto>{
    public Produto getById(final Long id) {
        return entityManager.find(Produto.class, id);
    }
    
    public Produto getByPessoa(Long id){
        return (Produto) entityManager.createQuery("SELECT * FROM Produto WHERE pessoa = '"+id+"';").getSingleResult();
    }
    
    public boolean removeById(final Long id) {
    	
    	boolean result = true;
    	
        try {
            Produto produto = this.getById(id);
            super.remove(produto);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }
        
        return result;
    }
 
    @SuppressWarnings("unchecked")
    public List<Produto> findAll() {
        return entityManager
            .createQuery("FROM Produto").getResultList();
    }
        
    public List<Produto> getByDescricao(String descricao) {        
       
        Query query = entityManager.createQuery("FROM Produto p WHERE p.descricao LIKE :descricao");
        query.setParameter("descricao", "%"+ descricao +"%");
        
        return query.getResultList();
    }    
}
