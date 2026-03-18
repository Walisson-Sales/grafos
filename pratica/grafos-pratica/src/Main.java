void main() {
    // Criando os objetos de vertices:
    Vertice vertice1 = new Vertice();
    Vertice vertice2 = new Vertice();

    // Atribuindo valores aos vertices:
    vertice1.setNome("V1");
    vertice1.setValor(10);

    vertice2.setNome("V2");
    vertice2.setValor(20);

    System.out.println("=========================================================================");
    System.out.println("Vertice 1:");
    System.out.println("Nome: " + vertice1.getNome() + ", Valor: " + vertice1.getValor());

    System.out.println("Vertice 2:");
    System.out.println("Nome: " + vertice2.getNome() + ", Valor: " + vertice2.getValor());
    // ==============================================================================

    // Criando os objetos de arestas:
    Aresta aresta1 = new Aresta();

    // Atribuindo valor às arestas:
    aresta1.setNome("A");
    aresta1.setVertice1(vertice1);
    aresta1.setVertice2(vertice2);

    System.out.println("=========================================================================");
    System.out.println("Nome: " + aresta1.getNome() + ", Vertice1: " + aresta1.getVertice1() + ", Vertice2: " +
            aresta1.getVertice2()); // Ver a parte do retorno dos vertices depois;

    // ==============================================================================

    // Criando o grafo:
    Grafo grafo = new Grafo();

    // Atribuindo valor dos vertices do grafo
    List<Vertice> vertices = new ArrayList<>();
    vertices.add(vertice1);
    vertices.add(vertice2);
    grafo.setVertices(vertices);

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
