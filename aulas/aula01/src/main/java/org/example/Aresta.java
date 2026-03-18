package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Aresta {
    private String nome;
    private Vertice v1;
    private Vertice v2;

    public Aresta(Vertice v1, Vertice v2){
        this.v1 = v1;
        this.v2 = v2;
    } // Esse construtor é criado para que seja possível criar uma aresta sem nome, no caso.
    //pq o @AllArgsConstructor cria um constructor com todos os argumentos

    @Override
    public String toString(){
        //String nomeAresta = "";
        //if (nome != null) nomeAresta = nome; // pra tratar quando a aresta não tem nome

        String nomeAresta = nome != null ? nome: ""; // if ternário - mais enxuto e elegante

        return nomeAresta + "(" + v1 + ", " + v2 + ")";
    }
}
