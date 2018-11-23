package org.jbit.news.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jbit.news.dao.TopicsDao;
import org.jbit.news.dao.impl.*;
import org.jbit.news.entity.Topics;
import org.jbit.news.service.TopicsService;
import org.jbit.news.service.impl.TopicsServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class TopicServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("UTF-8");
		String tname = request.getParameter("tname");// 获得主题
		String opr = request.getParameter("opr");
		
		TopicsService topicsService =new  TopicsServiceImpl();
		
		
		if ("list".equals(opr)) {
			List<Topics> list = topicsService.getAllTopics("");
			String newsJSON=JSON.toJSONString(list,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullNumberAsZero,SerializerFeature.WriteNullStringAsEmpty);
			out.print(newsJSON);
		} else if ("add".equals(opr)) {
			String status="";//记录执行结果
			String message="";//记录提示信息
			try {
				int result=topicsService.addTopic(tname);
				if (result==-1) {
					status="exist";
					message="当前主题已存在，请输入不同的主题！";
				}else {
					status="success";
					message="主题创建成功";
				}
				
				
			} catch (Exception e) {
				status="error";
				message="添加失败,请联系管理员!";
			}
			out.print("[{\"status\":\""+status+"\",\"message\":\""+message+"\"}]");

		} else if ("edit".equals(opr)) {
			String tid=request.getParameter("tid");
			Topics topics=new Topics();
			topics.setTid(Integer.parseInt(tid));
			topics.setTname(tname);
			String status;
			String message;
			try {
				int result=topicsService.updateTopic(topics);
				if(result==-1){
					status="exist";
					message="当前主题已存在，请输入不同的主题！";
				}else if (result==0) {
					status="error";
					message="未找到相关主题";
					
				}else {
					status="success";
					message="已成功更新主题";
					
				}
			} catch (Exception e) {
				status="error";
				message="更新失败,请联系管理员!";
			}
			out.print("[{\"status\":\""+status+"\",\"message\":\""+message+"\"}]");
			
			
			

		} else if ("del".equals(opr)) {
			/**
			 * 获得主题id 判断里面是否有文章 删除
			 */

			String tid = request.getParameter("tid");
			String status;
			String message;
			try {
				int result=topicsService.deleteTopics(Integer.parseInt(tid));
				if(result==-1){
					status="error";
					message="该主题下还有文章，不能删除!";
				}else if(result==0) {
					status="error";
					message="未找到相关主题!";
					
				}else{
					status="success";
					message="已成功删除主题";
				}
			} catch (Exception e) {
				status="error";
				message="删除失败,请联系管理员!";
			}
			out.print("[{\"status\":\""+status+"\",\"message\":\""+message+"\"}]");

		}else if("listHtml".equals(opr)){
			List<Topics> list = topicsService.getAllTopics("");
			request.setAttribute("list", list);
			request.getRequestDispatcher("newspages/showTopics.jsp").forward(request, response);
		}
	}
}
