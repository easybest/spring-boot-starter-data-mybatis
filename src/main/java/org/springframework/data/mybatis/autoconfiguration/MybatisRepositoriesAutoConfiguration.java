/*
 *
 *   Copyright 2016 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.springframework.data.mybatis.autoconfiguration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mybatis.repository.config.MybatisRepositoryConfigExtension;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.mybatis.support.SqlSessionFactoryBean;

import javax.sql.DataSource;

/**
 * @author Jarvis Song
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.data.mybatis.repositories", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(MybatisProperties.class)
@ConditionalOnBean({DataSource.class})
@ConditionalOnMissingBean({MybatisRepositoryConfigExtension.class})
@ConditionalOnClass({MybatisRepository.class, SqlSessionFactory.class})
@AutoConfigureAfter({DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@Import(MybatisRepositoriesAutoConfigureRegistrar.class)
public class MybatisRepositoriesAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean;
    }


}
