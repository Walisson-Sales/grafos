package org.example;

public class Aresta {
    private Vertice verticeOrigem;
    private Vertice verticeDestino;
    private String nome;

    // Construtor:
    public Aresta(Vertice v1, Vertice v2, String nome){
        this.verticeOrigem = v1;
        this.verticeDestino = v2;
        this.nome = nome;
    }
    public Aresta(Vertice v1, Vertice v2){
        this.verticeOrigem = v1;
        this.verticeDestino = v2;
    } // Sobreposisão de construtores pra caso tenha aresta sem nome.

    // Vertice 1:
    public Vertice getVerticeOrigem() {
        return verticeOrigem;
    }
    public void setVerticeOrigem(Vertice verticeOrigem) {
        this.verticeOrigem = verticeOrigem;
    }

    // Vertice 2:
    public Vertice getVerticeDestino() {
        return verticeDestino;
    }
    public void setVerticeDestino(Vertice verticeDestino) {
        this.verticeDestino = verticeDestino;
    }

    // Nome:
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Aresta " + this.nome + " (" + this.verticeOrigem.getNome() + " -> " + this.verticeDestino.getNome() + ")";
    }
}
