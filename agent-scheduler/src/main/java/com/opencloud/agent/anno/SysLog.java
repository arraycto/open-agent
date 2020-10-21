

package com.opencloud.agent.anno;

import java.lang.annotation.*;

/**
 * @author xielianjun
 * @date 2019-04-04 11:58
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}
