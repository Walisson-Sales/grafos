package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Aresta {
    private String nome;
    private Vertice verticeOrigem;
    private Vertice verticeDestino;

    public Aresta(Vertice v1, Vertice v2){
        this.verticeOrigem = v1;
        this.verticeDestino = v2;
    } // Esse construtor é criado para que seja possível criar uma aresta sem nome, no caso.
    //pq o @AllArgsConstructor cria um constructor com todos os argumentos

    @Override
    public String toString(){
        //String nomeAresta = "";
        //if (nome != null) nomeAresta = nome; // pra tratar quando a aresta não tem nome

        String nomeAresta = nome != null ? nome: ""; // if ternário - mais enxuto e elegante

        return nomeAresta + "(" + verticeOrigem + ", " + verticeDestino + ")";
    }
}
