package com.tanglei.springbootstudydemo.entity;


public class OperationLogPersistenceDTE {
    private String id; //UUID
    private String title; //调用接口描述--接口名称/路径
    private String logType; //日志类型--info/error
    private String className; //当前类名称
    private String method; //操作方法名
    private String reqContent; //请求对象json字符串
    private String resContent;  //响应对象json字符串
    private String serverIp; //服务端ip
    private String clientIp; //客户端ip
    private String source; //请求来源
    private String reservedText; //保留字段

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getReqContent() {
        return reqContent;
    }

    public void setReqContent(String reqContent) {
        this.reqContent = reqContent;
    }

    public String getResContent() {
        return resContent;
    }

    public void setResContent(String resContent) {
        this.resContent = resContent;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReservedText() {
        return reservedText;
    }

    public void setReservedText(String reservedText) {
        this.reservedText = reservedText;
    }

    @Override
    public String toString() {
        return "OperationLogPersistenceDTE{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", logType='" + logType + '\'' +
                ", className='" + className + '\'' +
                ", method='" + method + '\'' +
                ", reqContent='" + reqContent + '\'' +
                ", resContent='" + resContent + '\'' +
                ", serverIp='" + serverIp + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", source='" + source + '\'' +
                ", reservedText='" + reservedText + '\'' +
                '}';
    }
}
