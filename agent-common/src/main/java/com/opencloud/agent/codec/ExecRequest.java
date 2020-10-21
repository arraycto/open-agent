package com.opencloud.agent.codec;

public final class ExecRequest {

    public static final String CMD_REQUEST_ID = "CMD_REQUEST_ID";

    public static RpcRequest EXEC;

    static {
        EXEC = new RpcRequest() {
        };
        EXEC.setRequestId(CMD_REQUEST_ID);
    }

}
