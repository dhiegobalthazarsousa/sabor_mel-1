package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.BairroDAO;
import eagles.sabor_mel.model.Bairro;

/**
 *
 * @author tiago
 */
public class ControllerBairro {
    private static BairroDAO daoBairro = new BairroDAO();
    
    public static boolean foundBairro(String nome){
        return daoBairro.existBairro(nome);
    }
    
    public static Bairro getBairro(String nome){
        return daoBairro.getBairro(nome);
    }
}
