// mod_controlpack

package ctrlpack;

import net.minecraft.client.gui.GuiButton;

public class GuiSmallButtonCP extends GuiButton {

    public GuiSmallButtonCP(int i, int j, int k, String s) {
        this(i, j, k, null, s);
    }

    public GuiSmallButtonCP(int i, int j, int k, int l, int i1, String s) {
        super(i, j, k, l, i1, s);
        option = null;
    }

    public GuiSmallButtonCP(int id, int x, int y, int width, int height, ControlPackEnumOptions option, String s) {
        super(id, x, y, width, height, s);
        this.option = option;
    }
    
    public GuiSmallButtonCP(int i, int j, int k, ControlPackEnumOptions option, String s) {
        super(i, j, k, 150, 20, s);
        this.option = option;
    }

    public ControlPackEnumOptions getOption() {
        return option;
    }

    private final ControlPackEnumOptions option;
}
