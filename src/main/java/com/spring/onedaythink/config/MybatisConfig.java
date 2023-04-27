package com.spring.onedaythink.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

public class MybatisConfig {

//    @Bean(name = "mainSqlSessionFactory")
//    public SqlSessionFactory mainSqlSessionFactory(@Qualifier("main") DataSource dataSource) throws Exception{
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.example.ayoteralab.main.dto");
//        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
//        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:main_mapper/**/*.xml"));
//        return sqlSessionFactoryBean.getObject();
//    }
}
