package eagles.sabor_mel.dao;

import eagles.sabor_mel.model.Pessoa;
import java.util.*;
import javax.persistence.Query;

public class PessoaDAO extends DAO<Pessoa> {

    public Pessoa getById(final Long id) {
        
        Query query = entityManager.createQuery("FROM Pessoa p WHERE p.idPessoa = :idPessoa");
        query.setParameter("idPessoa", id);
        
        return (Pessoa) query.getSingleResult();
    }

    public boolean removeById(final Long id) {

        boolean result = true;

        try {
            Pessoa pessoa = this.getById(id);
            super.remove(pessoa);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Pessoa> findAll() {
        return entityManager
                .createQuery("FROM Pessoa").getResultList();
    }

    public Pessoa getByDocument(String numero) {        
       
        Query query = entityManager.createQuery("FROM Pessoa p WHERE p.documento.numero = :numero");
        query.setParameter("numero", numero);
        
        return (Pessoa) query.getSingleResult();
    }
    
    public List<Pessoa> getByNome(String nome) {        
       
        Query query = entityManager.createQuery("FROM Pessoa p WHERE p.nome LIKE :nome");
        query.setParameter("nome", "%"+ nome +"%");
        
        return query.getResultList();
    }
}