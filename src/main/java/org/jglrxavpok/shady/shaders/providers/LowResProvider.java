package org.jglrxavpok.shady.shaders.providers;

import org.jglrxavpok.shady.shaders.IPassProvider;
import org.jglrxavpok.shady.shaders.ShaderPass;
import org.jglrxavpok.shady.shaders.passes.LowResPass;

public class LowResProvider implements IPassProvider
{

    @Override
    public ShaderPass providePass()
    {
        return new LowResPass();
    }

}
