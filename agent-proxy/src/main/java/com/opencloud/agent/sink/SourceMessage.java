package com.opencloud.agent.sink;

import com.opencloud.agent.executor.ExecutorFromProxy;
import lombok.Data;

/**
 * @author xielianjun
 */
@Data
public class SourceMessage {
    private String channelHost;
    private Integer channelPort;
    private ExecutorFromProxy executorFromProxy;
}
