package eagles.sabor_mel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Tiago Lima Villalobos
 */

@Entity
@Table
public class Venda implements Serializable{
    @Id
    @GeneratedValue
    @Column
    private Long idVenda;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar dataVenda;
    
    @Enumerated(EnumType.STRING)
    private TipoVenda tipoVenda;
    
    @Column
    private Double desconto;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="idFuncionario", nullable=false)
    private Pessoa funcionario;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="idCliente", nullable=false)
    private Pessoa cliente;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "venda", targetEntity = Crediario.class)
    private Crediario crediario;

    public Crediario getCrediario() {
        return crediario;
    }

    public void setCrediario(Crediario crediario) {
        this.crediario = crediario;
    }
    
    @OneToMany(
       mappedBy = "venda", 
       targetEntity = ItemVenda.class, 
       fetch = FetchType.LAZY, 
       cascade = CascadeType.ALL)
    private List<ItemVenda> itens = new ArrayList<ItemVenda>();
    
    /*Construtores*/
    public Venda(){}
    
    public Venda(Calendar dataVenda, TipoVenda tipoVenda){
        this.dataVenda = dataVenda;
        this.tipoVenda = tipoVenda;
    }
    
    /*Getters*/

    public Long getIdVenda() {
        return idVenda;
    }

    public Calendar getDataVenda() {
        return dataVenda;
    }

    public TipoVenda getTipoVenda() {
        return tipoVenda;
    }

    public Double getDesconto() {
        return desconto;
    }

    public Pessoa getFuncionario() {
        return funcionario;
    }

    public Pessoa getCliente() {
        return cliente;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }
    
    /*Setters*/

    public void setIdVenda(Long idVenda) {
        this.idVenda = idVenda;
    }

    public void setDataVenda(Calendar dataVenda) {
        this.dataVenda = dataVenda;
    }

    public void setTipoVenda(TipoVenda tipoVenda) {
        this.tipoVenda = tipoVenda;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public void setFuncionario(Pessoa funcionario) {
        this.funcionario = funcionario;
    }

    public void setCliente(Pessoa cliente) {
        this.cliente = cliente;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }
    
    /*Add*/
    public void addItemVenda(ItemVenda item){
        item.setVenda(this);
        this.itens.add(item);
    }
}
