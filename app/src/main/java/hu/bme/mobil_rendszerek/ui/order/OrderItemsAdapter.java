package hu.bme.mobil_rendszerek.ui.order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import hu.bme.mobil_rendszerek.R;
import hu.bme.mobil_rendszerek.model.OrderItem;

import static hu.bme.mobil_rendszerek.ui.order.OrderActivity.REQUEST_NEW_ORDER_CODE;

/**
 * Created by nyikes on 2017. 04. 19..
 */

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemViewHolder> {

    public static final int CONTEXT_ACTION_DELETE = 10;
    public static final int CONTEXT_ACTION_EDIT = 11;

    private List<OrderItem> orderItems;
    private Context mContext;
    private DateFormat formatter;
    private DeleteListener deleteListener;

    public OrderItem getItem(int i) {
        return orderItems.get(i);
    }

    public OrderItemsAdapter(List<OrderItem> orderItems, Context mContext, DeleteListener deleteListener) {
        this.orderItems = orderItems;
        this.mContext = mContext;
        this.deleteListener = deleteListener;
        formatter = new SimpleDateFormat("yyyy.MM.dd");
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);
        OrderItemViewHolder contactViewHolder = new OrderItemViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(final OrderItemViewHolder holder, int position) {
        OrderItem orderItem = orderItems.get(position);
        holder.orderItemName.setText(orderItem.getProductName());
        holder.orderCost.setText(orderItem.getCost().toString() + " Ft");
        holder.orderCount.setText(orderItem.getCount().toString() + " db");
        holder.orderDate.setText(formatter.format(orderItem.getDate()));
    }

    public void removeOneItem(Integer orderItemId) {
        OrderItem toDelete = null;
        Integer position = null;
        for (OrderItem o : orderItems)
            if (o.getOrderItemId().equals(orderItemId)) {
                toDelete = o;
                position = orderItems.indexOf(o);
                break;
            }
        if (toDelete != null) {
            orderItems.remove(toDelete);
            notifyItemRemoved(position);
        }
    }

    public void newOneItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        notifyItemInserted(orderItems.size() - 1);
    }

    public void modifyOneItem(OrderItem newOrderItem) {
        Integer position = null;
        for (OrderItem o : orderItems)
            if (o.getOrderItemId().equals(newOrderItem.getOrderItemId())) {
                position = orderItems.indexOf(o);
                break;
            }
        if (position != null) {
            orderItems.set(position, newOrderItem);
            notifyItemChanged(position);
        }

    }

    public void swap(List<OrderItem> data) {
        orderItems.clear();
        if (data != null)
            orderItems.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public interface DeleteListener {
        public void onDeleted(OrderItem orderItem);
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,
            MenuItem.OnMenuItemClickListener {
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
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == CONTEXT_ACTION_DELETE) {
                deleteListener.onDeleted(getItem(getAdapterPosition()));
            } else if (item.getItemId() == CONTEXT_ACTION_EDIT) {
                OrderItem selectedItem = getItem(getAdapterPosition());
                Intent i = new Intent();
                i.setClass(mContext, CreateOrderActivity.class);
                i.putExtra(CreateOrderActivity.KEY_PRODUCT_FROM_EDIT, selectedItem);
                ((Activity)mContext).startActivityForResult(i, REQUEST_NEW_ORDER_CODE);
            } else {
                return false;
            }
            return true;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Menü");
            MenuItem myItem1 = menu.add(0, CONTEXT_ACTION_DELETE, 0, "Törlés");
            myItem1.setOnMenuItemClickListener(this);
            MenuItem myItem2 = menu.add(0, CONTEXT_ACTION_EDIT, 0, "Szerkesztés");
            myItem2.setOnMenuItemClickListener(this);
        }
    }
}
