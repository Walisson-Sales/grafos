package org.example;

public class Vertice {
    private String nome;
    //private int valor;

    // Nõa trabalho mais com valor, só nome.

    // Construtor
    public Vertice(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    };
    public void setNome(String nome) {
        this.nome = nome;
    };

    //Valor:
    //public int getValor() {
    //    return valor;
    //}

    //public void setValor(int valor) {
    //    this.valor = valor;
    //}

    @Override
    public String toString() {
        return this.nome;
    }
}
