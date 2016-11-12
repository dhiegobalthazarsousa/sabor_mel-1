/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.FuncionarioDAO;
import eagles.sabor_mel.dao.PessoaDAO;
import eagles.sabor_mel.model.Bairro;
import eagles.sabor_mel.model.Cidade;
import eagles.sabor_mel.model.Endereco;
import eagles.sabor_mel.model.Funcionario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dhiego.balthazar
 */
public class ControllerFuncionario {
    
    /*
     * @author dhiego
     * This function calls VendaDAO and CrediarioDAO to persist a sell.
     *
     */
    
    public Map<String, String> searchFuncionario(String nome){
        FuncionarioDAO daoFuncionario = new FuncionarioDAO();
        Funcionario funcionario = daoFuncionario.getByName(nome);
        Map <String, String> specFuncionario = new HashMap<>();
        specFuncionario.put("idFuncionario", String.valueOf(funcionario.getIdPessoa()));
        specFuncionario.put("nome", funcionario.getNome());
        specFuncionario.put("email", funcionario.getEmail());
        specFuncionario.put("documento", funcionario.getDocumento().getNumero());
        specFuncionario.put("sexo", String.valueOf(funcionario.getSexo()));
        specFuncionario.put("username", funcionario.getUsuario());
        specFuncionario.put("senha", funcionario.getSenha());
        return specFuncionario;
    }

    public boolean cadastrar(String usuario, String senha, String tipoFuncionario,
            String nome, String email, String dataNascimento,
            String numeroDocumento, String tipoDocumento,
            String logradouro, String numeroEndereco, String cep,
            String nomeBairro,
            String nomeCidade,
            String nomeEstado, String uf) {
        
        //Funcionario funcionario = new Funcionario(usuario, senha, tipoFuncionario);
        //Pessoa pessoa = new Pessoa(nome, email, transformData(dataNascimento));
       // Documento documento = new Documento(numeroDocumento, tipoDocumento);
        Endereco endereco = new Endereco();
        Bairro bairro = new Bairro(nomeBairro);
        Cidade cidade = new Cidade(nomeCidade);
       // Estado estado = new Estado(uf);
        
       // cidade.setEstado(estado);
        bairro.setCidade(cidade);
        endereco.setBairro(bairro);
       // pessoa.setEndereco(endereco);
       // pessoa.setDocumento(documento);
        
        PessoaDAO dao = new PessoaDAO();
        return false; //dao.persist(pessoa);
    }

    private Calendar transformData(String data) {
        Calendar cal = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            cal = Calendar.getInstance();
            cal.setTime(sdf.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }
    
    public List<Funcionario> findByName(String nome){        
        FuncionarioDAO dao = new FuncionarioDAO();
        return null; //dao.getByName(nome);
    }

}