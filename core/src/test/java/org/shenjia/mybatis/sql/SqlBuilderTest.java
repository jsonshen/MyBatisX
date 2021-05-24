package org.shenjia.mybatis.sql;

import static org.junit.Assert.assertEquals;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

import java.sql.JDBCType;

import org.junit.Test;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.shenjia.mybatis.condition.IsEqualsTo;
import org.shenjia.mybatis.condition.IsNullOrEqualTo;

public class SqlBuilderTest {

    @Test
    public void test_isNullOrEqualTo() {
        SelectModel selectModel = select(Support.qqNum).from(Support.singleColPk).where(Support.qqNum, IsNullOrEqualTo.of(123)).build();
        String springJdbcSql = selectModel.render(RenderingStrategies.SPRING_NAMED_PARAMETER).getSelectStatement();
        assertEquals(springJdbcSql, "select QQ_NUM from SINGLE_COL_PK where (QQ_NUM is null or QQ_NUM=:p1)");
        String mybatisSql = selectModel.render(RenderingStrategies.MYBATIS3).getSelectStatement();
        assertEquals(mybatisSql, "select QQ_NUM from SINGLE_COL_PK where (QQ_NUM is null or QQ_NUM=#{parameters.p1,jdbcType=INTEGER})");
    }
    
    @Test
    public void test_isEqualsTo() {
        SelectModel selectModel = select(Support.qqNum).from(Support.singleColPk).where(Support.qqNum, IsEqualsTo.of(123, 456)).build();
        String springJdbcSql = selectModel.render(RenderingStrategies.SPRING_NAMED_PARAMETER).getSelectStatement();
        assertEquals(springJdbcSql, "select QQ_NUM from SINGLE_COL_PK where (QQ_NUM = :p1 or QQ_NUM = :p2)");
        String mybatisSql = selectModel.render(RenderingStrategies.MYBATIS3).getSelectStatement();
        assertEquals(mybatisSql, "select QQ_NUM from SINGLE_COL_PK where (QQ_NUM = #{parameters.p1,jdbcType=INTEGER} or QQ_NUM = #{parameters.p2,jdbcType=INTEGER})");
    }
    
    static final class Support {
    	
        public static final SingleColPk singleColPk = new SingleColPk();

        public static final SqlColumn<Integer> qqNum = singleColPk.qqNum;

        public static final SqlColumn<String> realName = singleColPk.realName;

        public static final SqlColumn<String> nickname = singleColPk.nickname;

        public static final SqlColumn<String> password = singleColPk.password;

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


