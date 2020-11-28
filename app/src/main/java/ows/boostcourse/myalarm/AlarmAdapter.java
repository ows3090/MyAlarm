package ows.boostcourse.myalarm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * RecyclerView Adapter.
 */
public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private ArrayList<Alarm> alramList = new ArrayList<Alarm>();
    private Context context;

    public AlarmAdapter(Context context) {
        this.context = context;
    }

    /**
     * Provide a reference to the type of views that you are using (custom viewholder).
     */
    public class AlarmViewHolder extends RecyclerView.ViewHolder{
        private TextView meridiem;
        private TextView hourOfDay;
        private TextView minute;
        private Switch flag;

        /**
         * AlarmViewHolder constructor.
         * @param itemView itemView to inflate.
         */
        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            meridiem = itemView.findViewById(R.id.activity_main_lv_meridiem);
            hourOfDay = itemView.findViewById(R.id.activity_main_lv_hour);
            minute = itemView.findViewById(R.id.activity_main_lv_minute);
            flag = itemView.findViewById(R.id.activity_main_lv_switch);
            flag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = getAdapterPosition();
                    Alarm alarm = alramList.get(position);
                    alarm.setFlag(isChecked);

                    if(AlarmDatabase.getInstance(context).insertDatabase(alarm)){
                        Log.d("msg",AlarmDatabase.getInstance(context).selectDatabase().size()+"");
                    }
                }
            });
        }

        public TextView getMeridiem() {
            return meridiem;
        }

        public TextView getHourOfDay() {
            return hourOfDay;
        }

        public TextView getMinute() {
            return minute;
        }

        public Switch getFlag() { return flag; }
    }

    /**
     * Add item in alarmList
     * @param alarm alarm class have meridiem, hourofDay, minute information.
     */
    public void addItem(Alarm alarm){
        alramList.add(alarm);
        notifyDataSetChanged();
    }

    /**
     * Create a ViewHolder that fits the view.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_recyclerview,parent,false);
        return new AlarmViewHolder(view);
    }

    /**
     * Replace the content of view.
     * @param holder
     * @param position your dateset of position.
     */
    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        holder.getMeridiem().setText(alramList.get(position).getMeridiem());
        holder.getHourOfDay().setText(String.format("%02d시",alramList.get(position).getHourOfday()));
        holder.getMinute().setText(String.format("%02d분",alramList.get(position).getMinute()));
        holder.getFlag().setChecked(alramList.get(position).getFlag());
    }

    /**
     * Get number of views to be displayed.
     * @return number of views.
     */
    @Override
    public int getItemCount() {
        return alramList.size();
    }

}
