/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.CrediarioDAO;
import eagles.sabor_mel.dao.DocumentoDAO;
import eagles.sabor_mel.dao.FuncionarioDAO;
import eagles.sabor_mel.dao.PessoaDAO;
import eagles.sabor_mel.dao.ProdutoDAO;
import eagles.sabor_mel.dao.VendaDAO;
import eagles.sabor_mel.model.Crediario;
import eagles.sabor_mel.model.DateGenerator;
import eagles.sabor_mel.model.Documento;
import eagles.sabor_mel.model.Funcionario;
import eagles.sabor_mel.model.ItemVenda;
import eagles.sabor_mel.model.Parcela;
import eagles.sabor_mel.model.Pessoa;
import eagles.sabor_mel.model.Produto;
import eagles.sabor_mel.model.TipoVenda;
import eagles.sabor_mel.model.Venda;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dhiego.balthazar
 *
 */
public class ControllerVendas {

    /*
     * @author Dhiego
     * Map<String,String> produtos: recebe o id e a quantidade de produtos escolhidos na View
     */
    
    private static VendaDAO daoVenda = new VendaDAO();
    
    public static List<Map> searchVenda(int dia, int mes, int ano) {
        List<Map> listMapVendas = new ArrayList<>();
        Map<String, String> specVenda = new HashMap<>();
        Date start;
        Date end;
        Calendar cal = Calendar.getInstance();
        cal.set(ano, mes, dia);
        start = cal.getTime();
        cal.set(DateGenerator.getYear(), DateGenerator.getMonth(), DateGenerator.getDay());
        end = cal.getTime();

        List<Venda> vendas = daoVenda.getByInterval(start, end);

        for (Venda v : vendas) {
            specVenda.put("idVenda", String.valueOf(v.getIdVenda()));
            specVenda.put("dataVenda", String.valueOf(v.getDataVenda()));
            specVenda.put("tipoVenda", v.getTipoVenda().toString());
            specVenda.put("desconto", String.valueOf(v.getDesconto()));
            specVenda.put("cliente_name", v.getCliente().getNome());
            specVenda.put("cliente_document", v.getCliente().getDocumento().getNumero());
            specVenda.put("vendedor_name", v.getFuncionario().getNome());
            specVenda.put("vendedor_document", v.getFuncionario().getDocumento().getNumero());
            listMapVendas.add(specVenda);
        }
        return listMapVendas;
    }

    public static List<Map> searchVenda(String documentoCliente) {
        List<Map> listMapVendas = new ArrayList<>();
        Map<String, String> specVenda = new HashMap<>();
        PessoaDAO daoPessoa = new PessoaDAO();
        Pessoa pessoa = daoPessoa.getByDocument(documentoCliente);

        List<Venda> vendas = daoVenda.getByClient(pessoa.getIdPessoa());

        for (Venda v : vendas) {
            specVenda.put("idVenda", String.valueOf(v.getIdVenda()));
            specVenda.put("dataVenda", String.valueOf(v.getDataVenda()));
            specVenda.put("tipoVenda", v.getTipoVenda().toString());
            specVenda.put("desconto", String.valueOf(v.getDesconto()));
            specVenda.put("cliente_name", v.getCliente().getNome());
            specVenda.put("cliente_document", v.getCliente().getDocumento().getNumero());
            specVenda.put("vendedor_name", v.getFuncionario().getNome());
            specVenda.put("vendedor_document", v.getFuncionario().getDocumento().getNumero());
            listMapVendas.add(specVenda);
        }
        return listMapVendas;
    }
    
