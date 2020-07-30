/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Deinfo
 */
@Document
public class Empresa {
    @Id
    private String id;
    
    @Indexed(unique = true)
    String cnpj;
    
    String razaoSocial;
    String fantasia;
    String observacao;
    
    @DBRef
    Cidade cidade;

    public Empresa(String id, String cnpj, String razaoSocial, String fantasia, String observacao, Cidade cidade) {
        setId(id);
        setCnpj(cnpj);
        setRazaoSocial(razaoSocial);
        setFantasia(fantasia);
        setObservacao(observacao);
        setCidade(cidade);
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }
    
    public String getCnpjTb(){
        String[] parte = cnpj.split("");
        return parte[0]+parte[1]+"."
                +parte[2]+parte[3]+parte[4]+"."
                +parte[5]+parte[6]+parte[7]+"/"
                +parte[8]+parte[9]+parte[10]+parte[11]+
                "-"+parte[12]+parte[13];
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
    
    public String getCidadeNome(){
        return this.cidade.getNome();
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Empresa other = (Empresa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Empresa{" + "nome=" + fantasia + '}';
    }

    
}
