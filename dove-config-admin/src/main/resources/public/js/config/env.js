var table;
var options = {
  "serverSide": true,
  "sAjaxSource": "/config/env/ajaxData",
  "aoColumns": [
    { data: 'name' },
    { data: 'profile' },
    { data: null,"bSortable": false, "mRender":function(data,type,full){

      var btn = ''
          +'<a href="javascript:void(0)" onclick="edit('+data.id+',\''+data.name+'\',\''+data.profile+'\')" > 修改 </a>'
          +'<a href="javascript:void(0)" onclick="deleteById('+data.id+')" > 删除 </a>';
      return btn; }
    }

  ],
  "fnServerParams": function (aoData) {  //查询条件
    aoData.push(
        { "name": "query", "value": {"name":$("input[name='name']").val(),
          "profile":$("input[name='profile']").val()
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


function  edit(id,name,profile) {
  $("#myModal").modal("show");
  $("#myModalLabel").text("修改");
  $("#id").val(id);
  $("#name").val(name);
  $("#profile").val(profile);


}


function save() {
  var obj = fromData();
  ajax(obj);
}



function  fromData() {
  var obj = {};
  obj.id = $("#id").val();
  obj.name = $("#name").val();
  obj.profile = $("#profile").val();

  return obj;

}
function  clear() {
  $("#id").val("");
  $("#name").val("");
  $("#profile").val("");

}
function modalClose() {
  clear();

  $("#myModal").modal("hide");
}



function deleteById(id) {
  $.ajax({
    url:"/config/env/delete/"+id,
    type:"DELETE",
    success:function (data) {
      table.ajax.reload();
      layer.alert(data.desc);
    }
  })

}
function ajax(obj) {
  var url ="/config/env/save" ;

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