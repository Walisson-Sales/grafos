package org.example;

public class Main {
    public static void main(String[] args) {
        Grafo grafo = new Grafo(); //não dirigido
        grafo.adicionaVertices("5", "2", "3", "4", "1"); // ADICIONAR O 6 AQUI DEPOIS PRA VER SER É CONEXO!!
        grafo.addAresta("e1","1", "3", 5);
        grafo.addAresta("e2", "1", "4", 5);
        grafo.addAresta("e3", "2", "5", 5);
        grafo.addAresta("e4", "3", "5", 5);
        grafo.addAresta("e5", "4", "2", 5);

        System.out.println(grafo);

        System.out.println("MATRIZES:");
        grafo.exibeMatrizAdjacenciaCORRIGIDO();
        System.out.println("------------------------");
        grafo.exibeMatrizIncidencia();
        System.out.println();
        System.out.println("CAMINHOS:");
        grafo.verificaCaminho("1", "4");
        grafo.calculaComprimento("1", "5");
        System.out.println("------------------------");
        System.out.println("DSF:");
        grafo.dfsWithoutRecursion("1", "5");
        System.out.println("\nDSF COM RESCURSIVIDADE:");
        grafo.dfsWithRecursion("1","4");
        System.out.println("------------------------");
        grafo.descobreConexo("1");
    }
}