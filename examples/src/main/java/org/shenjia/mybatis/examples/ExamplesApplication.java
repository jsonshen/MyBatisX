/*
 * Copyright 2015-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.shenjia.mybatis.examples;

import java.util.List;

import org.shenjia.mybatis.examples.entity.MultiColPk;
import org.shenjia.mybatis.examples.entity.NoPk;
import org.shenjia.mybatis.examples.entity.SingleColPk;
import org.shenjia.mybatis.examples.service.ExamplesService;
import org.shenjia.mybatis.paging.MySQLPagingDecorator;
import org.shenjia.mybatis.paging.Page;
import org.shenjia.mybatis.paging.Pageable;
import org.shenjia.mybatis.paging.PagingDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExamplesApplication implements
    CommandLineRunner {
    
    private static final Logger LOG = LoggerFactory.getLogger(ExamplesApplication.class);

    @Autowired
    private ExamplesService examplesService;

    public static void main(String[] args) {
        SpringApplication.run(ExamplesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.setProperty(PagingDecorator.class.getName(), MySQLPagingDecorator.class.getName());
        LOG.info("---- Run Examples Application......");
        insertNoPkRecord();
        insertSingleColPkRecord();
        insertMultiColPkRecord();
        selectPageMultiColPkRecord(); 
        selectRangeMultiColPkRecord();
        LOG.info("---- The End.");
    }

    public void insertNoPkRecord() {
        NoPk record = new NoPk();
        record.setQqNum(10000);
        record.setRealName("Real name");
        record.setNickname("Nick name");
        record.setPassword("password");
        int count = examplesService.insertNoPkRecord(record);
        LOG.info("---- insertNoPkRecord: " + count);
    }

    public void insertSingleColPkRecord() {
        SingleColPk record = new SingleColPk();
        record.setQqNum(10000);
        record.setRealName("Real name");
        record.setNickname("Nick name");
        record.setPassword("password");
        int count = examplesService.insertSingleColPkRecord(record);
        LOG.info("---- insertSingleColPkRecord: " + count);
    }

    public void insertMultiColPkRecord() {
        MultiColPk record = new MultiColPk();
        record.setQqNum(10000);
        record.setRealName("Real name");
        record.setNickname("Nick name");
        record.setPassword("password");
        int count = examplesService.insertMultiColPkRecord(record);
        LOG.info("---- insertMultiColPkRecord: " + count);
    }
    
    public void selectPageMultiColPkRecord() {
        Page<MultiColPk> page = examplesService.selectPageMultiColPkRecord(new Pageable(1, 10));
        LOG.info("---- page.totalCount: " + page.getTotalCount());
        LOG.info("---- page.Data:" + page.getData());
    }
    
    public void selectRangeMultiColPkRecord() {
        List<MultiColPk> range = examplesService.selectRangeMultiColPkRecord(new Pageable(1, 10));
        LOG.info("---- range.totalCount: " + range.size());
    }
}
