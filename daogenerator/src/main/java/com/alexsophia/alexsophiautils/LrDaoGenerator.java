package com.alexsophia.alexsophiautils;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class LrDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "greendao");
        addNote(schema);
        new DaoGenerator().generateAll(schema, "./../AlexSophiaUtils/app/src/main/java-gen");
    }

    /**
     * @param schema
     */
    private static void addNote(Schema schema) {
        Entity download = schema.addEntity("DownLoadHistory");
        download.addIdProperty().primaryKey();
        download.addStringProperty("down_url").unique();// 下载文件的URL地址
        download.addLongProperty("total_size");//下载文件的总大小，单位字节
        download.addLongProperty("curr_size");//当前下载的文件大小，单位字节
        download.addFloatProperty("down_progress");//下载进度，%
        download.addStringProperty("target_url");//本地保存下载文件的地址
        download.addIntProperty("down_state");//下载状态
    }
}
