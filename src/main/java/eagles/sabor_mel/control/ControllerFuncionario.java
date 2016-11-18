/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.FuncionarioDAO;
import eagles.sabor_mel.model.Acesso;
import eagles.sabor_mel.model.Bairro;
import eagles.sabor_mel.model.Cidade;
import eagles.sabor_mel.model.Documento;
import eagles.sabor_mel.model.Endereco;
import eagles.sabor_mel.model.Estado;
import eagles.sabor_mel.model.Funcionario;
import eagles.sabor_mel.model.Sexo;
import eagles.sabor_mel.model.Telefone;
import eagles.sabor_mel.model.TipoDocumento;
import eagles.sabor_mel.model.TipoTelefone;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dhiego.balthazar
 */
public class ControllerFuncionario {

    private static FuncionarioDAO daoFuncionario = new FuncionarioDAO();

    /*
     * @author dhiego and ...
     * This method search a Funcionario Object by Funcionario.nome
     */
    public static Map<String, String> searchFuncionario(String nome) {
        Funcionario funcionario = daoFuncionario.getByName(nome);
        Map<String, String> specFuncionario = new HashMap<>();
        specFuncionario.put("idFuncionario", String.valueOf(funcionario.getIdPessoa()));
        specFuncionario.put("nome", funcionario.getNome());
        specFuncionario.put("email", funcionario.getEmail());
        specFuncionario.put("idDocumento", funcionario.getDocumento().getNumero());
        specFuncionario.put("sexo", String.valueOf(funcionario.getSexo()));
        specFuncionario.put("username", funcionario.getUsuario());
        specFuncionario.put("senha", funcionario.getSenha());
        return specFuncionario;
    }

    /*
     * @author dhiego and ...
     * This method creates and persists a Funcioanrio Object
     */
    public static boolean cadastrar(String usuario, String senha, Acesso acesso,
            String nome, String email, Calendar dataNascimento, Sexo sexo,
            String[] numerosTel, String[] dddsTel, TipoTelefone[] tiposTel,
            String estadoNome, String estadoUF, String cidadeNome,
            String bairroNome, String logradouro, String numero,
            String cep, String numeroDocumento, TipoDocumento tipoDocumento) {

        Estado estado = new Estado(estadoNome, estadoUF);
        Cidade cidade = new Cidade(cidadeNome);
        cidade.setEstado(estado);
        Bairro bairro = new Bairro(bairroNome);
        bairro.setCidade(cidade);
        Endereco endereco = new Endereco(logradouro, numero, cep);
        Documento documento = new Documento(numeroDocumento, TipoDocumento.CPF);
        Funcionario funcionario = new Funcionario(usuario, senha, acesso, nome, email, dataNascimento, sexo);
        funcionario.setDocumento(documento);
        funcionario.setEndereco(endereco);
        
        for(int i = 0; i < numerosTel.length; i++){
            Telefone telefone = new Telefone(dddsTel[i], numerosTel[i], tiposTel[i]);
            funcionario.addTelefone(telefone);
        }
        return daoFuncionario.merge(funcionario);
    }
}
