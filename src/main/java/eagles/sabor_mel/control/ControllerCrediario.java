/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.CrediarioDAO;
import eagles.sabor_mel.dao.ParcelaDAO;
import eagles.sabor_mel.model.Crediario;
import eagles.sabor_mel.model.DateGenerator;
import eagles.sabor_mel.model.Parcela;
import eagles.sabor_mel.model.StatusParcela;
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

    private static CrediarioDAO daoCrediario = new CrediarioDAO();
    private static ParcelaDAO daoParcela = new ParcelaDAO();

    public static boolean createCrediario(int quantidadeParcela, int dia, int mes, int ano, double valorTotal, Venda venda) {
        daoCrediario = new CrediarioDAO();
        mes -= 1;
        Crediario crediario = new Crediario();
        crediario.setQuantidadeParcela(quantidadeParcela);
        crediario.setVenda(venda);

        double valorParcela = valorTotal / quantidadeParcela;

        for (int i = 0; i < quantidadeParcela; i++) {
            Calendar cal = Calendar.getInstance();
            Parcela parcela = new Parcela();
            cal.set(ano, mes, dia);
            parcela.setDataVencimento(cal);
            parcela.setParcela(i + 1);
            parcela.setStatus(StatusParcela.NPAGO);
            parcela.setValorParcela(valorParcela);
            mes += 1;
            crediario.addParcela(parcela);
        }

        return daoCrediario.merge(crediario);
    }

    public static List<Map> searchCrediario(String documentoCliente) {

        Map<String, String> cliente = ControllerPessoa.searchPessoa(documentoCliente);
        String value = cliente.get("idPessoa");
        List<Crediario> crediarios = daoCrediario.getByCliente(Long.valueOf(cliente.get("idPessoa")));

        List<Map> mapCrediario = new ArrayList<>();
        if (!crediarios.isEmpty()) {
            for (Crediario c : crediarios) {
                Map<String, String> specCrediario = new HashMap<>();
                specCrediario.put("idCrediario", String.valueOf(c.getIdCrediario()));
                specCrediario.put("quantidade", String.valueOf(c.getQuantidadeParcela()));
                specCrediario.put("dataVenda", String.valueOf(c.getVenda().getDataVenda()));
                mapCrediario.add(specCrediario);
            }
        }
        return mapCrediario;
    }

    public static List<Map> searchParcelas(Long idCrediario) {
        List<Parcela> parcelas = daoParcela.getByCrediario(idCrediario);
        List<Map> listMapParcelas = new ArrayList<>();
        if (!parcelas.isEmpty()) {
            for (Parcela p : parcelas) {
                Map<String, String> specParcela = new HashMap<>();
                specParcela.put("idParcela", String.valueOf(p.getIdParcela()));
                specParcela.put("numeroParcela", String.valueOf(p.getParcela()));
                specParcela.put("dataVencimento", DateGenerator.dateFormat(p.getDataVencimento()));
                specParcela.put("status", p.getStatus().toString());
                specParcela.put("valor", String.valueOf(p.getValorParcela()));
                listMapParcelas.add(specParcela);
            }
        }
        return listMapParcelas;
    }
    
    public static boolean pagarParcela(Long idParcela){
        Parcela parcela = daoParcela.getById(idParcela);
        parcela.setStatus(StatusParcela.PAGO);
        return daoParcela.merge(parcela);
    }
}
