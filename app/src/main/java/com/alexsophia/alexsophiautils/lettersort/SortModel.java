package com.alexsophia.alexsophiautils.lettersort;

/**
 * 用于在列表中排序的信息
 */
public class SortModel {
    private int uid; // 用户ID
    private String avatar; // 用户头像
    private String name;    // 用户姓名
    private String subTitle; // 用户信息
    private boolean isChecked; // 是否选中
    private String sortLetters;   //显示数据拼音的首字母

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
