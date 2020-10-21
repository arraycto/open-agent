package com.opencloud.agent.service;

/**
 * AsyncJobService
 *
 * @author gumizy
 * @date 2019-11-06
 */
public interface AsyncJobService {

    /**
     * 异步调用发送容量管理报告方法
     * @param params
     */
    void capacityReport(String params);

    /**
     * 异步调用 每天更新GPU容量及设备管理报表（静态数据）
     * @param params
     */
    void hpcGenerateClusterCalculationNodeStatic(String params);

    /**
     * 异步调用 每天更新存储设备管理报表（静态数据）
     * @param params
     */
    void hpcGenerateClusterStorageNodeStatic(String params);

    /**
     * 异步调用 每天更新集群分区计算节点信息
     * @param params
     */
    void hpcSyncPartitionIp(String params);

    /**
     * 异步调用 每月-各部门集群GPU使用信息报表（静态数据）
     * @param params
     */
    void generateClusterReportCapacityGpuUserMonthlyStaticEveryMonth(String params);

    /**
     * 异步调用 每月 各部门集群存储使用信息报表（静态数据）
     * @param params
     */
    void generateClusterReportCapacityStorageUserMonthlyStaticEveryMonth(String params);

    /**
     * 异步调用 每天更新集群分区计算节点 GPU信息
     * @param params
     */
    void hpcSyncPartitionIpGpu(String params);

    /**
     * 异步调用 每天更新集群分区管理员信息表 中文名信息
     * @param params
     */
    void hpcSyncPartitionAdminsUsernameCn(String params);

    /**
     * 异步调用 每天更新 研究GPU设备补充信息表
     * @param params
     */
    void hpcSyncGpuAssetAdditionalInfo(String params);

    /**
     * 异步调用 定时将sys_user表中所有的用户的部门信息进行刷新
     * @param params
     */
    void syncSysUserDepartmentInfo(String params);

    /**
     * 异步调用 大数据续期提示 Mail
     * @param params
     */
    void bigdataRenewWarn(String params);


}
