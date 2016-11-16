/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.test;

import eagles.sabor_mel.dao.FuncionarioDAO;
import eagles.sabor_mel.dao.PessoaDAO;
import eagles.sabor_mel.dao.VendaDAO;
import eagles.sabor_mel.model.FormaPagamento;
import eagles.sabor_mel.model.TipoVenda;
import eagles.sabor_mel.model.Venda;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Calendar;
import javax.swing.JFileChooser;


/**
 *
 * @author Tiago Lima Villalobos
 * 
 */
public class Temp {
    public static void main(String[] args) throws FileNotFoundException, IOException, URISyntaxException{
//        JFileChooser arquivo = new JFileChooser();
//
//        int retorno = arquivo.showOpenDialog(arquivo);
//        String caminhoArquivo = "";
//        String nome = "";
//
//        if (retorno == JFileChooser.APPROVE_OPTION) {
//            caminhoArquivo = arquivo.getSelectedFile().getAbsolutePath();
//            nome = arquivo.getSelectedFile().getName();
//            System.out.println(caminhoArquivo);
//            System.out.println(nome);
//            File selecionado = arquivo.getSelectedFile();
//            
//            /**/
//            FileInputStream origem;
//            FileOutputStream destino;
//            FileChannel fcOrigem;
//            FileChannel fcDestino;
//            
//            origem = new FileInputStream(caminhoArquivo);
//            destino = new FileOutputStream("C:\\Users\\etivideo\\Documents\\"+nome);
//            
//            fcOrigem = origem.getChannel();
//            fcDestino = destino.getChannel();
//            
//            fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);
//            
//            origem.close();
//            destino.close();
            /**/
//        }

//        Double b = 25d;
//        String a = "R$ 25,50";
//        
//        a = a.replace("R$", "").replace(",", ".");
//        s
//        System.out.println(a);
//        System.out.println(Double.parseDouble(a)+b);
        VendaDAO dao = new VendaDAO();
        Venda venda = new Venda();
        PessoaDAO daoPes = new PessoaDAO();
        FuncionarioDAO daoFun = new FuncionarioDAO();
        
        Calendar data = Calendar.getInstance();
        Double desconto = 0.0;
        FormaPagamento formaPagamento = FormaPagamento.Vista_Dinheiro;
        TipoVenda tipoVenda = TipoVenda.Vista;
        Long idCliente = 33l;
        Long idFuncionario = 33l;
        
        venda.setDataVenda(data);
        venda.setDesconto(desconto);
        venda.setFormaPagamento(formaPagamento);
        venda.setTipoVenda(tipoVenda);
        venda.setCliente(daoPes.getById(idCliente));
        venda.setFuncionario(daoFun.getById(idFuncionario));
        
        dao.merge(venda);
        
        System.exit(0);
        
    }
}