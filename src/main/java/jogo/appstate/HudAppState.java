package jogo.appstate;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;
import jogo.gameobject.character.Player;
import jogo.gameobject.item.Inventario;
import jogo.gameobject.item.slot;
import jogo.voxel.VoxelBlockType;
import jogo.voxel.TextureBlockType;

public class HudAppState extends BaseAppState {

    private final Node guiNode;
    private final AssetManager assetManager;
    private final WorldAppState world;
    private BitmapText crosshair;
    private BitmapText textoVida;
    private Picture[] imagemslot;      // fundo dos slots
    private Picture[] imagemItem;      // textura dos blocos por cima
    private BitmapText[] slotquantidade;
    private float tempoAtualiza = 0f;
    private Player player;

    public HudAppState(Node guiNode, AssetManager assetManager, WorldAppState world) {
        this.guiNode = guiNode;
        this.assetManager = assetManager;
        this.world = world;
    }

    @Override
    protected void initialize(Application app) {
        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");

        // --- CROSSHAIR ---
        crosshair = new BitmapText(font, false);
        crosshair.setText("+");
        crosshair.setSize(font.getCharSet().getRenderedSize() * 2f);
        guiNode.attachChild(crosshair);
        centerCrosshair();

        // --- VIDA ---
        textoVida = new BitmapText(font, false);
        SimpleApplication sapp = (SimpleApplication) app;
        textoVida.setSize(font.getCharSet().getRenderedSize());
        textoVida.setText("Vida: ");
        textoVida.setLocalTranslation(10, sapp.getCamera().getHeight() - 10, 0);
        guiNode.attachChild(textoVida);

        imagemslot = new Picture[9];
        imagemItem = new Picture[9];
        slotquantidade = new BitmapText[9];

        for (int i = 0; i < 9; i++) {
            float x = 300 + i * 70;
            float y = 20;

            // Fundo do slot (quadrado base)
            Picture slotFundo = new Picture("slotFundo" + i);
            slotFundo.setImage(assetManager, "Textures/quadrado.png", true);
            slotFundo.setWidth(64);
            slotFundo.setHeight(64);
            slotFundo.setLocalTranslation(x, y, 0);
            guiNode.attachChild(slotFundo);
            imagemslot[i] = slotFundo;

            // Textura do item (por cima)
            Picture itemPic = new Picture("item" + i);
            itemPic.setWidth(48);
            itemPic.setHeight(48);
            itemPic.setLocalTranslation(x + 8, y + 8, 1); // centralizado no quadrado
            itemPic.setImage(assetManager, "Textures/quadrado.png", true); // começa com o quadrado padrão
            guiNode.attachChild(itemPic);
            imagemItem[i] = itemPic;

            // Texto da quantidade
            BitmapText quantidade = new BitmapText(font, false);
            quantidade.setSize(font.getCharSet().getRenderedSize());
            quantidade.setLocalTranslation(x + 46, y + 15, 2);
            quantidade.setText("");
            guiNode.attachChild(quantidade);
            slotquantidade[i] = quantidade;
        }
    }

    @Override
    public void update(float tpf) {
        centerCrosshair();

        if (player == null) return;

        tempoAtualiza += tpf;
        if (tempoAtualiza < 0.2f) return;
        tempoAtualiza = 0f;

        // Atualiza a vida do jogador
        textoVida.setText("Vida: " + player.getHealth());

        Inventario inv = player.getinventario();
        if (inv == null) return;

        slot[] slots = inv.getSlots();

        int selecionado = player.getSlotSelecionado();

        for (int i = 0; i < slots.length; i++) {
            slot s = slots[i];

            // Fundo do slot
            if (i == selecionado) {
                imagemslot[i].setImage(assetManager, "Textures/slot_selected.jpeg", true);
            } else {
                imagemslot[i].setImage(assetManager, "Textures/quadrado.png", true);
            }

            // Conteúdo do slot
            if (s.vazio()) {
                imagemItem[i].setImage(assetManager, "Textures/quadrado.png", true);
                slotquantidade[i].setText("");
            } else {
                String textura = getCaminhoTextura(s.getVoxelID());

                if (textura == null || textura.isEmpty()) {
                    imagemItem[i].setImage(assetManager, "Textures/quadrado.png", true);
                } else {
                    imagemItem[i].setImage(assetManager, textura, true);
                }

                slotquantidade[i].setText(String.valueOf(s.getQuantidade()));
            }
        }
    }



    private void centerCrosshair() {
        SimpleApplication sapp = (SimpleApplication) getApplication();
        int w = sapp.getCamera().getWidth();
        int h = sapp.getCamera().getHeight();
        float x = (w - crosshair.getLineWidth()) / 2f;
        float y = (h + crosshair.getLineHeight()) / 2f;
        crosshair.setLocalTranslation(x, y, 0);
    }

    private String getCaminhoTextura(byte voxelID) {
        VoxelBlockType voxelBlockType = world.getVoxelWorld().getPalette().get(voxelID);
        if (voxelBlockType instanceof TextureBlockType texturedBlock) {
            return texturedBlock.getTextureName();
        }
        return "";
    }

    @Override
    protected void cleanup(Application app) {
        if (crosshair != null) crosshair.removeFromParent();
    }

    @Override
    protected void onEnable() { }

    @Override
    protected void onDisable() { }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
