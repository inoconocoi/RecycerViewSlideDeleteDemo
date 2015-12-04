package tielizi.com.recycerviewslidedeletedemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 10840 on 2015/10/31.
 */
public class MyRecycerViewAdapter extends RecyclerView.Adapter<MyRecycerViewAdapter.ViewHolder> {
    private Context context;
    private List<ItemBean> list;
    private boolean isSlide;
    private RecyclerView recyclerView;
    private RecycerViewItemClickListener recycerViewItemClickListener;
    private RecycerViewItemLongClickListener recycerViewItemLongClickListener;

    public void setRecycerViewItemClickListener(RecycerViewItemClickListener recycerViewItemClickListener) {
        this.recycerViewItemClickListener = recycerViewItemClickListener;
    }

    public void setRecycerViewItemLongClickListener(RecycerViewItemLongClickListener recycerViewItemLongClickListener) {
        this.recycerViewItemLongClickListener = recycerViewItemLongClickListener;
    }

    public interface RecycerViewItemClickListener{//单击Item接口
        void RecycerViewOnClick(View view, int position);
    }

    public interface RecycerViewItemLongClickListener{//长击Item接口
        void RecycerViewLongClick(View view, int position);
    }

    public MyRecycerViewAdapter(Context context, final List<ItemBean> list, RecyclerView recyclerView) {
        this.context = context;
        this.list = list;
        this.recyclerView = recyclerView;
        this.recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                for(int i = 0; i < list.size(); i++){
                    ItemBean itemBean = list.get(i);
                    if(itemBean.isItemSlide()){
                        itemBean.getMyItemView().slideCloseWithoutAnimator();
                        itemBean.setIsItemSlide(false);
                        isSlide = true;
                    }
                }

            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Log.i("PX","我无语了"+i);
        ItemBean itemBean = list.get(i);
        viewHolder.itemTextView.setText(itemBean.getName());
        itemBean.setMyItemView(viewHolder.myItemView);
        if(!itemBean.isItemSlide()){
            itemBean.getMyItemView().slideCloseWithoutAnimator();
        }
        if(itemBean.isExtend()){
            viewHolder.extendsBtn.setVisibility(View.VISIBLE);
        }else{
            viewHolder.extendsBtn.findViewById(R.id.extendBtn).setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MyItemView myItemView;
        private TextView itemTextView;
        private Button extendsBtn;
        private Button deleteBtn;
        private LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            myItemView = (MyItemView) itemView.findViewById(R.id.item_view);
            itemTextView = (TextView) itemView.findViewById(R.id.item_text);
            extendsBtn = (Button) itemView.findViewById(R.id.extendBtn);
            deleteBtn = (Button) itemView.findViewById(R.id.delete_itemBtn);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.item_content);
            extendsBtn.setOnClickListener(this);
            deleteBtn.setOnClickListener(this);
            linearLayout.setOnClickListener(this);
            linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (recycerViewItemLongClickListener != null)
                        recycerViewItemLongClickListener.RecycerViewLongClick(v, getPosition());
                    return true;
                }
            });
            myItemView.setSlideListener(new MyItemView.SlideListener() {
                @Override
                public void slideState(boolean state) {
                    list.get(getPosition()).setIsItemSlide(state);
                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.extendBtn:
                    Toast.makeText(context,"扩展按钮"+getPosition(),Toast.LENGTH_SHORT).show();
                    break;
                case R.id.delete_itemBtn:
                    deleteItem(getPosition());
                    break;
                case R.id.item_content:
                    if (recycerViewItemClickListener != null) {
                        isSlide = list.get(getPosition()).isItemSlide();
                        cliseAllOpenItem();

                        if (isSlide) {
                            myItemView.slideClose();
                            isSlide = false;
                        } else {
                            Datas datas = new Datas();
                            datas.data = "哈哈哈";
                            recycerViewItemClickListener.RecycerViewOnClick(v, getPosition());
                        }
                    }

                    break;
            }
        }
    }

    public void cliseAllOpenItem(){
        if (!isSlide){
            for(int i = 0; i < list.size(); i++){
                ItemBean itemBean = list.get(i);
                if(itemBean.isItemSlide()){
                    itemBean.getMyItemView().slideClose();
                    itemBean.setIsItemSlide(false);
                    isSlide = true;
                }
            }
        }
    }

    public void insertItem(int position){
        list.add(position, new ItemBean("ItemAdd", false, false));
        notifyItemInserted(position);
    }

    public void deleteItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class Datas{
        public String data;
    }
}
