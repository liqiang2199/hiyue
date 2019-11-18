package com.lib.network.db;


public interface DataSheetManager {
    void add(String key,String value);//添加数据
    void insert(String key,String value);//插入数据
    void upData(String key,String value);//更新数据
    void clearData(String key);//清空数据
    String query(String key);//查询数据
    void deleteSheet();//删除表 清空全部
    boolean find(String key);//查找是否存在key
}
