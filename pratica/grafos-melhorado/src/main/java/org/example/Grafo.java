package org.example;


import java.util.*;

public class Grafo {
    private final List<Aresta> arestas;
    private final List<Vertice> vertices;
    private boolean eDirigido;
    private int ordem;
    private int tamanho;

    public Grafo() {
        this(false);
    }

    public Grafo(boolean eDirigido) {
        this.eDirigido = eDirigido;
        arestas = new ArrayList<>();
        vertices = new ArrayList<>();
    }

    public void adicionaVertices(String... nomes) {
        for (String nome : nomes) {
            vertices.add(new Vertice(nome));
            ordem++;
        }
    }

    public void addAresta(String nomeVertice1, String nomeVertice2) {
        arestas.add(criaAresta("", nomeVertice1, nomeVertice2, 1.0));
    }

    public void addAresta(String nomeAresta, String nomeVertice1, String nomeVertice2) {
        arestas.add(criaAresta(nomeAresta, nomeVertice1, nomeVertice2, 1.0));
    }

    public void addAresta(String nomeVertice1, String nomeVertice2, double peso) {
        arestas.add(criaAresta("", nomeVertice1, nomeVertice2, peso));
    }

    public void addAresta(String nomeAresta, String nomeVertice1, String nomeVertice2, double peso) {
        arestas.add(criaAresta(nomeAresta, nomeVertice1, nomeVertice2, peso));
    }

    private Aresta criaAresta(String nomeAresta, String nomeVertice1, String nomeVertice2, double peso) {
        Vertice v1 = encontraVertice(nomeVertice1).orElseThrow(
                () -> new IllegalArgumentException("Vertice " + nomeVertice1 + " não encontrado."));
        Vertice v2 = encontraVertice(nomeVertice2).orElseThrow(
                () -> new IllegalArgumentException("Vertice " + nomeVertice2 + " não encontrado."));
        if (!eDirigido) {
            infereSeGrafoEDirecionado(v1, v2);
        }
        aumentaGrauDosVertices(v1, v2);
        resolveAdjacencias(v1, v2);
        tamanho++;
        return nomeAresta.isEmpty() ? new Aresta(v1, v2, peso) : new Aresta(nomeAresta, v1, v2, peso);
    }

    private void resolveAdjacencias(Vertice v1, Vertice v2) {
        v1.adicionaAdjacencia(v2); //v1 envia p v2
        v2.adicionaAdjacente(v1); // v2 recebe de v1
        if (!eDirigido) {
            v1.adicionaAdjacente(v2);
            v2.adicionaAdjacencia(v1);
        }
    }

    private void aumentaGrauDosVertices(Vertice v1, Vertice v2) {
        if (eDirigido) {
            v1.aumentaOutDegree();
            v2.aumentaInDegree();
        } else {
            v1.aumentaGrau();
            v2.aumentaGrau();
        }
    }

    private void infereSeGrafoEDirecionado(Vertice v1, Vertice v2) {
        if (eSelfLoop(v1, v2)) {
            reprocessamentoParaDigrafo();
        } else {
            for (Aresta aresta : arestas) {
                if (eViaMaoDupla(v1, v2, aresta) || eArestaDuplicada(v2, v1, aresta)) {
                    reprocessamentoParaDigrafo();
                    break;
                }
            }
        }
    }

    private static boolean eArestaDuplicada(Vertice v1, Vertice v2, Aresta aresta) {
        return aresta.getVerticeOrigem().equals(v1) && aresta.getVerticeDestino().equals(v2);
    }

    private static boolean eViaMaoDupla(Vertice v1, Vertice v2, Aresta aresta) {
        return aresta.getVerticeOrigem().equals(v2) && aresta.getVerticeDestino().equals(v1);
    }

    private static boolean eSelfLoop(Vertice v1, Vertice v2) {
        return v1.getNome().equals(v2.getNome());
    }

    public Optional<Vertice> encontraVertice(String nome) {
        for (Vertice vertice : vertices) {
            if (vertice.getNome().equalsIgnoreCase(nome)) {
                return Optional.of(vertice);
            }
        }
        return Optional.empty();
    }

