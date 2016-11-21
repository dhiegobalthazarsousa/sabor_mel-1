/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.EstadoDAO;
import eagles.sabor_mel.model.Estado;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author a1655086
 */

public class ControllerEstado {
    
    private static final EstadoDAO daoEstado = new EstadoDAO();
    private static final List<Estado> estados = daoEstado.findAll();
    
    public static  Map <String, String> listEstados(){
        Map<String, String> specEstado = new HashMap<>();
        
        for(Estado e: estados){
            specEstado.put("uf", String.valueOf(e.getUf()));
        }
        
        return specEstado;
    }
    
    public static String escreverEstado(String uf){
        
        Estado estado = daoEstado.findByUf(uf);
        return estado.getNome();
        
    }
    
    public static Estado getEstadobyUf(String uf){
        return daoEstado.findByUf(uf);
    }
}