package org.shenjia.mybatis.examples.dao;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SingleColPkSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SingleColPk singleColPk = new SingleColPk();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> qqNum = singleColPk.qqNum;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> realName = singleColPk.realName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> nickname = singleColPk.nickname;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> password = singleColPk.password;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class SingleColPk extends AliasableSqlTable<SingleColPk> {
        public final SqlColumn<Integer> qqNum = column("QQ_NUM", JDBCType.INTEGER);

        public final SqlColumn<String> realName = column("REAL_NAME", JDBCType.VARCHAR);

        public final SqlColumn<String> nickname = column("NICKNAME", JDBCType.VARCHAR);

        public final SqlColumn<String> password = column("PASSWORD", JDBCType.VARCHAR);

        public SingleColPk() {
            super("SINGLE_COL_PK", SingleColPk::new);
        }
    }
}