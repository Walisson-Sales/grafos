package org.example;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
    private List<Vertice> vertices;
    private List<Aresta> arestas;

    public Grafo(){
        vertices = new ArrayList<>();
        arestas = new ArrayList<>();
    } //construtor vazio só pra iniciar as listas

    public void addVertice(String nomeVertice){
        // vertices.add(v) // Antes
        Vertice v = new Vertice(nomeVertice);
        vertices.add(v);
    }
    public void addAresta(String nomeAresta, String nomeVertice1, String nomeVertice2){
        // 1: descobrir quem é o vertice com o nome "A"
        // 2: descobrir quem é o vertice com nome "B"
        // criar a aresta conectando os 2
        // adicionar à lista

        Vertice v1 = encontraVerticePeloNome(nomeVertice1);
        if (v1 == null){
            System.out.println("Vertoce "+ nomeVertice1 + "não encontrado");
            return;
        } // validação pra não ligar com nulo

        Vertice v2 = encontraVerticePeloNome(nomeVertice2);
        if (v2 == null){
            System.out.println("Vertoce "+ nomeVertice2 + "não encontrado");
            return;
        } // validação pra não ligar com nulo

        Aresta a = new Aresta(nomeAresta, v1, v2);
        arestas.add(a);
    }
    private Vertice encontraVerticePeloNome(String nomeProcurado){
        for (Vertice vertice : vertices){
            if (vertice.getNome().equalsIgnoreCase(nomeProcurado)){
                return vertice; // achei
            }
        } // foreach
        return null; // não achei
    }

    public void addAresta(String nomeVertice1, String nomeVertice2){
        // 1: descobrir quem é o vertice com o nome "A"
        // 2: descobrir quem é o vertice com nome "B"
        // criar a aresta conectando os 2
        // adicionar à lista

        Vertice v1 = encontraVerticePeloNome(nomeVertice1);
        if (v1 == null){
            System.out.println("Vertoce "+ nomeVertice1 + "não encontrado");
            return;
        } // validação pra não ligar com nulo

        Vertice v2 = encontraVerticePeloNome(nomeVertice2);
        if (v2 == null){
            System.out.println("Vertoce "+ nomeVertice2 + "não encontrado");
            return;
        } // validação pra não ligar com nulo

        Aresta a = new Aresta(v1, v2);
        arestas.add(a);
    }

    //public void addAresta(Aresta a){
    //    arestas.add(a);
    //} // usa esses métodos add pq pra usar o set teria que passar uma lista inteira
    // o set muda o valor por completo da variável.

    @Override
    public String toString(){
        return "Grafo = {\n"
                + " vertices = " + vertices
                + "\n arestas = " + arestas
                + "\n}";
    }
}
