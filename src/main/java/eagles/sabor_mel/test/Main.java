package eagles.sabor_mel.test;

import eagles.sabor_mel.control.ControllerFuncionario;
import eagles.sabor_mel.control.ControllerPessoa;
import eagles.sabor_mel.model.Estado;
import eagles.sabor_mel.dao.EstadoDAO;
import eagles.sabor_mel.model.Acesso;
import eagles.sabor_mel.model.Sexo;
import eagles.sabor_mel.model.TipoDocumento;
import eagles.sabor_mel.model.TipoTelefone;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        
        /*Cria um DAO de Estado*/
        EstadoDAO dao = new EstadoDAO();
        
        /*Cria uma lista de Estados para persistência*/
        List<Estado> estados = new ArrayList<>();
        
        /*Adiciona Estados à lista*/
        estados.add(new Estado("São Paulo", "SP"));
        estados.add(new Estado("Acre", "AC"));
        estados.add(new Estado("Alagoas	", "AL"));
        estados.add(new Estado("Amapá", "AP"));
        estados.add(new Estado("Amazonas", "AM"));
        estados.add(new Estado("Bahia", "BA"));
        estados.add(new Estado("Ceará", "CE"));
        estados.add(new Estado("Distrito Federal", "DF"));
        estados.add(new Estado("Espírito Santo", "ES"));
        estados.add(new Estado("Goiás", "GO"));
        estados.add(new Estado("Maranhão", "MA"));
        estados.add(new Estado("Mato Grosso", "MT"));
        estados.add(new Estado("Mato Grosso do Sul", "MS"));
        estados.add(new Estado("Minas Gerais", "MG"));
        estados.add(new Estado("Pará", "PA"));
        estados.add(new Estado("Paraíba", "PB"));
        estados.add(new Estado("Paraná", "PR"));
        estados.add(new Estado("Pernambuco", "PE"));
        estados.add(new Estado("Piauí", "PI"));
        estados.add(new Estado("Rio de Janeiro", "RJ"));
        estados.add(new Estado("Rio Grande do Norte", "RN"));
        estados.add(new Estado("Rio Grande do Sul", "RS"));
        estados.add(new Estado("Rondônia", "RO"));
        estados.add(new Estado("Roraima", "RR"));
        estados.add(new Estado("Santa Catarina", "SC"));
        estados.add(new Estado("Sergipe", "SE"));
        estados.add(new Estado("Tocantins", "TO"));
        
        /*Persiste a lista de Estados*/
        for(int i = 0; i < estados.size(); i++){
            dao.persist(estados.get(i));
        }
        
        /*Criação de Usuário Administrador Padrão*/
        Calendar c = Calendar.getInstance();
        String[] numerosTel = new String[1];
        String[] dddsTel = new String[1];
        TipoTelefone[] tiposTel = new TipoTelefone[1];
        
        numerosTel[0] = "38977067";
        dddsTel[0] = "12";
        tiposTel[0] = TipoTelefone.Fixo;
        
        
        ControllerFuncionario.cadastrar(
            "Administrador", 
            "admin@admin.com",
            c, 
            Sexo.Masculino, 
            numerosTel, 
            dddsTel, 
            tiposTel, 
            "SP",
            "Caraguatatuba", 
            "Indaia", 
            "Av. Bahia", 
            "860",
            "11672-505", 
            "000.000.000-00", 
            TipoDocumento.CPF,
            "admin", 
            "admin123", 
            Acesso.Administrador
        );
        
        /*Criação de Cliente Padrão para Vendas sem Cadastro*/
        ControllerPessoa.cadastrar(
            "Cliente", 
            "cliente@cliente",
            c, 
            Sexo.Não_Definido,
            numerosTel, 
            dddsTel, 
            tiposTel, 
            "SP",
            "Caragutatuba", 
            "Indaia", 
            "Av. Bahia", 
            "860",
            "11672-505", 
            "111.111.111-11", 
            TipoDocumento.CPF
        );
        
        /*Encerra*/
        System.exit(0);
    }
}
