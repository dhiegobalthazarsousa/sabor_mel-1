package eagles.sabor_mel.dao;

import static eagles.sabor_mel.dao.DAO.entityManager;
import eagles.sabor_mel.model.ItemVenda;
import java.util.List;
import javax.persistence.Query;
/**
 *
 * @author Tiago Lima Villalobos
 */
public class ItemVendaDAO extends DAO<ItemVenda>{
    public ItemVenda getById(final Long id) {
        return entityManager.find(ItemVenda.class, id);
    }
    
    public ItemVenda getByPessoa(Long id){
        return (ItemVenda) entityManager.createQuery("SELECT * FROM ItemVenda WHERE pessoa = '"+id+"';").getSingleResult();
    }
    
    public boolean removeById(final Long id) {
    	
    	boolean result = true;
    	
        try {
            ItemVenda itemVenda = this.getById(id);
            super.remove(itemVenda);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }
        
        return result;
    }
 
    @SuppressWarnings("unchecked")
    public List<ItemVenda> findAll() {
        return entityManager.createQuery("FROM ItemVenda").getResultList();
    }
    
    /*Listar itemVenda group by produto*/
    public List<ItemVenda> getItemGroupByProduto(){
        Query query = entityManager.createQuery("FROM ItemVenda GROUP BY idProduto");
    
        return query.getResultList();
    }
    
    /*Contar produtos vendidos*/
    public Long contaProdutoVendido(Long idProduto){
        Query query = entityManager.createQuery("SELECT SUM(quantidade) FROM ItemVenda WHERE idProduto = :idProduto");
        query.setParameter("idProduto", idProduto);
        
        return (Long) query.getSingleResult();
    }
    
    /*Listar ItemVenda por Produto*/
    public List<ItemVenda> listItemProduto(Long idProduto){
        Query query = entityManager.createQuery("FROM ItemVenda WHERE idProduto = :idProduto");
        query.setParameter("idProduto", idProduto);
        
        return query.getResultList();
    }
}
