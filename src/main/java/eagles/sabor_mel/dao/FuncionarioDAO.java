
package eagles.sabor_mel.dao;

import eagles.sabor_mel.control.HashSha;
import eagles.sabor_mel.model.*;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.persistence.Query;
import javax.swing.JOptionPane;

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
    
    public Funcionario getByNameSenha(String usuario, String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        HashSha hash = new HashSha(senha);
        senha = hash.hashSenha();
        
        Query query = entityManager.createQuery("FROM Funcionario f WHERE f.usuario = :usuario AND f.senha = :senha");
        
        query.setParameter("usuario", usuario);
        query.setParameter("senha", senha);
        
        return (Funcionario) query.getSingleResult();
    }
    
    public List<Funcionario> getByNome(String nome) {        
       
        Query query = entityManager.createQuery("FROM Funcionario p WHERE p.nome LIKE :nome");
        query.setParameter("nome", "%"+ nome +"%");
        
        return query.getResultList();
    }
}
