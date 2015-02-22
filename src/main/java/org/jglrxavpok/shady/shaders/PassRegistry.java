package org.jglrxavpok.shady.shaders;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

public class PassRegistry
{

    private static Map<String, IPassProvider> providers = Maps.newHashMap();

    public static void register(String id, IPassProvider provider)
    {
        providers.put(id, provider);
    }

    public Collection<String> getAllIDs()
    {
        return providers.keySet();
    }

    public IPassProvider getFromID(String id)
    {
        return providers.get(id);
    }
}
