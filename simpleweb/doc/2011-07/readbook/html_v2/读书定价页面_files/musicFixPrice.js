var musicFixPrice = window.onload;
window.onload = function(){
  try{
	new common.setPageTitle();
	if(musicFixPrice){
		new musicFixPrice();
	}
	new music.pageInit();
	new music.initFormData();
	
	new music.addGoodsInfo();
	new music.addEventToCheckbox();
	
	new music.edit();	
	new common.selectProductOrGoods();
	
	new music.addOperateEvent();
	
	//new common.prohibitPageRefresh();
	
	new music.urlEvent();
	
	new common.selectPageLineEvent();
	
	new common.addPlan();
	
  }catch(e){
  	alert('请使用IE8 或 firefox3.x! ' + e);
  }
};

var music = {};
music = {
	music : 1,
	singleMusic : 2,
	albumMusic :3,
	suitMusic : 4,
	book : 5,
	comic : 6,
	nestedTableClass : 'nestedTable',
	thead : 'THEAD',
	trColor : 'background:#FFFFCC none repeat scroll 0 0;color:#222222;',
	bizPlanSelectId : 'planId',
	nestedTable : 'nestedTable',
	ie : 'Microsoft Internet Explorer',
	ff : 'Netscape',
	browser : navigator.appName,	
	itemChild : 'itemChild',
	pageInit : function(){
		var table = document.getElementById('tableId');
		if(!table)return;
		
		var rows = table.rows;
		if(! rows)return;
		for(var i = 1; i < rows.length; i++){  //first row is table title.
			var td2 = rows[i].cells[2];
			var tdChildren = td2.childNodes;
			for(var j = 0; j < tdChildren.length; j++){   
				var tdChild = tdChildren[j];
				if(tdChild.nodeName == 'SELECT'){
					new music.setSelectOption(tdChild);
				}
			}
		}
	},
	setSelectOption : function(selectNode){
		if(planJson){
			var opArr = selectNode.options;
			for(var i = 0; i < planJson.length; i++){
				var plan = planJson[i];
				var op = new Option();
				op.text = plan.name;
				op.value = plan.id;
				opArr[i + 1] = op;
			}
		}
	},
	
	addOperateEvent : function(){
		var search = document.getElementById('searchId');
		var submit = document.getElementById('submitId');
		search.onclick = function(){
			new common.search();
		};
		
		submit.onclick = function(){
			var url = 'goodsSave.action';
			var jsonParameters = [];
			
			var checkboxs = document.getElementsByName(music.itemChild);
			var table = document.getElementById('tableId');
			var trs = table.rows;
			var index = 0;
			for(var i = 0; i < trs.length; i++){
				var tr = trs[i + 1];   //i == 0 is table title
				if(!tr){
					break;
				}
				var tds = tr.cells;
				
				var row = {};
			    var checkbox;
				var planSelect;
				if(music.browser == music.ie){
					planSelect = tds[2].childNodes[0];
					checkbox = tds[0].childNodes[0];
				}else{
					planSelect = tds[2].childNodes[1];  //first child is TextNode
					checkbox = tds[0].childNodes[1];
				}
			    
				var productId = checkbox.value;
				row.productId = productId;
				
				if(planSelect.selectedIndex != 0){
					var selVal = planSelect.options[planSelect.selectedIndex].value;
					var goodsId = new common.getGoodsIdExistByProductId(productId);						
					
					row.goodsId = String(goodsId.goodsId);
					row.planId = String(selVal);
					row.createDate = goodsId.createDate;
					row.lastModify = goodsId.lastModify;
					jsonParameters[index++] = row;
				}				
			}
			
			//alert(jsonParameters.length);
			if(jsonParameters.length !== 0){
				var form = document.forms[0];
				var goodsData = document.getElementById('fixPriceDataId');
				goodsData.value = Object.toJSON(jsonParameters);
				document.getElementById('serviceId').value = serviceId;
				
				var lineSel = document.getElementById('pageLineId');
				var line = lineSel.options[lineSel.selectedIndex].value;			
				
				form.action = 'goodsSave.action'.concat('?pageLine=' + line);
				form.submit();
			}else{
				return ;
			}
		};
		//var selectPage = document.getElementById('selectPage');
		//var selectPageNodes = selectPage.childNodes; 
						
	},
	addEventToCheckbox : function(){
		var checkboxList = document.getElementsByName(music.itemChild);
		for(var i = 0; i < checkboxList.length; i++){
			var box = checkboxList[i];
			box.onclick = function(){
				var tr = this.parentNode.parentNode;
				if(this.checked == true){
					tr.style.cssText = music.trColor;					
				}else{
					tr.style.cssText = '';
				}
/*
				var productId = this.value; 
				var selectIdValue = music.bizPlanSelectId.concat(productId);
				var selectNode = document.getElementById(selectIdValue);	
				if(selectNode.selectedIndex == 0){
					
				} */
			};
		}		
	},	
	addGoodsInfo : function(){
		var infoArr = goodsInfo ;  //info is defined in musicFixPrice.jsp.
		if( !infoArr)return;

		for(var i = 0; i < infoArr.length; i++){
			var data = infoArr[i];
			if(!data)break;
			
			var productId = data.productId;
			var trNode = document.getElementById(productId);
			var tds = trNode.childNodes;
			var td2;    //select node parent.
			var td3;    //create date.
			var td4;    //last modified date.
			var selectNod;
			
			if(music.browser == music.ie && navigator.userAgent.indexOf('MSIE 8.0') != -1){
				//ie 8
				td2 = tds[2];
				td3 = tds[3];
				td4 = tds[4];				
				selectNode = td2.childNodes[0];	
			}else{
				td2 = tds[5];
				td3 = tds[7];
				td4 = tds[9];
				selectNode = td2.childNodes[1];	
			}

			var planIdValue = 0;
			if(data.planIds[0]){
				planIdValue = data.planIds[0]; 	
			}
			
			var opArr = selectNode.options;
			for(var j = 0; j < opArr.length; j++){
				if(parseInt(opArr[j].value) == planIdValue){
					opArr[j].selected = true;
					break;
				}	
			}	
						
			var span;
			var span1;
			if(music.browser == music.ff){
				span = td3.childNodes[1];
				span1 = td4.childNodes[1];
			}else if(navigator.userAgent.indexOf('MSIE 9.0') != -1){
				span = td3.childNodes[1];
				span1 = td4.childNodes[1];
			}else{
				//ie8
				span = td3.childNodes[0];
				span1 = td4.childNodes[0];						
			}
			span.innerHTML = data.createDate;
			span1.innerHTML = data.lastModify;
		}
	},
	edit : function(){
		var plans = document.getElementsByName('plan');		
		var checkboxs = document.getElementsByName('itemChild');	
		for(var i = 0; i < plans.length; i++){
			if(plans[i]){
				plans[i].onchange = function(){
					var id = this.id;
					var arr = id.split('_');
					var productId = arr[1];
					var thisSelected = this.selectedIndex;
					
					///商品的业务计划可以改变，不允许置为空。
					if(this.selectedIndex == 0){
						var flag = false;
						for(var j = 0; j < goodsInfo.length; j++){
							var goods = goodsInfo[j];
							if(goods.productId == productId) {
								var opValue = goods.planIds[0];
								var ops = this.options;
								for(var i1 = 0; i1 < ops.length; i1++){
									if(ops[i1].value == opValue){
										ops[i1].selected = true; 	
										flag = true;
										break;	
									} 
								}
								
								break;
							}
						}
						return;						
					}
															
					//增加批量定价功能
					var tableTr = this.parentNode.parentNode;
					var table;
					
					var firstTd;
					var input;
					if(music.browser == music.ie){
						//ie
						table = tableTr.parentNode;
						input = tableTr.cells[0].childNodes[0];												
					}else{
						table = tableTr.parentNode.parentNode;
						input = tableTr.cells[0].childNodes[1];
					}
					input.checked = true;
					tableTr.style.cssText = common.trColor;
								
						
					while(true){
						if(music.browser == music.ie){
							tableTr = tableTr.nextSibling;
							if(! tableTr){
								break;
							}
							input = tableTr.childNodes[0].childNodes[0];
						}else{
							tableTr = tableTr.nextSibling;
							if(! tableTr){
								break;
							}
							tableTr = tableTr.nextSibling;	
							input = tableTr.childNodes[1].childNodes[1];
						}
						
						if(input && input.checked){
							var td2 = tableTr.cells[2];
							
							var selectNode = music.browser == music.ie ? td2.childNodes[0] : td2.childNodes[1];
							selectNode.selectedIndex = thisSelected;  							
						}
					}						
				};			
			}
		}
	},
	initFormData : function(){
		if(pageLine){
			new common.initSelectNode('pageLineId',pageLine);			
		}
		//serviceId
		if(keyword){
			document.getElementById('keywordId').value = keyword;			
		}
	},
	productRemark : function(){
		var linkList = document.getElementsByTagName('A');
		for(var i = 0; i < linkList.length; i++){
			var a = linkList[i];
			if(a.className == 'remark'){
				a.onMouseMove = function(){
					
				};
				
				a.onMouseOut = function(){
					
				};
			}
		}
	},
	urlEvent : function(){
		var arr = document.getElementsByName('remark');
		for(var i = 0; i < arr.length; i++){
			var a = arr[i];
			a.onclick = function(){
				var url = 'productInfo.action';
				var productId = this.id;
				window.location = url.concat('?id=' + productId)
									 .concat('&serviceId=' + serviceId)
									 .concat('&detailType=' + detailType);
			};
		}
	}	
};

