package org.ljy.jtb.builder.domain;

import lombok.Data;

import java.util.List;

/**
 * @author ljy
 */
@Data
public class TableDO {

    private String dbName;

    private String tableName;

    private String engineType;

    private String charSet;

    private List<ColumnDO> columns;

    private List<IndexDO> indices;

    public static final class Builder {
        private String dbName;
        private String tableName;
        private String engineType;
        private String charSet;
        private List<ColumnDO> columns;
        private List<IndexDO> indices;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder dbName(String dbName) {
            this.dbName = dbName;
            return this;
        }

        public Builder tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public Builder engineType(String engineType) {
            this.engineType = engineType;
            return this;
        }

        public Builder charSet(String charSet) {
            this.charSet = charSet;
            return this;
        }

        public Builder columns(List<ColumnDO> columns) {
            this.columns = columns;
            return this;
        }

        public Builder indices(List<IndexDO> indices) {
            this.indices = indices;
            return this;
        }

        public TableDO build() {
            TableDO tableDO = new TableDO();
            tableDO.setDbName(dbName);
            tableDO.setTableName(tableName);
            tableDO.setEngineType(engineType);
            tableDO.setCharSet(charSet);
            tableDO.setColumns(columns);
            tableDO.setIndices(indices);
            return tableDO;
        }
    }
}
