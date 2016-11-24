package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.FuncionarioDAO;
import eagles.sabor_mel.model.Acesso;
import eagles.sabor_mel.model.Bairro;
import eagles.sabor_mel.model.Cidade;
import eagles.sabor_mel.model.DateGenerator;
import eagles.sabor_mel.model.Documento;
import eagles.sabor_mel.model.Endereco;
import eagles.sabor_mel.model.Funcionario;
import eagles.sabor_mel.model.Sexo;
import eagles.sabor_mel.model.Telefone;
import eagles.sabor_mel.model.TipoDocumento;
import eagles.sabor_mel.model.TipoTelefone;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dhiego.balthazar
 */
public class ControllerFuncionario {
    
    private static final FuncionarioDAO daoFuncionario = new FuncionarioDAO();
    
    /*
    * @author Tiago Lima Villalobos
    * Função para listar todos os funcionários cadastrados.
    *
    */
    public static  List<Map<String, String>> listFuncionarios(){
        List<Funcionario> funcionarios = daoFuncionario.findAll();
        List<Map<String, String>> listaFuncionarios = new ArrayList<>();
        
        for(Funcionario f: funcionarios){
            if(!(String.valueOf(f.getNome()).equals("Administrador"))){
                Map<String, String> specFuncionario = new HashMap<>();
                specFuncionario.put("id", String.valueOf(f.getIdPessoa()));
                specFuncionario.put("nome", String.valueOf(f.getNome()));
                specFuncionario.put("usuario", String.valueOf(f.getUsuario()));
                specFuncionario.put("acesso", String.valueOf(f.getAcesso()));
                specFuncionario.put("documento", String.valueOf(f.getDocumento().getNumero()));
                
                listaFuncionarios.add(specFuncionario);
            } 
        }
        
        return listaFuncionarios;
    }
    
    public static  Map<String, String> procuraFuncionario(Long id){
        Funcionario funcionario = daoFuncionario.getById(id);
        Map<String, String> specFuncionario = new HashMap<>();
        
        specFuncionario.put("idPessoa", String.valueOf(funcionario.getIdPessoa()));
        specFuncionario.put("nome", funcionario.getNome());
        specFuncionario.put("dataNascimento", DateGenerator.dateFormat(funcionario.getDataNascimento()));
        specFuncionario.put("email", funcionario.getEmail());
        specFuncionario.put("sexo", String.valueOf(funcionario.getSexo()));
        specFuncionario.put("documento", String.valueOf(funcionario.getDocumento().getNumero()));
        
        specFuncionario.put("cep", String.valueOf(funcionario.getEndereco().getCep()));
        specFuncionario.put("logradouro", String.valueOf(funcionario.getEndereco().getLogradouro()));
        specFuncionario.put("numero", String.valueOf(funcionario.getEndereco().getNumero()));
        specFuncionario.put("bairro", String.valueOf(funcionario.getEndereco().getBairro().getNome()));
        specFuncionario.put("cidade", String.valueOf(funcionario.getEndereco().getBairro().getCidade().getNome()));
        specFuncionario.put("estado", String.valueOf(funcionario.getEndereco().getBairro().getCidade().getEstado().getUf()));
        
        specFuncionario.put("usuario", String.valueOf(funcionario.getUsuario()));
        specFuncionario.put("acesso", String.valueOf(funcionario.getAcesso()));
        
        return specFuncionario;
    }
    
    /*
    * @author dhiego
    * This function calls VendaDAO and CrediarioDAO to persist a sell.
    *
    */
    public static Map<String, String> searchFuncionario(String nome){
        
        Funcionario funcionario = daoFuncionario.getByName(nome);
        Map <String, String> specFuncionario = new HashMap<>();
        specFuncionario.put("idFuncionario", String.valueOf(funcionario.getIdPessoa()));
        specFuncionario.put("nome", funcionario.getNome());
        specFuncionario.put("email", funcionario.getEmail());
        specFuncionario.put("idDocumento", funcionario.getDocumento().getNumero());
        specFuncionario.put("sexo", String.valueOf(funcionario.getSexo()));
        specFuncionario.put("username", funcionario.getUsuario());
        specFuncionario.put("senha", funcionario.getSenha());
        return specFuncionario;
    }
    
    public static Map<String, String> searchFuncionario(String nome, String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        
        Funcionario funcionario = daoFuncionario.getByNameSenha(nome, senha);
        Map <String, String> specFuncionario = new HashMap<>();
        
        specFuncionario.put("nome", funcionario.getNome());
        specFuncionario.put("login", funcionario.getUsuario());
        specFuncionario.put("senha", funcionario.getSenha());
        specFuncionario.put("acesso", String.valueOf(funcionario.getAcesso()));
        
        return specFuncionario;
    }
    
