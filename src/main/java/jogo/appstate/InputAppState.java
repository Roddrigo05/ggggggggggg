package jogo.appstate;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

public class InputAppState extends BaseAppState implements ActionListener, AnalogListener {

    private boolean forward, backward, left, right;
    private boolean sprint;
    private volatile boolean jumpRequested;
    private volatile boolean breakRequested;
    private volatile boolean toggleShadingRequested;
    private volatile boolean respawnRequested;
    private volatile boolean interactRequested;
    //
    private boolean[] slotRequested = new boolean[9];
    private boolean colocarrequested;

    private float mouseDX, mouseDY;
    private boolean mouseCaptured = true;
    //
    private boolean colocar;


    @Override
    protected void initialize(Application app) {
        var im = app.getInputManager();
        // Movement keys
        im.addMapping("MoveForward", new KeyTrigger(KeyInput.KEY_W));
        im.addMapping("MoveBackward", new KeyTrigger(KeyInput.KEY_S));
        im.addMapping("MoveLeft", new KeyTrigger(KeyInput.KEY_A));
        im.addMapping("MoveRight", new KeyTrigger(KeyInput.KEY_D));
        im.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        im.addMapping("Sprint", new KeyTrigger(KeyInput.KEY_LSHIFT));
        // Mouse look
        im.addMapping("MouseX+", new MouseAxisTrigger(MouseInput.AXIS_X, false));
        im.addMapping("MouseX-", new MouseAxisTrigger(MouseInput.AXIS_X, true));
        im.addMapping("MouseY+", new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        im.addMapping("MouseY-", new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        // Toggle capture (use TAB, ESC exits app by default)
        im.addMapping("ToggleMouse", new KeyTrigger(KeyInput.KEY_TAB));
        // Break voxel (left mouse)
        im.addMapping("Break", new MouseButtonTrigger(com.jme3.input.MouseInput.BUTTON_LEFT));
        // Toggle shading (L)
        im.addMapping("ToggleShading", new KeyTrigger(KeyInput.KEY_L));
        // Respawn (R)
        im.addMapping("Respawn", new KeyTrigger(KeyInput.KEY_R));
        // Interact (E)
        im.addMapping("Interact", new KeyTrigger(KeyInput.KEY_E));

        im.addListener(this, "MoveForward", "MoveBackward", "MoveLeft", "MoveRight", "Jump", "Sprint", "ToggleMouse", "Break", "ToggleShading", "Respawn", "Interact");
        im.addListener(this, "MouseX+", "MouseX-", "MouseY+", "MouseY-");

        // slot1
        im.addMapping("slot1", new KeyTrigger(KeyInput.KEY_1));
        im.addMapping("slot2", new KeyTrigger(KeyInput.KEY_2));
        im.addMapping("slot3", new KeyTrigger(KeyInput.KEY_3));
        im.addMapping("slot4", new KeyTrigger(KeyInput.KEY_4));
        im.addMapping("slot5", new KeyTrigger(KeyInput.KEY_5));
        im.addMapping("slot6", new KeyTrigger(KeyInput.KEY_6));
        im.addMapping("slot7", new KeyTrigger(KeyInput.KEY_7));
        im.addMapping("slot8", new KeyTrigger(KeyInput.KEY_8));
        im.addMapping("slot9", new KeyTrigger(KeyInput.KEY_9));
        im.addMapping("colocarbloco",  new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));

        im.addListener(this, "slot1","slot2","slot3","slot4","slot5","slot6","slot7","slot8","slot9","colocarbloco");
    }

    @Override
    protected void cleanup(Application app) {
        var im = app.getInputManager();
        im.deleteMapping("MoveForward");
        im.deleteMapping("MoveBackward");
        im.deleteMapping("MoveLeft");
        im.deleteMapping("MoveRight");
        im.deleteMapping("Jump");
        im.deleteMapping("Sprint");
        im.deleteMapping("MouseX+");
        im.deleteMapping("MouseX-");
        im.deleteMapping("MouseY+");
        im.deleteMapping("MouseY-");
        im.deleteMapping("ToggleMouse");
        im.deleteMapping("Break");
        im.deleteMapping("ToggleShading");
        im.deleteMapping("Respawn");
        im.deleteMapping("Interact");
        //
        im.deleteMapping("slot1");
        im.deleteMapping("slot2");
        im.deleteMapping("slot3");
        im.deleteMapping("slot4");
        im.deleteMapping("slot5");
        im.deleteMapping("slot6");
        im.deleteMapping("slot7");
        im.deleteMapping("slot8");
        im.deleteMapping("slot9");
        im.deleteMapping("colocarbloco");
        im.removeListener(this);
    }

    @Override
    protected void onEnable() {
        setMouseCaptured(true);
    }

    @Override
    protected void onDisable() { }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        switch (name) {
            case "MoveForward" -> forward = isPressed;
            case "MoveBackward" -> backward = isPressed;
            case "MoveLeft" -> left = isPressed;
            case "MoveRight" -> right = isPressed;
            case "Sprint" -> sprint = isPressed;
            case "Jump" -> {
                if (isPressed) jumpRequested = true;
            }
            case "ToggleMouse" -> {
                if (isPressed) setMouseCaptured(!mouseCaptured);
            }
            case "Break" -> {
                if (isPressed && mouseCaptured) breakRequested = true;
            }
            case "ToggleShading" -> {
                if (isPressed) toggleShadingRequested = true;
            }
            case "Respawn" -> {
                if (isPressed) respawnRequested = true;
            }
            case "Interact" -> {
                if (isPressed && mouseCaptured) interactRequested = true;
            }

            case "colocarbloco" ->{if (isPressed) colocarrequested = true;}
            case "slot1" -> { if (isPressed) slotRequested[0] = true; }
            case "slot2" -> { if (isPressed) slotRequested[1] = true; }
            case "slot3" -> { if (isPressed) slotRequested[2] = true; }
            case "slot4" -> { if (isPressed) slotRequested[3] = true; }
            case "slot5" -> { if (isPressed) slotRequested[4] = true; }
            case "slot6" -> { if (isPressed) slotRequested[5] = true; }
            case "slot7" -> { if (isPressed) slotRequested[6] = true; }
            case "slot8" -> { if (isPressed) slotRequested[7] = true; }
            case "slot9" -> { if (isPressed) slotRequested[8] = true; }
        }
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (!mouseCaptured) return;
        switch (name) {
            case "MouseX+" -> mouseDX += value;
            case "MouseX-" -> mouseDX -= value;
            case "MouseY+" -> mouseDY += value;
            case "MouseY-" -> mouseDY -= value;
        }
    }

