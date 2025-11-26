package jogo.gameobject.item;

import jogo.gameobject.GameObject;

public abstract class Item extends GameObject {
    private tipo tipo_arma;
    private int eficiencia;
    private int durabilidade;

    public enum tipo  {
        picareta,
        pá,
        enxada,
        espada
    }



    protected Item(String name, tipo tipo_arma, int eficiencia, int durabilidade) {
        super(name);
        this.tipo_arma = null;
        this.eficiencia = eficiencia;
        this.durabilidade = durabilidade;
    }

    public tipo getTipo_arma() {
        return tipo_arma;
    }

    public void setTipo_arma(tipo tipo_arma) {
        this.tipo_arma = tipo_arma;
    }

    public int getEficiencia() {
        return eficiencia;
    }

    public void setEficiencia(int eficiencia) {
        this.eficiencia = eficiencia;
    }

    public int getDurabilidade() {
        return durabilidade;
    }

    public void setDurabilidade(int durabilidade) {
        this.durabilidade = durabilidade;
    }

    public void onInteract() {
        // Hook for interaction logic (engine will route interactions)
    }
}
