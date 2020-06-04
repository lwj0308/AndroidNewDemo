package com.example.roombasic;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

class WordRepository {
    private LiveData<List<Word>> allWordsLive;
    private final WordDao wordDao;

    WordRepository(Context context) {
        WordDatabase wordDatabase = WordDatabase.getDatabase(context.getApplicationContext());
        wordDao = wordDatabase.getWordDao();
        allWordsLive = wordDao.getAllWordsLive();
    }

    LiveData<List<Word>> getAllWordsLive() {
        return allWordsLive;
    }

    LiveData<List<Word>> findWordsPattern(String pattern) {
        return wordDao.findWordsWithPattern("%" + pattern + "%");
    }

    void insertWords(Word... words) {
        new InsetAsyncTask(wordDao).execute(words);
    }

    void updateWords(Word... words) {
        new UpdateAsyncTask(wordDao).execute(words);
    }

    void deleteWords(Word... words) {
        new DeleteAsyncTask(wordDao).execute(words);
    }

    void deleteAllWords() {
        new DeleteAllAsyncTask(wordDao).execute();
    }

    //如果是内部类必须是static，否则告警，出现内存泄漏
    //第二个报告进度，后面报告结果
    static class InsetAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        InsetAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertWords(words);
//            publishProgress();
            return null;
        }

        //任务完成时呼叫，将结果带回主线程
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        //进度更新时呼叫
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        //在后台执行之前呼叫
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        UpdateAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.updateWords(words);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        DeleteAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteWords(words);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao wordDao;

        DeleteAllAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWords();
            return null;
        }
    }
}
