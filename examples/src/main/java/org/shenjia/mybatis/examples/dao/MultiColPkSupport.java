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
// @formatter:off
package org.shenjia.mybatis.examples.dao;

import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class MultiColPkSupport extends AliasableSqlTable<MultiColPkSupport> {
    public static final MultiColPkSupport multiColPk = new MultiColPkSupport();

    public static final SqlColumn<Integer> qqNum = SqlColumn.of("QQ_NUM", multiColPk, JDBCType.INTEGER);

    public static final SqlColumn<String> realName = SqlColumn.of("REAL_NAME", multiColPk, JDBCType.VARCHAR);

    public static final SqlColumn<String> nickname = SqlColumn.of("NICKNAME", multiColPk, JDBCType.VARCHAR);

    public static final SqlColumn<String> password = SqlColumn.of("PASSWORD", multiColPk, JDBCType.VARCHAR);

    private MultiColPkSupport() {
        super("MULTI_COL_PK", MultiColPkSupport::new);
    }
}