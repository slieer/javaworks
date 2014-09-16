/**
 * 例子： var template = "{0}欢迎你在{1}上给{0}留言，交流看法"; var msg =
 * String.format(template, “晴枫”, “知行网”);
 */
String.format = function(src) {
	if (arguments.length == 0)
		return null;
	var args = Array.prototype.slice.call(arguments, 1);
	return src.replace(/\{(\d+)\}/g, function(m, i) {
		return args[i];
	});
};
/*
 * #3.1.1 频道及爬取源获取 http://192.168.52.17/videoplus/hometv/video_channel.action
 * #3.1.4 获取频道下的视频列表/videoplus/hometv/video_list.action #3.1.5
 * 各视频详细信息/videoplus/hometv/video_detail.action
 */
var WR = {
	catUrl : '/tvlive/live/ott_catelist.action',
	catUrl : '/tvlive/live/ott_channel_cate.action?classId=',
	channelItemList : null,
	loadBar : function() {
		var barHtml = '';
		$.get(WR.catUrl, {}, 'xml').done(function(data) {

			var firstId = '';
			$(data).find('catelist item').each(function(index, field) {
				var row = $(field);
				var id = row.find("id").text();
				var name = row.find("name").text();

				if (index == 0) {
					firstId = id;
				}
				console.info('id:' + id);
				console.info('name:' + name);

				var barTemplate = $('#barItem').text();
				barHtml += String.format(barTemplate, id, name);
			});

			$('#category_box').append(barHtml);
			// WR.channelItemList = $(data).find('channellist>item');

			WR.loadList(firstId);
		});
	},

	loadList : function(cateId) {
		// $('#category_box').click(function(event){
		var template = $('#item').text();

		var result = '';

		$.get(WR.catUrl, {}, 'xml').done(function(data) {

			$(data).find("channellist>item").each(function(i) {
				var thiz = $(this);
				var json = {};

				var chId = thiz.find('channelid').text();
				var name = thiz.find('>name').text();

				// json.index = i;
				json.id = chId;
				json.name = name;
				// console.info(chId + ',' + name);

				thiz.find('progevent').each(function(index, node) {
					var nodeJ = $(node);
					// console.log("nodeJ:" + nodeJ + " , index:" +
					// index);
					var progName = nodeJ.find('name').text();

					var start = nodeJ.find('timestart').text();
					var end = nodeJ.find('timeend').text();
					start = start.split(' ')[1].replace(':00', '');
					end = end.split(' ')[1].replace(':00', '');

					// console.log("progName:" + progName + " ,start:" +
					// start + ", end:" + end);
					if (!json.prog) {
						json.prog = [];
					}
					// console.log(index);
					json.prog[index] = {
						'name' : progName,
						'time' : start + '~' + end
					};
				});

			});
		});
	}
}

$(function() {
	WR.loadBar();
});
