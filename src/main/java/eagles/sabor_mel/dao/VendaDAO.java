package eagles.sabor_mel.dao;

import eagles.sabor_mel.model.DateGenerator;
import eagles.sabor_mel.model.Pessoa;
import eagles.sabor_mel.model.Venda;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Tiago Lima Villalobos
 */
public class VendaDAO extends DAO<Venda>{
    public Venda getById(final Long id) {
        return entityManager.find(Venda.class, id);
    }
    
    public Venda getByFuncionario(Long id){
        Query query = entityManager.createQuery("FROM Venda v WHERE v.funcionario.idPessoa = :id");
        query.setParameter("id", id);
        return (Venda) query.getSingleResult();
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
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       Query query = entityManager.createQuery("FROM Venda WHERE dataVenda BETWEEN :startDate AND :endDate");
        
       query.setParameter("startDate", start);
       query.setParameter("endDate", end);
       
       return query.getResultList();
    }
         
    public List<Venda> getByClient(Long idCliente){
        Query query = entityManager.createQuery("FROM Venda WHERE idCliente = :idCliente");
        query.setParameter("idCliente", idCliente);
        return query.getResultList();
    }
    
    public List<Venda> listByFuncionario(Long idFuncionario){
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
