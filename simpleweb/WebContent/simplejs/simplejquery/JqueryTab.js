(function(w) {
	// ie8 or ie9 console problem solution.
	if (!w.console) {
		// alert(".....");
		w.console = {
			info : function() {
			},
			error : function() {
			},
			log : function() {
			}
		};
	}
})(window);

var Op = {
	jsonArr : [ 
	            {'name' : 'zhai', 'sex' : 'F'}, 
	            {'name' : 'lsie', 'sex' : 'M'},
	            {'name' : 'abcd', 'sex' : 'M'},
	            {'name' : 'jia1', 'sex' : 'F'} 
	],
	rowColor : [ '#EEEEEE', '#E6F3FF' ],
	tab : $('#tab'),
	constructTab : function() {
		console.info('OK, construction......');
		var body = $("<tbody></tbody>");

		for ( var i = 0; i < Op.jsonArr.length; i++) {
			var j = Op.jsonArr[i];
			var td1 = $('<td>' + j.name + '</td>');
			var td2 = $('<td>' + j.sex + '</td>');

			var tr = $('<tr></tr>');
			tr.attr('bgcolor', Op.rowColor[i % 2]);

			tr.append(td1);
			tr.append(td2);
			body.append(tr);
		}
		// console.info(body.toString());
		Op.tab.append(body);
		var trArr = $('#tab tbody tr');
		trArr.bind("mouseenter mouseleave", function(event) {
			$(this).toggleClass("trhover");
		});
	},

	tabEvent : function() {
		$('#para').click(function(event) {
			var id = event.target.id;
			switch (id) {
			case 'delTbody':
				console.info('del t');
				$("#tab tbody").remove();
				break;
			case 'delRow':
				console.info('del r');
				try {
					tab[0].tBodies[0].deleteRow(0);   // tab[0].deleteRow(1);
				} catch (e) {
					console.error('rows length is zero.' + e);
				}
				break;
			case 'tbodyEmpty':
				$('#tab tbody').empty();
				break;
			}
		});
	}
};

