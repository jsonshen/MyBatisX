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

public final class SingleColPkSupport extends AliasableSqlTable<SingleColPkSupport> {
    public static final SingleColPkSupport singleColPk = new SingleColPkSupport();

    public static final SqlColumn<Integer> qqNum = SqlColumn.of("QQ_NUM", singleColPk, JDBCType.INTEGER);

    public static final SqlColumn<String> realName = SqlColumn.of("REAL_NAME", singleColPk, JDBCType.VARCHAR);

    public static final SqlColumn<String> nickname = SqlColumn.of("NICKNAME", singleColPk, JDBCType.VARCHAR);

    public static final SqlColumn<String> password = SqlColumn.of("PASSWORD", singleColPk, JDBCType.VARCHAR);

    private SingleColPkSupport() {
        super("SINGLE_COL_PK", SingleColPkSupport::new);
    }
}