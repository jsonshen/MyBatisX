package org.shenjia.mybatis.examples.dao;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class MultiColPkSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final MultiColPk multiColPk = new MultiColPk();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> qqNum = multiColPk.qqNum;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> realName = multiColPk.realName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> nickname = multiColPk.nickname;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> password = multiColPk.password;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class MultiColPk extends SqlTable {
        public final SqlColumn<Integer> qqNum = column("QQ_NUM", JDBCType.INTEGER);

        public final SqlColumn<String> realName = column("REAL_NAME", JDBCType.VARCHAR);

        public final SqlColumn<String> nickname = column("NICKNAME", JDBCType.VARCHAR);

        public final SqlColumn<String> password = column("PASSWORD", JDBCType.VARCHAR);

        public MultiColPk() {
            super("MULTI_COL_PK");
        }
    }
}