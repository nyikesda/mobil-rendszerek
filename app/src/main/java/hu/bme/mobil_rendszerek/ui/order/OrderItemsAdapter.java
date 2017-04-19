package hu.bme.mobil_rendszerek.ui.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import hu.bme.mobil_rendszerek.R;
import hu.bme.mobil_rendszerek.model.OrderItem;

/**
 * Created by nyikes on 2017. 04. 19..
 */

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemViewHolder> {
    private List<OrderItem> orderItems;
    private Context mContext;
    private DateFormat formatter;

    public OrderItemsAdapter(List<OrderItem> orderItems, Context mContext) {
        this.orderItems = orderItems;
        this.mContext = mContext;
        formatter = new SimpleDateFormat("yyyy.MM.dd");
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item,parent,false);
        OrderItemViewHolder contactViewHolder = new OrderItemViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(final OrderItemViewHolder holder, int position) {
        OrderItem orderItem = orderItems.get(position);
        holder.orderItemName.setText(orderItem.getProductName());
        holder.orderCost.setText(orderItem.getCost().toString()+" Ft");
        holder.orderCount.setText(orderItem.getCount().toString()+" db");
        holder.orderDate.setText(formatter.format(orderItem.getDate()));
    }


    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public static class OrderItemViewHolder extends RecyclerView.ViewHolder {
        View container;
        TextView orderItemName;
        TextView orderCost;
        TextView orderDate;
        TextView orderCount;


        public OrderItemViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            orderItemName = (TextView) itemView.findViewById(R.id.orderItemName);
            orderCost = (TextView) itemView.findViewById(R.id.orderCost);
            orderDate = (TextView) itemView.findViewById(R.id.orderDate);
            orderCount = (TextView) itemView.findViewById(R.id.orderCount);
        }
    }
}
