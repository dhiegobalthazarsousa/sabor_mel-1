package eagles.sabor_mel.dao;

import eagles.sabor_mel.model.TipoVenda;
import eagles.sabor_mel.model.Venda;
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
    	return entityManager
    		.createQuery("FROM Venda").getResultList();
    }
        
    public List<Venda> getByInterval(Calendar start, Calendar end) {
       Query query = entityManager.createQuery("FROM Venda v WHERE v.dataVenda BETWEEN :startDate AND :endDate");
       query.setParameter("startDate", start);
       query.setParameter("endDate", end);
       return query.getResultList();
    }
         
    public List<Venda> getByClient(Long idCliente){
        Query query = entityManager.createQuery("FROM Venda v WHERE v.idCliente = :idCliente");
        query.setParameter("idCliente", idCliente);
        return query.getResultList();
    }
    
    public List<Venda> getByTipo(TipoVenda tv){
        Query query = entityManager.createQuery("FROM Venda v WHERE v.tipovenda = :tipo");
        query.setParameter("tipo", tv);
        return query.getResultList();
    }
}
