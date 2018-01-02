var table;
var options = {
  "serverSide": true,
  "sAjaxSource": "/config/app/ajaxData",
  "aoColumns": [{ data: 'application' },
    { data: 'url' },
    { data: 'lable' },
    { data: 'profile' },
    { data: 'remark' },
    { data: null,"bSortable": false, "mRender":function(data,type,full){

      var btn = ''
          +'<a href="javascript:void(0)" onclick="edit('+data.id+',\''+data.url+'\',\''+data.application+'\',\''+data.lable+'\',\''+data.profile+'\',\''+data.remark+'\')" > 修改 </a>'
          +'<a href="javascript:void(0)" onclick="deleteById('+data.id+')" > 删除 </a>'
          +'<a href="javascript:void(0)" onclick="refresh('+data.url+')" > 刷新配置 </a>';

      return btn; }
    }

  ],
  "fnServerParams": function (aoData) {  //查询条件
    aoData.push(
        { "name": "query", "value": {"application":$("input[name='application']").val(),"profile":$("input[name='profile']").val()} }
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


function  edit(id,url,application,lable,profile,remark) {
  $("#myModal").modal("show");
  $("#myModalLabel").text("修改");
  $("#id").val(id);
  $("#application").val(application);
  $("#url").val(url);
  $("#lable").val(lable);
  $("#profile").val(profile);
  $("#remark").val(remark);

}


function save() {
  var obj = fromData();
  ajax(obj);
}



function  fromData() {
  var obj = {};
  obj.id = $("#id").val();
  obj.application = $("#application").val();
  obj.url = $("#url").val();
  obj.lable = $("#lable").val();
  obj.profile = $("#profile").val();
  obj.remark = $("#remark").val();
  return obj;

}
function  clear() {
  $("#id").val("");
  $("#application").val("");
  $("#url").val("");
  $("#lable").val("");
  $("#profile").val("");
  $("#remark").val("");
}
function modalClose() {
  clear();

  $("#myModal").modal("hide");
}



function deleteById(id) {
  $.ajax({
    url:"/config/app/delete/"+id,
    type:"DELETE",
    success:function (data) {
      table.ajax.reload();
      layer.alert(data.desc);
    }
  })

}
function ajax(obj) {
  var url ="/config/app/save" ;

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

function  refresh(url) {
  $.post({url:url});
}