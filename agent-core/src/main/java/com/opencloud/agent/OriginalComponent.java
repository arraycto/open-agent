package com.opencloud.agent;

import com.opencloud.config.annotations.InterfaceAudience;
import com.opencloud.config.annotations.InterfaceStability;

@InterfaceAudience.Public
@InterfaceStability.Stable
public interface OriginalComponent {

    String getName();

    void setName(String name);

    String getType();

    void setType(String type);
}
