package org.ljy.jtb.builder;

import org.ljy.jtb.annotations.Column;
import org.ljy.jtb.annotations.Table;
import org.ljy.jtb.builder.domain.ColumnDO;
import org.ljy.jtb.builder.domain.TableDO;
import org.ljy.jtb.config.domain.BaseConfig;
import org.ljy.jtb.util.LogUtil;
import org.ljy.jtb.util.PackageScanUtil;
import org.ljy.jtb.util.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ljy
 */
public class MySqlTableBuilderImpl implements ITableBuilder {
    @Override
    public List<String> build(BaseConfig baseConfig) throws IllegalArgumentException {
        if (baseConfig == null) {
            throw new IllegalArgumentException("BaseConfig Is Null");
        }
        String scanPackage = baseConfig.getScanPackage();
        Set<Class<?>> classes = PackageScanUtil.scanClasses(scanPackage, Table.class);
        List<TableDO> tables = getTables(classes);
        StringBuilder sqlBuilder = new StringBuilder();
        List<String> sqlList = new ArrayList<>(classes.size());
        if(tables == null || tables.size() == 0) {
            return null;
        }

        buildSql(tables, sqlBuilder);


        return null;
    }

    private void buildSql(List<TableDO> tables, StringBuilder sqlBuilder) {
        for (TableDO table : tables) {
            String tableName = table.getTableName();
            String dbName = table.getDbName();
            String charSet = table.getCharSet();
            String engineType = table.getEngineType();
            List<ColumnDO> columns = table.getColumns();
            ArrayList<String> primaryKeys = new ArrayList<String>(columns.size());
            sqlBuilder.append("CREATE TABLE ").append(dbName).append(".`").append(tableName).append("`").append("(\n");
            ColumnDO column;
            String primaryKey = "";
            for (int i = 0; i < columns.size(); i++) {
                column = columns.get(i);
                String comment = columns.get(i).getComment();
                JDBCType jdbcType = column.getJdbcType();
                String columnName = column.getColumnName();
                String primaryKeyName = column.getPrimaryKey();
                if(!StringUtil.isBlank(primaryKeyName)){
                    primaryKey = column.getPrimaryKey();
                    primaryKeys.add(columnName);
                }
                String columnCharset = column.getCharSet();
                Boolean autoIncrement = column.getAutoIncrement();
                sqlBuilder.append("\t`").append(columnName).append("` ");
                sqlBuilder.append(jdbcType).append(" ");
                if(autoIncrement){
                    sqlBuilder.append("AUTO_INCREMENT ");
                }
                if (i < columns.size() - 1) {
                    sqlBuilder.append("CHARACTER SET ").append(columnCharset).append(" ");
                    sqlBuilder.append(comment).append(",\n");
                } else {
                    sqlBuilder.append("CHARACTER SET ").append(columnCharset).append(" ");
                    sqlBuilder.append(comment).append("\n");
                }
            }
            if (primaryKeys.size() > 0) {
                sqlBuilder.append(",\tPRIMARY KEY ").append(primaryKey).append(" (");
                for (String key : primaryKeys) {
                    sqlBuilder.append("`").append(key).append("`");
                }
                sqlBuilder.append(") USING BTREE\n");
            }

            //TODO 完善索引的build和逗号的细节

            sqlBuilder.append(") ENGINE=").append(engineType).append(" DEFAULT CHARSET=").append(charSet).append(";");
            LogUtil.debug(sqlBuilder.toString());
        }
    }

    private List<TableDO> getTables(Set<Class<?>> classes) {
        if (classes == null || classes.size() == 0) {
            return null;
        }
        TableDO tableDO = null;
        List<ColumnDO> columns = null;
        List<TableDO> tables = new ArrayList<>(classes.size());
        for (Class<?> aClass : classes) {
            Table table = aClass.getAnnotation(Table.class);
            String dbName = table.dbName();
            String tableName = table.tableName();
            String tableCharSet = table.charSet();
            String engineType = table.engineType();
            //从类中掏出所有的字段
            Field[] declaredFields = aClass.getDeclaredFields();
            if (declaredFields != null && declaredFields.length > 0) {
                columns = getColumns(declaredFields,tableName);
            }
            tableDO = TableDO.Builder.builder().dbName(dbName).tableName(tableName).
                    engineType(engineType).charSet(tableCharSet).columns(columns).build();
            LogUtil.debug("Target DB Name: {}, Table Name: {}, Engine Type: {}," +
                    " Charset : {}", dbName, tableName, engineType, tableCharSet);
        }
        tables.add(tableDO);
        return tables;
    }

    private List<ColumnDO> getColumns(Field[] declaredFields,String tableName) {
        List<ColumnDO> columns;
        columns = new ArrayList<>(declaredFields.length);
        for (Field declaredField : declaredFields) {
            //解析每个字段的注解
            Annotation[] annotations = declaredField.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Column) {
                    String comment = ((Column) annotation).comment();
                    JDBCType jdbcType = ((Column) annotation).jdbcType();
                    String columnCharset = ((Column) annotation).charSet();
                    String columnName = ((Column) annotation).columnName();
                    String primaryKey = ((Column) annotation).primaryKey();
                    boolean autoIncrement = ((Column) annotation).autoIncrement();
                    ColumnDO columnDO = ColumnDO.Builder.builder().columnName(columnName)
                            .primaryKey(primaryKey).autoIncrement(autoIncrement)
                            .comment(comment).charSet(columnCharset).jdbcType(jdbcType).build();
                    columns.add(columnDO);
                    LogUtil.debug("Table: {}, Column Name: {}, Primary Key: {}, JdbcType: {}," +
                            " Auto Increment: {}, Comment: {},Charset: {}",tableName,columnName,primaryKey,
                            jdbcType,autoIncrement,comment,columnCharset);
                }
            }
        }
        return columns;
    }
}
