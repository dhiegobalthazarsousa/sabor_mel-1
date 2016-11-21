package eagles.sabor_mel.dao;

import eagles.sabor_mel.model.Crediario;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Tiago Lima Villalobos
 */
public class CrediarioDAO extends DAO<Crediario>{
    public Crediario getById(final Long id) {
        return entityManager.find(Crediario.class, id);
    }
    
    public List<Crediario> getByCliente(Long id){
        Query query = entityManager.createQuery("FROM Crediario c WHERE c.venda.idCliente = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }
    
    public boolean removeById(final Long id) {
    	
    	boolean result = true;
    	
        try {
            Crediario crediario = this.getById(id);
            super.remove(crediario);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }
        
        return result;
    }
 
    @SuppressWarnings("unchecked")
	public List<Crediario> findAll() {
    	return entityManager
    		.createQuery("FROM Crediario").getResultList();
    }
        
    public Crediario getByVendaId(Long id){
        Query query = entityManager.createQuery("FROM Crediario c WHERE c.venda.idvenda = :id");
        query.setParameter("id", id);
        return (Crediario) query.getSingleResult();
    }
}