    private void reprocessamentoParaDigrafo() {
        eDirigido = true;
        System.out.println("Reprocessamento para digrafo necessário. O grafo agora é direcionado.");
        //limpeza
        vertices.forEach(vertice -> {
            vertice.resetaGraus();
            vertice.resetaAdjacenciasEAdjacentes();
        });
        //recalcular tudo
        arestas.forEach(aresta -> {
            Vertice origem = aresta.getVerticeOrigem();
            Vertice destino = aresta.getVerticeDestino();
            aumentaGrauDosVertices(origem, destino);
            resolveAdjacencias(origem, destino);
        });
    }

    public String exibeGrausDosVertices() {
        StringBuilder graus = new StringBuilder();
        for (Vertice vertice : vertices) {
            graus.append(vertice.exibeGraus());
        }
        return graus.toString();
    }

    public String exibeAdjacencias() {
        StringBuilder adjacencias = new StringBuilder();
        for (Vertice vertice : vertices) {
            adjacencias
                    .append("\n")
                    .append(vertice.getNome())
                    .append(": ")
                    .append(vertice.getAdjacencias());
        }
        return adjacencias.toString();
    }

    public String exibeAdjacentes() {
        StringBuilder adjacencias = new StringBuilder();
        for (Vertice vertice : vertices) {
            adjacencias
                    .append("\n")
                    .append(vertice.getNome())
                    .append(": ")
                    .append(vertice.getAdjacentes());
        }
        return adjacencias.toString();
    }

    // ------------------------------------------------------------------------------------

