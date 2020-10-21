package com.opencloud.config.annotations;

import java.lang.annotation.Documented;

@InterfaceAudience.Public
@InterfaceStability.Evolving
public class InterfaceStability {
    /**
     * 稳定
     */
    @Documented
    public @interface Stable {
    }

    /**
     * 迭代开发
     */
    @Documented
    public @interface Evolving {
    }

    /**
     * 不稳定
     */
    @Documented
    public @interface Unstable {
    }

}