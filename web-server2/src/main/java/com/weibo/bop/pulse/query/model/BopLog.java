package main.java.com.weibo.bop.pulse.query.model;


import lombok.Data;

import javax.persistence.Id;
import java.util.List;

/**
 * @author wenqian9
 */
@Data
public class BopLog {

    @Id
    private int id;
    private String name  ;
    private String email  ;
    private String userid  ;
    private String ip;
    private String operation;
    private String createtime;
    private String remark;
    private String source;
    private String page;
    private String accessKey;
    private List userids;





}
