/* Copyright (c) 2011-2016 Julian Uy, Dave Reed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

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
