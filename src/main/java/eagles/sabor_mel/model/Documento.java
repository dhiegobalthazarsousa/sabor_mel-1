package eagles.sabor_mel.model;


import javax.persistence.*;
import java.io.*;

@Entity
@Table/*( uniqueConstraints ={ @UniqueConstraint( columnNames = "numero" ) } )*/
public class Documento implements Serializable{
    
    @Id
    @Column
    @GeneratedValue
    private Long idDocumento;
    
    @Enumerated(EnumType.ORDINAL)
    private TipoDocumento tipo;
    
    @Column(nullable = false, length = 18)
    private String numero;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "documento", targetEntity = Pessoa.class)
    private Pessoa pessoa;
       
    public Documento(){}
    
    public Pessoa getPessoa() {
        return this.pessoa;
    }
    
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    
    public Documento(String numero, TipoDocumento tipo){
        this.numero = numero;
        this.tipo = tipo;
    }
    
    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    public void setTipo(TipoDocumento tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    
}
