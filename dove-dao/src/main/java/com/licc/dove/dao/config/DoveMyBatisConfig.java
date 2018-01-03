package com.licc.dove.dao.config;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.licc.dove.dao.ParamMap;

@Configuration
public  class DoveMyBatisConfig implements TransactionManagementConfigurer {




  @Resource
	private MyBatisConfigManager  myBatisConfigManager;
	@Resource
	DataSource dataSource;

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean() {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
	  bean.setTypeAliasesPackage(myBatisConfigManager.getTypeAliasesPackage());
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setCacheEnabled(false);
		configuration.setUseGeneratedKeys(true);
		configuration.setCallSettersOnNulls(true);
		configuration.setLogPrefix("dao.");
		configuration.setDefaultExecutorType(ExecutorType.REUSE);
		configuration.getTypeAliasRegistry().registerAlias("ParamMap", ParamMap.class);
		bean.setConfiguration(configuration);

		// 添加XML目录
		try {

			org.springframework.core.io.Resource[] resources1 = resolver
					.getResources("classpath:/mybatis/mapper/**/*.xml");
			org.springframework.core.io.Resource[] resources2 = resolver
					.getResources("classpath:/mybatis/mapper/CommonEntityMapper.xml");
			int length = resources1.length + resources2.length;
			if("CommonEntityMapper.xml".equals(resources1[0].getFilename())){
				bean.setMapperLocations(resources2);
			}else {
				org.springframework.core.io.Resource[] resources = Arrays.copyOf(resources1, length);
				System.arraycopy(resources2, 0, resources, resources1.length, resources2.length);
				bean.setMapperLocations(resources);
			}
			return bean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

}
