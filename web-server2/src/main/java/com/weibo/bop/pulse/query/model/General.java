package main.java.com.weibo.bop.pulse.query.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class General implements Serializable {

    private static final long serialVersionUID = 3701895883605341223L;

    private Integer id;

    private String name;

    private String code;

    private String attr1;

    private String attr2;

    private String attr3;
}
