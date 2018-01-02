
//override dialog's title function to allow for HTML titles
// $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
// 	_title: function(title) {
// 		var $title = this.options.title || '&nbsp;'
// 		if( ("title_html" in this.options) && this.options.title_html == true )
// 			title.html($title);
// 		else title.text($title);
// 	}
// }));
//
// function logoff() {
// 	//this.preventDefault();
//
// 	var dialog = $( "#dialog-message" ).removeClass('hide').dialog({
// 		modal: true,
// 		title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='icon-ok'></i> 确定要退出吗?</h4></div>",
// 		title_html: true,
// 		buttons: [
// 			{
// 				text: "Cancel",
// 				"class" : "btn btn-xs",
// 				click: function() {
// 					$( this ).dialog( "close" );
// 				}
// 			},
// 			{
// 				text: "OK",
// 				"class" : "btn btn-primary btn-xs",
// 				click: function() {
// 					$( this ).dialog( "close" );
// 				}
// 			}
// 		]
// 	});
// }

function  dataTables(elm,options) {
	defaultOptions =
  {
     "bJQueryUI":false,
      "bPaginate" : true,// 分页按钮
      "bFilter" : false,// 搜索栏
      "bLengthChange" : true,// 每行显示记录数
      "bInfo" : true,// Showing 1 to 10 of 23 entries 总记录数没也显示多少等信息
      "bWidth":true,
      "iDisplayLength" : 10,// 每页显示行数
      "bStateSave": true,//状态保存
      "processing": true, //打开数据加载时的等待效果
      "serverSide": true,//打开后台分页
		  "fnServerData": retrieveData,
		  "language": {
        "lengthMenu": "_MENU_ 条记录每页",
        "zeroRecords": "没有找到记录",
        "info": "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
        "infoEmpty": "无记录",
        "infoFiltered": "(从 _MAX_ 条记录过滤)",
        "paginate": {
          "previous": "上一页",
          "next": "下一页"
        }
      }

  }
	$.extend(options,defaultOptions);
	return $(elm).DataTable(options);
}

// 3个参数的名字可以随便命名,但必须是3个参数,少一个都不行
function retrieveData( sSource,aoData, fnCallback) {
  var start = aoData[3].value;
  var length = aoData[4].value;
  var page = start/length+1;

  var query ;
  for(var i=0 ;i<aoData.length;i++){
    var data = aoData[i];
    if(data.name == 'query'){
      query = data;
    }
  }

  if(typeof query === 'undefined'){
    query = {"page":page, "size":length};
  }else{
    query = query.value;
    query.page = page;
    query.size = length;
  }
   console.log(query)

  $.ajax({
    url : sSource,//这个就是请求地址对应sAjaxSource
    data : query,//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
    type : 'POST',
    async : false,
    success : function(result) {
    	var tarnsResult = {};
      if(result.code==200){
      	var data = result.data;
      	var draw = result.draw;
        tarnsResult.data = data.result;
        tarnsResult.recordsTotal = data.totalResult;
        tarnsResult.recordsFiltered = data.totalResult;
        tarnsResult.draw =draw;
      }
      fnCallback(tarnsResult);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
    },
    error : function(msg) {
    }
  });
}

