package eagles.sabor_mel.dao;

import eagles.sabor_mel.model.Documento;
import java.util.*;
import javax.persistence.Query;
/**
 *
 * @author etivideo
 */
public class DocumentoDAO extends DAO<Documento>{
    public Documento getById(final Long id) {
        return entityManager.find(Documento.class, id);
    }
    
    public Documento getByPessoa(Long id){
        Query query = entityManager.createQuery("FROM Documento d WHERE d.pessoa.idPessoa = :idPessoa");
        
        query.setParameter("idPessoa", id);
        
        return (Documento) query.getSingleResult();
    }
    
    public Documento getByNumero(String numero){
       
        Query query = entityManager.createQuery("FROM Documento d WHERE d.numero = :numero");
        
        query.setParameter("numero", numero);
        
        return (Documento) query.getSingleResult();
    }
    
    public boolean removeById(final Long id) {
    	
    	boolean result = true;
    	
        try {
            Documento documento = this.getById(id);
            super.remove(documento);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }
        
        return result;
    }
 
    @SuppressWarnings("unchecked")
	public List<Documento> findAll() {
    	return entityManager
    		.createQuery("FROM Documento").getResultList();
    }
}
