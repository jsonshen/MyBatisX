package org.shenjia.mybatis.examples.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shenjia.mybatis.examples.ExamplesApplication;
import org.shenjia.mybatis.examples.entity.MultiColPk;
import org.shenjia.mybatis.examples.entity.NoPk;
import org.shenjia.mybatis.examples.entity.SingleColPk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = ExamplesApplication.class)
public class ExamplesServiceTests {

    @Autowired
    private ExamplesService examplesService;
    
    @Test
    public void test_insertNoPkRecord() {
        NoPk record = new NoPk();
        record.setQqNum(10000);
        record.setRealName("Real name");
        record.setNickname("Nick name");
        record.setPassword("password");
        int count = examplesService.insertNoPkRecord(record);
        Assertions.assertEquals(count, 1);
    }
    
    @Test
    public void test_insertSingleColPkRecord() {
        SingleColPk record = new SingleColPk();
        record.setQqNum(10000);
        record.setRealName("Real name");
        record.setNickname("Nick name");
        record.setPassword("password");
        int count = examplesService.insertSingleColPkRecord(record);
        Assertions.assertEquals(count, 1);
    }
    
    @Test
    public void test_insertMultiColPkRecord() {
        MultiColPk record = new MultiColPk();
        record.setQqNum(10000);
        record.setRealName("Real name");
        record.setNickname("Nick name");
        record.setPassword("password");
        int count = examplesService.insertMultiColPkRecord(record);
        Assertions.assertEquals(count, 1);
    }
    
}
