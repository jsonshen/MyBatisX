package org.shenjia.mybatis.examples;

import org.shenjia.mybatis.examples.entity.MultiColPk;
import org.shenjia.mybatis.examples.entity.NoPk;
import org.shenjia.mybatis.examples.entity.SingleColPk;
import org.shenjia.mybatis.examples.service.ExamplesService;
import org.shenjia.mybatis.paging.MySQLPagingDecorator;
import org.shenjia.mybatis.paging.Page;
import org.shenjia.mybatis.paging.Pageable;
import org.shenjia.mybatis.paging.PagingDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExamplesApplication implements
    CommandLineRunner {

    @Autowired
    private ExamplesService examplesService;

    public static void main(String[] args) {
        SpringApplication.run(ExamplesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.setProperty(PagingDecorator.class.getName(), MySQLPagingDecorator.class.getName());
        System.out.println("Run Examples Application......");
        insertNoPkRecord();
        insertSingleColPkRecord();
        insertMultiColPkRecord();
        System.out.println("The End.");
    }

    public void insertNoPkRecord() {
        NoPk record = new NoPk();
        record.setQqNum(10000);
        record.setRealName("Real name");
        record.setNickname("Nick name");
        record.setPassword("password");
        int count = examplesService.insertNoPkRecord(record);
        System.out.println("insertNoPkRecord: " + count);
        Page<MultiColPk> page = examplesService.selectPageMultiColPkRecord(new Pageable(1, 10));
        System.out.println("page.totalCount: " + page.getTotalCount());
        System.out.println("page.Data:" + page.getData());
    }

    public void insertSingleColPkRecord() {
        SingleColPk record = new SingleColPk();
        record.setQqNum(10000);
        record.setRealName("Real name");
        record.setNickname("Nick name");
        record.setPassword("password");
        int count = examplesService.insertSingleColPkRecord(record);
        System.out.println("insertSingleColPkRecord: " + count);
    }

    public void insertMultiColPkRecord() {
        MultiColPk record = new MultiColPk();
        record.setQqNum(10000);
        record.setRealName("Real name");
        record.setNickname("Nick name");
        record.setPassword("password");
        int count = examplesService.insertMultiColPkRecord(record);
        System.out.println("insertMultiColPkRecord: " + count);
    }
}
