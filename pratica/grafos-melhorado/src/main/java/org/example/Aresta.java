package org.example;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Aresta {
    private String nome;
    private Vertice verticeOrigem;
    private Vertice verticeDestino;
    private Integer peso;

    //Construtor para arestas sem nome e sem peso
    public Aresta(Vertice v1, Vertice v2) {
        this.verticeOrigem = v1;
        this.verticeDestino = v2;
        this.peso = 1;
    }

    // Construtor para arestas sem peso
    public Aresta(String nome, Vertice v1, Vertice v2) {
        this.nome = nome;
        this.verticeOrigem = v1;
        this.verticeDestino = v2;
        this.peso = 1;
    }

    // Construtor para arestas com peso e sem nome
    public Aresta(Vertice v1, Vertice v2, Integer peso) {
        this.verticeOrigem = v1;
        this.verticeDestino = v2;
        this.peso = peso;
    }

    @Override
    public String toString() {
        String nomeAresta = nome != null ? nome : "";
        return "\n" + nomeAresta + "{" + verticeOrigem.getNome() + "," + verticeDestino.getNome() + "}";
    }
}