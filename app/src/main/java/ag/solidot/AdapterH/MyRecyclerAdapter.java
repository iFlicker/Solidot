package ag.solidot.AdapterH;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import ag.solidot.R;

/**
 * Created by flicker on 16/6/5.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private List<Map<String, String>> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    OnItemClickListener mOnItemClickListener;

    //构造方法传参datas为数据
    public MyRecyclerAdapter(Context context, List<Map<String, String>> datas){
        this. mContext=context;
        this. mDatas=datas;
        inflater=LayoutInflater.from(mContext);
    }
    //删除单个数据
    public void remove(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }
    //删除所有数据
    public void removeAll(){
        if (mDatas.size()!=0){
            notifyItemRangeRemoved(0,mDatas.size());
            mDatas.clear();
        }
    }
    //按list添加数据
    public void addAll(List<Map<String,String>> newdata) {
        mDatas.addAll(newdata);
        notifyItemRangeInserted(0,newdata.size());
        //notifyItemInserted(position);
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //添加点击和长按事件监听器
    public interface OnItemClickListener{
        void onClick( int position);
        void onLongClick( int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this. mOnItemClickListener=onItemClickListener;
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_title.setText( mDatas.get(position).get("title") );
        holder.tv_content.setText(mDatas.get(position).get("content"));
        holder.tv_author.setText(mDatas.get(position).get("author"));

        //----------------------
        if( mOnItemClickListener!= null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });

            holder.itemView.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(position);
                    //这儿如果返回false 会导致长按结束触发点击事件
                    return true;
                }
            });
        }
        //------------------------

    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_recyclerview,parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    //ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        TextView tv_author;
        TextView tv_content;

        public MyViewHolder(View view) {
            super(view);
//            tv=(TextView) view.findViewById(R.id.tv_item);
            tv_title = (TextView) view.findViewById(R.id.rvi_title);
            tv_author = (TextView) view.findViewById(R.id.rvi_author);
            tv_content = (TextView) view.findViewById(R.id.rvi_content);

        }

    }
}