    public static List<Map> searchVenda(TipoVenda tipo) {
        List<Map> listMapVendas = new ArrayList<>();
        Map<String, String> specVenda = new HashMap<>();        
        List<Venda> vendas = daoVenda.getByTipo(tipo);

        for (Venda v : vendas) {
            specVenda.put("idVenda", String.valueOf(v.getIdVenda()));
            specVenda.put("dataVenda", String.valueOf(v.getDataVenda()));
            specVenda.put("tipoVenda", v.getTipoVenda().toString());
            specVenda.put("desconto", String.valueOf(v.getDesconto()));
            specVenda.put("cliente_name", v.getCliente().getNome());
            specVenda.put("cliente_document", v.getCliente().getDocumento().getNumero());            
            specVenda.put("vendedor_name", v.getFuncionario().getNome());
            specVenda.put("vendedor_document", v.getFuncionario().getDocumento().getNumero());
            listMapVendas.add(specVenda);
        }
        return listMapVendas;
    }

    /*
     * @author Dhiego e Thiago
     * Método para venda À VISTA
     */
    public boolean vender(Long idPessoa, Long idFuncionario, TipoVenda tipoVenda, Long[] produtos, int[] quantidades, double desconto) {
        Venda venda = createVenda(idPessoa, idFuncionario, tipoVenda, desconto, produtos, quantidades);

        return daoVenda.merge(venda);

    }
    
    /*
     * @author Dhiego e Thiago
     * Método para venda a Crediário
    */
    public static boolean vender(Long idPessoa, Long idFuncionario, TipoVenda tipoVenda, Long[] produtos, int[] quantidades, double desconto, int quantidadeParcela, int dia, int mes, int ano) {

        Venda venda = createVenda(idPessoa, idFuncionario, tipoVenda, desconto, produtos, quantidades);

        double valorTotal = getValorTotal(venda.getItens(), desconto);

        return ControllerCrediario.createCrediario(quantidadeParcela, dia, mes, ano, valorTotal, venda);
    }

    private static Venda createVenda(Long idPessoa, Long idFuncionario, TipoVenda tipoVenda, double desconto, Long[] produtos, int[] quantidades) {

        Venda venda = new Venda();
        PessoaDAO daoPessoa = new PessoaDAO();

        FuncionarioDAO daoFuncionario = new FuncionarioDAO();
        ProdutoDAO daoProduto = new ProdutoDAO();

        Pessoa cliente = daoPessoa.getById(idPessoa);
        Funcionario funcionario = daoFuncionario.getById(idFuncionario);

        Calendar cal = Calendar.getInstance();
        cal.set(DateGenerator.getYear(), DateGenerator.getMonth(), DateGenerator.getDay());
        venda.setDataVenda(cal);
        venda.setTipoVenda(tipoVenda);
        venda.setDesconto(desconto);
        venda.setCliente(daoPessoa.getById(idPessoa));
        venda.setFuncionario(daoFuncionario.getById(idFuncionario));

        for (int i = 0; i < produtos.length; i++) {
            Produto produto = daoProduto.getById(produtos[i]);
            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(quantidades[i]);
            changeQuantityProduto(produto, quantidades[i]);
            venda.addItemVenda(item);
        }
        return venda;

    }

    private static Double getValorTotal(List<ItemVenda> itens, double desconto) {
        double valorTotal = 0d;
        for (ItemVenda iv : itens) {
            valorTotal += iv.getProduto().getValorUnitario();
        }
        if (desconto > 0) {
            valorTotal = valorTotal - (valorTotal * (desconto / 100));
        }
        return valorTotal;
    }

    private static boolean changeQuantityProduto(Produto p, int quantity) {
        ProdutoDAO pDAO = new ProdutoDAO();
        int newQuantity = p.getQuantidade() - quantity;
        p.setQuantidade(newQuantity);
        return pDAO.merge(p);
    }

    /*
    private boolean analisaQuantidadeProdutos(Long[] produtos, int[] quantidades) {
        ProdutoDAO daoProduto = new ProdutoDAO();
        boolean vendaOk = true;
        for (int i = 0; i < produtos.length; i++) {
            Produto produto = daoProduto.getById(produtos[i]);
            if (produto.getQuantidade() < quantidades[i]) {
                vendaOk = false;
            }
        }
        return vendaOk;
    }
     */
}
