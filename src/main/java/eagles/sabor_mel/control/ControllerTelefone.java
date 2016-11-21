
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.TelefoneDAO;
import eagles.sabor_mel.model.Telefone;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tiago Lima Villalobos
 */
public class ControllerTelefone {
    
    private static TelefoneDAO daoTelefone = new TelefoneDAO();
    private static final List<Telefone> telefones = daoTelefone.findAll();
    private static Map<String, String> specTelefone;
    private static List<Map<String, String>> specTelefones = new ArrayList<>();
    
    public static List<Map<String, String>> procuraTelefonePessoa(Long id){
        List<Telefone> telefonesPessoa = daoTelefone.getByPessoa(id);
        
        for(Telefone t: telefonesPessoa){
            specTelefone.put("ddd", String.valueOf(t.getDdd()));
            specTelefone.put("numero", String.valueOf(t.getNumero()));
            specTelefones.add(specTelefone);
        }
        
        return specTelefones;
    }
}
