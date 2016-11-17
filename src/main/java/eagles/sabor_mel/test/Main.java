package eagles.sabor_mel.test;

import eagles.sabor_mel.control.HashSha;
import eagles.sabor_mel.dao.BairroDAO;
import eagles.sabor_mel.dao.CidadeDAO;
import eagles.sabor_mel.model.Estado;
import eagles.sabor_mel.dao.EstadoDAO;
import eagles.sabor_mel.dao.FuncionarioDAO;
import eagles.sabor_mel.model.Acesso;
import eagles.sabor_mel.model.Bairro;
import eagles.sabor_mel.model.Cidade;
import eagles.sabor_mel.model.Documento;
import eagles.sabor_mel.model.Endereco;
import eagles.sabor_mel.model.Funcionario;
import eagles.sabor_mel.model.Sexo;
import eagles.sabor_mel.model.TipoDocumento;
import eagles.sabor_mel.view.Principal;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args){
        
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
        
        /*Encerra*/
        System.exit(0);
    }
}
