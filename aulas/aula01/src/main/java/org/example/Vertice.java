package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor // Já faz o constructor
@Getter // Compila todos os getter
@Setter // Compila todos os setter
public class Vertice {
    private String nome;

    @Override
    public String toString(){
        return nome;
    }

    /*
    //boilerplate code: repetitivo, massante...
    public Vertice(String nome){
        this.nome = nome;
    } //Contstrutor (mesmo nome da classe ee sem retorno)
    // construtor tem a 1 letra maiúscula

    //Get e set:
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    */
    // com a biblioteca longbot não precisa de tudo isso
}
