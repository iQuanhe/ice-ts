package main.java.com.weibo.bop.pulse.query.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weibo.bop.pulse.query.facade.ConfigService;
import com.weibo.bop.pulse.query.model.BopLog;
import com.weibo.bop.pulse.query.repo.mapper.QueryMapper;
import com.weibo.bop.pulse.query.utils.FormatUtils;
import com.weibo.common.pojo.Result;
import com.weibo.common.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RefreshScope
@Configuration
@Component
public class QueryController {

    private static final Logger logger = LoggerFactory.getLogger(QueryController.class);

    public static QueryController controller;

    public QueryController() {

    }

    @Autowired
    private MailService mailService;

    @Value("${sessionKey.jsessionid}")
    private String sessionKey;

    @Autowired
    private JedisClient jedisClient;
    @Autowired
    RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        controller = this;
    }

    private static String POST = "POST";
    private static String GET = "GET";

    @Autowired
    private QueryMapper queryMapper;

    @Autowired
    private ConfigService configService;

    private static final int LENGTH = 500;

    /**
     * 查询
     *
     * @param request
     * @return
     */
    @GetMapping("/query/**")
    public Result query(HttpServletRequest request) {

        String url = request.getRequestURI();
        logger.info("------url: " + url);

        JSONObject source = getSource();
        String[] split = source.getString("index").split(",");

        for (String item : split) {
            String _url = source.getString("url-" + item);
            if (url.equals("/query" + _url)) {
                Map<String, String[]> map = getResquestParam(request);
                String sql = getSqlString(request, source, item, map);
                String param = source.getString("param-" + item);
                String operation = source.getString("operat-name-" + item);
                String remark = source.getString("param-name-" + item);

                sql = buildSql(request, sql, param, remark, operation, map);
                List list = queryMapper.listAll(sql);
                if ("/listPrincipalsByChannel".equals(_url)) {
                    return Result.ok(listPrincipalsByChannel(list));
                } else {
                    return Result.ok(list);
                }
            }
        }
        return Result.ok();
    }

    /**
     * 获取sql
     *
     * @param request
     * @param source
     * @param item
     * @return
     */
    private String getSqlString(HttpServletRequest request, JSONObject source, String item, Map<String, String[]> map) {
        String sql = "", dim = "";
        if (request.getRequestURI().contains("/query/")) {
            dim = source.getString("query-dim-" + item);
        } else if (request.getRequestURI().contains("/export/")) {
            dim = source.getString("export-dim-" + item);
        }
        if (map.containsKey("dim") && StringUtil.isNotNull(dim)) {
            String[] dims = dim.split(",");
            for (String dimItem : dims) {
                String p = null;
                if (GET.equals(request.getMethod())) {
                    p = map.get("dim")[0];
                } else if (POST.equals(request.getMethod())) {
                    p = String.valueOf(map.get("dim"));
                }
                if (dimItem.equalsIgnoreCase(p)) {
                    if (request.getRequestURI().contains("/query/")) {
                        sql = source.getString("sql-" + item + "-" + dimItem);
                    } else if (request.getRequestURI().contains("/export/")) {
                        sql = source.getString("export-sql-" + item + "-" + dimItem);
                    }
                }
            }
        } else {
            if (request.getRequestURI().contains("/query/")) {
                sql = source.getString("sql-" + item);
            } else if (request.getRequestURI().contains("/export/")) {
                sql = source.getString("export-sql-" + item);
            }
        }
        return sql;
    }

    /**
     * 构建季度sql
     *
     * @return
     */
    private Map useDefinedParams(HttpServletRequest request, String[] params) {
        Map result = new HashMap();
        List paramList = new ArrayList();
        if (request.getRequestURI().contains("/listPrincipalsByQuarter")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal1 = Calendar.getInstance();
            cal1.add(Calendar.DAY_OF_MONTH, -1);//当前日期-1
            Date dt1 = cal1.getTime();
            String currentEnd = sdf.format(dt1);
            String currentStart = FinanceDateUtils.startDayOfQuarter(currentEnd);

            cal1.add(Calendar.MONTH, -3); //日期-3个月
            Date dt2 = cal1.getTime();
            String reMonth = sdf.format(dt2);
            String lastStart = FinanceDateUtils.startDayOfQuarter(reMonth);
            long days = FinanceDateUtils.daysOfTwo(currentStart, currentEnd);
            long currentDays = FinanceDateUtils.daysOfTwo(currentStart, FinanceDateUtils.endDayOfQuarter(currentEnd));
            long lastDays = FinanceDateUtils.daysOfLastQuarter(currentEnd);
            int allDays = Math.round(days * lastDays / currentDays);

            try {
                Date date = sdf.parse(lastStart);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, allDays);
                Date dt3 = calendar.getTime();
                String endLast = sdf.format(dt3);

                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(new Date());
                cal2.add(Calendar.DAY_OF_MONTH, -1);//当前日期-1
                cal2.add(Calendar.YEAR, -1);
                Date dt4 = cal2.getTime();
                String sameEnd = sdf.format(dt4);
                String sameStart = FinanceDateUtils.startDayOfQuarter(sameEnd);

                //自定义参数顺序需要与配置文件参数顺序一致
                paramList.add(currentStart);
                paramList.add(currentEnd);
                paramList.add(lastStart);
                paramList.add(endLast);
                paramList.add(sameStart);
                paramList.add(sameEnd);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //存放配置和自定义参数对应关系
        for (int i = 0; i < params.length; i++) {
            result.put(params[i], paramList.get(i));
        }
        return result;
    }

    /**
     * 导出数据
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/export/**")
    public Result download(HttpServletRequest request, HttpServletResponse response) {
        JSONObject source = getSource();
        String url = request.getRequestURI();
        logger.info("------url: " + url);
        String[] split = source.getString("export").split(",");
        for (String item : split) {

            String _url = source.getString("export-url-" + item);
            if (url.equals("/export" + _url)) {
                Map<String, String[]> map = getResquestParam(request);
                String sql = getSqlString(request, source, item, map);
                String param = source.getString("export-param-" + item);
                String remark = source.getString("export-param-name-" + item);
                String operation = source.getString("export-name-" + item);
                sql = buildSql(request, sql, param, remark, operation, map);
                List list = queryMapper.listAll(sql);
                checkDownload(request, list);
                String userAgent = request.getHeader("User-Agent");
                LinkedHashMap<String, Object> headerMap = new LinkedHashMap<>();
                String cols = source.getString("export-cols-" + item);
                String[] colArr = cols.split(";");
                for (int i = 0; i < colArr.length; i++) {
                    String[] colSplit = colArr[i].split(",");
                    headerMap.put(colSplit[0], colSplit[1]);
                }
                CsvUtils.csvFile(list, headerMap, response, "数据下载", userAgent);
            }
        }
        return Result.ok();
    }

    /**
     * 拼接sql
     *
     * @param request 请求内容
     * @param sql     配置sql
     * @param param   配置参数
     * @return
     */
    private String buildSql(HttpServletRequest request, String sql, String param, String remark, String operation, Map<String, String[]> map) {
        if (!StringUtils.isBlank(param)) {
            Map prmMap = new HashMap();
            String[] params = param.split(",");
            if (params.length > 1 && params[0].startsWith("¥")) {
                prmMap = useDefinedParams(request, params);
            }
            String p = null;
            for (String subItem : params) {
                if (subItem.startsWith("¥")) {
                    //需要自定义处理的参数
                    sql = sql.replace(subItem, prmMap.get(subItem).toString());
                } else {
                    //取请求参数
                    if (GET.equals(request.getMethod())) {
                        p = map.get(subItem.substring(1))[0];
                    } else if (POST.equals(request.getMethod())) {
                        p = String.valueOf(map.get(subItem.substring(1)));
                    }
                    //判断参数
                    if (StringUtils.isBlank(p)) {
                        p = "";
                    }
                    //sql
                    if (subItem.startsWith("$")) {
                        sql = sql.replace(subItem, "'" + p + "'");
                    } else if (subItem.startsWith("#")) {
                        sql = sql.replace(subItem, p);
                    }
                }

                logger.info("-------------sql:" + sql);
            }
        }
        //保存操作日志
        saveLog(request, operation, remark, param, map);
        return sql;
    }

    /**
     * 保存操作日志
     *
     * @param request
     * @param operation
     * @param remark
     * @param param
     * @param paraMap
     */
    private void saveLog(HttpServletRequest request, String operation, String remark, String param, Map<String, String[]> paraMap) {
        //保存日志，operation不能为空
        if (StringUtils.isNotBlank(operation)) {
            BopLog record = new BopLog();
            StringBuffer context = new StringBuffer();
            record.setOperation(operation);
            String[] remarkArr;
            String[] paraArr;
            if (StringUtils.isNotBlank(remark) && StringUtils.isNotBlank(param)) {
                remarkArr = remark.split(",");
                paraArr = param.split(",");
                for (int i = 0; i < paraArr.length; i++) {
                    context.append("|" + remarkArr[i] + ":");
                    context.append(String.valueOf(paraMap.get(paraArr[i].substring(1))));
                }
            }
            record.setRemark(StringUtils.isBlank(context) ? "" : context.toString());
            String sessionId = CookieUtils.getCookieValue(request, sessionKey);
            logger.info("sessionId:" + sessionId);

            //redis里获取登录人信息
            record.setIp(WebUtils.getIpAddr(request));
            record.setName(controller.jedisClient.get("ticket-" + sessionId));
            record.setEmail(controller.jedisClient.get("ticket-email-" + sessionId));
            record.setUserid(controller.jedisClient.get("ticket-uid-" + sessionId));
            record.setSource("2");
            controller.restTemplate.postForObject("http://kpi-managementlog/saveLog", record, Result.class);
        }
    }

    private Map<String, String[]> getResquestParam(HttpServletRequest request) {
        Map<String, String[]> map = new HashMap<>();
        try {
            if (POST.equals(request.getMethod())) {
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();
                String inputStr;
                //读取流
                while ((inputStr = streamReader.readLine()) != null) {
                    responseStrBuilder.append(inputStr);
                }
                //格式转换
                map = JSON.parseObject(responseStrBuilder.toString(), Map.class);
            } else if (GET.equals(request.getMethod())) {
                map = request.getParameterMap();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private void checkDownload(HttpServletRequest request, List list) {
        String sessionId = CookieUtils.getCookieValue(request, sessionKey);
        String staffNumber = controller.jedisClient.get("ticket-uid-" + sessionId);
        JSONObject source = getSource();
        List rets = queryMapper.listAll(source.getString("check-mail-sql").replace("#", staffNumber));
        if (rets.size() == 0) {
            if (list.size() > LENGTH) {
                String to = controller.jedisClient.get("ticket-femail-" + sessionId);
                mailService.sendSimpleMail(to, "【异常报警】下载数据超过500条",
                        staffNumber + "：您下载的数据量超过500条，请注意数据保密。");
            }
        }
    }

    /**
     * 获取配置内容
     *
     * @return
     */
    private JSONObject getSource() {
        JSONObject json = configService.getConfigClient();
        JSONArray array = json.getJSONArray("propertySources");
        return array.getJSONObject(0).getJSONObject("source");
    }

    /**
     * 运营收入分通路
     *
     * @param list
     * @return
     */
    private Map listPrincipalsByChannel(List<Map> list) {
        Map<String, List<Map>> map = new HashMap();
        Map result = new HashMap();
        //获取通路
        String sql = "select distinct dim_value channel from bop_ads_operating_consume_summary_80 where dim_type=2";
        List<Map> channelList = queryMapper.listAll(sql);

        if (channelList.size() > 0 && list.size() > 0) {
            for (int i = 0; i < channelList.size(); i++) {
                List<Map> mapList = new ArrayList<>();
                for (int j = 0; j < list.size(); j++) {
                    if (channelList.get(i).get("channel").toString().equalsIgnoreCase(list.get(j).get("dim").toString())) {
                        mapList.add(list.get(j));
                    }
                }
                map.put(channelList.get(i).get("channel").toString(), mapList);
            }
            //界面展示大写
            result = FormatUtils.rebuildMap(new String[]{"KA-", "EBS-"}, map);
        }
        return result;
    }
}
