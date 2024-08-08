// @formatter:off
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
package org.shenjia.mybatis.examples.entity;

import java.math.BigDecimal;
import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;
import org.shenjia.mybatis.spring.JdbcModel;
import org.springframework.jdbc.core.RowMapper;

public class NoPk implements JdbcModel<NoPk> {

    public static final Table TABLE = new Table();

    private Integer qqNum;

    private String realName;

    private String nickname;

    private String password;

    private BigDecimal balance;

    public Integer getQqNum() {
        return qqNum;
    }

    public void setQqNum(Integer qqNum) {
        this.qqNum = qqNum;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public RowMapper<NoPk> rowMapper() {
        return (rs, rowNum) -> {
            NoPk record = new NoPk();
            record.setQqNum(rs.getInt("QQ_NUM"));
            record.setRealName(rs.getString("REAL_NAME"));
            record.setNickname(rs.getString("NICKNAME"));
            record.setPassword(rs.getString("PASSWORD"));
            record.setBalance(rs.getBigDecimal("BALANCE"));
            return record;
        };
    }

    @Override
    public Table table() {
        return TABLE;
    }

    @Override
    public List<SqlColumn<?>> columns() {
        return TABLE.columns;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        NoPk other = (NoPk) that;
        return (this.getQqNum() == null ? other.getQqNum() == null : this.getQqNum().equals(other.getQqNum()))
            && (this.getRealName() == null ? other.getRealName() == null : this.getRealName().equals(other.getRealName()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getBalance() == null ? other.getBalance() == null : this.getBalance().equals(other.getBalance()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getQqNum() == null) ? 0 : getQqNum().hashCode());
        result = prime * result + ((getRealName() == null) ? 0 : getRealName().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getBalance() == null) ? 0 : getBalance().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", TABLE=").append(TABLE);
        sb.append(", qqNum=").append(qqNum);
        sb.append(", realName=").append(realName);
        sb.append(", nickname=").append(nickname);
        sb.append(", password=").append(password);
        sb.append(", balance=").append(balance);
        sb.append("]");
        return sb.toString();
    }

    public static class Table extends AliasableSqlTable<Table> {

        public final List<SqlColumn<?>> columns;

        public final SqlColumn<Integer> qqNum = column("QQ_NUM", JDBCType.INTEGER);

        public final SqlColumn<String> realName = column("REAL_NAME", JDBCType.VARCHAR);

        public final SqlColumn<String> nickname = column("NICKNAME", JDBCType.VARCHAR);

        public final SqlColumn<String> password = column("PASSWORD", JDBCType.VARCHAR);

        public final SqlColumn<BigDecimal> balance = column("BALANCE", JDBCType.DECIMAL);

        public Table() {
            super("NO_PK", Table::new);
            List<SqlColumn<?>> list = new ArrayList<>();
            list.add(qqNum);
            list.add(realName);
            list.add(nickname);
            list.add(password);
            list.add(balance);
            this.columns = Collections.unmodifiableList(list);
        }
    }
}