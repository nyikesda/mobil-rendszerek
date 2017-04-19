package hu.bme.mobil_rendszerek.ui.department;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import java.util.List;

import hu.bme.mobil_rendszerek.R;

/**
 * Created by nyikes on 2017. 04. 21..
 */

public class MultiSpinner extends AppCompatSpinner implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {

    private List<String> items;
    private boolean[] selected;
    private MultiSpinnerListener listener;

    public MultiSpinner(Context context) {
        super(context);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (isChecked)
            selected[which] = true;
        else
            selected[which] = false;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        int selectedCount = 0;
        for (int i=0; i<selected.length; i++)
            if (selected[i])
                selectedCount++;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                new String[]{selectedCount+" "+getContext().getString(R.string.spinner_elem_count_text)});
        setAdapter(adapter);
        listener.onItemsSelected(selected);
    }

    @Override
    public boolean performClick() {
        if (items.size() == 0){
            listener.onItemsSelected(null);
            return true;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(
                items.toArray(new CharSequence[items.size()]), selected, this);
        builder.setOnCancelListener(this);
        builder.show();
        return true;
    }

    public void setItems(List<String> items,
                         MultiSpinnerListener listener, boolean[] selected) {
        this.items = items;
        this.listener = listener;

        if (selected != null){
            this.selected = selected;
        } else {
            this.selected = new boolean[items.size()];
            for (int i = 0; i < this.selected.length; i++)
                this.selected[i] = false;
        }

        int selectedCount = 0;
        for (int i=0; i<this.selected.length; i++)
            if (this.selected[i])
                selectedCount++;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, new String[]{selectedCount+" "+getContext().getString(R.string.spinner_elem_count_text)});
        setAdapter(adapter);
    }

    public interface MultiSpinnerListener {
        public void onItemsSelected(boolean[] selected);
    }
}