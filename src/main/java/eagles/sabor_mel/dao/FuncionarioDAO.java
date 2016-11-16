
package eagles.sabor_mel.dao;

import eagles.sabor_mel.model.*;
import java.util.*;
import javax.persistence.Query;

public class FuncionarioDAO extends DAO<Funcionario>{
    public Funcionario getById(final Long id) {
        
        Query query = entityManager.createQuery("FROM Funcionario f WHERE f.idPessoa = :idPessoa");
        query.setParameter("idPessoa", id);
        
        return (Funcionario) query.getSingleResult();
    }
 
    public boolean removeById(final Long id) {
    	
    	boolean result = true;
    	
        try {
            Funcionario funcionario = this.getById(id);
            super.remove(funcionario);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }
        
        return result;
    }
 
    @SuppressWarnings("unchecked")
	public List<Funcionario> findAll() {
    	return entityManager
    		.createQuery("FROM Funcionario").getResultList();
    }
        
        public Funcionario getByName(String name){
        Query query = entityManager.createQuery("FROM Funcionario f WHERE f.nome = :name");
        query.setParameter("name", name);
        return (Funcionario) query.getSingleResult();
    }
}
