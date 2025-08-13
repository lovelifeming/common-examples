package com.zsm.commonexample.thridtools;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.comment.Comment;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.Index;
import net.sf.jsqlparser.statement.drop.Drop;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ConvertMySQLToPG
{
  //region 建表语句参数配置
  private static Charset CHARSET = StandardCharsets.UTF_8;    // 编码格式
  private static boolean IF_EXISTS= false;             // 创建表时，是否添加IF EXISTS

  private static boolean UPPER_CASE= false;    // 是否将字段名大写
  private static boolean RETAIN_QUOTE = false;    // 是否 保留反单引号
  private static boolean RETAIN_CHARACTER_SET = false;   // 是否保留 CHARACTER SET utf8mb4
  private static boolean RETAIN_COLLATE = false;         // 是否 保留 COLLATE utf8mb4_bin
  private static boolean RETAIN_INDEX = false;          // 是否保留 INDEX
  private static boolean RETAIN_KEY = true;          // 是否保留 KEY
  private static boolean RETAIN_TABLE_OPTIONS = false;   // 是否保留表配置项

  private static boolean ADD_TABLE_COMMENTS= true;   // 是否添加表注释
  private static boolean ADD_COLUMN_COMMENTS= true;  // 是否添加表字段注释
  private static boolean ADD_DROP_TABLE_SQL= true;    // 是否添加 drop table 语句

  private static String REGEX_PARTITION_BY ="(?s)PARTITION\\s+BY\\s+[^)]*\\)[^;]*;";  //  分区语句
  //endregion

  /**
   * @Description: 将MySQL导出的表结构转换成 PostgreSQL
   */
  public static void main(String[] args)
      throws IOException, JSQLParserException
  {
    // MySQL DDL路径
    String mysqlDDLPath = "D:\\old_create_table.sql";
    String postgreSqlDDLPath = "D:\\new_create_table.sql";
    String dDLs = FileUtils.readFileToString(new File(mysqlDDLPath), CHARSET);
    File file = new File(postgreSqlDDLPath);

    System.out.println("++++++++++开始转换SQL语句+++++++++++++");
    dDLs = dDLs.replaceAll(REGEX_PARTITION_BY, ";");
    if(dDLs.trim().isEmpty()){
      System.out.println("old create table file is empty!");
      return;
    }
    Statements statements = CCJSqlParserUtil.parseStatements(dDLs);
    for (Statement statement : statements.getStatements())
    {
      if (statement instanceof CreateTable)
      {
        CreateTable ct = (CreateTable) statement;
        Table table = ct.getTable();
        List<ColumnDefinition> columnDefinitions = ct.getColumnDefinitions();
        List<String> comments = new ArrayList<>();
        List<ColumnDefinition> collect = columnDefinitions.stream().peek(columnDefinition -> {
          handleColumn(table, comments, columnDefinition);
        }).collect(Collectors.toList());
        if(ADD_TABLE_COMMENTS){
          List<String> tableOptionsStrings = ct.getTableOptionsStrings();
          if(tableOptionsStrings==null)
            tableOptionsStrings = new ArrayList<>();
          int index = getCommentIndex(tableOptionsStrings);
          if(index != -1){        // 如果存在表注释
            String tableComment = tableOptionsStrings.get(index + 2);
            String commentSql = genCommentSql(table.toString(), null, tableComment);
            comments.add(commentSql);
          }
        }
        if(!RETAIN_TABLE_OPTIONS){
          ct.setTableOptionsStrings(null);
        }
        ct.setColumnDefinitions(collect);
        if(ct.getIndexes() != null && ct.getIndexes().size() > 0){
          Stream<Index> indexStream = ct.getIndexes().stream().peek(n -> n.setIndexSpec(null));
          if(!RETAIN_INDEX){
            indexStream = indexStream.filter(n -> !"INDEX".equals(n.getType()));
          }
          if(!RETAIN_KEY){
            indexStream = indexStream.filter(n -> !"KEY".equals(n.getType()));
          }
          ct.setIndexes(indexStream.collect(Collectors.toList()));
        }

        String createSQL = ct.toString()
            .replaceAll("BIGINT UNIQUE NOT NULL AUTO_INCREMENT", "BIGSERIAL PRIMARY KEY")
            .replaceAll("BIGINT NULL AUTO_INCREMENT", "BIGSERIAL PRIMARY KEY")
            .replaceAll("BIGINT NOT NULL AUTO_INCREMENT", "BIGSERIAL PRIMARY KEY")
            .replaceAll("INT NOT NULL AUTO_INCREMENT", "BIGSERIAL PRIMARY KEY")
            .replaceAll("INT NULL AUTO_INCREMENT", "BIGSERIAL PRIMARY KEY")
            .replaceAll("tinyint", "smallint")
            .replaceAll("mediumtext","text")
            .replaceAll("longtext","text")
            .replaceAll("datetime", "timestamp")
            .replaceAll(",(?![^(]*\\))", ",\n")
            .replaceAll(", PRIMARY KEY \\(\"id\"\\)", "");
        createSQL = RETAIN_QUOTE ? createSQL :createSQL.replaceAll("`", "");
        createSQL = !IF_EXISTS ? createSQL : createSQL.replaceAll("IF NOT EXISTS", "");

        FileUtils.write(file,createSQL + ";", CHARSET,true);
        if(ADD_COLUMN_COMMENTS){
          FileUtils.writeLines(file,comments,true);
          FileUtils.write(file,"\n",true);
        }
      }
      else if (statement instanceof Drop)
      {
        Drop drop = (Drop) statement;
        if (drop.getType().equals("TABLE") && ADD_DROP_TABLE_SQL)
        {
          drop.setIfExists(IF_EXISTS);
          String dropSql = RETAIN_QUOTE ? drop.toString():drop.toString().replaceAll("`", "");
          FileUtils.write(file, dropSql+ ";\n", CHARSET, true);
        }
      }
      else if ((ADD_COLUMN_COMMENTS || ADD_TABLE_COMMENTS) && statement instanceof Comment)
      {
        FileUtils.write(file, statement + ";\n", CHARSET, true);
      }
      else if (!RETAIN_KEY && statement instanceof Alter)
      {
        FileUtils.write(file, statement + ";\n", CHARSET, true);
      }
      else
      {
        System.out.println("+++++++++++非CreateTable语句+++++++++++");
        System.out.println(statement.toString());
      }
    }
  }

  /**
   * 转换列的类型，处理列的大小写，解析生成字段注释
   */
  private static void handleColumn(Table table, List<String> comments, ColumnDefinition columnDefinition)
  {
    List<String> columnSpecStrings = columnDefinition.getColumnSpecs();
    if(columnSpecStrings==null)
      columnSpecStrings = new ArrayList<>();
    int commentIndex = getCommentIndex(columnSpecStrings);
    if (commentIndex != -1 && ADD_COLUMN_COMMENTS)
    {
      int commentStringIndex = commentIndex + 1;
      String commentString = columnSpecStrings.get(commentStringIndex);
      String commentSql = genCommentSql(table.toString(), columnDefinition.getColumnName(),
          commentString);
      comments.add(commentSql);
      columnSpecStrings.remove(commentStringIndex);
      columnSpecStrings.remove(commentIndex);
    }
    if (!RETAIN_COLLATE && columnSpecStrings.contains("COLLATE"))
    {
      int index = columnSpecStrings.indexOf("COLLATE");
      columnSpecStrings.remove(index);
      columnSpecStrings.remove(index);
    }
    if (!RETAIN_CHARACTER_SET){
      columnDefinition.getColDataType().setCharacterSet(null);
    }
    if( "bigint".equals(columnDefinition.getColDataType().getDataType())){
      columnDefinition.getColDataType().setArgumentsStringList( null);
    }
    if( "int".equals(columnDefinition.getColDataType().getDataType())){
      columnDefinition.getColDataType().setArgumentsStringList( null);
    }
    if(UPPER_CASE){
      columnDefinition.setColumnName(columnDefinition.getColumnName().toUpperCase());
    }else {
      columnDefinition.setColumnName(columnDefinition.getColumnName().toLowerCase());
    }
    columnDefinition.setColumnSpecs(columnSpecStrings);
  }

  /**
   * 获得注释的下标
   * @param columnSpecStrings columnSpecStrings
   * @return 下标
   */
  private static int getCommentIndex(List<String> columnSpecStrings) {
    for (int i = 0; i < columnSpecStrings.size(); i++) {
      if ("COMMENT".equalsIgnoreCase(columnSpecStrings.get(i))) {
        return i;
      }
    }
    return -1;
  }
  /**
   * 生成COMMENT语句
   * @param table        表名
   * @param column       字段名
   * @param commentValue 描述文字
   * @return COMMENT语句
   */
  private static String genCommentSql(String table, String column, String commentValue) {
    table = RETAIN_QUOTE ? table:table.replaceAll("`", "");
    table = UPPER_CASE ? table.toUpperCase(): table.toLowerCase();
    if( column == null){
      return String.format("COMMENT ON TABLE %s IS %s ;", table, commentValue);
    }
    column = RETAIN_QUOTE ? column:column.replaceAll("`", "");
    column = UPPER_CASE ? column.toUpperCase(): column.toLowerCase();
    return String.format("COMMENT ON COLUMN %s.%s IS %s ;", table, column, commentValue);
  }

}
