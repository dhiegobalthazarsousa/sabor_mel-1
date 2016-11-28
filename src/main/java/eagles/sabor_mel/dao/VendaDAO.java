package eagles.sabor_mel.dao;

import eagles.sabor_mel.model.Pessoa;
import eagles.sabor_mel.model.Venda;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import javax.persistence.Query;

/**
 *
 * @author Tiago Lima Villalobos
 */
public class VendaDAO extends DAO<Venda>{
    public Venda getById(final Long id) {
        return entityManager.find(Venda.class, id);
    }
    
    public Venda getByPessoa(Long id){
        return (Venda) entityManager.createQuery("SELECT * FROM Venda WHERE pessoa = '"+id+"';").getSingleResult();
    }
    
    public boolean removeById(final Long id) {
    	
    	boolean result = true;
    	
        try {
            Venda venda = this.getById(id);
            super.remove(venda);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }
        
        return result;
    }
 
    @SuppressWarnings("unchecked")
    public List<Venda> findAll() {
        return entityManager.createQuery("FROM Venda").getResultList();
    }
        
    public List<Venda> getByInterval(Calendar start, Calendar end) {
       Query query = entityManager.createQuery("FROM Venda v WHERE v.dataVenda BETWEEN :startDate AND :endDate");
       query.setParameter("startDate", start);
       query.setParameter("endDate", end);
       return query.getResultList();
    }
         
    public List<Venda> getByClient(Long idCliente){
        Query query = entityManager.createQuery("FROM Venda WHERE idCliente = :idCliente");
        query.setParameter("idCliente", idCliente);
        return query.getResultList();
    }
    
    public List<Venda> getByFuncionario(Long idFuncionario){
        Query query = entityManager.createQuery("FROM Venda WHERE idFuncionario = :idFuncionario");
        query.setParameter("idFuncionario", idFuncionario);
        return query.getResultList();
    }
    
    public List<Pessoa> groupByFuncionario(){
        Query query = entityManager.createQuery("SELECT DISTINCT funcionario FROM Venda");
        
        return query.getResultList();
    }
    
    public List<Pessoa> groupByCliente(){
        Query query = entityManager.createQuery("SELECT DISTINCT cliente FROM Venda");
        
        return query.getResultList();
    }
    
    public Calendar getMaxdataCliente(Long idCliente){
        Query query = entityManager.createQuery("SELECT MAX(dataVenda) FROM Venda WHERE idCliente = :idCliente");
        query.setParameter("idCliente", idCliente);
        
        return (Calendar) query.getSingleResult();
    }
    
    public Calendar getMindataCliente(Long idCliente){
        Query query = entityManager.createQuery("SELECT MIN(dataVenda) FROM Venda WHERE idCliente = :idCliente");
        query.setParameter("idCliente", idCliente);
        
        return (Calendar) query.getSingleResult();
    }
}
