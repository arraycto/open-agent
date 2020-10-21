package com.opencloud.agent.codec;

public final class ExecResponse {

    public static final String CMD_RESPONSE_ID = "CMD_RESPONSE_ID";

    public static RpcRequest EXEC;

    static {
        EXEC = new RpcRequest() {
        };
        EXEC.setRequestId(CMD_RESPONSE_ID);
    }

}
