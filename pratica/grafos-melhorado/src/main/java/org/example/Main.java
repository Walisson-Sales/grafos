package org.example;

public class Main {
    public static void main(String[] args) {
        Grafo grafo = new Grafo(); //não dirigido
        grafo.adicionaVertices("5", "2", "3", "4", "1", "6");
        grafo.addAresta("e1","1", "3", 5);
        grafo.addAresta("e2", "1", "4", 5);
        grafo.addAresta("e3", "2", "5", 5);
        grafo.addAresta("e4", "3", "5", 5);
        grafo.addAresta("e5", "4", "4", 5);

        System.out.println(grafo);

    }
}