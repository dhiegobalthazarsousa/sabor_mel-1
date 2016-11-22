/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.model;

/**
 *
 * @author a1655086
 */
public enum StatusParcela {
    NPAGO ("NÃO PAGO"),
    PAGO("PAGO");
    
    private final String status;
    
    private StatusParcela(String status){
        this.status = status;
    }
    
    @Override
    public String toString(){
        return this.status;
    }
}
