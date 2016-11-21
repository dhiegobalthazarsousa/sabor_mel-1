/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.control;

import eagles.sabor_mel.dao.EstadoDAO;
import eagles.sabor_mel.model.Estado;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author a1655086
 */

public class ControllerEstado {
    
    public static  List<Map<String, String>> listEstados(){
        
        EstadoDAO daoEstado = new EstadoDAO();
        List<Estado> estados = daoEstado.findAll();
        List<Map<String, String>> listaEstados = new ArrayList<Map<String, String>>();
        
        for(Estado e: estados){
            Map<String, String> specEstado = new HashMap<>();
            specEstado.put("uf", String.valueOf(e.getUf()));
            listaEstados.add(specEstado);
        }
        
        return listaEstados;
    }
    
    public static String escreverEstado(String uf){
        EstadoDAO daoEstado = new EstadoDAO();
        Estado estado = daoEstado.findByUf(uf);
        return estado.getNome();
        
    }
    
    public static Estado getEstadobyUf(String uf){
        EstadoDAO daoEstado = new EstadoDAO();
        return daoEstado.findByUf(uf);
    }
}