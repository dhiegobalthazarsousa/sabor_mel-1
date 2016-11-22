/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.ProdutoDAO;
import eagles.sabor_mel.model.Produto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 *
 * @author dhiego.balthazar
 */
public class ControllerProduto {
    
    private static ProdutoDAO daoProduto = new ProdutoDAO();
    
    public static List<Map<String, String>> listProdutos(){
        List<Map<String, String>> listaProdutos = new ArrayList<>();
        List<Produto> produtos = daoProduto.findAll();
        
        for(Produto p: produtos){
            Double total = p.getQuantidade() * p.getValorUnitario();
            Map<String, String> specProduto = new HashMap();
            specProduto.put("id", String.valueOf(p.getIdProduto()));
            specProduto.put("descricao", String.valueOf(p.getDescricao()));
            specProduto.put("valorUnitario", String.valueOf(p.getValorUnitario()));
            specProduto.put("quantidade", String.valueOf(p.getQuantidade()));
            specProduto.put("total", String.valueOf(total));
            
            listaProdutos.add(specProduto);
        }
        
        return listaProdutos;
    }
    
    public static boolean cadastrar(String descricao, Integer quantidade,
        Double valorUnitario, String imagem){
        
        Produto produto = new Produto(descricao, quantidade, valorUnitario, imagem);
        return daoProduto.merge(produto); 
    }
    
    public static Map<String,String> searchProduto(Long id){
        Produto produto = daoProduto.getById(id);
        Map<String,String> specProduto = new HashMap<>();
        specProduto.put("idProduto", String.valueOf(produto.getIdProduto()));
        specProduto.put("descricao", produto.getDescricao());
        specProduto.put("quantidade", String.valueOf(produto.getQuantidade()));
        specProduto.put("valorUnitario", String.valueOf(produto.getValorUnitario()));
        specProduto.put("imagem", produto.getImagem());
        
        return specProduto;
    }
    
    public static List<Map<String,String>> searchProduto(String descricao){
        List<Map<String, String>> produtos = new ArrayList<>();
        List<Produto> listProdutos = daoProduto.getByDescricao(descricao);
        
        for(Produto p : listProdutos){
            
            Double total = p.getQuantidade() * p.getValorUnitario();
            
            Map<String, String> specProduto = new HashMap();
            specProduto.put("id", String.valueOf(p.getIdProduto()));
            specProduto.put("descricao", String.valueOf(p.getDescricao()));
            specProduto.put("valorUnitario", String.valueOf(p.getValorUnitario()));
            specProduto.put("quantidade", String.valueOf(p.getQuantidade()));
            specProduto.put("total", String.valueOf(total));
            
            produtos.add(specProduto);
            
        }
        
        return produtos;
        
    }
    
    public static boolean deleteProduto(Long id){
        return daoProduto.removeById(id);
    }
    
    public static boolean alterProduto(Long id, String descricao, Double valorUnitario,
            Integer quantidade, String imagem){
        
        Produto produto = daoProduto.getById(id);
        produto.setDescricao(descricao);
        produto.setImagem(imagem);
        produto.setQuantidade(quantidade);
        produto.setValorUnitario(valorUnitario);
        
        return daoProduto.merge(produto);
    }
}
