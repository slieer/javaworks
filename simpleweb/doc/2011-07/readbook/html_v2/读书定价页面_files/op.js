window.onload = function() {	
	var pagelinksClass = 'pagelinks';
	var spans;
	if(document.getElementsByClassName){
		//not ie
		spans = document.getElementsByClassName(pagelinksClass);	
	}else{
		spans = document.getElementsByTagName("span");
	}
	
	if(!spans)return ;
	for(var i = 0; i < spans.length; i++){
		var span = spans[i];
		if(span.className == pagelinksClass){
			var nodes = span.childNodes;
			for(var j = 0; j < nodes.length; j++){
				var node = nodes[j];
				var nodeName = nodes[j].nodeName;
				if(nodeName == 'A'){
					node.onclick = function(){
						var url = this.href;
						var index = url.indexOf('?');
						
						var prefixUrl = url.substring(0,index);
						var suffixUrl = url.substring(index + 1,url.length); 
						
						var pageNumString = getPageNum(suffixUrl);
						
						var newUrl = prefixUrl + '?' + queryString() + '&' + pageNumString;
						var out = encodeURI(newUrl);
						
						//alert(out);
						this.href = out;
					};
				}
			}
		}
	}	
};

function getPageNum(suffixUrl){
	if(suffixUrl){
		var array = suffixUrl.split('&');
		for(var i = 0; i < array.length; i++){
			var keyValue = array[i];
			var kvArr = keyValue.split("=");
			if(kvArr[0].startWith("d-") && kvArr[0].endWith("-p")){
				//alert(keyValue);
				return keyValue;
			}
		}
	}
	return;
}

function queryString(){
	var inputs = document.getElementsByTagName('input');
	var re = '';
	for(var i = 0; i < inputs.length; i++){
		var node = inputs[i];
		var name = node.name;
		
		var type = node.getAttribute('type');
		if(type = 'text' || type == 'hidden'){
			var value = node.value;
			if(value){
				value = encodeURI(value);			
				re = re.concat(name + '=' + value + '&');				
			}
		}		
	}
	
	var selects = document.getElementsByTagName('select');
	if(selects){
		var selNode = selects[0];
		var selecedOp = selNode.options[selNode.selectedIndex];		
		re = re.concat(selNode.name + '=' + selecedOp.value + "&")
	}
	
	return re.substring(0, re.length - 1);
}

String.prototype.endWith = function(str){
	if(str && typeof(str) == 'string'){
		if(str.length <= this.length){
			if( this.substring(this.length - str.length)==str){
				return true;
			}
		}
	}
	
	return false;
}
	  
String.prototype.startWith = function(str){
	if(str && typeof(str) == 'string'){
		if(str.length <= this.length){
			if( this.substring(0,str.length) == str){
				return true;
			}
		}
	}
	
	return false;
}

