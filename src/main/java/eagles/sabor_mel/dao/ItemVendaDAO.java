package eagles.sabor_mel.dao;

import static eagles.sabor_mel.dao.DAO.entityManager;
import eagles.sabor_mel.model.ItemFornecimento;
import eagles.sabor_mel.model.ItemVenda;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Tiago Lima Villalobos
 */
public class ItemVendaDAO extends DAO<ItemVenda> {

    public ItemVenda getById(final Long id) {
        return entityManager.find(ItemVenda.class, id);
    }

    public boolean removeById(final Long id) {

        boolean result = true;

        try {
            ItemVenda itemVenda = this.getById(id);
            super.remove(itemVenda);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<ItemVenda> findAll() {
        return entityManager
                .createQuery("FROM ItemVenda").getResultList();
    }
}