    public void exibeMatrizAdjacenciaCORRIGIDO(){
        // IDEIA 1 - Mas só funciona com vértices INT
        //1º Colocando a lista de Vertices em ordem para o cabeçalho:
        String listaVertices = vertices.toString();
        String[] partes = listaVertices.replace("[", "").replace("]", "").split(", ");
        Arrays.sort(partes);
        String verticesOrdenados = Arrays.toString(partes);
        String[] teste = verticesOrdenados.replace("[", "").replace("]", "").split(", ");


        // 2º Iniciaindo a matriz com valores 0:
        int tamanho = vertices.size(); // Pegando o tamanho da matriz - dava pra ter usado a ordem, mas eu só lembrei depois
        //Criando a matriz:
        int[][] matriz = new int[tamanho][tamanho];
        // Iniciando com 0:
        for(int i = 0; i < tamanho; i++ ){
            for(int j = 0; j < tamanho; j++){
                matriz[i][j] = 0;
            }
        }

        // 3º Preenchendo a matriz:
        // todo: Comparar o vetor em questão com a linha e depois preencher na coluna certa
        for(Vertice vertice : vertices){
            List<Vertice> listasAdjacencias = vertice.getAdjacencias();
            String nomeVertice = vertice.getNome();
            for(Vertice adjacencia : listasAdjacencias){
                String getnomeAdjacencia = adjacencia.getNome();
                matriz[Integer.parseInt(nomeVertice)-1][Integer.parseInt(getnomeAdjacencia)-1] = 1;
            }
        }

        // 4º Imprimindo a matriz:
        System.out.println("Matriz de Adjacência:");
        System.out.print("   "); // Deixando um espaço no cabeçalho
        for (int j = 0; j < tamanho; j++) {
            System.out.print(teste[j] + " "); // Cabeçalho das colunas
        }
        System.out.println();
        for(int i = 0; i < tamanho; i++ ){
            System.out.print(teste[i] + "| "); // Cabeçalho das linhas
            for(int j = 0; j < tamanho; j++){
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }

        /* Fazendo com ajuda da IA:
         IDEIA 2: Pedi ajuda à ia mais pro final
         TENTANDO DEIXAR ELA COMO STRING:
         TENTANDO COLOCAR O CABEÇALHO NA MATRIZ:
        String[][] matriz2 = new String[tamanho+1][tamanho+1];
        // Iniciando com 0:
        for(int i = 0; i <= tamanho; i++ ){
            for(int j = 0; j <= tamanho; j++){
                matriz2[i][j] = "0";
            }
        }

        matriz2[0][0] = "   ";

        System.out.println("MATRIZ PREENCHIDA!!");
        for (int i = 0; i < tamanho; i++) {
            // teste[i] vai para o cabeçalho das colunas (Linha 0)
            matriz2[0][i + 1] = teste[i];
            // teste[i] vai para o cabeçalho das linhas (Coluna 0)
            matriz2[i + 1][0] = teste[i];
        }

        // 3. Lógica de preenchimento (Ajustada para lidar com a matriz ordenada)
        for (int i = 0; i < tamanho; i++) {
            Vertice atual = vertices.get(i);
            String nomeAtual = atual.getNome();
            List<Vertice> vizinhos = atual.getAdjacencias();

            // Como a matriz está em ordem alfabética e a lista original talvez não,
            // primeiro descobrimos em qual LINHA este vértice mora agora.
            int linhaCorreta = -1;
            for (int linha = 1; linha <= tamanho; linha++) {
                if (matriz2[linha][0].equals(nomeAtual)) {
                    linhaCorreta = linha;
                    break;
                }
            }

            // Agora varremos os vizinhos e descobrimos as COLUNAS
            for (Vertice vizinho : vizinhos) {
                String nomeVizinho = vizinho.getNome();

                for (int coluna = 1; coluna <= tamanho; coluna++) {
                    if (matriz2[0][coluna].equals(nomeVizinho)) {
                        // Achou! Marca "1" na intersecção exata
                        matriz2[linhaCorreta][coluna] = "1";
                        break;
                    }
                }
            }
        }

        // 4. Imprimindo a matriz pronta (Como ela já tem cabeçalho, é só imprimir direto!)
        System.out.println("MATRIZ PREENCHIDA!!");
        for (int i = 0; i <= tamanho; i++) {
            for (int j = 0; j <= tamanho; j++) {
                // Usamos printf "%3s" para garantir que os espaços fiquem alinhados como tabela
                System.out.printf("%3s ", matriz2[i][j]);
            }
            System.out.println(); // Quebra a linha da matriz
        }

         Ideia 3:
         A junção das 2 ideias - era o que eu tava tentando fazer mas não consegui chegar aqui só
        for (Vertice atual : vertices) {
            // 1. Procura a LINHA (onde o vértice atual está no array 'teste')
            int linha = -1;
            for (int i = 0; i < tamanho; i++) {
                if (teste[i].equals(atual.getNome())) {
                    linha = i;
                    break;
                }
            }

            // 2. Pega os vizinhos, acha a COLUNA deles e marca '1' na matriz
            for (Vertice vizinho : atual.getAdjacencias()) {
                for (int coluna = 0; coluna < tamanho; coluna++) {
                    if (teste[coluna].equals(vizinho.getNome())) {
                        matriz[linha][coluna] = 1; // BINGO!
                        break;
                    }
                }
            }
        }

        System.out.println("\nMATRIZ DE ADJACÊNCIA:");

        // 1. Imprime o cabeçalho das COLUNAS
        System.out.print("    "); // Dá o espaço inicial do canto esquerdo
        for (int j = 0; j < tamanho; j++) {
            System.out.printf(" %2s ", teste[j]);
        }
        System.out.println(); // Pula a linha do cabeçalho

        // 2. Imprime as LINHAS com os dados da matriz
        for (int i = 0; i < tamanho; i++) {
            // Imprime a letra da linha atual
            System.out.printf(" %2s |", teste[i]);

            // Imprime os números 0 ou 1 lado a lado
            for (int j = 0; j < tamanho; j++) {
                System.out.printf(" %2d ", matriz[i][j]);
            }
            System.out.println(); // Pula para a próxima linha
        }
        */

    }

    public void exibeMatrizIncidencia(){
        //1º Colocando a lista de Vertices em ordem para o cabeçalho:
        String listaVertices = vertices.toString();
        String[] partes = listaVertices.replace("[", "").replace("]", "").split(", ");
        Arrays.sort(partes);
        String verticesOrdenados = Arrays.toString(partes);
        String[] teste = verticesOrdenados.replace("[", "").replace("]", "").split(", ");

        // 2º Iniciaindo a matriz com valores 0:
        //Criando a matriz:
        int[][] matriz = new int[ordem][tamanho];
        // Iniciando com 0:
        for(int i = 0; i < ordem; i++ ){
            for(int j = 0; j < tamanho; j++){
                matriz[i][j] = 0;
            }
        }

        // 3º Preenchendo a matriz:
        // todo: Comparar as linhas com os vértices e as colunas com as arestas
        for(int coluna = 0; coluna < tamanho; coluna++) {

            Aresta arestaAtual = arestas.get(coluna);

            String nomeOrigem = arestaAtual.getVerticeOrigem().getNome();
            String nomeDestino = arestaAtual.getVerticeDestino().getNome();

            // Acha a LINHA do vértice de ORIGEM
            int linhaOrigem = -1;
            for(int i = 0; i < ordem; i++) {
                if(teste[i].equals(nomeOrigem)) {
                    linhaOrigem = i; break;
                }
            }

            // Acha a LINHA do vértice de DESTINO
            int linhaDestino = -1;
            for(int i = 0; i < ordem; i++) {
                if(teste[i].equals(nomeDestino)) {
                    linhaDestino = i; break;
                }
            }

            // Preenchendo a coluna atual da matriz
            if (linhaOrigem != -1 && linhaDestino != -1) {
                if (!eDirigido){
                    matriz[linhaOrigem][coluna] = 1;
                    matriz[linhaDestino][coluna] = 1;
                }
                else{
                    if (linhaOrigem == linhaDestino) {
                        matriz[linhaOrigem][coluna] = 2; // self-loop
                    } else {
                        matriz[linhaOrigem][coluna] = -1;  // Saida
                        matriz[linhaDestino][coluna] = 1; // Entrada
                    }
                }

            }
        }

        // 4º Imprimindo a matriz:
        System.out.println("Matriz de Incidência:");
        System.out.print("    "); // Deixando um espaço no cabeçalho
        for (int j = 0; j < tamanho; j++) {
            System.out.printf(" e%d ", (j + 1)); // Cabeçalho das colunas
        }
        System.out.println();
        for(int i = 0; i < ordem; i++ ){
            System.out.printf("%2s |", teste[i]); // Cabeçalho das linhas
            for(int j = 0; j < tamanho; j++){
                System.out.printf(" %2d ", matriz[i][j]);
            }
            System.out.println();
        }
    }

    // ------------------------------------------------------------------------------------

    //todo: 1- Verificação da existência de caminho simples
    // Isto é, se x é a alcançável a partir de y
    public void verificaCaminho(String nomeOrigem, String nomeDestino){
        // 1 ideia - pensava que precisava percorrer todas as arestas, daí vi que era impossível para grafos maiores.
        // E tentei passar os vértices direto
        /*
        for(Aresta aresta : arestas){
            boolean achou = aresta.getVerticeDestino().equals(verticeDestino);
            if(achou){
                System.out.println("VOCÊ ACHOU!!");
            }
        }
         */
        // 2 ideia - usar a lista de adjacências (com ajuda de ia):
        // Encontrando os vértices pelo nome (sem passar os objetos vértice)
        Vertice verticeOrigem = encontraVertice(nomeOrigem).orElseThrow(
                () -> new IllegalArgumentException("Vertice " + nomeOrigem + " não encontrado."));
        Vertice verticeDestino = encontraVertice(nomeDestino).orElseThrow(
                () -> new IllegalArgumentException("Vertice " + nomeDestino + " não encontrado."));
        // Iniciando as listas
        List<Vertice> visitas = new ArrayList<>();
        List<Vertice> proximosAVisitar = new ArrayList<>();
        proximosAVisitar.add(verticeOrigem); // Que é o primeiro vértice da busca
        while(!proximosAVisitar.isEmpty()){
            Vertice verticeAtual = proximosAVisitar.remove(0); // faz ele ir para o próximo vértice

            boolean achou = verticeAtual.equals(verticeDestino);
            if(achou){
                System.out.println("VOCÊ ACHOU!!");
                return;
            }

            visitas.add(verticeAtual);
            for (Vertice proximoVertice : verticeAtual.getAdjacencias()){ // Verificação de caminho simples
                if(!visitas.contains(proximoVertice) && !proximosAVisitar.contains(proximoVertice)){
                    proximosAVisitar.add(proximoVertice);
                }
            }
        }
        System.out.println("Sem caminho para este vértice!");
    }

    /*
        Linha de raciocínio:
            *5*, 7
             7 , 4
             4 , 1
             1 ,*2*
        (o último vértice é igual o 1º da próxima aresta)
        Vertice de destino é a parada do loop
        SEMPRE o start é o vértice de origem (por isso não precisa passar por todas as arestas)

     */

    //todo: 2- Cálculo do comprimento do caminho
    // OBS: diferenciar o cálculo de acordo com o tipo de grafo (ponderado ou não)
    //Nesta segunda parte da tarefa eu usei mais inteligência artificial.
    public void calculaComprimento(String nomeOrigem, String nomeDestino) {
        Vertice origem = encontraVertice(nomeOrigem).orElseThrow(
                () -> new IllegalArgumentException("Vertice " + nomeOrigem + " não encontrado."));
        Vertice destino = encontraVertice(nomeDestino).orElseThrow(
                () -> new IllegalArgumentException("Vertice " + nomeDestino + " não encontrado."));

        List<Vertice> proximosAVisitar = new ArrayList<>();
        Map<Vertice, Double> distanciasAcumuladas = new HashMap<>();

        proximosAVisitar.add(origem);
        distanciasAcumuladas.put(origem, 0.0);

        while (!proximosAVisitar.isEmpty()) {
            Vertice atual = proximosAVisitar.remove(0);

            if (atual.equals(destino)) {
                System.out.println("Comprimento total: " + distanciasAcumuladas.get(atual));
                return;
            }
            for (Aresta aresta : this.arestas) {
                Vertice vizinho = null;

                if (aresta.getVerticeOrigem().equals(atual)) {
                    vizinho = aresta.getVerticeDestino();
                }

                else if (!this.eDirigido && aresta.getVerticeDestino().equals(atual)) {
                    vizinho = aresta.getVerticeOrigem();
                }

                if (vizinho != null && !distanciasAcumuladas.containsKey(vizinho)) {
                    double custoAteAqui = distanciasAcumuladas.get(atual);

                    double pesoAresta = aresta.getPeso();

                    distanciasAcumuladas.put(vizinho, custoAteAqui + pesoAresta);
                    proximosAVisitar.add(vizinho);
                }
            }
        }
        System.out.println("Sem caminho para este vértice!");
    }

    // Obs: Atentar para as diferenças quando os grafos são dígrafos ou não

    // ------------------------------------------------------------------------------------
    // Método 1:
    // todo:
    //    1- Ajustar argumentos para String origem e String destino
    //    2- Considere realizar uma caminhada quando não há destino informado
    //    3- Reveja o retorno do métod0 para algo que faça mais sentido
    //    4- Printe o percurso que está sendo feito
    public List<String> dfsWithoutRecursion(String start, String end) {
//        current = atual
        // LEMBRANDO:
        // vou passar o NOME do vértice no start
        // coisa como: A, B, C - não número do vértice.

        // Criação das variáveis
        Stack<String> stack = new Stack<String>();
        Set<String> isVisited = new HashSet<>(); // deixou de ser uma lista de boolean e passou a ser um set, para trabalhar com Strings
        stack.push(start);
        List<String> caminhoPercorrido = new ArrayList<>(); // usado no retorno

        // lógica:
        System.out.println("Construção do percurso:");
        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (isVisited.contains(current)) {
                continue;
            }
            isVisited.add(current);

            System.out.print(current + ", ");
            caminhoPercorrido.add(current);

            if (end != null && !end.isEmpty() && current.equals(end)) {
                System.out.println("Destino '" + end + "' encontrado! Busca encerrada.");
                System.out.println(caminhoPercorrido);
                return caminhoPercorrido;
            }

            Vertice verticeAtual = encontraVertice(current).orElseThrow(
                    () -> new IllegalArgumentException("Vertice " + current + " não encontrado."));
            for (Vertice dest : verticeAtual.getAdjacencias()) {
                // dest = destino, eu acho
                if (!isVisited.contains(dest.getNome()))
                    stack.push(dest.getNome());
            }
        }
        // Retorna lista vazia quando não há caminho possível até o destino
        if (end != null && !end.isEmpty()) {
            System.out.println("\nDestino '" + end + "' inalcançável a partir de " + start );
            return new ArrayList<>();
        }

        // Caminhada completa
        System.out.println("Caminhada completa finalizada");
        System.out.println(caminhoPercorrido);
        return caminhoPercorrido;
    }

