package com.zzy.jpaCrud.common.utils.sqlParser;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.*;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.springframework.util.CollectionUtils;

import java.io.StringReader;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * jsqlparser解析SQL工具类
 * PlainSelect类不支持union、union all等请使用SetOperationList接口
 *
 */
public class SqlParserTool {
    /**
     * 由于jsqlparser没有获取SQL类型的原始工具，并且在下面操作时需要知道SQL类型，所以编写此工具方法
     * @param sql sql语句
     * @return sql类型，
     * @throws JSQLParserException
     */
    public static SqlType getSqlType(String sql) throws JSQLParserException {
        Statement sqlStmt = CCJSqlParserUtil.parse(new StringReader(sql));
        if (sqlStmt instanceof Alter) {
            return SqlType.ALTER;
        } else if (sqlStmt instanceof CreateIndex) {
            return SqlType.CREATEINDEX;
        } else if (sqlStmt instanceof CreateTable) {
            return SqlType.CREATETABLE;
        } else if (sqlStmt instanceof CreateView) {
            return SqlType.CREATEVIEW;
        } else if (sqlStmt instanceof Delete) {
            return SqlType.DELETE;
        } else if (sqlStmt instanceof Drop) {
            return SqlType.DROP;
        } else if (sqlStmt instanceof Execute) {
            return SqlType.EXECUTE;
        } else if (sqlStmt instanceof Insert) {
            return SqlType.INSERT;
        } else if (sqlStmt instanceof Merge) {
            return SqlType.MERGE;
        } else if (sqlStmt instanceof Replace) {
            return SqlType.REPLACE;
        } else if (sqlStmt instanceof Select) {
            return SqlType.SELECT;
        } else if (sqlStmt instanceof Truncate) {
            return SqlType.TRUNCATE;
        } else if (sqlStmt instanceof Update) {
            return SqlType.UPDATE;
        } else if (sqlStmt instanceof Upsert) {
            return SqlType.UPSERT;
        } else {
            return SqlType.NONE;
        }
    }
 
    /**
     * 获取sql操作接口,与上面类型判断结合使用
     * example:
     * String sql = "create table a(a string)";
     * SqlType sqlType = SqlParserTool.getSqlType(sql);
     * if(sqlType.equals(SqlType.SELECT)){
     *     Select statement = (Select) SqlParserTool.getStatement(sql);
     *  }
     * @param sql
     * @return
     * @throws JSQLParserException
     */
    public static Statement getStatement(String sql) throws JSQLParserException {
        Statement sqlStmt = CCJSqlParserUtil.parse(new StringReader(sql));
        return sqlStmt;
    }
 
    /**
     * 获取tables的表名
     * @param statement
     * @return
     */
    public static List<String> getTableList(Select statement){
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(statement);
        return tableList;
    }
 
    /**
     * 获取join层级
     * @param selectBody
     * @return
     */
    public static List<Join> getJoins(SelectBody selectBody){
        if(selectBody instanceof PlainSelect){
            List<Join> joins =((PlainSelect) selectBody).getJoins();
            return joins;
        }
        return new ArrayList<Join>();
    }
 
    /**
     *
     * @param selectBody
     * @return
     */
    public static List<Table> getIntoTables(SelectBody selectBody){
        if(selectBody instanceof PlainSelect){
            List<Table> tables = ((PlainSelect) selectBody).getIntoTables();
            return tables;
        }
        return new ArrayList<Table>();
    }
 
    /**
     *
     * @param selectBody
     * @return
     */
    public static void setIntoTables(SelectBody selectBody,List<Table> tables){
        if(selectBody instanceof PlainSelect){
            ((PlainSelect) selectBody).setIntoTables(tables);
        }
    }
 
    /**
     * 获取limit值
     * @param selectBody
     * @return
     */
    public static Limit getLimit(SelectBody selectBody){
        if(selectBody instanceof PlainSelect){
            Limit limit = ((PlainSelect) selectBody).getLimit();
            return limit;
        }
        return null;
    }
 
    /**
     * 为SQL增加limit值
     * @param selectBody
     * @param l
     */
    public static void setLimit(SelectBody selectBody,long l){
        if(selectBody instanceof PlainSelect){
            Limit limit = new Limit();
            limit.setRowCount(new LongValue(String.valueOf(l)));
            ((PlainSelect) selectBody).setLimit(limit);
        }
    }
 
    /**
     * 获取FromItem不支持子查询操作
     * @param selectBody
     * @return
     */
    public static FromItem getFromItem(SelectBody selectBody){
        if(selectBody instanceof PlainSelect){
            FromItem fromItem = ((PlainSelect) selectBody).getFromItem();
            return fromItem;
        }else if(selectBody instanceof WithItem){
            SqlParserTool.getFromItem(((WithItem) selectBody).getSelectBody());
        }
        return null;
    }
 
    /**
     * 获取子查询
     * @param selectBody
     * @return
     */
    public static SubSelect getSubSelect(SelectBody selectBody){
        if(selectBody instanceof PlainSelect){
            FromItem fromItem = ((PlainSelect) selectBody).getFromItem();
            if(fromItem instanceof SubSelect){
                return ((SubSelect) fromItem);
            }
        }else if(selectBody instanceof WithItem){
            SqlParserTool.getSubSelect(((WithItem) selectBody).getSelectBody());
        }
        return null;
    }
 
