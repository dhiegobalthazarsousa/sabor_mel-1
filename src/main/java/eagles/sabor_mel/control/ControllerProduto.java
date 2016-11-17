/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.ProdutoDAO;
import eagles.sabor_mel.model.Produto;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dhiego.balthazar
 */
public class ControllerProduto {
    
    private static ProdutoDAO daoProduto = new ProdutoDAO();
    
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
}
