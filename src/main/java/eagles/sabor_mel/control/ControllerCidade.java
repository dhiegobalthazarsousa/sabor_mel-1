package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.CidadeDAO;
import eagles.sabor_mel.model.Cidade;

/**
 *
 * @author tiago
 */
public class ControllerCidade {
    
    private static CidadeDAO daoCidade = new CidadeDAO();
    
    public static boolean foundCidade(String nome){
        return daoCidade.existCidade(nome);
    }
    
    public static Cidade getCidade(String nome){
        return daoCidade.getCidade(nome);
    }
    
}
