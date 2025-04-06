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
package org.shenjia.mybatis.examples.service;

import static org.shenjia.mybatis.examples.entity.MultiColPk.MULTI_COL_PK;

import java.util.List;

import org.mybatis.dynamic.sql.SqlBuilder;
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
		return multiColPkDao.selectPage(MULTI_COL_PK.columns, pageable,
		    c -> c.and(MULTI_COL_PK.NICKNAME, SqlBuilder.isEqualTo("123")), MULTI_COL_PK.REAL_NAME);
	}

	public List<MultiColPk> selectRangeMultiColPkRecord(Pageable pageable) {
		return multiColPkDao.selectRange(MULTI_COL_PK.columns, pageable,
		    c -> c.and(MULTI_COL_PK.REAL_NAME, SqlBuilder.isEqualTo("123")), MULTI_COL_PK.REAL_NAME);
	}
}
