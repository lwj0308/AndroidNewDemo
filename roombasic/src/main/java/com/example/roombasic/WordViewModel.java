package com.example.roombasic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

//管理界面的数据
public class WordViewModel extends AndroidViewModel {
    private WordRepository repository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        repository = new WordRepository(application);
    }

    LiveData<List<Word>> getAllWordsLive() {
        return repository.getAllWordsLive();
    }

    LiveData<List<Word>> findvWordsPattern(String pattern){
        return repository.findWordsPattern(pattern);
    }

    void insertWords(Word... words) {
        repository.insertWords(words);
    }

    void updateWords(Word... words) {
        repository.updateWords(words);
    }

    void deleteWords(Word... words) {
        repository.deleteWords(words);
    }

    void deleteAllWords() {
        repository.deleteAllWords();
    }

}
