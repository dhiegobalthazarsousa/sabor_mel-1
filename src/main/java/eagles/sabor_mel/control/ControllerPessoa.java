/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.PessoaDAO;
import eagles.sabor_mel.model.Bairro;
import eagles.sabor_mel.model.Cidade;
import eagles.sabor_mel.model.DateGenerator;
import eagles.sabor_mel.model.Documento;
import eagles.sabor_mel.model.Endereco;
import eagles.sabor_mel.model.Pessoa;
import eagles.sabor_mel.model.Sexo;
import eagles.sabor_mel.model.Telefone;
import eagles.sabor_mel.model.TipoDocumento;
import eagles.sabor_mel.model.TipoTelefone;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author a1655086
 */
public class ControllerPessoa {
    
    private static PessoaDAO daoPessoa = new PessoaDAO();
    
    public static List<Map<String, String>> relatorioListaClientes(){
        List<Map<String, String>> listaPessoas = new ArrayList<>();
        
        List<Pessoa> pessoas = daoPessoa.findAll();
        
        for(Pessoa p: pessoas){
            if(String.valueOf(p.getDocumento().getTipo()).equals("CPF")){
                if(!(String.valueOf(p.getNome()).equals("Cliente")) && !(String.valueOf(p.getNome()).equals("Administrador"))){
                    Map<String, String> specPessoa = new HashMap();
                    
                    specPessoa.put("nome", String.valueOf(p.getNome()));
                    specPessoa.put("documento", String.valueOf(p.getDocumento().getNumero()));
                    specPessoa.put("cidade", String.valueOf(p.getEndereco().getBairro().getCidade().getNome()));
                    specPessoa.put("bairro", String.valueOf(p.getEndereco().getBairro().getNome()));
                    specPessoa.put("email", String.valueOf(p.getEmail()));
                    
                    listaPessoas.add(specPessoa);
                }
            }
        }
        
        return listaPessoas;
    }
    
    public static List<Map<String, String>> listClientes(){
        List<Map<String, String>> listaPessoas = new ArrayList<>();
        
        List<Pessoa> pessoas = daoPessoa.findAll();
        
        for(Pessoa p: pessoas){
            if(String.valueOf(p.getDocumento().getTipo()).equals("CPF")){
                if(!(String.valueOf(p.getNome()).equals("Cliente")) && !(String.valueOf(p.getNome()).equals("Administrador"))){
                    Map<String, String> specPessoa = new HashMap();
                    specPessoa.put("idPessoa", String.valueOf(p.getIdPessoa()));
                    specPessoa.put("nome", String.valueOf(p.getNome()));
                    listaPessoas.add(specPessoa);
                }
            }
        }
        
        return listaPessoas;
    }
    
    public static List<Map<String, String>> listFornecedores(){
        List<Map<String, String>> listaPessoas = new ArrayList<>();
        List<Pessoa> pessoas = daoPessoa.findAll();
        
        for(Pessoa p: pessoas){
            if(String.valueOf(p.getDocumento().getTipo()).equals("CNPJ")){
                Map<String, String> specPessoa = new HashMap();
                specPessoa.put("id", String.valueOf(p.getIdPessoa()));
                specPessoa.put("nome", String.valueOf(p.getNome()));
                
                listaPessoas.add(specPessoa);
            }
        }
        
        return listaPessoas;
    }
    
    /*
     * @author Dhiego and ...
     * This mehtod creates and persists a Pessoa Object
    */
    public static boolean cadastrar(String nome, String email,
        Calendar dataNascimento, Sexo sexo, String[] numerosTel, 
        String[] dddsTel, TipoTelefone[] tiposTel, String estadoUF,
        String cidadeNome, String bairroNome, String logradouro, String numero,
        String cep, String numeroDocumento, TipoDocumento tipoDocumento){
      
        Cidade cidade = null;
        Bairro bairro = null;
        
        if(ControllerCidade.foundCidade(cidadeNome)){
            cidade = ControllerCidade.getCidade(cidadeNome);
        }
        else{
            cidade = new Cidade(cidadeNome);
        }
        
        if(ControllerBairro.foundBairro(bairroNome)){
            bairro = ControllerBairro.getBairro(bairroNome);
        }
        else{
            bairro = new Bairro(bairroNome);
        }
        
        Endereco endereco = new Endereco(logradouro, numero, cep);
        Documento documento = new Documento(numeroDocumento, tipoDocumento);
        Pessoa pessoa = new Pessoa(nome, email, dataNascimento, sexo);
        
        ControllerEstado.getEstadobyUf(estadoUF).addCidade(cidade);
        cidade.addBairro(bairro);
        bairro.addEndereco(endereco);
        pessoa.setEndereco(endereco);
        pessoa.setDocumento(documento);
        
        for(int i = 0; i < numerosTel.length; i++){
            Telefone telefone = new Telefone(dddsTel[i], numerosTel[i], tiposTel[i]);
            pessoa.addTelefone(telefone);
        }
        
        return daoPessoa.merge(pessoa);        
    }
    
