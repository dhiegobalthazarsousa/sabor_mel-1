/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.PessoaDAO;
import eagles.sabor_mel.model.Pessoa;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author a1655086
 */
public class ControllerPessoa {
    
    public Map<String,String> searchPessoa(String documento){
        Map<String, String> specPessoa = new HashMap<>();
        PessoaDAO daoPessoa = new PessoaDAO();
        Pessoa pessoa = daoPessoa.getByDocument(documento);
        
        specPessoa.put("idPessoa", String.valueOf(pessoa.getIdPessoa()));
        specPessoa.put("nome", pessoa.getNome());
        specPessoa.put("data_nascimento", String.valueOf(pessoa.getDataNascimento()));
        specPessoa.put("email", pessoa.getEmail());
        specPessoa.put("sexo", String.valueOf(pessoa.getSexo()));
        return specPessoa;
        
    }
}