package org.ljy.jtb.builder.domain;

import lombok.Data;

import java.sql.JDBCType;

/**
 * @author ljy
 */
@Data
public class ColumnDO {

    private String columnName;

    private String primaryKey ;

    private JDBCType jdbcType;

    private Boolean autoIncrement;

    private String comment ;

    private String charSet;


    public static final class Builder {
        private String columnName;
        private String primaryKey ;
        private JDBCType jdbcType;
        private Boolean autoIncrement;
        private String comment ;
        private String charSet;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder columnName(String columnName) {
            this.columnName = columnName;
            return this;
        }

        public Builder primaryKey(String primaryKey) {
            this.primaryKey = primaryKey;
            return this;
        }

        public Builder jdbcType(JDBCType jdbcType) {
            this.jdbcType = jdbcType;
            return this;
        }

        public Builder autoIncrement(Boolean autoIncrement) {
            this.autoIncrement = autoIncrement;
            return this;
        }

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder charSet(String charSet) {
            this.charSet = charSet;
            return this;
        }

        public ColumnDO build() {
            ColumnDO columnDO = new ColumnDO();
            columnDO.setColumnName(columnName);
            columnDO.setPrimaryKey(primaryKey);
            columnDO.setJdbcType(jdbcType);
            columnDO.setAutoIncrement(autoIncrement);
            columnDO.setComment(comment);
            columnDO.setCharSet(charSet);
            return columnDO;
        }
    }
}
