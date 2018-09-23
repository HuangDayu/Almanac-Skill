package com.yibuwulianwang.almanac.bot;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Almanac")
public class AlmanacAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlmanacAction() {
        super();
    }

    /**
     * 重写doPost方法，处理POST请求
     *
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 根据request创建Bot
        AlmanacBot bot = new AlmanacBot(request);
        // 打开签名验证
        bot.enableVerify();

        // 关闭签名验证
        // bot.disableVerify();

        try {
            // 调用bot的run方法
            String responseJson = bot.run();
            // 设置response的编码UTF-8
            response.setCharacterEncoding("UTF-8");
            // 返回response
            response.getWriter().append(responseJson);
        } catch (Exception e) {
            response.getWriter().append("{\"status\":1,\"msg\":\"\"}");
        }
    }
}
