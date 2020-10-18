package com.jackluan.bigflag.common.base;

import lombok.Data;

import java.util.Date;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/1 17:00
 */
@Data
public class BaseDo {

    private String isDelete;

    private Date createDate;

    private Date modifyDate;

    private Integer start;

    private Integer limit;
}
