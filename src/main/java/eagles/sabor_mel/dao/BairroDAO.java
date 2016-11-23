
package eagles.sabor_mel.dao;

import static eagles.sabor_mel.dao.DAO.entityManager;
import eagles.sabor_mel.model.*;
import java.util.*;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class BairroDAO extends DAO<Bairro>{
    public Bairro getById(final Long id) {
        return entityManager.find(Bairro.class, id);
    }
 
    public boolean removeById(final Long id) {
    	
    	boolean result = true;
    	
        try {
            Bairro bairro = this.getById(id);
            super.remove(bairro);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }
        
        return result;
    }
 
    @SuppressWarnings("unchecked")
    public List<Bairro> findAll() {
        return entityManager
            .createQuery("FROM Bairro").getResultList();
    }
    
    public boolean existBairro(String nome){
        try{
            Query query =  entityManager.createQuery("FROM Bairro WHERE nome = :nome");
            query.setParameter("nome", nome);
            query.getSingleResult();
            return true;
        }
        catch(NoResultException e){
            return false;
        }

    }
    
    public Bairro getBairro(String nome){
        Query query =  entityManager.createQuery("FROM Bairro WHERE nome = :nome");
        query.setParameter("nome", nome);
        
        return (Bairro) query.getSingleResult();
    }
}
