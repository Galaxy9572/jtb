package org.ljy.jtb.domain;

import lombok.Data;
import org.ljy.jtb.annotations.Column;
import org.ljy.jtb.annotations.Index;
import org.ljy.jtb.annotations.Table;

import java.sql.JDBCType;

/**
 * @author ljy
 */
@Data
@Table(dbName = "jtb",tableName = "user")
public class User {
    @Column(columnName = "USER_ID",primaryKey = "pk_user_id",autoIncrement = true,jdbcType = JDBCType.INTEGER)
    private Integer userId;

    @Index
    @Column(columnName = "USER_NAME",jdbcType = JDBCType.VARCHAR)
    private String userName;

}
