package com.licc.dove.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * 说明：反射工具
 * 
 * 修改时间：2014年9月20日
 * @version
 */
public class ReflectHelper {
	private static final Logger logger = LoggerFactory.getLogger(ReflectHelper.class);
	
	public static Map transformEntityToMap(Object obj){
		Map map = new HashMap();
		for(Field f : obj.getClass().getDeclaredFields()){
			f.setAccessible(true);
			try {
				Object value = f.get(obj);
				map.put(f.getName() , value);
			} catch (Exception e) {
				logger.warn("get "+f.getName()+" value of "+obj.getClass().getName()+" failed ");
			}
		}
		return map;
	}
	
	public static <T> List<T> transformMapToEntity(Class<T> clazz , List<Map<String,Object>> mapResult){
		List<T> entityResult = new ArrayList<T>();
		for(Map<String,Object> map : mapResult){
			try {
				T entity = clazz.newInstance();
				ReflectHelper.setProperties(entity, map);
				entityResult.add(entity);
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("try to wrap entity "+clazz.getName()+" failed" , e);
			}
		}
		return entityResult;
	}
	
	public static void setProperties(Object dest , Map<String,Object> origin) throws IllegalArgumentException, IllegalAccessException{
		origin = SqlHelper.dbFieldToEntityField(dest.getClass(), origin);
		Field[] fields = dest.getClass().getDeclaredFields();
		for(Field field : fields){
//			String name = field.getName().toUpperCase();
			String name = field.getName();
			Object value = origin.get(name);
			if(value==null){
				value = origin.get(name.toUpperCase());
			}
			field.setAccessible(true);
			if(value!=null){
				if(field.getType().equals(Character.class) || field.getClass().equals(char.class) ){
					String str = (String)value;
					if(str.length()>0){
						field.set(dest, str.charAt(0));
					}
				}else{
					if(field.getType().equals(Short.class)){
				        field.set(dest, Short.valueOf(value.toString()));
				    }else{
				        field.set(dest, value);
				    }
				}
				
			}
		}
	}
	
	public static <T> T  transformMapToEntity(Class<T> clazz , Map map){
        try {
            T entity = clazz.newInstance();
            ReflectHelper.setProperties(entity, map);
            return entity;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("try to wrap entity "+clazz.getName()+" failed" , e);
        }
    }
}
