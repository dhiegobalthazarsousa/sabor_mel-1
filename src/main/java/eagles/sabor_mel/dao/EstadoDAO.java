
package eagles.sabor_mel.dao;

import eagles.sabor_mel.model.*;
import java.util.*;
import javax.persistence.Query;

public class EstadoDAO extends DAO<Estado>{
    public Estado getById(final Long id) {
        return entityManager.find(Estado.class, id);
    }
    
    public boolean removeById(final Long id) {
    	
    	boolean result = true;
    	
        try {
            Estado estado = this.getById(id);
            super.remove(estado);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }
        
        return result;
    }
 
    @SuppressWarnings({"unchecked", "JPQLValidation"})
	public List<Estado> findAll() {
    	return entityManager
    		.createQuery("FROM Estado").getResultList();
    }
        
        
    @SuppressWarnings("JPQLValidation")
    public Estado findByUf(String uf){
        Query query = entityManager.createQuery("FROM Estado e WHERE e.uf = :uf");
        query.setParameter("uf", uf);
        return (Estado) query.getSingleResult();
        
    }
}
