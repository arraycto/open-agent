package com.opencloud.agent.executor;

public enum ExecutorArgs {

    EID("eid", "executeId");

    private String argName;

    private String valueName;

    ExecutorArgs(String argName, String valueName) {
        this.argName = argName;
        this.valueName = valueName;
    }

    public String getArgName() {
        return argName;
    }

    public void setArgName(String argName) {
        this.argName = argName;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }
}
