/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import eagles.sabor_mel.dao.CrediarioDAO;
import eagles.sabor_mel.dao.PessoaDAO;
import eagles.sabor_mel.dao.VendaDAO;
import eagles.sabor_mel.model.Crediario;
import eagles.sabor_mel.model.Parcela;
import eagles.sabor_mel.model.Pessoa;
import eagles.sabor_mel.model.Venda;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author a1655086
 */
public class ControllerCrediario {
    
    public static boolean createCrediario(int quantidadeParcela, int dia, int mes, int ano, double valorTotal, Venda venda){
        CrediarioDAO daoCrediario = new CrediarioDAO();
        mes -= 1;
        Crediario crediario = new Crediario();
        //crediario.setQuantidadeParcelas(quantidadeParcela);
        crediario.setVenda(venda);

        double valorParcela = valorTotal / quantidadeParcela;

        for (int i = 0; i < quantidadeParcela; i++) {
            Calendar cal = Calendar.getInstance();
            Parcela parcela = new Parcela();
            cal.set(ano, mes, dia);
            parcela.setDataVencimento(cal);
            parcela.setParcela(i + 1);
            parcela.setStatus("Não Pago");
            parcela.setValorParcela(valorParcela);
            mes += 1;
            crediario.addParcela(parcela);
        }

        return daoCrediario.persist(crediario);
    }
    
//    public static List<Map> searchCrediario(String clientDocument){
//        PessoaDAO daoPessoa = new PessoaDAO();
//        Pessoa pessoa = daoPessoa.getByDocument(clientDocument);
//        
//        VendaDAO daoVenda = new VendaDAO();
//        List<Venda> vendas = daoVenda.getByClient(pessoa.getIdPessoa());
//        
//        Map<String, String> specCrediario = new HashMap<>();
//        List<Crediario> crediarios = new ArrayList<>();
//        CrediarioDAO daoCrediario = new CrediarioDAO();
//        for(Venda v : vendas){
//            Map<String, String> parcela = new HashMap<>();
//            List<Map> parcelas = new ArrayList<>();
//            //Crediario crediario = daoCrediario.getByVenda(v.getIdVenda());
//            specCrediario.put("idCrediario", String.valueOf(crediario.getIdCrediario()));
//            specCrediario.put("quantidade_parcelas", String.valueOf(crediario.getQuantidadeParcela()));
//            specCrediario.put("dataVenda", String.valueOf(crediario.getVenda().getDataVenda()));
////            for(int i = 0; i<crediario.getQuantidadeParcela(); i++){
////                fdsa
////            }
//        }
        
//    }
}