
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.TelefoneDAO;
import eagles.sabor_mel.model.Telefone;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tiago Lima Villalobos
 */
public class ControllerTelefone {
    
    private static TelefoneDAO daoTelefone = new TelefoneDAO();
    
    public static List<Map<String, String>> procuraTelefonePessoa(Long id){
        List<Telefone> telefonesPessoa = daoTelefone.getByPessoa(id);
        List<Map<String, String>> specTelefones = new ArrayList<>();
        
        
        for(Telefone t: telefonesPessoa){
            Map<String, String> specTelefone = new HashMap();
            specTelefone.put("ddd", String.valueOf(t.getDdd()));
            specTelefone.put("numero", String.valueOf(t.getNumero()));
            specTelefones.add(specTelefone);
        }
        
        return specTelefones;
    }
}
