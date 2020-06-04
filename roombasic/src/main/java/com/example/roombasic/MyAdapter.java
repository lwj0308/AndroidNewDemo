package com.example.roombasic;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends ListAdapter<Word, MyAdapter.ViewHolder> {
    //父类已有
//    private List<Word> allWords = new ArrayList<>();
    private boolean userCardView;
    private WordViewModel wordViewModel;

    MyAdapter(boolean userCardView, WordViewModel wordViewModel) {
        //处理两个列表差异化的回调，后台异步
        super(new DiffUtil.ItemCallback<Word>() {
            //元素是否相同
            @Override
            public boolean areItemsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
                return oldItem.getId() == newItem.getId();
            }

            //内容是否相同
            @Override
            public boolean areContentsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
                return (oldItem.getWord().equals(newItem.getWord())
                        && oldItem.getChineseMessing().equals(newItem.getChineseMessing())
                        && oldItem.isChineseInvisible() == newItem.isChineseInvisible());
            }
        });
        this.userCardView = userCardView;
        this.wordViewModel = wordViewModel;
    }
//同上
//    public void setAllWords(List<Word> allWords) {
//        this.allWords = allWords;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;
        if (userCardView) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_card_2, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_normal_2, parent, false);
        }
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.baidu.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });

        viewHolder.sw_chinese_invisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Word word = (Word) viewHolder.itemView.getTag(R.id.word_for_view_holder);
                if (isChecked) {
                    viewHolder.tv_chinese.setVisibility(View.GONE);
                    word.setChineseInvisible(true);
                    wordViewModel.updateWords(word);
                } else {
                    viewHolder.tv_chinese.setVisibility(View.VISIBLE);
                    word.setChineseInvisible(false);
                    wordViewModel.updateWords(word);
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Word word = getItem(position);
        holder.itemView.setTag(R.id.word_for_view_holder, word);
        holder.tv_num.setText(String.valueOf(position + 1));
        holder.tv_english.setText(word.getWord());
        holder.tv_chinese.setText(word.getChineseMessing());
        if (word.isChineseInvisible()) {
            holder.tv_chinese.setVisibility(View.GONE);
            holder.sw_chinese_invisible.setChecked(true);
        } else {
            holder.tv_chinese.setVisibility(View.VISIBLE);
            holder.sw_chinese_invisible.setChecked(false);
        }
    }

    //保险刷新序号
    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.tv_num.setText(String.valueOf(holder.getAdapterPosition() + 1));
    }

    //加static防止内存泄漏，内部类
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_num, tv_english, tv_chinese;
        Switch sw_chinese_invisible;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_num = itemView.findViewById(R.id.tv_num);
            tv_english = itemView.findViewById(R.id.tv_english);
            tv_chinese = itemView.findViewById(R.id.tv_chinese);
            sw_chinese_invisible = itemView.findViewById(R.id.sw_chinese_invisible);
        }
    }
}
