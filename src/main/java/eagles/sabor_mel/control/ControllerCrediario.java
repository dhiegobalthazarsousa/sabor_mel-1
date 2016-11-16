/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.CrediarioDAO;
import eagles.sabor_mel.model.Crediario;
import eagles.sabor_mel.model.Parcela;
import eagles.sabor_mel.model.TipoVenda;
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

    public static boolean createCrediario(int quantidadeParcela, int dia, int mes, int ano, double valorTotal, Venda venda) {
        daoCrediario = new CrediarioDAO();
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

    public static List<Map> searchCrediario(String documentoCliente) {
        List<Map> mapVendasParcelada = ControllerVendas.searchVenda(TipoVenda.Parcelado);
        List<Map> mapVendas = ControllerVendas.searchVenda(documentoCliente);

        List<Map> mapCrediario = new ArrayList<>();
        if (!mapVendas.isEmpty()) {
            for (Map<String, String> specVenda : mapVendas) {
                Crediario c = daoCrediario.getByVendaId(Long.valueOf(specVenda.get("idVenda")));
                Map<String, String> specCrediario = new HashMap<>();
                specCrediario.put("idCrediario", String.valueOf(c.getIdCrediario()));
                specCrediario.put("quantidade", String.valueOf(c.getQuantidadeParcela()));
                specCrediario.put("dataVenda", String.valueOf(c.getVenda().getDataVenda()));
                List<Map> mapParcelas = new ArrayList();
                for (Parcela par : c.getParcelas()) {
                    Map<String, String> specParcela = new HashMap<>();
                    specParcela.put("dataVencimento", String.valueOf(par.getDataVencimento()));
                    specParcela.put("numeroParcela", String.valueOf(par.getParcela()));
                    specParcela.put("valorParcela", String.valueOf(par.getValorParcela()));
                    specParcela.put("status", par.getStatus());
                    mapParcelas.add(specParcela);
                }
                mapCrediario.add(specCrediario);
            }
        }
        return mapCrediario;
    }
}
