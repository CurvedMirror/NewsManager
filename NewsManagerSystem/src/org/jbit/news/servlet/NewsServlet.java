package org.jbit.news.servlet;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jbit.news.service.impl.*;
import org.jbit.news.service.*;
import org.jbit.news.dao.*;
import org.jbit.news.entity.*;
import org.jbit.news.dao.impl.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.text.SimpleDateFormat;

public class NewsServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		response.setContentType("text/html;charset=UTF-8");
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		TopicsService topicsService=new TopicsServiceImpl();
		CommentsService commentsService=new CommentsServiceImpl();
		NewsService newsService = new NewsServiceImpl();//新闻业务类
		
		String opr = request.getParameter("opr");
		
		if ("topicLatest".equals(opr)) {//初始化首页侧边栏和主题列表
			Map<Integer, Integer> topics=new HashMap<Integer, Integer>();
			topics.put(1, 5);
			topics.put(2, 5);
			topics.put(5, 5);
			List<List<News>> latests=newsService.findLatestNewsByTid(topics);	// 查询最新消息
			List<Topics> list=topicsService.getAllTopics("");						//查询所有主题
			request.setAttribute("list1", latests.get(0));						//左侧国内新闻
			request.setAttribute("list2", latests.get(1));						//左侧国际新闻
			request.setAttribute("list3", latests.get(2));						//左侧娱乐新闻
			request.setAttribute("list", list);									//所有的主题
			request.getRequestDispatcher("index.jsp").forward(request, response);
			
		}else if ("topicNews".equals(opr)) {	//分页查询新闻
			String tid=request.getParameter("tid");
			String pageIndex=request.getParameter("pageIndex");
			Page pageObj=new Page();
			if (pageIndex == null) {
				pageIndex = "1";
			}
			pageObj.setCurrPageNo(Integer.parseInt(pageIndex));
			if (tid == null || (tid = tid.trim()).length() == 0) 
				newsService.findPageNews(null, pageObj);
			else{
				newsService.findPageNews(Integer.valueOf(tid), pageObj);
			}
			//使用FastJSON将Page对象序列化成 JSON字符串
			String newsJSON=JSON.toJSONStringWithDateFormat(pageObj, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			//向客户端发回响应数据
			out.print("[{\"tid\":\""+tid+"\"},"+newsJSON+"]");
			
		}
		
		else if ("newsRead".equals(opr)) {//news_read.jsp新闻
			int nid = Integer.parseInt(request.getParameter("nid"));
			News news = newsService.getNewByNid(nid);//新闻
			session.setAttribute("myNews", news);
			List<Comments> list = commentsService.getAllComments(nid);
			session.setAttribute("commentsList", list);
			request.getRequestDispatcher("newspages/news_read.jsp")
					.forward(request, response);

		} else if ("addComment".equals(opr)) {//news_read.jsp发表评论
			String cauthor = request.getParameter("cauthor");//用户名
			String cip = request.getParameter("cip");//ip
			String ccontent = request.getParameter("ccontent");//内容
			String cnid=request.getParameter("nid");
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String date = df.format(new Date());// new Date()为获取当前系统时间
			
			Comments comments=new Comments();
			comments.setCnid(Integer.parseInt(cnid));
			comments.setCauthor(cauthor);
			comments.setCip(cip);
			comments.setContent(ccontent);
			comments.setCdate(date);
			
			int result = commentsService.insertComment(comments);
			
			if (result == 1) {
				out.println("{\"result\":\"success\",\"date\":\""+date+"\"}");
				
			}else{
				out.println("{\"result\":\"fail\"}");
			}
			
			out.flush();
			out.close();

		} else if ("showNews".equals(opr)) {//admin.jsp显示新闻页面布局
			List<News> list = newsService.getAllnews();//查询全部新闻
			News news=null;
			StringBuffer newsJSON=new StringBuffer("[");
			for(int i=0;i<list.size();i++){
				news=list.get(i);
				String newsStr="{\"nid\":\""+news.getNid()+"\",\"ntitle\":\""+news.getNtitle().replaceAll("\"", "")+"\",\"nauthor\":\""+news.getNauthor().replaceAll("\"","")+"\"}";
				newsJSON.append(newsStr);
				if(i<list.size()-1){
					newsJSON.append(",");
					
				}
			}
			newsJSON.append("]");
			out.print(newsJSON);

		} else if ("delNews".equals(opr)) {//删除新闻
			int nid = Integer.parseInt(request.getParameter("nid"));
			int result = newsService.deleteNewByNid(nid);
			if (result == 1) {
				request.setAttribute("list4", null);//起到一个刷新的作用
				out.print("<script type='text/javascript'>"
						+ "alert('新闻删除成功！');location.href='newspages/admin.jsp'</script>");

			} else if (result == -1) {
				out.print("<script type='text/javascript'>alert('新闻删除出错哦！');location.href='newspages/admin.jsp'</script>");
			}

		} else if ("addNews".equals(opr)) {//新增新闻
			String ntid = null;//主题id
			String ntitle = null;//标题
			String nauthor = null;//作者
			String nsummary = null;//摘要
			String ncontent = null;//内容
			SimpleDateFormat df = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String date = df.format(new Date());// new Date()为获取当前系统时间

			// 			获得图片文件名
			String uploadFileName = "";//文件名
			boolean isMultipart = ServletFileUpload
					.isMultipartContent(request);
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				
				factory.setSizeThreshold(4096);
				upload.setSizeMax(1024 * 50);//设置不能超过5mb
				try {
					List<FileItem> items = upload.parseRequest(request);
					Iterator<FileItem> iter = items.iterator();
					while (iter.hasNext()) {
						FileItem item = (FileItem) iter.next();
						if (item.isFormField()) {
							String fieldName = item.getFieldName();
							if (fieldName.equals("ntid")) {
								ntid = item.getString("UTF-8");
							}
							if (fieldName.equals("ntitle")) {
								ntitle = item.getString("UTF-8");
							}
							if (fieldName.equals("nauthor")) {
								nauthor = item.getString("UTF-8");
							}
							if (fieldName.equals("nsummary")) {
								nsummary = item.getString("UTF-8");
							}
							if (fieldName.equals("ncontent")) {
								ncontent = item.getString("UTF-8");
							}
						} else {
							String fileName = item.getName();
							List<String> fileType = Arrays.asList("gif",
									"jpg", "jpeg");
							String ext = fileName.substring(fileName
									.lastIndexOf(".") + 1);
							if (!fileType.contains(ext)) {
								out.print("<script>alert('上传失败，文件类型只能是gif jpeg jpg');location.href='newspages/news_add.jsp'</script>");
								return;
							} else {
								if (fileName != null
										&& !fileName.equals("")) {
									File fullFile = new File(item.getName());
									uploadFileName = fullFile.getName();
								}
							}
						}
					}
				} catch (FileUploadBase.SizeLimitExceededException ex) {
					out.print("<script>alert('文件上传上传失败，文件最大限制为:"
							+ upload.getSizeMax() + "')</script>");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// 			为新闻赋值
			News news = new News();
			news.setNtid(Integer.parseInt(ntid));
			news.setNtitle(ntitle);
			news.setNauthor(nauthor);
			news.setNsummary(nsummary);
			news.setNcontent(ncontent);
			news.setNpicPath(uploadFileName);
			news.setNcreateDate(date);
			news.setNmodifyDate(date);
			// 			接受添加新闻返回值
			int result = newsService.addNews(news);
			if (result > 0) {
				out.print("<script>alert('添加新闻成功！');location.href='newspages/admin.jsp'</script>");
			} else {
				out.print("<script>alert('添加新闻失败！');location.href='newspages/admin.jsp'</script>");
			}

		} else if ("editNewsShow".equals(opr)) {//传值给修改新闻页面
			String nid2=request.getParameter("nid");
			int nid=Integer.parseInt(nid2);
			News news=newsService.getNewByNid(nid);
			session.setAttribute("news", news);
			List<Comments> comments=commentsService.getAllComments(nid);
			session.setAttribute("comments", comments);
			request.getRequestDispatcher("newspages/news_modify.jsp?nid="+nid).forward(
					request, response);
		}else if("editNews".equals(opr)){//修改新闻
			String ntid = null;//主题id
			String ntitle = null;//标题
			String nauthor = null;//作者
			String nsummary = null;//摘要
			String ncontent = null;//内容
			// 获得图片文件名
			String uploadFileName = "";//文件名
			boolean isMultipart = ServletFileUpload
					.isMultipartContent(request);
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(4096);
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(1024 * 50);//设置不能超过5mb
				try {
					List<FileItem> items = upload.parseRequest(request);
					Iterator<FileItem> iter = items.iterator();
					while (iter.hasNext()) {
						FileItem item = (FileItem) iter.next();
						if (item.isFormField()) {
							String fieldName = item.getFieldName();
							if (fieldName.equals("ntid")) {
								ntid = item.getString("UTF-8");
							}
							if (fieldName.equals("ntitle")) {
								ntitle = item.getString("UTF-8");
							}
							if (fieldName.equals("nauthor")) {
								nauthor = item.getString("UTF-8");
							}
							if (fieldName.equals("nsummary")) {
								nsummary = item.getString("UTF-8");
							}
							if (fieldName.equals("ncontent")) {
								ncontent = item.getString("UTF-8");
							}
						} else {
							String fileName = item.getName();
							List<String> fileType = Arrays.asList("gif",
									"jpg", "jpeg");
							String ext = fileName.substring(fileName
									.lastIndexOf(".") + 1);
							if (!fileType.contains(ext)) {
								if(ext!=""){
									out.print("<script>alert('上传失败，文件类型只能是gif jpeg jpg');location.href='newspages/news_modify.jsp'</script>");
									return;	
								}
							} else {
								if (fileName != null
										&& !fileName.equals("")) {
									File fullFile = new File(item.getName());
									uploadFileName = fullFile.getName();
								}
							}
						}
					}
				} catch (FileUploadBase.SizeLimitExceededException ex) {
					out.print("<script>alert('文件上传上传失败，文件最大限制为:"
							+ upload.getSizeMax() + "')</script>");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// 			为新闻赋值
			String nid2=request.getParameter("nid");
			News news = new News();
			news.setNtid(Integer.parseInt(ntid));
			news.setNid(Integer.parseInt(nid2));
			news.setNtitle(ntitle);
			news.setNauthor(nauthor);
			news.setNsummary(nsummary);
			news.setNcontent(ncontent);
			news.setNpicPath(uploadFileName);
			int result=newsService.editNews(news);
			if(result>0){
				out.print("<script>alert('新闻修改成功');location.href='newspages/admin.jsp'</script>");
			}else{
				out.print("<script>alert('新闻修改失败');location.href='newspages/news_modify.jsp'</script>");
			}
		}else if("listHtml".equals(opr)){
			List<News> list = newsService.getAllnews();//查询全部新闻
			request.setAttribute("list", list);
			request.getRequestDispatcher("newspages/showNews.jsp").forward(request, response);
		}
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
