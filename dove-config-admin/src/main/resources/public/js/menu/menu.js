var table;
var options = {
  "serverSide": true,
  "sAjaxSource": "/menu/ajaxData",
  "aoColumns": [{ data: 'name' },
    { data: 'path' },
    { data: 'icon' },
    { data: 'parentName' },
    { data: 'orderNum' },
    { data: null,"bSortable": false, "mRender":function(data,type,full){

      var btn = ''
          +'<a href="javascript:void(0)" onclick="edit('+data.id+','+data.parentId+',\''+data.name+'\',\''+data.path+'\',\''+data.icon+'\','+data.orderNum+')" > 修改 </a>'
          +'<a href="javascript:void(0)" onclick="deleteById('+data.id+')" > 删除 </a>';
      return btn; }
    }

  ]
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



function  add() {
  $("#myModal").modal("show");
}


function  edit(id,parentId,name,path,icon,orderNum) {
  $("#myModal").modal("show");
  $("#myModalLabel").text("修改");
 $("#id").val(id);
 $("#name").val(name);
 $("#parentId").val(parentId);
 $("#icon").val(icon);
 $("#path").val(path);
 $("#orderNum").val(orderNum);

}


function save() {
  var obj = fromData();
  ajax(obj);
}



function  fromData() {
  var obj = {};
  obj.id = $("#id").val();
  obj.name = $("#name").val();
  obj.parentId = $("#parentId").val();
  obj.icon = $("#icon").val();
  obj.path = $("#path").val();
  obj.orderNum = $("#orderNum").val();
  return obj;

}
function  clear() {
  $("#id").val("");
  $("#name").val("");
  $("#parentId").val("");
  $("#icon").val("");
  $("#path").val("");
  $("#orderNum").val("");
}
function modalClose() {
  clear();

  $("#myModal").modal("hide");
}



function deleteById(id) {
  $.ajax({
    url:"/menu/delete/"+id,
    type:"DELETE",
    success:function (data) {
      table.ajax.reload();
      layer.alert(data.desc);
    }
  })

}
function ajax(obj) {
  var url ="/menu/save" ;

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