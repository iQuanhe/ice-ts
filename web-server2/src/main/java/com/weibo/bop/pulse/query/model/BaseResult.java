package main.java.com.weibo.bop.pulse.query.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResult implements Serializable {

    private static final long serialVersionUID = 3701895883605341222L;

    private String dt;

    private String principal;

    private String total;

    private String dim;

    private String date;

    private String channel;
}
