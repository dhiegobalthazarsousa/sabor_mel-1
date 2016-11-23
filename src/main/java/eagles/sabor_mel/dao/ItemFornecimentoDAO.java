package eagles.sabor_mel.dao;

import eagles.sabor_mel.model.Fornecimento;
import eagles.sabor_mel.model.ItemFornecimento;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Tiago Lima Villalobos
 */
public class ItemFornecimentoDAO extends DAO<ItemFornecimento> {

    public ItemFornecimento getById(final Long id) {
        return entityManager.find(ItemFornecimento.class, id);
    }
    
    public List<ItemFornecimento> getByIdFornecimento(Long idFornecimento){
        Query query = entityManager.createQuery("FROM ItemVenda iv WHERE iv.fornecimento.idFornecimento = :id");
        query.setParameter("id", idFornecimento);
        return query.getResultList();
    }
    

    public List<ItemFornecimento> getByFornecedor(Long id) {
        Query query = entityManager.createQuery("FROM Forncecimento f WHERE f.pessoa.idPessoa = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    public boolean removeById(final Long id) {

        boolean result = true;

        try {
            ItemFornecimento itemFornecimento = this.getById(id);
            super.remove(itemFornecimento);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<ItemFornecimento> findAll() {
        return entityManager
                .createQuery("FROM ItemFornecimento").getResultList();
    }
}
