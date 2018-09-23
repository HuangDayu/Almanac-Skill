package com.yibuwulianwang.almanac.bot;

import com.baidu.dueros.bot.BaseBot;
import com.baidu.dueros.data.request.IntentRequest;
import com.baidu.dueros.data.request.LaunchRequest;
import com.baidu.dueros.data.request.SessionEndedRequest;
import com.baidu.dueros.data.response.OutputSpeech;
import com.baidu.dueros.data.response.Reprompt;
import com.baidu.dueros.data.response.card.TextCard;
import com.baidu.dueros.model.Response;
import com.yibuwulianwang.almanac.bot.CalendarTime;
import com.yibuwulianwang.almanac.bot.DataMapList;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;

public class AlmanacBot extends BaseBot{

    /**
     * 重写BaseBot构造方法
     *
     * @param request
     *            servlet Request作为参数
     * @throws IOException
     *             抛出异常
     */
    public AlmanacBot(HttpServletRequest request) throws IOException {
        super(request);
    }

    /**
     * 重写BaseBot构造方法
     *
     * @param request
     *            Request字符串
     * @throws IOException
     *             抛出异常
     */
    public AlmanacBot(String request) throws IOException {
        super(request);
    }

    /**
     * 重写onLaunch方法，处理onLaunch对话事件
     *
     * @param launchRequest
     *            LaunchRequest请求体
     * @see com.baidu.dueros.bot.BaseBot#onLaunch(com.baidu.dueros.data.request.LaunchRequest)
     */
    @Override
    protected Response onLaunch(LaunchRequest launchRequest) {
        String text="万年历为您服务，您只需要指定的时间和位置信息，便可查询星期，年号，农历，天干，地支，生辰八字，" +
                "伊斯兰历，儒略日，黄帝纪年，生肖，最近节气，节假日，经度，纬度，时区，附近港口，昼长时间，夜长时间，" +
                "天亮时间，日出时间，中天时间，日落时间，天黑时间，月出时间，月中时间，月落时间，月相，农历月天数，" +
                "是否大月，是否闰月，是否闰年，星座和指定节气的时间。";
        String reText="万年历为您服务！";
        // 新建文本卡片
        TextCard textCard = new TextCard(reText);
        // 设置链接地址
        textCard.setUrl("www:....");
        // 设置链接内容
        textCard.setAnchorText("setAnchorText");
        // 添加引导话术
        textCard.addCueWord("欢迎进入");

        // 新建返回的语音内容
        OutputSpeech outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, reText);

        // 构造返回的Response
        Response response = new Response(outputSpeech, textCard);

