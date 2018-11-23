jQuery.noConflict();
(function($) {
	$(document).ready(function(){
		var $optArea =$("#opt_area");
		function initNews() {//使用Ajax技术获取新闻列表数据
			$optArea.load("news","opr=listHtml");
		};
		function initTopics() {//使用Ajax技术获取新闻列表数据
			$optArea.load("topic","opr=listHtml");
			
		}
		
		/*
		 * 获取页面左侧功能链接
		 */
		var $leftLinks=$("#opt_list a");
		$leftLinks.eq(3).click(initTopics);
		$leftLinks.eq(1).click(initNews);
		$leftLinks.eq(0).click(function() {
			$optArea.load("newspages/news_add.jsp #opt_area>*");
		});
		$leftLinks.eq(2).click(function() {
			
			$optArea.load("newspages/topic_add.jsp #opt_area>*");
		});
		
		
		var $msg=$("#msg");//获取展示提示信息的div
		
		//添加主题
		$optArea.on("click","#addTopicSubmit",function(){
			var $tname=$optArea.find("#tname");//获取输入主题的文本框
			var tnameValue=$tname.val();
			if(tnameValue==""){
				$msg.html("请输入主题名称!").fadeIn(1000).fadeOut(5000);
				$tname.focus();
				return false;
			}
			//通过验证则发送Ajax请求，添加新主题
			$.getJSON("topic","opr=add&tname="+tnameValue,afterTopicAdd);
			function afterTopicAdd(data) {
				if(data[0].status=="success"){
					//添加成功，给出信息
					$msg.html(data[0].message).fadeIn(1000).fadeOut(5000);
					$optArea.load("topic","opr=listHtml");
					
				}else if(data[0].status="exist"){
					//主题存在
					$msg.html(data[0].message).fadeIn(1000).fadeOut(5000);
					$tname.select();
				}else if(data[0].status=="error"){
					//发生错误
					$msg.html(data[0].message).fadeIn(1000).fadeOut(5000);
					$optArea.load("topic","opr=listHtml");
				}
			}
		});//“提交”按钮单击事件结束
		
		
		//修改主题
		$optArea.on("click","#topicsList .tpsMdfLink",function(){
			var params=this.id.split(":");//从id属性获得主题id与主题名
			$optArea.load("newspages/topic_modify.jsp #opt_area>*","tid="+params[0]+"&tname="+params[1]);
		});
		$optArea.on("click","#updateTopicSubmit",function(){
			var $tname=$optArea.find("#tname");
			var tnameValue=$tname.val();
			if(tnameValue==""){
				$msg.html("请输入主题名称");
				$tname.focus();
				return false;
			}
			var tidValue=$optArea.find("#tid").val();
			$.getJSON("topic","opr=edit&tid="+tidValue+"&tname="+tnameValue,afterTopicUpdate);
			function afterTopicUpdate(data) {
				if(data[0].status=="success"){
					$msg.html(data[0].message).fadeIn(1000).fadeOut(5000);
					$optArea.load("topic","opr=listHtml");
				}else if(data[0].status="exist"){
					$msg.html(data[0].message).fadeIn(1000).fadeOut(5000);
					$tname.select();
				}else if(data[0].status="error"){
					$msg.html(data[0].message).fadeIn(1000).fadeOut(5000);
					$optArea.load("topic","opr=listHtml");
				}
			}
		});
		
		//删除主题
		$optArea.on("click","#topicsList .tpsDelLink",function(){
			var tid=this.id;//从id属性获得主题id与主题名
			$.getJSON("topic","opr=del&tid="+tid,afterTopicUpdate);
			function afterTopicUpdate(data) {
				if(data[0].status=="success"){
					$msg.html(data[0].message).fadeIn(1000).fadeOut(5000);
					$optArea.load("topic","opr=listHtml");
				}else if(data[0].status="error"){
					$msg.html(data[0].message).fadeIn(1000).fadeOut(5000);
					$optArea.load("topic","opr=listHtml");
				}
			}
		});
		
		initNews();
	});
})(jQuery);