package jogo.gameobject.item;

public class Inventario {
    private slot[] slots;


    public Inventario(int tamanho) {
        slots = new slot[tamanho];
        for (int i = 0; i < tamanho; i++) {
            slots[i] = new slot((byte) -1, 0);
        }
    }

    public void adicionarItem(byte voxelID, int quantidade) {
        for (slot s : slots) {
            if (s.getVoxelID() == voxelID && s.adicionar(voxelID, quantidade)) {
                return;
            }
        }

        for (slot s : slots) {
            if (s.vazio()) {
                s.adicionar(voxelID, quantidade);
                return;
            }
        }
    }

    public void removerItem(byte voxelID, int quantidade) {
        for (slot s : slots) {
            if (s.getVoxelID() == voxelID) {
                s.remover(quantidade);
                return;
            }
        }
    }

    public void setSlots(slot[] slots) {
        this.slots = slots;
    }

    public slot[] getSlots() {
        return slots;
    }

    public int getTamanho() {
        return slots.length;
    }
}