    public static boolean editarPessoa(Long id, String nome, String email,
        Calendar dataNascimento, Sexo sexo, String[] numerosTel, 
        String[] dddsTel, TipoTelefone[] tiposTel, String estadoUF,
        String cidadeNome, String bairroNome, String logradouro, String numero,
        String cep, String numeroDocumento){
        
        Pessoa pessoa = daoPessoa.getById(id);
        
        pessoa.setNome(nome);
        pessoa.setEmail(email);
        pessoa.setDataNascimento(dataNascimento);
        pessoa.setSexo(sexo);
        pessoa.getEndereco().getBairro().setNome(bairroNome);
        pessoa.getEndereco().getBairro().getCidade().setEstado(ControllerEstado.getEstadobyUf(estadoUF));
        pessoa.getEndereco().getBairro().getCidade().setNome(cidadeNome);
        pessoa.getEndereco().getBairro().setNome(bairroNome);
        pessoa.getEndereco().setLogradouro(logradouro);
        pessoa.getEndereco().setNumero(numero);
        pessoa.getEndereco().setCep(cep);
        pessoa.getDocumento().setNumero(numeroDocumento);
        
        if(numerosTel.length == 1){
            pessoa.getTelefones().get(0).setDdd(dddsTel[0]);
            pessoa.getTelefones().get(0).setNumero(numerosTel[0]);
        }
        else{
            pessoa.getTelefones().get(0).setDdd(dddsTel[0]);
            pessoa.getTelefones().get(0).setNumero(numerosTel[0]);
            
            pessoa.getTelefones().get(1).setDdd(dddsTel[1]);
            pessoa.getTelefones().get(1).setNumero(numerosTel[1]);
        }
        
        return daoPessoa.merge(pessoa);
    }
    
    public static boolean deletePessoa(Long id){
        return daoPessoa.removeById(id);
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
    
    /*Consulta pessoas por nome*/
    public static List<Map<String,String>> searchPessoaNome(String nome){
        List<Map<String, String>> pessoas = new ArrayList<>();
        List<Pessoa> listPessoas = daoPessoa.getByNome(nome);
        
        for(Pessoa p : listPessoas){
            Map<String, String> specPessoa = new HashMap<>();
            if(!(p.getNome().equals("Cliente")) && !(p.getNome().equals("Administrador"))){
                specPessoa.put("idPessoa", String.valueOf(p.getIdPessoa()));
                specPessoa.put("nome", p.getNome());
                specPessoa.put("dataNascimento", String.valueOf(p.getDataNascimento()));
                specPessoa.put("email", p.getEmail());
                specPessoa.put("sexo", String.valueOf(p.getSexo()));

                pessoas.add(specPessoa);
            }
        }
        
        return pessoas;
        
    }
    
    /*
     * @author Dhiego and ...
     * This Method searchs a Pessoa Object by Pessoa.idPessoa
    */
    public static Map<String,String> searchPessoa(Long id){
        Pessoa pessoa = daoPessoa.getById(id);
        Map<String, String> specPessoa = new HashMap();
        DateGenerator dg = new DateGenerator();
        
        specPessoa.put("idPessoa", String.valueOf(pessoa.getIdPessoa()));
        specPessoa.put("nome", pessoa.getNome());
        specPessoa.put("dataNascimento", dg.dateFormat(pessoa.getDataNascimento()));
        specPessoa.put("email", pessoa.getEmail());
        specPessoa.put("sexo", String.valueOf(pessoa.getSexo()));
        specPessoa.put("documento", String.valueOf(pessoa.getDocumento().getNumero()));
        
        specPessoa.put("cep", String.valueOf(pessoa.getEndereco().getCep()));
        specPessoa.put("logradouro", String.valueOf(pessoa.getEndereco().getLogradouro()));
        specPessoa.put("numero", String.valueOf(pessoa.getEndereco().getNumero()));
        specPessoa.put("bairro", String.valueOf(pessoa.getEndereco().getBairro().getNome()));
        specPessoa.put("cidade", String.valueOf(pessoa.getEndereco().getBairro().getCidade().getNome()));
        specPessoa.put("estado", String.valueOf(pessoa.getEndereco().getBairro().getCidade().getEstado().getUf()));
        
        return specPessoa;
        
    }
    
    public static List<Map<String,String>> searchFornecedor(String nome){
        List<Map<String, String>> pessoas = new ArrayList<>();
        List<Pessoa> listPessoas = daoPessoa.getByNome(nome);
        
        for(Pessoa p: listPessoas){
            if(String.valueOf(p.getDocumento().getTipo()).equals("CNPJ")){
                Map<String, String> specPessoa = new HashMap();
                specPessoa.put("id", String.valueOf(p.getIdPessoa()));
                specPessoa.put("nome", String.valueOf(p.getNome()));
                
                pessoas.add(specPessoa);
            }
        }
        
        return pessoas;
        
    }
    
    public static List<Map<String, String>> procuraTelefones(Long id){
        return ControllerTelefone.procuraTelefonePessoa(id);
    }
}