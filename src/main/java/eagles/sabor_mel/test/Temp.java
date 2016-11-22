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
import eagles.sabor_mel.model.Pessoa;
import eagles.sabor_mel.model.Telefone;
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
        
        PessoaDAO dao = new PessoaDAO();
        
        Pessoa pessoa = dao.getById(32L);
        System.out.println(pessoa.getIdPessoa());
        
        for(Telefone t: pessoa.getTelefones()){
            System.out.println(t.getNumero());
            
        }
        
        ControllerPessoa.
        
        System.exit(0);
        
    }
}