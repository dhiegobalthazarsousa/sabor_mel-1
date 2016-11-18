/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.PessoaDAO;
import eagles.sabor_mel.model.Pessoa;
import eagles.sabor_mel.model.Sexo;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author a1655086
 */
public class ControllerPessoa {
    
    private static PessoaDAO daoPessoa = new PessoaDAO();
    
    
    /*
     * @author Dhiego and ...
     * This mehtod creates and persists a Pessoa Object
    */
//    public static boolean cadastrar(String nome, String email, Calendar dataNascimento, Sexo sexo, String[] numeroTelefone, String[] ddd, String[] tipo){
//        Pessoa pessoa = new Pessoa(nome, email, dataNascimento, sexo);
//    }
    
    
    /*
     * @author Dhiego and ...
     * This method searchs a Pessoa Object by document number.
    */
    public static Map<String,String> searchPessoa(String documento){
        Map<String, String> specPessoa = new HashMap<>();
        Pessoa pessoa = daoPessoa.getByDocument(documento);
        
        specPessoa.put("idPessoa", String.valueOf(pessoa.getIdPessoa()));
        specPessoa.put("nome", pessoa.getNome());
        specPessoa.put("data_nascimento", String.valueOf(pessoa.getDataNascimento()));
        specPessoa.put("email", pessoa.getEmail());
        specPessoa.put("sexo", String.valueOf(pessoa.getSexo()));
        return specPessoa;
        
    }
    
    /*
     * @author Dhiego and ...
     * This Method searchs a Pessoa Object by id number
    */
    public static Map<String,String> searchPessoa(Long id){
        Map<String, String> specPessoa = new HashMap<>();
        Pessoa pessoa = daoPessoa.getById(id);
        
        specPessoa.put("idPessoa", String.valueOf(pessoa.getIdPessoa()));
        specPessoa.put("nome", pessoa.getNome());
        specPessoa.put("data_nascimento", String.valueOf(pessoa.getDataNascimento()));
        specPessoa.put("email", pessoa.getEmail());
        specPessoa.put("sexo", String.valueOf(pessoa.getSexo()));
        return specPessoa;
        
    }
}