package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Grafo {
    private List<Vertice> vertices;
    private List<Aresta> arestas;
    private boolean eDirigido;

    public Grafo(){
        this(false); //Quando tem 2 construtores e um chama o outro a gente chama o this.
    }

    public Grafo(boolean eDirigido){
        this.eDirigido = eDirigido;
        vertices = new ArrayList<>();
        arestas = new ArrayList<>();
    } // Agora o construtor passa eDirigido para quando o construtor for dirigido

    public void adicionaVertice(String ... nomes){ // ... permite passar quantos argumentos quiser de 1 vez
        for (String nome : nomes){
            vertices.add(new Vertice(nome));
        } // vai adicionado a lista de nomes à lista de vértices;

        // Antes:
        //vertices.add(v)
        //Vertice v = new Vertice(nomeVertice);
        //vertices.add(v);
    }

    public void addAresta(String nomeVertice1, String nomeVertice2){
        arestas.add(criaAresta("", nomeVertice1, nomeVertice2));
    }

    public void addAresta(String nomeAresta, String nomeVertice1, String nomeVertice2){
        arestas.add(criaAresta(nomeAresta, nomeVertice1, nomeVertice2));
    } // Sobrecarga de método, nesse passa o nome da aresta.

    private Aresta criaAresta(String nomeAresta, String nomeVertice1, String nomeVertice2){
        Vertice v1 = encontraVertice(nomeVertice1)
                .orElseThrow(() -> new IllegalArgumentException("Vertice" + nomeVertice1 + "Não encontrado,"));
        Vertice v2 = encontraVertice(nomeVertice2)
                .orElseThrow(() -> new IllegalArgumentException("Vertice" + nomeVertice2 + "Não encontrado,"));
        infereSeGrafoEDirecionado(v1, v2);
        return nomeAresta.isEmpty() ? new Aresta (v1, v2) : new Aresta (nomeAresta, v1, v2);

        // note: variavel.isPresent(); é o contrário de variavel.isEmpty();
        // note2: método orElseThrow - se pegar um conteier e ele estiver vazio, manda uma excessão do java
    }

    private void infereSeGrafoEDirecionado(Vertice v1, Vertice v2){
        if(!eDirigido){
            if(eSelfLoop(v1, v2)){
                eDirigido = true;
            } else{
                for (Aresta aresta : arestas){
                    if(eViaMaoDupla(v1, v2, aresta)){
                        eDirigido = true;
                        break;
                    }
                    if(eArestaDuplicada(v1, v2, aresta)){
                        eDirigido = true;
                        break;
                    }
                }
            }
        }
    }

    private static boolean eArestaDuplicada(Vertice v1, Vertice v2, Aresta aresta){
        return aresta.getVerticeOrigem().equals(v1) && aresta.getVerticeDestino().equals(v2);
    }

    private static boolean eViaMaoDupla(Vertice v1, Vertice v2, Aresta aresta){
        return aresta.getVerticeOrigem().equals(v2) && aresta.getVerticeDestino().equals(v1);
    }

    private static boolean eSelfLoop(Vertice v1, Vertice v2){
        return v1.getNome().equals(v2.getNome());
    }

    public Optional<Vertice> encontraVertice(String nome){
        for(Vertice vertice : vertices){
            if(vertice.getNome().equalsIgnoreCase(nome)){
                return Optional.of(vertice);
            }
        }
        return Optional.empty();
    } // Assim ele não pode mais retornar mais null quando não encontrar o vértice;


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