var common={};
common = {
	trColor : 'background:#FFFFCC none repeat scroll 0 0;color:#222222;',
	aColor : 'color:#FF0099;',	
	selectProductOrGoods : function(){
		var selectAll = document.getElementsByName('selectAll');
		selectAll[1].onclick  = selectAll[0].onclick = function(){
			document.getElementById("all").checked="true";
			new common.setAColor(this);
			new common.checkall();
		};
		
		var selectNone = document.getElementsByName('selectNone');
		selectNone[1].onclick = selectNone[0].onclick = function(){
			new common.setAColor(this);			
			common.checkNone();
		};
		var product = document.getElementsByName('selectProduct');
		product[1].onclick = product[0].onclick = function(){
			new common.setAColor(this);
			new common.select(true);
		};
		
		var goods = document.getElementsByName('selectGoods');
		goods[1].onclick  = goods[0].onclick = function(){
			new common.setAColor(this);
			new common.select(false);
		};
	},
	checkall : function () {
	
		var checkboxs = document.getElementsByName("itemChild");
		for (i = 0; i < checkboxs.length; i++) {
			var checkbox = checkboxs[i];
			checkbox.checked = document.getElementById("all").checked;

			var trNode = checkbox.parentNode.parentNode;
			if(checkbox.checked == true){
				trNode.style.cssText = common.trColor;
			}else{
				trNode.style.cssText = '';
			}
		}
	},
	checkNone : function(){
		document.getElementById("all").checked = false;
		var checkboxs = document.getElementsByName("itemChild");
		for (i = 0; i < checkboxs.length; i++) {
			var checkbox = checkboxs[i];
			checkbox.checked = false;
			var trNode = checkbox.parentNode.parentNode;	
			trNode.style.cssText = '';
		}
		
	},
	
	
	/**
	 * isOnlyProduct true ：select product,not goods；
	 * isOnlyProduct false ：only select goods；
	 */
	select : function(isOnlyProduct){
			var checkboxs = document.getElementsByName("itemChild");
			for (i = 0; i < checkboxs.length; i++) {
				var checkbox = checkboxs[i];
				var val = checkbox.value;
				
				var flag = false;
				if( goodsInfo.length > 0){
					for(var j = 0; j < goodsInfo.length; j++){
						var goods = goodsInfo[j];
						var productId = goods.productId;
						
						if(val == productId){
							flag = true;
							break;
						}
					}
				}else{
					flag = false;					
				}
				
				if(isOnlyProduct){
					//选页面所有未定价的。
					if(flag){
						checkbox.checked = false;
						checkbox.parentNode.parentNode.style.cssText = '';
					}else{
						checkbox.checked = true;
						checkbox.parentNode.parentNode.style.cssText = common.trColor;
					}
				}else{
					if(flag){
						checkbox.checked = true;
						checkbox.parentNode.parentNode.style.cssText = common.trColor;									
					}else{
						checkbox.checked = false;
						checkbox.parentNode.parentNode.style.cssText = '';
					}
				}			
			}
	},
	setAColor : function(thisNode){
		var thisNodeName = thisNode.name;
		var spans = document.getElementsByTagName('SPAN');
		var spanArray = [];
		var index = 0;
		for(var i = 0; i < spans.length; i++){
			var span = spans[i];
			if(span.className == 'selectPage'){
				spanArray[index ++] = span; 
			}
		}
		
		for(var i = 0; i < spanArray.length; i++){
			var selectPageNode = spanArray[i];
			var links = selectPageNode.childNodes;
			for(var j = 0; j < links.length; j++){
				var link = links[j];
				if(link.nodeName == 'A'){
					if(link.name == thisNodeName){
						//link.disabled = true;
						link.style.cssText = common.aColor;
					}else{
						//link.disabled = false;
						link.style.cssText = '';
					}
				}
			}
		}
	},
	getGoodsIdExistByProductId : function(productId){
		var goods = goodsInfo;
		var re = {goodsId:'0',createDate : '',lastModify : ''};
		for(var i = 0; i < goods.length; i++){
			var good = goods[i];
			if(good.productId == productId){
				var goodsId = good.goodsId;
				var createDate = good.createDate;
				var lastModify = good.lastModify;
				
				re.goodsId = goodsId;
				re.createDate = createDate;
				re.lastModify = lastModify;
				return re; 
			}
		}
		return re;
	},
	selectPage : function(){
		var span = document.getElementById('selectPage');
		var spanNodes = span.childNodes;
	},
	initSelectNode: function(selectNodeId,value){
		if(value != ''){
			var selectNode = document.getElementById(selectNodeId);
			var options = selectNode.options ;
			for(var i =0; i < options.length; i++){
				var op = options[i];
				if(op.value == value){
					op.selected = true;
					break;
				}
			}
		}		
	},
	prohibitPageRefresh :function(){
	  //禁止用F5键   
	  document.onkeydown = function(){   
	        if(event.keyCode==116){   
	             event.keyCode   =   0;   
	             event.cancelBubble   =   true;   
	             return   false;   
	        }   
	  };   
    
  	 //禁止右键弹出菜单   
     document.oncontextmenu = function(){   
      return   false;   
     };  	
	},
	setPageTitle : function(){
		var url = 'goodsList.action';
		var suitUrl = 'suitList.action';
		var album = document.getElementById('albumMusicId');
		var single = document.getElementById('singleMusicId');
		var suit = document.getElementById('suitId');
		
		if(!album || !single || !suit){
			return null;
		}
		if(detailType && detailType != -1){
			switch(parseInt(detailType)){
			case music.singleMusic:{
				//single.style='color：#5EA4CE';
				if(single.disabled != undefined){
					single.disabled = true;					
				}else{
					single.className = 'disable'; 
				}
				break;
			}
			case music.albumMusic :{
				//album.style='color：#5EA4CE';
				if(album.disabled  != undefined){
					album.disabled = true;
				}else{
					album.className = 'disable'; 
				}
				break;
			}
			case music.suitMusic:{
				//suit.style='color：#5EA4CE';
				if(suit.disabled  != undefined){
					suit.disabled = true;					
				}else{
					suit.className = 'disable'; 
				}
				break;
			}
			};
		}else{
			album.style.display = "none"; 
			single.style.display = "none"; 
			suit.style.display = "none";
			
			if(serviceId == music.book){
				
			}else if(serviceId == music.comic){
				
			}
		}
				
		album.onclick = function(){
			if(this.className == 'disable'){
				return false;
			}
			window.location = url.concat('?serviceId=')
								  .concat(music.music + "&")
								  .concat('detailType=')
								  .concat(music.albumMusic);
			this.disabled = true;
			single.disabled = false;
			suit.disabled = false;
			
		};
		single.onclick = function(){
			if(this.className == 'disable'){
				return false;
			}
			
			window.location = url.concat('?serviceId=')
								 .concat(music.music + "&")
								 .concat('detailType=')
								 .concat(music.singleMusic);	
			this.disabled = true;
			album.disabled = false;
			suit.disabled = false;
		};
		
		suit.onclick = function(){
			if(this.className == 'disable'){
				return false;
			}
			
			window.location = suitUrl.concat('?serviceId=')
								  .concat(music.music + "&")
								  .concat('detailType=')
								  .concat(music.suitMusic);	
			album.disabled = false;
			single.disabled = false;			
		};		
	},
	selectPageLineEvent : function(){
		var pageLine = 'pageLineId';
		var line = document.getElementById(pageLine);
		
		line.onchange = function(url,paramIds){
			new common.search();
		}
	},
	search : function(){
			var url = 'goodsList.action';
			var ser = serviceId;
			var lineSel = document.getElementById('pageLineId');
			var key = document.getElementById('keywordId').value;
			key = key.replace(/(^\s*)|(\s*$)/g, "");
			
			var line = lineSel.options[lineSel.selectedIndex].value;
						
			key = encodeURI(key);
			url = url.concat('?keyword=' + key)
							   .concat('&serviceId=' + serviceId)
							   .concat('&detailType=' + detailType)
							   .concat('&pageLine=' + line);
							   //.concat('&isUrl=true');
			window.location = encodeURI(url); 		
	},
	
	addPlan : function(){
		var addList = document.getElementsByName("add");
		var plan = [{id : "1",name : "积分购买音乐"}];
		for(var i = 0; i < addList.length; i++){
			var add = addList[i];
			add.onClick = function(){
				//如果第二个业务计划不存在
				var sel = document.createElement("select");
				sel.id = "";
				sel.name = "";
				for(var j = 0; j < plan.length; j++){
					var op = new Option();
					op.value = plan.id;
					op.text = plan.name;
					sel.options[j] = op;
				}
				
				
			}
		}
	}
}