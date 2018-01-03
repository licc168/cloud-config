package com.licc.dove.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.licc.dove.dao.anno.Id;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.licc.dove.dao.anno.Sequence;

@Component
public class CommonDao {

  @Autowired
  SqlSessionTemplate sqlSessionTemplate;


  /**
   * @author Guojiangtao
   * @Description:增加数据到数据库
   * @date 2017/12/22
   */
  public void save(Object entity) {
    List<QueryPair> queryPairList = SqlHelper.getQueryPairs(entity);
    ParamMap pm = new ParamMap();
    pm.put("queryPairList", queryPairList);
    pm.put("tableName", SqlHelper.getTableName(entity.getClass()));
    sqlSessionTemplate.insert("CommonEntityMapper.insertEntity", pm);
  }


  /**
   * @author Guojiangtao
   * @Description:根据id(由注解 @Id 决定 ) 更新entity中不为null的值
   * @date 2017/12/22
   */
  public void update(Object entity) {
    //得到类中属性id
    Field id = SqlHelper.getIdOfEntity(entity);
    id.setAccessible(true);
    try {
      if (id != null) {
        Object val = id.get(entity);
        if (val == null) {
          throw new RuntimeException("id can't be null when update");
        }
        this.updateByField(entity.getClass(), id.getName(), val, entity);
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * @author Guojiangtao
   * @Description:根据id批量删除表数据,适用于小数量级的批量删除，如一次删除50条以内的
   * @date 2017/12/22
   */
  public int deleteByIds(Class<?> clazz, List<Object> ids) {
    ParamMap pm = new ParamMap();
    pm.put("idColumn", SqlHelper.getIdColumnOfClass(clazz));
    pm.put("tableName", SqlHelper.getTableName(clazz));
    pm.put("ids", ids);
    return sqlSessionTemplate.delete("CommonEntityMapper.deleteByIds", pm);
  }

  /**
   * @author Guojiangtao
   * @Description:根据id(由注解 @Id 决定 ) 删除表数据
   * @date 2017/12/22
   */
  public void delete(Object entity) {
    ParamMap pm = new ParamMap();
    Field[] fields = entity.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (SqlHelper.isIdField(field)) {
        try {
          field.setAccessible(true);
          pm.put("idColumn", SqlHelper.getIdColumnOfEntity(entity));
          pm.put("idValue", field.get(entity));
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    pm.put("tableName", SqlHelper.getTableName(entity.getClass()));
    sqlSessionTemplate.delete("CommonEntityMapper.deleteById", pm);
  }

  public <T> T get(Class<T> clazz, Object id) {
    String tableName = SqlHelper.getTableName(clazz);
    ParamMap pm = new ParamMap();
    pm.put("tableName", tableName);
    pm.put("idColumn", SqlHelper.getIdColumnOfClass(clazz));
    pm.put("idValue", id);
    Map<String, Object> map = sqlSessionTemplate.selectOne("CommonEntityMapper.getById", pm);
    T result = ReflectHelper.transformMapToEntity(clazz, map);
    return result;
  }

  /**
   * 与listByExample方式一样，单返回单条数据
   */
  public <T> T getByExample(T example) {
    List<T> list = listByExample(example);
    if (list == null || list.isEmpty()) {
      return null;
    } else {
      return list.get(0);
    }
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> listByExample(T example) {
    String tableName = SqlHelper.getTableName(example.getClass());
    List<QueryPair> queryPairs = SqlHelper.getQueryPairs(example);
    ParamMap pm = new ParamMap();
    pm.put("tableName", tableName);
    pm.put("queryPairList", queryPairs);
    List<Map<String, Object>> mapResult = sqlSessionTemplate
        .selectList("CommonEntityMapper.listByExample", pm);
    List<?> entityResult = ReflectHelper.transformMapToEntity(example.getClass(), mapResult);
    return (List<T>) entityResult;
  }

  /**
   * @author Guojiangtao
   * @Description:意义与 <code>listByExample(Object vo)</code>一样，增加了对排序的支持
   * @date 2017/12/22
   */
  public <T> List<T> listByExample(Object vo, List<Order> orders) {
    String tableName = SqlHelper.getTableName(vo.getClass());
    List<QueryPair> queryPairs = SqlHelper.getQueryPairs(vo);
    ParamMap pm = new ParamMap();
    pm.put("tableName", tableName);
    pm.put("queryPairList", queryPairs);
    pm.put("orders", orders);
    List<Map<String, Object>> mapResult = sqlSessionTemplate
        .selectList("CommonEntityMapper.listByExample", pm);
    List<?> entityResult = ReflectHelper.transformMapToEntity(vo.getClass(), mapResult);
    return (List<T>) entityResult;
  }

  /**
   * 根据查询条件查询结果
   *
   * @param statement mybatis mapper文件中定义的查询语句id
   * @param paramMap 参数，是一个map ,包含了排序条件
   * @return 返回结果是map list.
   */
  public List<Map<String, Object>> listByParams(String statementId, ParamMap pm) {
    List<Map<String, Object>> result = sqlSessionTemplate.selectList(statementId, pm);
    return result;
  }

  /**
   * 返回符合条件的第一条数据
   *
   * @param statement mybatis mapper文件中定义的查询语句id
   * @param paramMap 参数，是一个map ,包含了排序条件
   * @return 返回结果是map list.
   */
  public Map<String, Object> findOne(String statement, ParamMap paramMap) {
    List<Map<String, Object>> list = this.listByParams(statement, paramMap);
    if (list != null && !list.isEmpty()) {
      return list.get(0);
    }
    return null;
  }

  /**
   * 根据查询条件查询结果
   *
   * @param clazz 返回结果被封装成的java类，在联表查询时可以定义个类，包含所有查询语句需要返回的字段。
   * @param statement mybatis mapper文件中定义的查询语句id
   * @param paramMap 参数，是一个map,,包含了排序条件
   * @return 结果被封装成 参数clazz的实例集合
   */
  public <T> List<T> listByParams(Class<T> clazz, String statementId, ParamMap paramMap) {
    List<Map<String, Object>> mapResult = listByParams(statementId, paramMap);
    List<T> entityResult = null;
    if (mapResult.isEmpty()) {
      entityResult = ReflectHelper.transformMapToEntity(clazz, mapResult);
    } else {
      if (mapResult.get(0) instanceof Map) {
        entityResult = ReflectHelper.transformMapToEntity(clazz, mapResult);
      } else {
        entityResult = (List<T>) mapResult;
      }
    }
    return entityResult;
  }

  /**
   * @author Guojiangtao
   * @Description:给定查询对象进行分页查询
   * @date 2017/12/22
   */
  public <T> Page<T> findPageByExample(Class<T> clazz, Page<T> page, Object example) {
    String tableName = SqlHelper.getTableName(clazz);
    List<QueryPair> queryPairs = SqlHelper.getQueryPairs(example);
    ParamMap paramMap = new ParamMap();
    paramMap.put("tableName", tableName);
    paramMap.put("queryPairList", queryPairs);
    paramMap.put("page", page);
    try {
      if (example == null) {
        throw new RuntimeException("example can not be null when findPageByExample");
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    List<Map<String, Object>> mapResult = sqlSessionTemplate
        .selectList("CommonEntityMapper.findPage", paramMap);
    List<?> entityResult = ReflectHelper.transformMapToEntity(clazz, mapResult);
    page.result = (List<T>) entityResult;
    page.setTotalResult(entityResult.size());
    return page;
  }

  /**
   * @author Guojiangtao
   * @Description:分页查询，返回分页信息
   * @date 2017/12/22
   */
  public <T> Page<T> findPageByParams(Class<T> clazz, Page<T> page, String statement,
      ParamMap paramMap) {
    try {
      if (page == null) {
        throw new RuntimeException("page can not be null when findPageByParams");
      } else {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<T> resultLists = listByParams(clazz, statement, paramMap);
        PageInfo pageInfo = new PageInfo(resultLists);
        page.result=resultLists;
        // setTotalResult 总的结果数
        page.setTotalResult((int) pageInfo.getTotal());
        //设置当前页
        page.setCurrentPage(page.getCurrentPage());
        //设置每页展示结果数
        page.setPageSize(page.getPageSize());
        return page;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * @author Guojiangtao
   * @Description:分页查询，返回分页信息,泛型是Map
   * @date 2017/12/22
   */
  public Page<Map<String, Object>> findPageByParams(Page<Map<String, Object>> page, String
      statement, ParamMap paramMap) {
  //pageHelper只对紧跟着的sql查询起作用
    try {
      if (page == null) {
        throw new RuntimeException("page can not be null when findPageByParams");
      } else {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<Map<String, Object>> resultsMap = listByParams(statement, paramMap);
        PageInfo pageInfo = new PageInfo(resultsMap);
        page.result=resultsMap;
        // setTotalResult 总的结果数
        page.setTotalResult((int) pageInfo.getTotal());
        //设置当前页
        page.setCurrentPage(page.getCurrentPage());
        //设置每页展示结果数
        page.setPageSize(page.getPageSize());
        return page;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * 通常用来执行一个非查询的sql
   */
  public int execute(String statement, ParamMap paramMap) {
    return sqlSessionTemplate.update(statement, paramMap);
  }

  /**
   * 根据给定的字段值更新数据，要更新的字段值在updateObj中所有不为null的字段
   *
   * @param updateObj 例子 Book book = new Book(); book.setWords(100); book.setPrice(22f);
   * CommonDao.updateByField(Book.class, "authorId", 1L, book);//authorId为Book类的字段
   * @author yexinzhou
   * @date 2017年12月15日 下午1:24:53
   */
  public <T> int updateByField(Class<T> clazz, String fieldName, Object fieldValue,
      Object updateObj) {
    try {
      clazz.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException("类" + clazz + "中不存在字段" + fieldName, e);
    } catch (SecurityException e) {
      throw new RuntimeException(e);
    }
    String tableName = SqlHelper.getTableName(clazz);
    Field[] fields = SqlHelper.getFieldsWithoutTransient(clazz);
    List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
    String whereColumnName = fieldName;
    for (Field f : fields) {
      if (f.getName().equals(fieldName)) {
        whereColumnName = SqlHelper.getColumnName(f);
        continue;
      }
      Map<String, Object> column = new HashMap<String, Object>();
      String columnName = SqlHelper.getColumnName(f);
      column.put("name", columnName);
      f.setAccessible(true);
      try {
        Object value = f.get(updateObj);
        if (value == null) {
          continue;
        }
        column.put("value", value);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      columns.add(column);
    }
    ParamMap pm = new ParamMap();
    pm.put("tableName", tableName);
    pm.put("fieldName", whereColumnName);
    pm.put("fieldValue", fieldValue);
    pm.put("columnList", columns);
    int result = this.execute("CommonEntityMapper.updateByField", pm);
    return result;
  }

  /**
   * 批量插入数据，一条sql语句
   *
   * @author yexinzhou
   * @date 2017年12月18日 上午9:12:59
   */
  public <T> int batchInsert(Class<T> clazz, List<T> list) {
    ParamMap pm = new ParamMap();
    Field[] fields = clazz.getDeclaredFields();
    List<String> columns = new ArrayList<String>();
    for (int i = 0; i < fields.length; i++) {
      if (Modifier.isTransient(fields[i].getModifiers())) {
        continue;
      }
      if (SqlHelper.isAutoInstreaseField(fields[i])) {
        continue;
      }
      columns.add(SqlHelper.getColumnName(fields[i]));
    }
    List<List<Object>> rows = new ArrayList<List<Object>>();
    for (T obj : list) {
      List<Object> values = getColumnValues(obj);
      rows.add(values);
    }
    pm.put("tableName", SqlHelper.getTableName(clazz));
    pm.put("columns", columns);
    pm.put("rows", rows);
    return this.execute("CommonEntityMapper.batchInsert", pm);
  }

  private List<Object> getColumnValues(Object obj) {
    List<Object> values = new ArrayList<Object>();
    Field[] fields = obj.getClass().getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      if (Modifier.isTransient(fields[i].getModifiers())) {
        continue;
      }
      if (SqlHelper.isAutoInstreaseField(fields[i])) {
        continue;
      }
      if (SqlHelper.isIdField(fields[i])) {
        // 根据序列获取id
        Field idField = fields[i];
        Sequence seqAno = idField.getAnnotation(Sequence.class);
        if (seqAno == null) {
          // 业务赋值，走下面try方法
        } else {
          //默认用MYCAT_SEQ_NEXTVAL
          String tableName = SqlHelper.getTableName(obj.getClass());
          String selectKey = "select  " + seqAno.name() + "('" + tableName + "')";
          if (seqAno.isSharding()) {
            selectKey = "select next value for MYCATSEQ_" + tableName;
          }
          ParamMap pm = new ParamMap();
          pm.put("selectKey", selectKey);
//                    SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(true);
//                    Long id = session.selectOne("CommonEntityMapper.selectId" ,pm);
          Long id = sqlSessionTemplate.selectOne("CommonEntityMapper.selectId", pm);
          values.add(id);
          continue;
        }
      }
      try {
        fields[i].setAccessible(true);
        Object val = fields[i].get(obj);
        values.add(val);
      } catch (Exception e) {
        throw new RuntimeException("批量插入数据失败", e);
      }
    }
    return values;
  }
}