        return response;
    }

    /**
     * 重写onInent方法，处理onInent对话事件
     *
     * @param intentRequest
     *            IntentRequest请求体
     * @see com.baidu.dueros.bot.BaseBot#onInent(com.baidu.dueros.data.request.IntentRequest)
     */
    @Override
    protected Response onInent(IntentRequest intentRequest) {

        // 判断NLU解析的意图名称是否匹配 inquiry
        if ("inquiry_Almanac".equals(intentRequest.getIntentName())) {
            // 判断NLU解析解析后是否存在这个槽位
            if (getSlot("znwnl_data") == null) {
                // 询问月薪槽位monthlysalary
                ask("znwnl_data");
                return askData();
            }else {
                // 具体计算方法
                return computeData();
            }
        }

        return null;
    }

    /**
     * 重写onSessionEnded事件，处理onSessionEnded对话事件
     *
     * @param sessionEndedRequest
     *            SessionEndedRequest请求体
     * @see com.baidu.dueros.bot.BaseBot#onSessionEnded(com.baidu.dueros.data.request.SessionEndedRequest)
     */
    @Override
    protected Response onSessionEnded(SessionEndedRequest sessionEndedRequest) {
        String text="感谢使用万年历技能服务，欢迎再次使用。";
        // 构造TextCard
        TextCard textCard = new TextCard(text);
        textCard.setAnchorText("setAnchorText");
        textCard.addCueWord("欢迎再次使用");

        // 构造OutputSpeech
        OutputSpeech outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, text);

        // 构造Response
        Response response = new Response(outputSpeech, textCard);

        return response;
    }

    /**
     * 询问城市信息
     *
     * @return Response 返回Response
     */
    private Response askLocation() {

        TextCard textCard = new TextCard("您所在的城市是哪里呢?");
        textCard.setUrl("www:......");
        textCard.setAnchorText("setAnchorText");
        textCard.addCueWord("您所在的城市是哪里呢?");

        setSessionAttribute("key_1", "value_1");
        setSessionAttribute("key_2", "value_2");

        OutputSpeech outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, "您所在的城市是哪里呢?");

        Reprompt reprompt = new Reprompt(outputSpeech);

        Response response = new Response(outputSpeech, textCard, reprompt);

        return response;
    }

    /**
     * 询问月薪
     *
     * @return Response 返回Response
     */
    private Response askData() {
        String reText="您需要查询什么数据呢？";
        TextCard textCard = new TextCard(reText);
        textCard.setUrl("www:......");
        textCard.setAnchorText("链接文本");
        textCard.addCueWord("您的税前工资是多少呢?");

        // 设置会话信息
        setSessionAttribute("key_1", "value_1");
        setSessionAttribute("key_2", "value_2");

        OutputSpeech outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, reText);

        // 构造reprompt
        Reprompt reprompt = new Reprompt(outputSpeech);

        Response response = new Response(outputSpeech, textCard, reprompt);

        return response;
    }

    /**
     * 询问个税种类
     *
     * @return Response 返回Response
     */
    private Response askComputeType() {
        TextCard textCard = new TextCard("请选择您要查询的种类");
        textCard.setAnchorText("setAnchorText");
        textCard.addCueWord("请选择您要查询的种类");

        setSessionAttribute("key_1", "value_1");
        setSessionAttribute("key_2", "value_2");

        OutputSpeech outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, "请选择您要查询的种类");

        Reprompt reprompt = new Reprompt(outputSpeech);

        Response response = new Response(outputSpeech, textCard, reprompt);

        return response;
    }

    /**
     * 计算个税
     *
     * @return Response
     */
    private Response computeData() {

        CalendarTime calendarTime = new CalendarTime();
        DataMapList dataMapList = new DataMapList();

        String reText="";
        // 获取多轮槽位值：月薪值、城市信息、查询种类
        String getData = getSlot("znwnl_data");

//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);

        String[] key_DayTimeType = { "日期", "时间", "星期", "地点", "经度", "纬度", "时区", "昼长", "夜长", "天亮", "日出", "中天", "日落", "天黑",
                "月出", "月中", "月落", "港口", "儒略日", "干支年", "干支月", "干支日", "干支时", "黄帝纪年", "生肖", "农历年", "农历月", "农历日", "月天数",
                "大月否", "闰月否", "闰年否", "回历年", "回历月", "回历日", "星座", "节气","天干","地支","八字","黄历","伊斯兰历","年号","节假日","月相","农历",
                "立春","雨水","惊蛰","春分","清明","谷雨","立夏","小满","芒种","夏至","小暑","大暑","立秋","处暑","白露","秋分","寒露","霜降","立冬","小雪","大雪","冬至","小寒","大寒"};

        dataMapList.addDataMapList("广东省", "徐闻", calendarTime);

        dataMapList.printDataMapList(key_DayTimeType);

        if(dataMapList.returnDataVolue(getData) != ""){
            reText=getData+"是"+dataMapList.returnDataVolue(getData);
        }else{
            reText=getData;
        }

        TextCard textCard = new TextCard(reText);
        textCard.setAnchorText("setAnchorText");
        textCard.addCueWord("查询成功");

        setSessionAttribute("key_1", "value_1");
        setSessionAttribute("key_2", "value_2");

        OutputSpeech outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, reText);

        Reprompt reprompt = new Reprompt(outputSpeech);

        // 主动结束会话
        //this.endDialog();

        Response response = new Response(outputSpeech, textCard, reprompt);

        return response;
    }
}