    public Vector3f getMovementXZ() {
        float fb = (forward ? 1f : 0f) + (backward ? -1f : 0f);
        float lr = (right ? 1f : 0f) + (left ? -1f : 0f);
        return new Vector3f(lr, 0f, -fb); // -fb so forward maps to -Z in JME default
    }

    public boolean isSprinting() {
        return sprint;
    }

    public boolean consumeJumpRequested() {
        boolean jr = jumpRequested;
        jumpRequested = false;
        return jr;
    }

    public boolean colocarblocoRequested() {
        boolean a =  colocarrequested;
        colocarrequested = false;
        return a;
    }

    public boolean consumeBreakRequested() {
        boolean r = breakRequested;
        breakRequested = false;
        return r;
    }

    public boolean consumeToggleShadingRequested() {
        boolean r = toggleShadingRequested;
        toggleShadingRequested = false;
        return r;
    }

    public boolean consumeRespawnRequested() {
        boolean r = respawnRequested;
        respawnRequested = false;
        return r;
    }

    public boolean consumeInteractRequested() {
        boolean r = interactRequested;
        interactRequested = false;
        return r;
    }

    public int consumeSlotSelection() {
        for (int i = 0; i < slotRequested.length; i++) {
            if (slotRequested[i]) {
                slotRequested[i] = false;
                return i;
            }
        }
        return -1;
    }


    public Vector2f consumeMouseDelta() {
        Vector2f d = new Vector2f(mouseDX, mouseDY);
        mouseDX = 0f;
        mouseDY = 0f;
        return d;
    }

    public void setMouseCaptured(boolean captured) {
        this.mouseCaptured = captured;
        var im = getApplication().getInputManager();
        im.setCursorVisible(!captured);
        // Clear accumulated deltas when switching state
        mouseDX = 0f;
        mouseDY = 0f;
    }

    public boolean isMouseCaptured() {
        return mouseCaptured;
    }
}
