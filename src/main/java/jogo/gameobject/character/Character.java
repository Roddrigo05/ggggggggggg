package jogo.gameobject.character;

import jogo.gameobject.GameObject;

public abstract class Character extends GameObject {

    protected Character(String name, int vida) {
        super(name);
    }



    public void levarDano(int dano) {
        health -= dano;
        if (health < 0) health = 0;
        System.out.println("Vida atual: " + health);
    }

    // Example state hooks students can extend
    private int health = 100;

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
}
