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

import java.util.List;
import net.minecraft.profiler.Profiler;

public class ControlPackProfilerProxy extends Profiler
{
	public Profiler wrapped;
	
	public ControlPackProfilerProxy(Profiler wrapped) {
		this.wrapped = wrapped;
		super.profilingEnabled = wrapped.profilingEnabled;
	}

    @Override
    public void clearProfiling()
    {
		this.wrapped.profilingEnabled = super.profilingEnabled;
		this.wrapped.clearProfiling();
    }

    @Override
    public void startSection(String par1Str)
    {
		this.wrapped.profilingEnabled = super.profilingEnabled;
		mod_controlpack_checkForRender(par1Str);
	    this.wrapped.startSection(par1Str);
    }
	
	private void mod_controlpack_checkForRender(String par1Str) {
		if (par1Str != null && par1Str.equals("gameRenderer") && ControlPackMain.instance != null) {
//System.out.println("ControlPack: setupRenderHook");
			ControlPackMain.instance.setupRenderHook();
		}
		if (par1Str != null && par1Str.equals("lightTex") && ControlPackMain.instance != null) {
			// i dont understand it, but this updateCameraAngle hook only works when its from INSIDE entityRenderer.updateCameraAndRender.
			// even though the setupRenderhook happens right before that! The problem is the Mouse.getDX() check always returns 0. Oddly.
			// This works fine but it's more dependant on the profiler hooks, but that's ok I guess.... 
			ControlPackMain.instance.updateCameraAngle();
		}
	}

    @Override
    public void endSection()
    {
		this.wrapped.profilingEnabled = super.profilingEnabled;
		this.wrapped.endSection();
    }

    @Override
    public List<Result> getProfilingData(String par1Str)
    {
		this.wrapped.profilingEnabled = super.profilingEnabled;
		return this.wrapped.getProfilingData(par1Str);
    }

    @Override
    public void endStartSection(String par1Str)
    {
		this.wrapped.profilingEnabled = super.profilingEnabled;
		mod_controlpack_checkForRender(par1Str);
		this.wrapped.endStartSection(par1Str);
    }

	@Override
    public String getNameOfLastSection()
    {
		this.wrapped.profilingEnabled = super.profilingEnabled;
		return this.wrapped.getNameOfLastSection();
    }
}