    public static List<Map<String,String>> searchUsuario(String nome){
        List<Map<String, String>> funcionarios = new ArrayList<>();
        List<Funcionario> listFuncionarios = daoFuncionario.getByNome(nome);
        
        for(Funcionario f: listFuncionarios){
            if(!f.getNome().equals("Administrador")){
                Map<String, String> specFuncionario = new HashMap();

                specFuncionario.put("id", String.valueOf(f.getIdPessoa()));
                specFuncionario.put("nome", String.valueOf(f.getNome()));

                funcionarios.add(specFuncionario);
            }
            
        }
        
        return funcionarios;
        
    }

    public static boolean cadastrar(String nome, String email,
        Calendar dataNascimento, Sexo sexo, String[] numerosTel, 
        String[] dddsTel, TipoTelefone[] tiposTel, String estadoUF,
        String cidadeNome, String bairroNome, String logradouro, String numero,
        String cep, String numeroDocumento, TipoDocumento tipoDocumento,
        String usuario, String senha, Acesso acesso
    ) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
        Cidade cidade = new Cidade(cidadeNome);
        Bairro bairro = new Bairro(bairroNome);
        Endereco endereco = new Endereco(logradouro, numero, cep);
        Documento documento = new Documento(numeroDocumento, TipoDocumento.CPF);
        
        /*Hash Senha*/
        HashSha hashSenha = new HashSha(senha);
        senha = hashSenha.hashSenha();
        
        Funcionario funcionario = new Funcionario(
                usuario, senha, acesso, nome, email, dataNascimento, sexo);

        ControllerEstado.getEstadobyUf(estadoUF).addCidade(cidade);
        cidade.addBairro(bairro);
        bairro.addEndereco(endereco);
        funcionario.setEndereco(endereco);
        funcionario.setDocumento(documento);

        for(int i = 0; i < numerosTel.length; i++){
            Telefone telefone = new Telefone(dddsTel[i], numerosTel[i], tiposTel[i]);
            funcionario.addTelefone(telefone);
        }

        return daoFuncionario.merge(funcionario);    
    }
    
    public static boolean editarFuncionario(Long id, String nome, String email,
        Calendar dataNascimento, Sexo sexo, String[] numerosTel, 
        String[] dddsTel, TipoTelefone[] tiposTel, String estadoUF,
        String cidadeNome, String bairroNome, String logradouro, String numero,
        String cep, String numeroDocumento,String usuario, Acesso acesso){
        
        Funcionario funcionario = daoFuncionario.getById(id);
        
        funcionario.setNome(nome);
        funcionario.setEmail(email);
        funcionario.setDataNascimento(dataNascimento);
        funcionario.setSexo(sexo);
        funcionario.getEndereco().getBairro().setNome(bairroNome);
        funcionario.getEndereco().getBairro().getCidade().setEstado(ControllerEstado.getEstadobyUf(estadoUF));
        funcionario.getEndereco().getBairro().getCidade().setNome(cidadeNome);
        funcionario.getEndereco().getBairro().setNome(bairroNome);
        funcionario.getEndereco().setLogradouro(logradouro);
        funcionario.getEndereco().setNumero(numero);
        funcionario.getEndereco().setCep(cep);
        funcionario.getDocumento().setNumero(numeroDocumento);
        funcionario.setUsuario(usuario);
        funcionario.setAcesso(acesso);
        
        if(numerosTel.length == 1){
            funcionario.getTelefones().get(0).setDdd(dddsTel[0]);
            funcionario.getTelefones().get(0).setNumero(numerosTel[0]);
        }
        else{
            funcionario.getTelefones().get(0).setDdd(dddsTel[0]);
            funcionario.getTelefones().get(0).setNumero(numerosTel[0]);
            
            funcionario.getTelefones().get(1).setDdd(dddsTel[1]);
            funcionario.getTelefones().get(1).setNumero(numerosTel[1]);
        }
        
        return daoFuncionario.merge(funcionario);
    }
    
    public static boolean novaSenha(Long id, String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        Funcionario funcionario = daoFuncionario.getById(id);
        
        HashSha hashSenha = new HashSha(senha);
        senha = hashSenha.hashSenha();
        
        funcionario.setSenha(senha);
        
        return daoFuncionario.merge(funcionario);
    }
    
    public static boolean deleteFuncionario(Long id){
        return daoFuncionario.removeById(id);
    }

    public static Calendar transformData(String data) {
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
    
   /*Conta as vendas do funcionario*/
    public static Integer contarVendas(Long id){
        Integer total = 0;
        
        Funcionario funcionario = daoFuncionario.getById(id);
        List<Map<String, String>> vendas = ControllerVendas.listVendas();
        
        for(Map<String, String> venda : vendas){
            if(venda.get("idFuncionario").equals(String.valueOf(id))){
                total++;
            }
        }
        
        return total;
    }

}