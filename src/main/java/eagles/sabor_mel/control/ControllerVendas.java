package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.FuncionarioDAO;
import eagles.sabor_mel.dao.PessoaDAO;
import eagles.sabor_mel.dao.ProdutoDAO;
import eagles.sabor_mel.dao.VendaDAO;
import eagles.sabor_mel.model.DateGenerator;
import eagles.sabor_mel.model.Funcionario;
import eagles.sabor_mel.model.ItemVenda;
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

    public static List<Map<String, String>> searchVenda(Calendar start, Calendar end) {
        List<Map<String, String>> listMapVendas = new ArrayList<>();
        List<Venda> vendas = daoVenda.getByInterval(start, end);

        for (Venda v : vendas) {
            System.out.println(v.getDataVenda().getTime());
            System.out.println(start.getTime());
            
            
            Map<String, String> specVenda = new HashMap<>();

            specVenda.put("idVenda", String.valueOf(v.getIdVenda()));
            specVenda.put("dataVenda", DateGenerator.dateFormat(v.getDataVenda()));
            specVenda.put("tipoVenda", v.getTipoVenda().toString());
            specVenda.put("desconto", String.valueOf(v.getDesconto()));
            specVenda.put("cliente", v.getCliente().getNome());
            specVenda.put("documentoCliente", v.getCliente().getDocumento().getNumero());
            specVenda.put("funcionario", v.getFuncionario().getNome());
            specVenda.put("documentoFuncionario", v.getFuncionario().getDocumento().getNumero());
            specVenda.put("valorTotal", String.valueOf(getValorTotal(v.getItens(), v.getDesconto())));
            specVenda.put("quantidadeTotal", String.valueOf(getQuantidadeTotal(v.getItens())));

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

    /*
     * @author Dhiego e Thiago
     * Método para venda À VISTA
     */
    public static boolean vender(Long idPessoa, Long idFuncionario, TipoVenda tipoVenda, Long[] produtos, int[] quantidades, double desconto) {
        Venda venda = createVenda(idPessoa, idFuncionario, tipoVenda, desconto, produtos, quantidades);

        return daoVenda.merge(venda);

    }
    
    /*
     * @author Dhiego e Tiago
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
            valorTotal += iv.getProduto().getValorUnitario() * iv.getQuantidade();
        }
        if (desconto > 0) {
            valorTotal = valorTotal - (valorTotal * (desconto / 100));
        }
        return valorTotal;
    }
    
    private static Integer getQuantidadeTotal(List<ItemVenda> itens) {
        Integer quantidade = 0;
        
        for (ItemVenda iv : itens) {
            quantidade += iv.getQuantidade();
        }
 
        return quantidade;
    }

    private static boolean changeQuantityProduto(Produto p, int quantity) {
        ProdutoDAO pDAO = new ProdutoDAO();
        int newQuantity = p.getQuantidade() - quantity;
        p.setQuantidade(newQuantity);
        return pDAO.merge(p);
    }
    
    
    /*Método para listar vendas*/
    public static List<Map<String, String>> listVendas(){
        List<Venda> vendas = daoVenda.findAll();
        List<Map<String, String>> listaVendas = new ArrayList<>();
        
        for(Venda v : vendas){
            
            Map<String, String> specVenda = new HashMap<>();
            
            specVenda.put("idVenda", String.valueOf(v.getIdVenda()));
            specVenda.put("dataVenda", String.valueOf(v.getDataVenda()));
            specVenda.put("desconto", String.valueOf(v.getDesconto()));
            specVenda.put("tipoVenda", String.valueOf(v.getTipoVenda()));
            specVenda.put("idCliente", String.valueOf(v.getCliente()));
            specVenda.put("idFuncionario", String.valueOf(v.getFuncionario().getIdPessoa()));
            specVenda.put("cliente", String.valueOf(v.getCliente().getNome()));
            specVenda.put("funcionario", String.valueOf(v.getFuncionario().getNome()));

            listaVendas.add(specVenda);
            
        }
        
        return listaVendas;
    }
    
    /*Método para listar itens da venda - calculando o total de quantidade e valor por venda*/
    /*ID - funcionario ou cliente*/
    public static Map<String, String> listItensTotalFuncionario(Long id){
        
        List<Venda> vendas = daoVenda.getByFuncionario(id);
        int tam = 0;
        
        Map<String, String> specVenda = new HashMap<>();
        Integer quantitadeTotal = 0;
        Double valorTotal = 0.0;
        
        for(int i = 0; i < vendas.size(); i++){
            
            tam = vendas.get(i).getItens().size();
            for(int j = 0; j < tam; j++){
                quantitadeTotal += vendas.get(i).getItens().get(j).getQuantidade();
                valorTotal += vendas.get(i).getItens().get(j).getProduto().getValorUnitario() *
                        vendas.get(i).getItens().get(j).getQuantidade();
            }
        }
        
        specVenda.put("valorTotal", String.valueOf(valorTotal));
        specVenda.put("quantidadeTotal", String.valueOf(quantitadeTotal));
        
        return specVenda;
    }
    
    /*Método para listar itens da venda - calculando o total de quantidade e valor por venda*/
    /*ID - funcionario ou cliente*/
    public static Map<String, String> listItensTotalCliente(Long id){
        
        List<Venda> vendas = daoVenda.getByClient(id);
        int tam = 0;
        
        Map<String, String> specVenda = new HashMap<>();
        Integer quantitadeTotal = 0;
        Double valorTotal = 0.0;
        
        for(int i = 0; i < vendas.size(); i++){
            
            tam = vendas.get(i).getItens().size();
            for(int j = 0; j < tam; j++){
                quantitadeTotal += vendas.get(i).getItens().get(j).getQuantidade();
                valorTotal += vendas.get(i).getItens().get(j).getProduto().getValorUnitario() *
                        vendas.get(i).getItens().get(j).getQuantidade();
            }
        }
        
        specVenda.put("valorTotal", String.valueOf(valorTotal));
        specVenda.put("quantidadeTotal", String.valueOf(quantitadeTotal));
        
        return specVenda;
    }
    
    /*Método para Listar as vendas dos funcionarios*/
    public static List<Map<String, String>> listVendasFuncionario(){
        List<Pessoa> vendas = daoVenda.groupByFuncionario();
        List<Map<String, String>> listaVendas = new ArrayList<>();
        
        for(Pessoa v : vendas){
            Map<String, String> specVenda = new HashMap<>();
            
            specVenda.put("idFuncionario", String.valueOf(v.getIdPessoa()));
            specVenda.put("funcionario", v.getNome());
            specVenda.put("vendas", String.valueOf(ControllerFuncionario.contarVendas(v.getIdPessoa())));
            specVenda.put("itens", listItensTotalFuncionario(v.getIdPessoa()).get("quantidadeTotal"));
            specVenda.put("valor", listItensTotalFuncionario(v.getIdPessoa()).get("valorTotal"));
            specVenda.put("desconto", String.valueOf(ControllerFuncionario.somarDesconto(v.getIdPessoa())));
            
            listaVendas.add(specVenda);
        }
        
        return listaVendas;
        
    }
    
    /*Método para Listar as compras dos clientes*/
    public static List<Map<String, String>> listVendasCliente(){
        List<Pessoa> vendas = daoVenda.groupByCliente();
        List<Map<String, String>> listaVendas = new ArrayList<>();
        
        for(Pessoa v : vendas){
            Map<String, String> specVenda = new HashMap<>();
            
            specVenda.put("cliente", v.getNome());
            specVenda.put("produtos", listItensTotalCliente(v.getIdPessoa()).get("quantidadeTotal"));
            specVenda.put("valor", listItensTotalCliente(v.getIdPessoa()).get("valorTotal"));
            specVenda.put("primeiraCompra", DateGenerator.dateFormat(daoVenda.getMindataCliente(v.getIdPessoa())));
            specVenda.put("ultimaCompra", DateGenerator.dateFormat(daoVenda.getMaxdataCliente(v.getIdPessoa())));
            
            listaVendas.add(specVenda);
        }
        
        return listaVendas;
        
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
