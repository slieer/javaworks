var page = require('webpage').create();

page.settings = {
	javascriptEnabled: true,
	loadImages: true,
	webSecurityEnabled: false,
	userAgent: 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36'
};

page.libraryPath
var url = 'http://flights.ctrip.com/international/round-beijing-bangkok-bjs-bkk?2016-12-22&2016-12-29&y_s';
//url = 'http://192.168.60.52:49001/';
page.open(url, function (status) {
	if (status !== 'success') {
        console.log('Unable to access network');
    } else {		
		page.includeJs("http://192.168.60.50:8080/res/js/jquery-2.1.4.min.js", function() {
			var body = page.evaluate(function() {
				/*return document.getElementById('flightList').innerHTML;*/				
				var list = $("#flightList.flight-item");				
				return list;
			});
			
			console.log("test:" + body);
			phantom.exit();
		});
	}		
});

/**
D:\devTools\phantomjs\phantomjs-2.1.1-windows\bin\phantomjs.exe --remote-debugger-port=9000 --remote-debugger-autorun=true getUrl.v2.js
 * 
 */