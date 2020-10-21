package com.opencloud.agent.message;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class IpsResultInfo {
    private Integer id;
    private String label;
    private List<IpsResultInfo> children;
}
