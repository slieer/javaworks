/**
 * version 1.0beta author by slieer. last modified 2012/1029.
 */
var jsonTable = {
	constants : {
		auto : 'auto'
	},
	init : {
		// <table id='tabId'></table>
		tabId : '',
		// title json, data array json
		jData : {
			/*
			 * title : {dataKey0 : titleText0,dataKey1 : titleText1} 
			 * title : [titleText0, titleText1]
			 * title : [ {dataKey0 : titleText0},
			 *           {dataKey1 : titleText1} 
			 *         ]
			 */
			title : {},
			data : []
		},
		dataFormat : {
		// optional, colKey : function(rowJSON, colKey){}
		}
	},
	construct : function() {
		var init = jsonTable.init;
		var tabId = init.tabId, colOrder = [], tab = $("#" + tabId);

		var c = {
			validate : (function() {
				if (tabId && tab[0] && init.jData.data instanceof Object)
					return {
						"re" : true
					};
			})(),
			tHead : function() {
				var defaultIndex = 0;
				// construct title.
				var thead = '<thead><tr>';
				var title = init.jData.title;
				if (typeof title != 'undefined') {
					if (!title.length && title instanceof Object) {
						for ( var titleKey in title) {
							thead = thead.concat("<th>" + title[titleKey]
									+ "</th>");
							colOrder.push(titleKey);
						}
					} else if (title instanceof Array) {
						if (!(title[defaultIndex] instanceof Object)) {
							for ( var i = 0; i < title.length; i++) {
								thead = thead.concat("<th>" + title[i]
										+ "</th>");
								colOrder.push(title[i]);
							}
						}else{
							for ( var i = 0; i < title.length; i++) {
								var kvJson = title[i];
								for(var titleKey in kvJson){
									var titleText = kvJson[titleKey];
									thead = thead.concat("<th>" + titleText
											+ "</th>");
									
									colOrder.push(titleKey);
								}
							}
						}
					}
				} else {
					var firstRow = init.jData.data[defaultIndex];
					for ( var titleKey in firstRow) {
						thead = thead.concat("<th>" + titleKey + "</th>");
						colOrder.push(titleKey);
					}
				}
				thead = thead.concat('</tr></thead>');
				tab.append(thead);
			},
			tBody : function() {
				// construct body.
				var tabBody = $('<tbody></tbody>');
				for ( var i = 0; i < init.jData.data.length; i++) {
					var tr = $('<tr></tr>');
					var row = init.jData.data[i];
					for ( var j = 0; j < colOrder.length; j++) {
						var colKey = colOrder[j];
						var val = row[colKey];

						var tdHtml = "";
						if (typeof init.dataFormat != "undefined"
								&& typeof init.dataFormat[colKey] != 'undefined') {

							var func = init.dataFormat[colKey];
							val = func(row, colKey);
						}

						if (val === 0 || val) {
							if (val == jsonTable.constants.auto) {
								tdHtml = "<td>" + i + "</td>";
							} else {
								tdHtml = "<td>" + val + "</td>";
							}
						} else {
							tdHtml = "<td>&nbsp;</td>";
						}
						tr.append(tdHtml);
					}
					tabBody.append(tr);
				}
				tab.append(tabBody);
			}
		};
		if (c.validate) {
			tab.empty();
			c.tHead();
			c.tBody();
		}
	},
	refreshTBody : function() {

	},
	/**
	 * 
	 * A format==> {dataKey0 : titleText0,dataKey1 : titleText1} 
	 *  
	 * B format==> [ {dataKey0 : titleText0},
	 *		      {dataKey1 : titleText1}
	 *          ]
	 * function description:
	 * 	 A to B 
	 */
	titleDataTransform : function(a){
		var re = [];
		if(a){
			for(var key in a){
				var val = a[key];
				var j = {};
				j[key] = val;
				re.push(j);
			}
		}
		return re;
	}
};