package org.jglrxavpok.shady.shaders;

import org.jglrxavpok.shady.ShadyResManager;

public abstract class ShaderPass
{

    public ShaderPass()
    {
        ;
    }

    public abstract String getProgram();

    public abstract void init();

    public abstract void registerVirtuals(ShadyResManager resManager);

}
