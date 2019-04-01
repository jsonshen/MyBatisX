package org.shenjia.mybatis.examples.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class NoPkDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final NoPk noPk = new NoPk();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> qqNum = noPk.qqNum;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> realName = noPk.realName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> nickname = noPk.nickname;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> password = noPk.password;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class NoPk extends SqlTable {
        public final SqlColumn<Integer> qqNum = column("QQ_NUM", JDBCType.INTEGER);

        public final SqlColumn<String> realName = column("REAL_NAME", JDBCType.VARCHAR);

        public final SqlColumn<String> nickname = column("NICKNAME", JDBCType.VARCHAR);

        public final SqlColumn<String> password = column("PASSWORD", JDBCType.VARCHAR);

        public NoPk() {
            super("NO_PK");
        }
    }
}