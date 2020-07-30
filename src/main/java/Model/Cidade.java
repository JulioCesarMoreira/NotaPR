/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Deinfo
 */
@Document
public class Cidade {
    @Id
    private String id;
    
    @Indexed(unique = true)
    String nome;
    @Indexed(unique = true)
    String sigla;

    public Cidade() {
    }

    
    public Cidade(String nome, String sigla) {
        setNome(nome);
        setSigla(sigla);
    }
    
    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = this.getSigla();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
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
        final Cidade other = (Cidade) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return nome;
    }
}
