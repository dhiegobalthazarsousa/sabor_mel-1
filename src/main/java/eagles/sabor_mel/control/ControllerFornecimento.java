                                                                     /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.FornecimentoDAO;
import eagles.sabor_mel.dao.PessoaDAO;
import eagles.sabor_mel.dao.ProdutoDAO;
import eagles.sabor_mel.model.DateGenerator;
import eagles.sabor_mel.model.Fornecimento;
import eagles.sabor_mel.model.ItemFornecimento;
import eagles.sabor_mel.model.Pessoa;
import eagles.sabor_mel.model.Produto;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dhiego.balthazar
 */
public class ControllerFornecimento {
    private static FornecimentoDAO daoFornecimento = new FornecimentoDAO();
    
    /*
     * @author Dhiego and ... 
     * This method creates and persists a Fornecimento Object
     * The Param idFornecedor recieve the idPessoa atribute of Pessoa Object
    */
    public static boolean cadastrar(Calendar data, Long idFornecedor, Long[] idProdutos, Integer[] quantityProdutos){
        PessoaDAO daoPessoa = new PessoaDAO();
        ProdutoDAO daoProduto = new ProdutoDAO();
        
        Pessoa fornecedor = daoPessoa.getById(idFornecedor);
        Calendar cal = Calendar.getInstance();
        cal.set(DateGenerator.getYear(), DateGenerator.getMonth(), DateGenerator.getDay());
        Fornecimento fornecimento = new Fornecimento(data);
        fornecimento.setPessoa(fornecedor);
        for(int i = 0; i < idProdutos.length; i++){
            ItemFornecimento itemFornecimento = new ItemFornecimento(quantityProdutos[i]);
            Produto produto = daoProduto.getById(idProdutos[i]);
            itemFornecimento.setProduto(produto);
            fornecimento.addItem(itemFornecimento);
        }
        return daoFornecimento.merge(fornecimento);
    }
    
    public static Map<String, String> searchFornecimento(Calendar start){
        Calendar end = Calendar.getInstance();
        end.set(DateGenerator.getYear(), DateGenerator.getMonth(), DateGenerator.getDay());
        
        List<Fornecimento> fornecimentos = daoFornecimento.getByInterval(start, end);
        
        List<Map> 
    }
        
}
