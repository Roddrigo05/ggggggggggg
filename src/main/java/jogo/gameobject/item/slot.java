package jogo.gameobject.item;

public class slot {
    private byte voxelID;
    private int quantidade;

    public slot(byte voxelID, int quantidade) {
        this.voxelID = voxelID;
        this.quantidade = quantidade;
    }

    public byte getVoxelID() {
        return voxelID;
    }

    public void setVoxelID(byte voxelID) {
        this.voxelID = voxelID;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean vazio() {
        return voxelID == -1 || quantidade <= 0;
    }

    public boolean adicionar(byte voxelID, int quantidade) {
        if (vazio()) {
            this.voxelID = voxelID;
            this.quantidade = quantidade;
            return true;
        }

        if (this.voxelID == voxelID && this.quantidade < 64) {
            this.quantidade += quantidade;
            return true;
        }

        return false;
    }

    public boolean remover(int quantidade) {
        if (vazio()) return false;

        this.quantidade -= quantidade;
        if (this.quantidade <= 0) {
            this.quantidade = 0;
            this.voxelID = -1;
        }
        return true;
    }
}
