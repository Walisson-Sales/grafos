package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main(String[] args) {
        Grafo grafo = new Grafo(); // listas zeradas;

        // Antes:
        //Vertice v1 = new Vertice("A");
        //Vertice v2 = new Vertice("B");
        //Aresta a1 = new Aresta("a1", v1, v2);
        // add vertice ao grafo
        //grafo.addVertice(v1);
        //grafo.addVertice(v2);

        // add aresta:
        //grafo.addAresta(a1);

        // depois:
        // enxugando o código:
        grafo.addVertice("A");
        grafo.addVertice("B");

        grafo.addAresta("a1", "A", "B");

        System.out.println(grafo);
    }
}
