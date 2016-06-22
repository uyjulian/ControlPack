/* Copyright (c) 2014, Julian Uy
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