    // Sobrecarga do métod0 para deixar o end como opcional
    public List<String> dfsWithoutRecursion(String start) {
        // usando recursividade, passando só o start e deixando o end vazio.
        return dfsWithoutRecursion(start, "");
    }
    // pra resolver o problema do get (no for) eu primeiro pesquisei se tinha outra forma de fazer isso, vi que não tinha.
    // depois tentei descobrir a posição do current pra então passar a posição do no get(), mas recebi outro erro.
    // Com ajuda da ia vi que era só pegar a lista de adjacência do objeto current

    // Método 2:
    // todo:
    //    1- Ajustar argumentos para String origem e String destino
    //    2- Considere realizar uma caminhada quando não há destino informado
    //    3- Reveja o retorno do métod0 para algo que faça mais sentido
    //    4- Printe o percurso que está sendo feito

    private boolean dfsRecursive(String current, String end, Set<String> isVisited, List<String> caminhoPercorrido) {
        isVisited.add(current);
        caminhoPercorrido.add(current);
        System.out.print(current + ", ");
        // Verifica se chegou no destino (end)
        if (end != null && !end.isEmpty() && current.equals(end)) {
            return true;
        }

        // Pegando o vértice através do nome passado em current
        Vertice verticeAtual = encontraVertice(current).orElseThrow(
                () -> new IllegalArgumentException("Vertice " + current + " não encontrado."));

        // Explora os vizinhos do vértice atual
        for (Vertice dest : verticeAtual.getAdjacencias()) {
            if (!isVisited.contains(dest.getNome())) {
                boolean achouLaNoFundo = dfsRecursive(dest.getNome(), end, isVisited, caminhoPercorrido);
                if (achouLaNoFundo) {
                    return true;
                }
            }
        }
        // se não achou o destino, retorna falso:
        return false;
    }
    public List<String> dfsWithRecursion(String start, String end) {
        Set<String> isVisited = new HashSet<>();
        List<String> caminhoPercorrido = new ArrayList<>();

        System.out.println("Construção do percurso:");

        boolean achouDestino = dfsRecursive(start, end, isVisited, caminhoPercorrido);

        // Lida com a resposta
        if (end != null && !end.isEmpty()) {
            if (achouDestino) {
                System.out.println("\nDestino " + end + " encontrado! Busca encerrada.");
            } else {
                System.out.println("\nDestino " + end + " inalcançável a partir de '" + start);
                return new ArrayList<>(); // Retorna lista vazia se falhou
            }
        } else {
            System.out.println("\nCaminhada completa finalizada");
        }

        //retornando o caminho percorrido:
        System.out.println(caminhoPercorrido);
        return caminhoPercorrido;
    }

