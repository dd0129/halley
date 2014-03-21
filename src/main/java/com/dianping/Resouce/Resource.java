package com.dianping.Resouce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hongdi.tang on 14-3-15.
 */
public class Resource{
    private Logger logger = LoggerFactory.getLogger(Resource.class);

    private String resourceName;
    private Integer limit;
    private Integer runningNum;

    public Resource(String resourceName, Integer limit, Integer runningNum) {
        this.resourceName = resourceName;
        this.limit = limit;
        this.runningNum = runningNum;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getRunningNum() {
        return runningNum;
    }

    public void setRunningNum(Integer runningNum) {
        this.runningNum = runningNum;
    }

    public void addRunningNum() {
        ++this.runningNum ;
    }

    public void minusRunningNum() {
        --this.runningNum ;
    }

    public Integer pushResource(){
        if(runningNum<limit){
            logger.info(resourceName.concat(" resouce equal" ).concat(String.valueOf(runningNum)));
            return ++runningNum;
        }else{
            logger.info(resourceName.concat(" resouce is full; limit :=" ).concat(String.valueOf(limit)));
            return limit;
        }
    }

    public Integer pullResouce(){
        return --runningNum;
    }
}
