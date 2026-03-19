package org.example;

public class Aresta {
    private Vertice vertice1;
    private Vertice vertice2;
    private String nome;

    // Vertice 1:
    public Vertice getVertice1() {
        return vertice1;
    }
    public void setVertice1(Vertice vertice1) {
        this.vertice1 = vertice1;
    }

    // Vertice 2:
    public Vertice getVertice2() {
        return vertice2;
    }
    public void setVertice2(Vertice vertice2) {
        this.vertice2 = vertice2;
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
        return "Aresta " + this.nome + " (" + this.vertice1.getNome() + " -> " + this.vertice2.getNome() + ")";
    }
}
