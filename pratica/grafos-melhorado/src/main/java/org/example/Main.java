package org.example;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        /*
        Grafo grafo = new Grafo(); //não dirigido
        grafo.adicionaVertices("5", "2", "3", "4", "1"); // ADICIONAR O 6 AQUI DEPOIS PRA VER SER É CONEXO!!
        grafo.addAresta("e1","1", "3", 5);
        grafo.addAresta("e2", "1", "4", 15);
        grafo.addAresta("e3", "2", "5", 2);
        grafo.addAresta("e4", "3", "5", 9);
        grafo.addAresta("e5", "4", "2", 11);

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
        System.out.println("------------------------");
        System.out.println("BSF:");
        grafo.bfsWithoutRecursion("1");
        System.out.println("------------------------");
        // Falta terminar:
//        System.out.println("Busca gulosa");
//        grafo.buscaGulosa("1", "3");
         */

        // Projeto final - LinkedIn Analyzer:
        // =========================================================
        System.out.println("\n========== LINKEDIN ANALYSER =============");
        System.out.println("------------------------------------------");

        // 1. Instanciando um NOVO Grafo exclusivo para a rede social
        Grafo redeLinkedIn = new Grafo();

        // 2. Cadastrando os Perfis usando o seu métod0 adicionaVertices
        redeLinkedIn.adicionaVertices("Ana", "Bruno", "Carlos", "Daniela", "Eduardo", "Fernanda", "Gabriel", "Hugo", "Igor", "Juliana");

        // 3. Cadastrando as Conexões e Afinidades (Arestas)
        redeLinkedIn.addAresta("c1", "Ana", "Bruno", 1);
        redeLinkedIn.addAresta("c2", "Ana", "Carlos", 2);
        redeLinkedIn.addAresta("c3", "Ana", "Daniela", 8);
        redeLinkedIn.addAresta("c4", "Bruno", "Eduardo", 1);
        redeLinkedIn.addAresta("c5", "Carlos", "Eduardo", 1);
        redeLinkedIn.addAresta("c6", "Daniela", "Fernanda", 5);
        redeLinkedIn.addAresta("c7", "Eduardo", "Fernanda", 1);

        // Grupos Isolados
        redeLinkedIn.addAresta("c8", "Gabriel", "Hugo", 1);
        redeLinkedIn.addAresta("c9", "Igor", "Juliana", 1);

        // 4. Inicializando o Motor de Análise (Missão 1)
        System.out.println("=== MISSAO 1: INICIALIZACAO ===");
        LinkedInAnalyzer analyzer = new LinkedInAnalyzer(redeLinkedIn);

        // 5. Testando Missão 2
        System.out.println("\n=== MISSAO 2: SUGESTAO DE CONEXOES (ANA) ===");
        List<LinkedInAnalyzer.Sugestao> sugestoesAna = analyzer.sugerirConexoes("Ana");
        for (LinkedInAnalyzer.Sugestao s : sugestoesAna) {
            System.out.println("Sugerido: " + s.nome() + " | Amigos em comum: " + s.amigosEmComum());
        }

        // 6. Testando Missão 3
        System.out.println("\n=== MISSAO 3: GRAU DE SEPARACAO ===");
        System.out.println("Distancia entre Ana e Fernanda: " + analyzer.grauDeSeparacao("Ana", "Fernanda") + " passos.");
        System.out.println("Distancia entre Ana e Gabriel: " + analyzer.grauDeSeparacao("Ana", "Gabriel") + " passos.");

        // 7. Testando Missão 4
        System.out.println("\n=== MISSAO 4: MAIOR AFINIDADE (DIJKSTRA) ===");
        Grafo.ResultadoDijkstra melhorRota = analyzer.rotaMaiorAfinidade("Ana", "Fernanda");
        System.out.println("Melhor caminho: " + melhorRota.caminho());
        System.out.println("Custo (Afinidade Acumulada): " + melhorRota.custo());

        // 8. Testando Missão 5
        System.out.println("\n=== MISSAO 5: GRUPOS ISOLADOS ===");
        List<List<String>> grupos = analyzer.mapearGruposIsolados();
        for (int i = 0; i < grupos.size(); i++) {
            System.out.println("Grupo " + (i + 1) + ": " + grupos.get(i));
        }

    }
}