    /**
     * 判断是否为多级子查询
     * @param selectBody
     * @return
     */
    public static boolean isMultiSubSelect(SelectBody selectBody){
        if(selectBody instanceof PlainSelect){
            FromItem fromItem = ((PlainSelect) selectBody).getFromItem();
            if(fromItem instanceof SubSelect){
                SelectBody subBody = ((SubSelect) fromItem).getSelectBody();
                if(subBody instanceof PlainSelect){
                    FromItem subFromItem = ((PlainSelect) subBody).getFromItem();
                    if(subFromItem instanceof SubSelect){
                        return true;
                    }
                }
            }
        }
        return false;
    }
 
    /**
     * 获取查询字段
     * @param selectBody
     * @return
     */
    public static List<SelectItem> getSelectItems(SelectBody selectBody){
        if(selectBody instanceof PlainSelect){
            List<SelectItem> selectItems = ((PlainSelect) selectBody).getSelectItems();
            return selectItems;
        }
        return null;
    }

    /**
     * 获取SQL中的全部表名
     *
     * @param sql
     * @return
     */
    public static String getTableName(String sql) {
        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            List<String> tableNameList = tablesNamesFinder.getTableList(statement);
            if (!CollectionUtils.isEmpty(tableNameList)) {
                StringBuffer allTableNames = new StringBuffer();
                tableNameList.forEach(tableName -> {
                    allTableNames.append(tableName + ",");
                });
                String allTableName = allTableNames.toString().substring(0, allTableNames.toString().length() - 1);
                return allTableName;
            }
        } catch (JSQLParserException e) {

        }
        return null;
    }

    public static void parserColumnExpression(Expression expression, Set<String> set) {
        Expression leftExpression = null;
        if (expression instanceof Column) {
            set.add(((Column) leftExpression).getColumnName());
        } else if (expression instanceof InExpression) {
            leftExpression = ((InExpression)expression).getLeftExpression();
            set.add(((Column) leftExpression).getColumnName());
        } else if (expression instanceof IsNullExpression) {
            leftExpression = ((IsNullExpression)expression).getLeftExpression();
            set.add(((Column) leftExpression).getColumnName());
        } else if (expression instanceof BinaryExpression) {
            leftExpression = ((BinaryExpression)expression).getLeftExpression();
            set.add(((Column) leftExpression).getColumnName());
        } else if (expression instanceof Parenthesis) {//递归调用
            Expression expression1 = ((Parenthesis) expression).getExpression();
            getParser(expression1, set);
        }
        if (leftExpression != null && leftExpression instanceof Column) {
            set.add(((Column) leftExpression).getColumnName());
        }
    }

    private static Set<String> getParser(Expression expression, Set<String> set) {
        //初始化接受获得的字段信息
        if (expression instanceof BinaryExpression) {
            //获得左边表达式
            Expression leftExpression = ((BinaryExpression) expression).getLeftExpression();
            //获得左边表达式为Column对象，则直接获得列名
            if (leftExpression instanceof Parenthesis) {//递归调用
                Expression expression1 = ((Parenthesis) leftExpression).getExpression();
                getParser(expression1, set);
            } else {
                parserColumnExpression(leftExpression, set);
            }

            //获得右边表达式，并分解
            Expression rightExpression = ((BinaryExpression) expression).getRightExpression();
            if (rightExpression instanceof Parenthesis) {//递归调用
                Expression expression1 = ((Parenthesis) rightExpression).getExpression();
                getParser(expression1, set);
            } else {
                parserColumnExpression(rightExpression, set);
            }
        } else if (expression instanceof Parenthesis) {//递归调用
            Expression expression1 = ((Parenthesis) expression).getExpression();
            getParser(expression1, set);
        } else {
            parserColumnExpression(expression, set);
        }
        return set;
    }


    /**
     * 获取SQL中的where后面的条件名称
     *
     * @param sql
     * @return
     * @throws JSQLParserException
     */
    public static Set<String> getColumnNames(String sql) throws JSQLParserException {
        Set<String> columnNames = null;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(sql);
        Statement statement = CCJSqlParserUtil.parse(new StringReader(stringBuffer.toString()));
        Expression where = null;
        if (statement instanceof Select) {
            Select istatement = (Select) statement;
            where = ((PlainSelect) istatement.getSelectBody()).getWhere();
        }else if(statement instanceof Update){
            Update updateSta = (Update) statement;
            where = updateSta.getWhere();
            System.out.println(where);
        }
        if (where != null) {
            columnNames = getParser(where, new HashSet<>());
        }

        return columnNames;
    }

    public static void main(String[] args) throws JSQLParserException, SQLSyntaxErrorException {
//        String sql = " update a set b = 1 where (id = 1 and name = 2) or (game like \'bb\' and org in (\'aa\',\'bb\')) ";
//        Set<String> columnNames = getColumnNames(sql);
//        System.out.println(columnNames);

        String sql = "select t.name, t.id, (select p.name from post p where p.id = t.post_id)" +
                "from acct t where t.id = 10 and exists (select r.id from role r where r.id = t.role_id) ";
        String dbType = "mysql";
        System.out.println("原始SQL 为 ： " + sql);
        String result = SQLUtils.format(sql, dbType);
        System.out.println(result);
        SQLUpdateStatement statement = (SQLUpdateStatement) parser(sql, dbType);
        System.out.println(statement.getWhere());
        System.out.println("修改表名后的SQL 为 ： [" + statement.toString() + "]");
    }

    public static SQLStatement parser(String sql, String dbType) throws SQLSyntaxErrorException {
        List<SQLStatement> list = SQLUtils.parseStatements(sql, dbType);
        if (list.size() > 1) {
            throw new SQLSyntaxErrorException("MultiQueries is not supported,use single query instead ");
        }
        return list.get(0);
    }
 
}