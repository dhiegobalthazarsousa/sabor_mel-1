package eagles.sabor_mel.dao;

import eagles.sabor_mel.model.Parcela;
import java.util.List;
import javax.persistence.Query;
/**
 *
 * @author Tiago Lima Villalobos
 */
public class ParcelaDAO extends DAO<Parcela>{
    public Parcela getById(final Long id) {
        return entityManager.find(Parcela.class, id);
    }
    
    public List<Parcela> getByCrediario(Long idCrediario){
        Query query = entityManager.createQuery("FROM Parcela WHERE idCrediario = :idCrediario");
        query.setParameter("idCrediario", idCrediario);
        
        return query.getResultList();
        
    }
    
    public boolean removeById(final Long id) {
    	
    	boolean result = true;
    	
        try {
            Parcela parcela = this.getById(id);
            super.remove(parcela);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }
        
        return result;
    }
 
    @SuppressWarnings("unchecked")
	public List<Parcela> findAll() {
    	return entityManager
    		.createQuery("FROM Parcela").getResultList();
    }
}
