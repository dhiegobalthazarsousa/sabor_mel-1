package eagles.sabor_mel.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Tiago Lima Villalobos
 */
@Entity
@Table
public class Parcela implements Serializable{
    @Id
    @GeneratedValue
    @Column
    private Long idParcela;
    
    @Column
    private Double valorParcela;
    
    @Column
    private boolean statusParcela;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "crediario", nullable = false)
    private Crediario crediario;
    
    /*Construtor*/
    public Parcela(){}
   
    /*Setters*/
    public void setIdParcela(Long idParcela) {
        this.idParcela = idParcela;
    }

    public void setValorParcela(Double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public void setStatusParcela(boolean statusParcela) {
        this.statusParcela = statusParcela;
    }

    public void setCrediario(Crediario crediario) {
        this.crediario = crediario;
    }
   
    /*Getters*/
    public Long getIdParcela() {
        return idParcela;
    }

    public Double getValorParcela() {
        return valorParcela;
    }

    public boolean isStatusParcela() {
        return statusParcela;
    }

    public Crediario getCrediario() {
        return crediario;
    }
}
