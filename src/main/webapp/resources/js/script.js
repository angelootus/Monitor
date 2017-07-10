function Main()
{	
	if(typeof System == undefined)

	this.object = null
	this.message = "";
	
	this.test1 = testing1;
	this.test2 = testing2;
	this.test3 = testing3;
	this.clearValues = mainClearValues;
	this.changeBackgroundColor = mainChangeBackgroundColor;
	this.changeBackgroundTransparent = mainChangeBackgroundTransparent;
}

function testing1(message)
{	
	this.message += 'Hello ' + message;
}

function testing2()
{	
	alert(this.message);
}

function testing3()
{	
	this.test2();
}

function mainClearValues(id)
{	
	var container = $(PrimeFaces.escapeClientId(id));
	container.each(
		function(){
			//alert($(this).html())
	    	$(this).find(':input').each(
				function(){
					var input = $(this);
					//alert(input.val())
					input.val('');
				}
	    	);
		}
	);
	//$(PrimeFaces.escapeClientId('formMain:newTitle')).val('');
	var messages = $(PrimeFaces.escapeClientId('formMain:messages'));
	//alert(messages.html())
	messages.hide();
}

function mainChangeBackgroundColor(id)
{	
//	alert(id)
//	var container = $('#'+id);
	var container = $(PrimeFaces.escapeClientId(id));
//	alert(typeof txt)
	for (var key in container) {
	    var obj = container[key];
//	    alert(key + ": " + obj)
	}
	container.css("background-color", "#FBEE9A");
}

function mainChangeBackgroundTransparent(id)
{	
//	alert(id)
//	var container = $('#'+id);
	var container = $(PrimeFaces.escapeClientId(id));
//	alert(typeof txt)
	for (var key in container) {
	    var obj = container[key];
//	    alert(key + ": " + obj)
	}
	container.css("background-color", "#FFFFFF");
}

var mainHelper = new Main();
//alert(mainHelper)
//mainHelper.test1('Cesar');
//mainHelper.test2();
//mainHelper.test3();
//mainHelper.clearValues('formMain:newTask');

mainStaticHelper = function(){
}

mainStaticHelper.test1 = function(message){
	this.message = ' Hello ' + message;
}

mainStaticHelper.test2 = function()
{    
	alert(this.message);
}

mainStaticHelper.test3 = function()
{    
	mainStaticHelper.test2();
}

mainStaticHelper.clearValues = function(id)
{	
	var container = $(PrimeFaces.escapeClientId(id));
	container.each(
		function(){
			//alert($(this).html())
	    	$(this).find(':input').each(
				function(){
					var input = $(this);
					//alert(input.val())
					input.val('');
				}
	    	);
		}
	);
	//$(PrimeFaces.escapeClientId('formMain:newTitle')).val('');
	var messages = $(PrimeFaces.escapeClientId('formMain:messages'));
	//alert(messages.html())
	messages.hide();
}

//mainStaticHelper.test1('Cesar');
//mainStaticHelper.test2(); 
//mainStaticHelper.test3(); 
//mainStaticHelper.clearValues('formMain:newTask');
