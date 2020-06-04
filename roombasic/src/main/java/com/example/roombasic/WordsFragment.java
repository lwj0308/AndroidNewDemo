package com.example.roombasic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordsFragment extends Fragment {
    private WordViewModel model;
    private RecyclerView rv_word;
    private MyAdapter adapter;
    private MyAdapter cardAdapter;
    private LiveData<List<Word>> filteredWords;
    private static final String VIEW_TYPE_SHP = "view_type_shp";
    private static final String IS_USING_CARD_VIEW = "is_using_card_view";
    private List<Word> allWords;
    private boolean undoAction;

    //边线
    private DividerItemDecoration dividerItemDecoration;

    public WordsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);//显示菜单
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_words, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(requireActivity()).get(WordViewModel.class);
        rv_word = requireActivity().findViewById(R.id.rv_word);
        rv_word.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new MyAdapter(false, model);
        cardAdapter = new MyAdapter(true, model);
        //当动画完，刷新序列号
        rv_word.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public void onAnimationFinished(@NonNull RecyclerView.ViewHolder viewHolder) {
                super.onAnimationFinished(viewHolder);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rv_word.getLayoutManager();
                assert linearLayoutManager != null;
                int firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
                int lastPosition = linearLayoutManager.findLastVisibleItemPosition();
                for (int i = firstPosition; i <= lastPosition; i++) {
                    MyAdapter.ViewHolder holder = (MyAdapter.ViewHolder) rv_word.findViewHolderForAdapterPosition(i);
//                    assert holder != null;
                    if (holder != null) {
                        holder.tv_num.setText(String.valueOf(i + 1));
                    }
                }
            }
        });

        SharedPreferences sp = requireActivity().getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE);
        boolean view_type = sp.getBoolean(IS_USING_CARD_VIEW, false);
        dividerItemDecoration = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        if (view_type) {
            rv_word.setAdapter(cardAdapter);
        } else {
            rv_word.setAdapter(adapter);
            rv_word.addItemDecoration(dividerItemDecoration);
        }

        filteredWords = model.getAllWordsLive();
        //感知生命周期
        filteredWords.observe(getViewLifecycleOwner(), new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                allWords = words;
                int temp = adapter.getItemCount();
                if (temp != words.size()) {
                    adapter.submitList(words);
                    cardAdapter.submitList(words);
//                    adapter.notifyDataSetChanged();
//                    cardAdapter.notifyDataSetChanged();
//                    adapter.notifyItemChanged(0);
                }
            }
        });
        //recycle的辅助工具
        //或
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START | ItemTouchHelper.END) {
            //拖拽排序的操作由于动作的和数据更新不同步，所以不能这样简单处理。拖拽速度快了就出错了。
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                Word wordFrom = allWords.get(viewHolder.getAdapterPosition());
//                Word wordTO = allWords.get(target.getAdapterPosition());
//                long idTemp = wordFrom.getId();
//                wordTO.setId(idTemp);
//                model.updateWords(wordFrom, wordTO);
//                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//                cardAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final Word wordToDelete = allWords.get(viewHolder.getAdapterPosition());
                model.deleteWords(wordToDelete);
                Snackbar.make(requireActivity().findViewById(R.id.frameLayout), "删除了一个词汇", Snackbar.LENGTH_SHORT)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                undoAction = true;
                                model.insertWords(wordToDelete);
                            }
                        })
                        .show();
            }

            //在滑动的时候，画出浅灰色背景和垃圾桶图标，增强删除的视觉效果

            Drawable icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_delete_forever_black_24dp);
            Drawable background = new ColorDrawable(Color.LTGRAY);
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                View itemView = viewHolder.itemView;
                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;

                int iconLeft,iconRight,iconTop,iconBottom;
                int backTop,backBottom,backLeft,backRight;
                backTop = itemView.getTop();
                backBottom = itemView.getBottom();
                iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) /2;
                iconBottom = iconTop + icon.getIntrinsicHeight();
                if (dX > 0) {
                    backLeft = itemView.getLeft();
                    backRight = itemView.getLeft() + (int)dX;
                    background.setBounds(backLeft,backTop,backRight,backBottom);
                    iconLeft = itemView.getLeft() + iconMargin ;
                    iconRight = iconLeft + icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);
                } else if (dX < 0){
                    backRight = itemView.getRight();
                    backLeft = itemView.getRight() + (int)dX;
                    background.setBounds(backLeft,backTop,backRight,backBottom);
                    iconRight = itemView.getRight()  - iconMargin;
                    iconLeft = iconRight - icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);
                } else {
                    background.setBounds(0,0,0,0);
                    icon.setBounds(0,0,0,0);
                }
                background.draw(c);
                icon.draw(c);
            }

        }).attachToRecyclerView(rv_word);
        FloatingActionButton button = requireActivity().findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_wordsFragment_to_addFragment);
            }
        });
    }



    @Override
    public void onResume() {
        //返回关闭键盘2
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(Objects.requireNonNull(getView()).getWindowToken(), 0);
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        android.widget.SearchView searchView = (android.widget.SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setMaxWidth(1000);
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String pattern = newText.trim();
                filteredWords.removeObservers(getViewLifecycleOwner());//!!!
                filteredWords = model.findvWordsPattern(pattern);
                filteredWords.observe(requireActivity(), new Observer<List<Word>>() {
                    @Override
                    public void onChanged(List<Word> words) {
                        int temp = adapter.getItemCount();
                        allWords = words;
                        if (temp != words.size()) {
                            if (temp < words.size() && !undoAction) {
                                rv_word.smoothScrollBy(0, -200);
                            }
                            undoAction = false;
                            adapter.submitList(words);
                            cardAdapter.submitList(words);
                        }
                    }
                });
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clearData:
                new AlertDialog.Builder(requireActivity())
                        .setTitle("清空数据")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                model.deleteAllWords();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.switchViewType:
                SharedPreferences sp = requireActivity().getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE);
                boolean view_type = sp.getBoolean(IS_USING_CARD_VIEW, false);
                if (view_type) {
                    rv_word.setAdapter(adapter);
                    rv_word.addItemDecoration(dividerItemDecoration);
                    sp.edit().putBoolean(IS_USING_CARD_VIEW, false).apply();
                } else {
                    rv_word.setAdapter(cardAdapter);
                    rv_word.removeItemDecoration(dividerItemDecoration);
                    sp.edit().putBoolean(IS_USING_CARD_VIEW, true).apply();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
