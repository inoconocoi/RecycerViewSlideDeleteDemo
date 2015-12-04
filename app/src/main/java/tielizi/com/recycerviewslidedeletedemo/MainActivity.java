package tielizi.com.recycerviewslidedeletedemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements MyRecycerViewAdapter.RecycerViewItemClickListener,MyRecycerViewAdapter.RecycerViewItemLongClickListener,View.OnClickListener
{

    private Button insertBtn,deleteBtn;
    private RecyclerView recyclerView;
    private MyRecycerViewAdapter myRecycerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ItemBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insertBtn = (Button) findViewById(R.id.insertBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        insertBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycerview);
        list = new ArrayList<>();
        String str = "";
        for (int i = 0; i < 25; i++){
            str += "Item";
            list.add(new ItemBean(str,false,false));
        }

        myRecycerViewAdapter = new MyRecycerViewAdapter(this,list,recyclerView);
        myRecycerViewAdapter.setRecycerViewItemClickListener(this);
        myRecycerViewAdapter.setRecycerViewItemLongClickListener(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myRecycerViewAdapter);

    }

    @Override
    public void RecycerViewOnClick(View view, int position) {
        for(int i = 0 ; i < list.size(); i++){
            if(i == position) {
                list.get(i).setIsExtend(!list.get(position).isExtend());
            }else{
                list.get(i).setIsExtend(false);
            }
        }
        myRecycerViewAdapter.notifyDataSetChanged();
        Toast.makeText(this,"单击"+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RecycerViewLongClick(View view, int position) {
        Toast.makeText(this,"长击"+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.insertBtn:
                myRecycerViewAdapter.insertItem(2);
                break;
            case R.id.deleteBtn:
                myRecycerViewAdapter.deleteItem(2);
                break;
        }
    }
}
