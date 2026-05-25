package org.example;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        // Tarefa de hoje:
        /*
            Melhorar o código (usar construtores) - feito;
            Guardar a direção;

            Conseguir inferir/descobrir se o gráfico é dirigido/direcionado ou não

            com selfloop para verificar:
            se receber uma aresta que liga o mesmo gráfico
            ou uma que vai de a pra b e outra que vem de b pra a
        */

        // Criando os objetos de vertices:
        Vertice vertice1 = new Vertice("V1");
        Vertice vertice2 = new Vertice("v2");

        System.out.println("=========================================================================");
        System.out.println("Vertice 1:");
        System.out.println("Nome: " + vertice1.getNome());

        System.out.println("Vertice 2:");
        System.out.println("Nome: " + vertice2.getNome());
        // ==============================================================================

        // Criando os objetos de arestas:
        Aresta aresta1 = new Aresta(vertice1, vertice2, "A");

        System.out.println("=========================================================================");
        System.out.println("Nome: " + aresta1.getNome() + ", Vertice1: " + aresta1.getVerticeOrigem() + ", Vertice2: " +
                aresta1.getVerticeDestino()); // Ver a parte do retorno dos vertices depois;

        // ==============================================================================

        // Criando o grafo:
        Grafo grafo = new Grafo();

        // Atribuindo valor dos vertices do grafo
        grafo.addVertice(vertice1.getNome()); //Agora da pra passar o nome direto também
        grafo.addVertice(vertice2.getNome());

        // Atribuindo valor às arestas do grafo:
        List<Aresta> arestas = new ArrayList<>();
        arestas.add(aresta1);

        grafo.setArestas(arestas);

        System.out.println("=========================================================================");
        System.out.println("Vertices: " + grafo.getVertices() + ", Arestas: " + grafo.getArestas());
        System.out.println("=========================================================================");

        //System.out.println(vertice1.getNome());
        //System.out.println(vertice1.getValor());
    }
}
