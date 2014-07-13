package controlpack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    public List<?> getProfilingData(String par1Str)
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
