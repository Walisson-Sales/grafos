package org.example;

import java.util.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class LinkedInAnalyzer {
    // A instância do grafo
    private Grafo redeSocial;

    // Estrutura para empacotar o resultado da sugestão
    public record Sugestao(String nome, int amigosEmComum) {}

    //------------------------------------------------------------------------------------

    /**
     * Missão 2: Sugestão de Conexões (Amigos de 2º Grau)
     */
    public List<Sugestao> sugerirConexoes(String nomeUsuario) {
        // 1- Valida se o usuário existe na rede
        redeSocial.encontraVertice(nomeUsuario).orElseThrow(
                () -> new IllegalArgumentException("Usuário não encontrado: " + nomeUsuario));

        // 2- Mapeia os contatos diretos (1º grau)
        List<String> amigosDiretos = obterVizinhos(nomeUsuario);
        Set<String> setAmigosDiretos = new HashSet<>(amigosDiretos);

        // 3- Caderno para contar os amigos em comum
        Map<String, Integer> contagemSugestoes = new HashMap<>();

        // 4- Vasculha os amigos dos amigos
        for (String amigo : amigosDiretos) {
            List<String> amigosDoAmigo = obterVizinhos(amigo);

            for (String candidato : amigosDoAmigo) {
                // Filtro implacável: Não posso sugerir o próprio usuário e nem quem já é amigo direto
                if (!candidato.equals(nomeUsuario) && !setAmigosDiretos.contains(candidato)) {
                    // Adiciona o candidato no caderno. Se ele já estiver lá, soma +1 amigo em comum
                    contagemSugestoes.put(candidato, contagemSugestoes.getOrDefault(candidato, 0) + 1);
                }
            }
        }

        // 5- Captação dos resultados
        List<Sugestao> ranking = new ArrayList<>();
        for (Map.Entry<String, Integer> entrada : contagemSugestoes.entrySet()) {
            ranking.add(new Sugestao(entrada.getKey(), entrada.getValue()));
        }

        // 6- Ordena o ranking de forma decrescente (quem tem mais amigos em comum fica no topo)
        ranking.sort((s1, s2) -> Integer.compare(s2.amigosEmComum(), s1.amigosEmComum()));

        return ranking;
    }

    /**
     * Métod0 Auxiliar de Segurança:
     * Garante que pegamos todos os vizinhos de um vértice, independentemente
     * de como a aresta foi cadastrada (Origem->Destino ou Destino->Origem).
     */
    private List<String> obterVizinhos(String nomeVertice) {
        List<String> vizinhos = new ArrayList<>();
        for (Aresta aresta : redeSocial.getArestas()) {
            if (aresta.getVerticeOrigem().getNome().equals(nomeVertice)) { // Vertice é de origem?
                vizinhos.add(aresta.getVerticeDestino().getNome());
            } else if (aresta.getVerticeDestino().getNome().equals(nomeVertice)) { // Vértice é de destino?
                vizinhos.add(aresta.getVerticeOrigem().getNome());
            }
        }
        return vizinhos;
    }

    //------------------------------------------------------------------------------------

    /**
     * Missão 3: Grau de Separação
     * Descobre a quantos passos de distância duas pessoas estão uma da outra.
     */
    public int grauDeSeparacao(String origem, String destino) {
        // 1- Validação de segurança (Fail-Fast)
        redeSocial.encontraVertice(origem).orElseThrow(
                () -> new IllegalArgumentException("Usuário de origem não encontrado: " + origem));
        redeSocial.encontraVertice(destino).orElseThrow(
                () -> new IllegalArgumentException("Usuário de destino não encontrado: " + destino));

        // Se for a mesma pessoa (origem e destino), o grau de separação é 0
        if (origem.equals(destino)) return 0;

        // 2- Fila para a Busca em Largura (BFS)
        Queue<String> fila = new LinkedList<>();

        // 3- Caderno de distâncias (funciona também como 'isVisited')
        Map<String, Integer> distancias = new HashMap<>();

        // Configuração do ponto 0 (o peso da pessoa para ela mesma é 0)
        fila.add(origem);
        distancias.put(origem, 0);

        // 4- BFS adaptado para este métod0:
        while (!fila.isEmpty()) {
            String current = fila.poll();

            // Se o atual for o destino, achou o caminho mais curto!
            if (current.equals(destino)) {
                return distancias.get(current);
            }

            // Reutiliza o métod0 da Fase 2 para pegar todos os contatos diretos (o obterVizinhos)
            List<String> vizinhos = obterVizinhos(current);

            for (String vizinho : vizinhos) {
                // Se o vizinho AINDA NÃO está no mapa de distâncias, é porque nunca o visitamos
                if (!distancias.containsKey(vizinho)) {
                    // A distância do vizinho é a distância do 'current' + 1 passo
                    distancias.put(vizinho, distancias.get(current) + 1);
                    // Coloca na fila para explorar os amigos dele depois
                    fila.add(vizinho);
                }
            }
        }

        // 5- Se não achar o destino, retorna -1
        return -1;
    }

    //------------------------------------------------------------------------------------

    /**
     * Missão 4: Rota e Custo de Maior Afinidade
     * Encontra a melhor rota considerando a afinidade acumulada (Dijkstra).
     */
    public Grafo.ResultadoDijkstra rotaMaiorAfinidade(String origem, String destino) {
        // Utiliza o algoritmo de Dijkstra:
        return redeSocial.dijkstra(origem, destino);
    }

    //------------------------------------------------------------------------------------
    /**
     * Missão 5: Mapear Grupos Isolados (Sub-redes)
     * Acha todos os componentes conexos do grafo.
     */
    public List<List<String>> mapearGruposIsolados() {
        // A lista principal que vai guardar todas as sub-redes (grupos)
        List<List<String>> subRedes = new ArrayList<>();
        Set<String> isVisited = new HashSet<>();

        // Pega todos os vértices cadastrados na rede
        List<Vertice> todosVertices = redeSocial.getVertices();

        for (Vertice v : todosVertices) {
            String nomeOrigem = v.getNome();

            // Se o perfil ainda não foi visitado, descobrimos um novo grupo isolado
            if (!isVisited.contains(nomeOrigem)) {
                List<String> grupoAtual = new ArrayList<>();
                Queue<String> fila = new LinkedList<>();

                // Configura o ponto de partida da nova busca
                fila.add(nomeOrigem);
                isVisited.add(nomeOrigem); // Adiciona o vértice como visitado

                // Usa busca em largura para mapear todos do grupo
                while (!fila.isEmpty()) {
                    String current = fila.poll();
                    grupoAtual.add(current); // Adiciona a pessoa ao grupo atual

                    // Pega os contatos e continua espalhando
                    List<String> vizinhos = obterVizinhos(current);
                    for (String vizinho : vizinhos) {
                        if (!isVisited.contains(vizinho)) {
                            isVisited.add(vizinho);
                            fila.add(vizinho);
                        }
                    }
                }

                // Quando a fila esvazia, o grupo inteiro foi mapeado
                // Então guarda o grupo na lista geral
                subRedes.add(grupoAtual);
            }
        }

        return subRedes;
    }
}
