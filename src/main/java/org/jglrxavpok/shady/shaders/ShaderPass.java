package org.jglrxavpok.shady.shaders;

import org.jglrxavpok.shady.ShadyResManager;

public abstract class ShaderPass
{

    protected IPassProvider provider;

    public ShaderPass(IPassProvider provider)
    {
        this.provider = provider;
    }

    public abstract String getName();

    public abstract String getProgram();

    public abstract void init();

    public abstract void registerVirtuals(ShadyResManager resManager);

    public IPassProvider getProvider()
    {
        return provider;
    }

}
