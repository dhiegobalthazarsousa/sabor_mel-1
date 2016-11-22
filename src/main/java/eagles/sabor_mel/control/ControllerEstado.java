/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.EstadoDAO;
import eagles.sabor_mel.model.Estado;

/**
 *
 * @author a1655086
 */



public class ControllerEstado {
    
    private static EstadoDAO daoEstado = new EstadoDAO();
    
    
    public static String escreverEstado(String uf){
        
        return daoEstado.findByUf(uf).getNome();
        
    }
    
    public static Estado getEstadoByUf(String uf){
        return daoEstado.findByUf(uf);
    }
}
