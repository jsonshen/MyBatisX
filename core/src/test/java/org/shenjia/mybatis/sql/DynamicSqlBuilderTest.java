package org.shenjia.mybatis.sql;

import static org.junit.Assert.assertEquals;
import static org.mybatis.dynamic.sql.SqlBuilder.select;
import static org.shenjia.mybatis.sql.DynamicSqlBuilder.isEqualsTo;
import static org.shenjia.mybatis.sql.DynamicSqlBuilder.isNullOrEqualTo;

import java.sql.JDBCType;

import javax.annotation.Generated;

import org.junit.Test;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.SelectModel;

public class DynamicSqlBuilderTest {

    @Test
    public void test_isNullOrEqualTo() {
        SelectModel selectModel = select(Support.qqNum).from(Support.singleColPk).where(Support.qqNum, isNullOrEqualTo(123)).build();
        String springJdbcSql = selectModel.render(RenderingStrategies.SPRING_NAMED_PARAMETER).getSelectStatement();
        assertEquals(springJdbcSql, "select QQ_NUM from SINGLE_COL_PK where (QQ_NUM is null or QQ_NUM = :p1)");
        String mybatisSql = selectModel.render(RenderingStrategies.MYBATIS3).getSelectStatement();
        assertEquals(mybatisSql, "select QQ_NUM from SINGLE_COL_PK where (QQ_NUM is null or QQ_NUM = #{parameters.p1,jdbcType=INTEGER})");
    }
    
    @Test
    public void test_isEqualsTo() {
        SelectModel selectModel = select(Support.qqNum).from(Support.singleColPk).where(Support.qqNum, isEqualsTo(123, 456)).build();
        String springJdbcSql = selectModel.render(RenderingStrategies.SPRING_NAMED_PARAMETER).getSelectStatement();
        assertEquals(springJdbcSql, "select QQ_NUM from SINGLE_COL_PK where (QQ_NUM = :p1 or QQ_NUM = :p2)");
        String mybatisSql = selectModel.render(RenderingStrategies.MYBATIS3).getSelectStatement();
        assertEquals(mybatisSql, "select QQ_NUM from SINGLE_COL_PK where (QQ_NUM = #{parameters.p1,jdbcType=INTEGER} or QQ_NUM = #{parameters.p2,jdbcType=INTEGER})");
    }
    
    static final class Support {
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
        public static final class SingleColPk extends SqlTable {
            public final SqlColumn<Integer> qqNum = column("QQ_NUM", JDBCType.INTEGER);

            public final SqlColumn<String> realName = column("REAL_NAME", JDBCType.VARCHAR);

            public final SqlColumn<String> nickname = column("NICKNAME", JDBCType.VARCHAR);

            public final SqlColumn<String> password = column("PASSWORD", JDBCType.VARCHAR);

            public SingleColPk() {
                super("SINGLE_COL_PK");
            }
        }
    }
}


