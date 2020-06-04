package com.example.roombasic;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Word {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "english_word")
    private String word;

    @ColumnInfo(name = "chinese_meaning")
    private String chineseMessing;

    @ColumnInfo(name = "chinese_invisible")
    private boolean chineseInvisible;

    boolean isChineseInvisible() {
        return chineseInvisible;
    }

    void setChineseInvisible(boolean chineseInvisible) {
        this.chineseInvisible = chineseInvisible;
    }

    Word(String word, String chineseMessing) {
        this.word = word;
        this.chineseMessing = chineseMessing;
    }

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    String getChineseMessing() {
        return chineseMessing;
    }

    public void setChineseMessing(String chineseMessing) {
        this.chineseMessing = chineseMessing;
    }
}
