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
import java.text.DecimalFormat;
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
        DecimalFormat df = new DecimalFormat("0.00");
        daoCrediario = new CrediarioDAO();
        mes -= 1;
        Crediario crediario = new Crediario();
        crediario.setQuantidadeParcela(quantidadeParcela);
        crediario.setVenda(venda);

        double valorParcela = Double.parseDouble(df.format(valorTotal / quantidadeParcela).replace(",", "."));

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
    
    /*Método para Listar Crediarios*/
    public static List<Map<String, String>> listCrediario(){
        List<Map<String, String>> listCrediario = new ArrayList<>();
        
        List<Crediario> crediarios = daoCrediario.findAll();
        
        for(Crediario c : crediarios){
            Map<String, String> specCrediario = new HashMap<>();
            
            Double valorParcela = Double.parseDouble(listParcela(c.getIdCrediario()).get(0).get("valorParcela"));
            
            specCrediario.put("idCrediario", String.valueOf(c.getIdCrediario()));
            specCrediario.put("quantidadeParcela", String.valueOf(c.getQuantidadeParcela()));
            specCrediario.put("dataVenda", DateGenerator.dateFormat(c.getVenda().getDataVenda()));
            specCrediario.put("cliente", c.getVenda().getCliente().getNome());
            specCrediario.put("funcionario", c.getVenda().getFuncionario().getNome());
            specCrediario.put("parcelas", String.valueOf(c.getParcelas().size()));
            specCrediario.put("parcelasPagas", String.valueOf(contaParcelasPagas(c.getIdCrediario())));
            specCrediario.put("valorParcela", String.valueOf(valorParcela));
            specCrediario.put("proximoVencimento", proximoVencimento(c.getIdCrediario()));
            specCrediario.put("parcelasAtrasadas", String.valueOf(contaParcelasAtrasadas(c.getIdCrediario())));
            specCrediario.put("valorDevido", String.valueOf(somaValorDevido(c.getIdCrediario())));
            
            listCrediario.add(specCrediario);
        }
        
        return listCrediario;
    }
    
    /*Método para listas parcelas*/
    public static List<Map<String, String>> listParcela(Long idCrediario){
        ParcelaDAO daoParcela = new ParcelaDAO();
        List<Map<String, String>> listParcelas = new ArrayList<>();
        
        List<Parcela> parcelas = daoParcela.getByCrediario(idCrediario);
        
        for(Parcela p : parcelas){
            Map<String, String> specParcela = new HashMap<>();
            
            specParcela.put("idParcela", String.valueOf(p.getIdParcela()));
            specParcela.put("dataVencimento", DateGenerator.dateFormat(p.getDataVencimento()));
            specParcela.put("parcela", String.valueOf(p.getParcela()));
            specParcela.put("statusParcela", p.getStatus());
            specParcela.put("valorParcela", String.valueOf(p.getValorParcela()));
            specParcela.put("parcelasPagas", String.valueOf(contaParcelasPagas(idCrediario)));
            
            listParcelas.add(specParcela);
        }
        
        return listParcelas;
    }
    
    /*Método para contar parcelas pagas*/
    public static Integer contaParcelasPagas(Long idCrediario){
        Integer count = 0;
        ParcelaDAO daoParcela = new ParcelaDAO();
        
        List<Parcela> parcelas = daoParcela.getByCrediario(idCrediario);
        
        for(Parcela p : parcelas){
            if(p.getStatus().equals("Pago")){
                count++;
            }    
        }
        return count;
    }
    
    /*Método para retornar o próximo vencimento*/
    public static String proximoVencimento(Long idCrediario){
        Calendar c = null;
        ParcelaDAO daoParcela = new ParcelaDAO();
        
        List<Parcela> parcelas = daoParcela.getByCrediario(idCrediario);
        
        for(Parcela p : parcelas){
            if(p.getStatus().equals("Não Pago")){
                c = p.getDataVencimento();
                break;
            }
        }
        
        if(c != null){
            return DateGenerator.dateFormat(c);
        }
        else{
            return "Concluído";
        }
    }
    
    /*Método para contar parcelas atrasadas*/
    public static Integer contaParcelasAtrasadas(Long idCrediario){
        Integer count = 0;
        Calendar dataAtual = Calendar.getInstance();
        
        ParcelaDAO daoParcela = new ParcelaDAO();
        
        List<Parcela> parcelas = daoParcela.getByCrediario(idCrediario);
        
        for(Parcela p : parcelas){
            if(dataAtual.after(p.getDataVencimento())){
                count++;
            }
        }
        
        return count;
    }
    
    /*Método para somar o valor devido*/
    public static String somaValorDevido(Long idCrediario){
        DecimalFormat df = new DecimalFormat("0.00");
        Double total = 0.0;
        Calendar dataAtual = Calendar.getInstance();
        
        ParcelaDAO daoParcela = new ParcelaDAO();
        
        List<Parcela> parcelas = daoParcela.getByCrediario(idCrediario);
        
        for(Parcela p : parcelas){
            if(dataAtual.after(p.getDataVencimento())){
                total += p.getValorParcela();
            }
        }
        
        
        //return df.format(total + (total * 0.02));
        return df.format(total);
    }
}
