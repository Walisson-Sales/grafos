package org.example;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
    //List<String> nomes = new ArrayList<>();
    private List<Vertice> vertices = new ArrayList<>();
    private List<Aresta> arestas = new ArrayList<>();

    // Alterar para não precisar mandar uma lista de vertices e arestas;
    //mas sim criar a lista aqui e ir adicioanndo;

    // Construtor:
    public Grafo(){
        vertices = new ArrayList<>();
        arestas = new ArrayList<>();
    } // Apenas para iniciar as listas

    // Adicionando vertices na lista:
    public void addVertice(String nome){
        Vertice vertice = new Vertice(nome);
        vertices.add(vertice);
    }

    // Vertices:
    public List<Vertice> getVertices(){
        return vertices;
    }
    public void setVertices(List<Vertice> vertices){
        this.vertices = vertices;
    }

    // Arestas:
    public List<Aresta> getArestas(){
        return arestas;
    }
    public void setArestas(List<Aresta> arestas){
        this.arestas = arestas;
    }

    @Override
    public String toString() {
        return "Grafo { " +
                "Vertices=" + this.vertices +
                ", Arestas=" + this.arestas + " }";
    }
}
