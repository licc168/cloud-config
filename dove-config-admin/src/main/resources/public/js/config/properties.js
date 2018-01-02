var table;
var options = {
  "serverSide": true,
  "sAjaxSource": "/config/properties/ajaxData",
  "aoColumns": [
    { data: 'appName' },
    { data: 'appProfile' },
    { data: 'proKey' },
    { data: 'proValue' },
    { data: null,"bSortable": false, "mRender":function(data,type,full){

      var btn = ''
          +'<a href="javascript:void(0)" onclick="edit('+data.id+',\''+data.proKey+'\',\''+data.proValue+'\','+data.configAppId+')" > 修改 </a>'
          +'<a href="javascript:void(0)" onclick="deleteById('+data.id+')" > 删除 </a>';
      return btn; }
    }

  ],
  "fnServerParams": function (aoData) {  //查询条件
    aoData.push(
        { "name": "query", "value": {"appName":$("input[name='appName']").val(),
          "appProfile":$("input[name='appProfile']").val(),
          "proKey":$("input[name='proKey']").val()

        } }
    );
  }

};


jQuery(function($) {

  table = dataTables('#table_id_example',options);
  $('table th input:checkbox').on('click' , function(){
    var that = this;
    $(this).closest('table').find('tr > td:first-child input:checkbox')
    .each(function(){
      this.checked = that.checked;
      $(this).closest('tr').toggleClass('selected');
    });

  });

});

function  doSearch() {
  table.ajax.reload();
}

function  add() {
  $("#myModal").modal("show");
}


function  edit(id,proKey,proValue,configAppId) {
  $("#myModal").modal("show");
  $("#myModalLabel").text("修改");
  $("#id").val(id);
  $("#configAppId").val(configAppId);
  $("#proKey").val(proKey);
  $("#proValue").val(proValue);


}


function save() {
  var obj = fromData();
  ajax(obj);
}



function  fromData() {
  var obj = {};
  obj.id = $("#id").val();
  obj.configAppId = $("#configAppId").val();
  obj.proKey = $("#proKey").val();
  obj.proValue = $("#proValue").val();

  return obj;

}
function  clear() {
  $("#id").val("");
  $("#configAppId").val("");
  $("#proKey").val("");
  $("#proValue").val("");

}
function modalClose() {
  clear();

  $("#myModal").modal("hide");
}



function deleteById(id) {
  $.ajax({
    url:"/config/properties/delete/"+id,
    type:"DELETE",
    success:function (data) {
      table.ajax.reload();
      layer.alert(data.desc);
    }
  })

}
function ajax(obj) {
  var url ="/config/properties/save" ;

  $.ajax({
    url:url ,
    type:"POST",
    data: obj,
    success: function (data) {
      layer.alert(data.desc);
      if(data.code === 200) {
        table.ajax.reload();
        $("#myModal").modal("hide");
        $("#myModalLabel").text("新增");
        clear();
      }

      console.log("结果" + data);
    }
  });
}