jQuery.noConflict();
(function($) {
	$(document).ready(function() {
		
		function getPagi(tid, pageIndex) {
			
			var data = "opr=topicNews";// 准备请求参数
			if (tid) {
				data += "&tid=" + tid;
			}
			if (pageIndex && pageIndex > 0) {
				data += "&pageIndex="+pageIndex;
			}
			$.getJSON("news",data,pagi);
		}
		getPagi(); 	//首页加载时,初始化加载新闻列表
		
		var $centerNewsList=$("#container .main .content .classlist");
		
		function pagi(datas) {
			var tid=datas[0].tid=="null"?"":datas[0].tid;
			var data=datas[1]; //获得分组相关数据
			//1.展示本业新闻数据
			$centerNewsList.html("");
			if(data.newsList==null){
				$centerNewsList.html("<h6>出现错误，请稍后再试或与管理员联系</h6>");
			}else if (data.newsList.length==0) {
				$centerNewsList.html("<h6>抱歉，没有找到相关的新闻</h6>");
			}else{
				var news;
				for(var i=0;i<data.newsList.length;){
					news=data.newsList[i];
					$centerNewsList.append(
							"<li><a href='news?opr=newsRead&nid="+news.nid+"'>"+news.ntitle+"</a><span>"
							+news.ncreateDate+"</span></li>");
					if((++i)%5==0){
						$centerNewsList.append("<li class='space'></li>");
					}
					
				}
			}//本业新闻展示完毕
			//2.生成分页操作链接并注册事件，单击时调用getPagi()方法
			var $operArea=$("<p align=\"center\">当前页数:["+data.currPageNo+"/"+data.totalPageCount+"]&nbsp;</p>")
							.appendTo($centerNewsList);
			if(data.currPageNo>0){
				var $first=$("<a href=\"javascript:;\">首页</a>").click(function() {
					getPagi(tid, 1);
				});
				var $prev=$("<a href=\"javascript:;\">上一页</a>").click(function() {
					getPagi(tid, (data.currPageNo-1));
				});
				$operArea.append($first).append("&nbsp;").append($prev);
				
			}
			if(data.currPageNo<data.totalPageCount){
				var $next=$("<a href=\"javascript:;\">下一页</a>").click(function() {
					getPagi(tid, (data.currPageNo+1));
				});
				var $last=$("<a href=\"javascript:;\">末页</a>").click(function() {
					getPagi(tid, (data.totalPageCount));
				});
				$operArea.append($next).append("&nbsp;").append($last);
			}
			
		}//pagi()结束
		
		$centerNewsList.prev("ul.class_date").find("a").each(function() {
			var a=this;
			a.onclick=function(){
				getPagi(a.id, 1);//id属性作为条件
			};
		});
		
	});
})(jQuery);