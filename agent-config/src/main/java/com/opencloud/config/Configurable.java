package com.opencloud.config;

import com.opencloud.config.annotations.InterfaceAudience;
import com.opencloud.config.annotations.InterfaceStability;

@InterfaceAudience.Public
@InterfaceStability.Stable
public interface Configurable {

    void configure(Context context);

}
