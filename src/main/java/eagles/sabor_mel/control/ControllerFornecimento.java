/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.FornecimentoDAO;
import eagles.sabor_mel.dao.ItemFornecimentoDAO;
import eagles.sabor_mel.dao.PessoaDAO;
import eagles.sabor_mel.dao.ProdutoDAO;
import eagles.sabor_mel.model.DateGenerator;
import eagles.sabor_mel.model.Fornecimento;
import eagles.sabor_mel.model.ItemFornecimento;
import eagles.sabor_mel.model.Pessoa;
import eagles.sabor_mel.model.Produto;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
    public static boolean cadastrar(Calendar data, Long idFornecedor, Long[] idProdutos, Integer[] quantityProdutos) {
        PessoaDAO daoPessoa = new PessoaDAO();
        ProdutoDAO daoProduto = new ProdutoDAO();

        Pessoa fornecedor = daoPessoa.getById(idFornecedor);
        Calendar cal = Calendar.getInstance();
        cal.set(DateGenerator.getYear(), DateGenerator.getMonth(), DateGenerator.getDay());
        Fornecimento fornecimento = new Fornecimento(data);
        fornecimento.setPessoa(fornecedor);
        for (int i = 0; i < idProdutos.length; i++) {
            ItemFornecimento itemFornecimento = new ItemFornecimento(quantityProdutos[i]);
            Produto produto = daoProduto.getById(idProdutos[i]);
            itemFornecimento.setProduto(produto);
            fornecimento.addItem(itemFornecimento);
        }
        return daoFornecimento.merge(fornecimento);
    }

    public static List<Map> searchFornecimentoByDate(Calendar start) {
        Calendar end = Calendar.getInstance();
        end.set(DateGenerator.getYear(), DateGenerator.getMonth(), DateGenerator.getDay());

        List<Fornecimento> resultFornecimentos = daoFornecimento.getByInterval(start, end);

        List<Map> listMapFornecimentos = new ArrayList<>();
        for (Fornecimento f : resultFornecimentos) {
            Map<String, String> specFornecimento = new HashMap<>();
            specFornecimento.put("idFornecimento", String.valueOf(f.getIdFornecimento()));
            specFornecimento.put("data", DateGenerator.dateFormat(f.getDataFornecimento()));
            specFornecimento.put("nomeFornecedor", f.getPessoa().getNome());
            listMapFornecimentos.add(specFornecimento);
        }
        return listMapFornecimentos;
    }

    public static List<Map> searchItemFornecimentoByIdFornecimento(Long idFornecimento) {
        ItemFornecimentoDAO daoItemFornecimento = new ItemFornecimentoDAO();
        List<ItemFornecimento> resultItemFornecimento = daoItemFornecimento.getByIdFornecimento(idFornecimento);
        
        List<Map> itens = new ArrayList<>();
        for (ItemFornecimento itemFornecimento : resultItemFornecimento) {
            Map<String, String> specItemF = new HashMap<>();
            specItemF.put("idItem", String.valueOf(itemFornecimento.getIdItemFornecimento()));
            specItemF.put("produto", itemFornecimento.getProduto().getDescricao());
            specItemF.put("quantidade", String.valueOf(itemFornecimento.getQuantidade()));
            itens.add(specItemF);
        }
        
        return itens;
    }

}
