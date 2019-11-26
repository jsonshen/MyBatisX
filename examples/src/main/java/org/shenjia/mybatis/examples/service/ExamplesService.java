package org.shenjia.mybatis.examples.service;

import org.shenjia.mybatis.examples.dao.MultiColPkDao;
import org.shenjia.mybatis.examples.dao.NoPkDao;
import org.shenjia.mybatis.examples.dao.SingleColPkDao;
import org.shenjia.mybatis.examples.entity.MultiColPk;
import org.shenjia.mybatis.examples.entity.NoPk;
import org.shenjia.mybatis.examples.entity.SingleColPk;
import org.shenjia.mybatis.paging.Page;
import org.shenjia.mybatis.paging.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamplesService {

    @Autowired
    private NoPkDao noPkDao;

    @Autowired
    private SingleColPkDao singleColPkDao;

    @Autowired
    private MultiColPkDao multiColPkDao;

    public int insertNoPkRecord(NoPk record) {
        return noPkDao.insert(record);
    }

    public int insertSingleColPkRecord(SingleColPk record) {
        singleColPkDao.deleteByPrimaryKey(record.getQqNum());
        return singleColPkDao.insert(record);
    }

    public int insertMultiColPkRecord(MultiColPk record) {
        multiColPkDao.deleteByPrimaryKey(record.getQqNum(), record.getRealName());
        return multiColPkDao.insert(record);
    }

    public Page<MultiColPk> selectPageMultiColPkRecord(Pageable pageable) {
//        return multiColPkDao.selectPageByExample(pageable.getCurrentPage(), pageable.getPageSize())
//            .build()
//            .execute();
        return null;
    }
}
