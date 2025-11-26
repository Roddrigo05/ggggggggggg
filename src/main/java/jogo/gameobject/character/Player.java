package jogo.gameobject.character;

import jogo.gameobject.item.Inventario;

public class Player extends Character {
    private Inventario inventario;
    private int slotSelecionado = 0; // começa no slot 0

    public Player() {
        super("Player", 100);
        this.inventario = new Inventario(9);
    }


    public int mostrarvida(){
        return getHealth();
    }

    public boolean estavivo(){
        if(getHealth() > 0){
            return true;
        }
        else{
            return false;}
    }

    public void setinventario(Inventario inventario){
        this.inventario = inventario;
    }

    public Inventario getinventario(){
        return inventario;
    }

    public void adicionarItem(byte voxelID, int quantidade) {
        inventario.adicionarItem(voxelID, quantidade);
    }

    public void removerItem(byte voxelID, int quantidade) {
        inventario.removerItem(voxelID, quantidade);
    }



    public void setSlotSelecionado(int s) {
        if (s < 0 || s >= inventario.getTamanho()) return;
        this.slotSelecionado = s;
    }

    public int getSlotSelecionado() {
        return this.slotSelecionado;
    }


}
