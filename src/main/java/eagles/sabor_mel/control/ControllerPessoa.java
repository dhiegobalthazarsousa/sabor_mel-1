/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.PessoaDAO;
import eagles.sabor_mel.model.Bairro;
import eagles.sabor_mel.model.Cidade;
import eagles.sabor_mel.model.Documento;
import eagles.sabor_mel.model.Endereco;
import eagles.sabor_mel.model.Estado;
import eagles.sabor_mel.model.Pessoa;
import eagles.sabor_mel.model.Sexo;
import eagles.sabor_mel.model.Telefone;
import eagles.sabor_mel.model.TipoDocumento;
import eagles.sabor_mel.model.TipoTelefone;
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
    public static boolean cadastrar(String nome, String email,
            Calendar dataNascimento, Sexo sexo, String[] numerosTel, 
            String[] dddsTel, TipoTelefone[] tiposTel, String estadoUF,
            String cidadeNome, String bairroNome, String logradouro, String numero,
            String cep, String numeroDocumento, TipoDocumento tipoDocumento){
        
        Estado estado = new Estado(ControllerEstado.escreverEstado(estadoUF), estadoUF);
        Cidade cidade = new Cidade(cidadeNome);
        cidade.setEstado(estado);
        Bairro bairro = new Bairro(bairroNome);
        bairro.setCidade(cidade);
        Endereco endereco = new Endereco(logradouro, numero, cep);
        Documento documento = new Documento(numeroDocumento, TipoDocumento.CPF);
        Pessoa pessoa = new Pessoa(nome, email, dataNascimento, sexo);
        pessoa.setDocumento(documento);
        pessoa.setEndereco(endereco);
        for(int i = 0; i < numerosTel.length; i++){
            Telefone telefone = new Telefone(dddsTel[i], numerosTel[i], tiposTel[i]);
            pessoa.addTelefone(telefone);
        }
        return daoPessoa.merge(pessoa);        
    }
    
    
    /*
     * @author Dhiego and ...
     * This method searchs a Pessoa Object by Documento.number
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
     * This Method searchs a Pessoa Object by Pessoa.idPessoa
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