    // sobrecarga para quando não é passado um destino
    public List<String> dfsWithRecursion(String start) {
        return dfsWithRecursion(start, "");
    }

    // ------------------------------------------------------------------------------------

    // Implementação do método para descobrir se o grafo é conexo ou não:

    public boolean descobreConexo(String start){
        //todo: Primeiro verificar se há atalhos para saber se o vértice é conexo ou não
        // todo: Fazer a caminhada no grafo e ver se a quantidade de vértices é igual a quantidade percorrida

        //Atalhos:
        // vertices isolados, se NÃO manda ou NÃO recebe de alguém;

        //1º verifica se o vértice existe
        Vertice inicio = encontraVertice(start).orElseThrow(
                () -> new IllegalArgumentException("Vertice " + start + " não encontrado."));

        //2º verificando in e out degree
        for(Vertice vertice : vertices){
            if(vertice.getInDegree() == 0 || vertice.getOutDegree() == 0){
                //Tentei utilizar grau nesse if de cima, mas por algum motivo ele tava dando resultados errados, daí voltei pro in e out
                System.out.println("Grafo DESconexo!");
                System.out.println("Nome:" + vertice.getNome());
                System.out.println(vertice.getOutDegree());
                System.out.println(vertice.getInDegree());
                System.out.println("Grau:" + vertice.getGrau());
                return false;
            }
        } 

        List<String> caminhada = dfsWithoutRecursion(inicio.getNome());


        // OBS: A CAMINHADA TEM QUE SER PARA TODOS OS VÉRTICES!!
        // imagina que do vértice 1 ele consegue se conectar com todos, mas o vértice 2 não (com grafo dirigido)
        // Ajustar para percorrer a caminhada em todos os vértices depois:
        if(vertices.size() != caminhada.size()){
            System.out.println("Grafo DESconexo!");
            return false;
        }

        // Ideia de colocar tudo num if só: (esqueci que tinha que ver o in e out de todos os vértices
//        if(inicio.getInDegree() == 0 || inicio.getOutDegree() == 0 || vertices.size() != caminhada.size()){
//            System.out.println("Grafo DESconexo!");
//            return false;
//        }

        System.out.println("Grafo CONexo!");
        return true;
    }

    @Override
    public String toString() {
        return """
                direcionado = %s,
                ordem = %d,
                tamanho = %d,
                vertices = %s,
                arestas = %s,
                graus = %s,
                adjacencias = %s,
                adjacentes = %s
                }""".formatted(
                eDirigido ? "sim" : "não",
                ordem,
                tamanho,
                vertices,
                arestas,
                exibeGrausDosVertices(),
                exibeAdjacencias(),
                exibeAdjacentes());
    }
}

