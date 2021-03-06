package ows.boostcourse.myalarm.Component;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ows.boostcourse.myalarm.Interface.SwitchListener;
import ows.boostcourse.myalarm.R;

/**
 * RecyclerView Adapter.
 */
public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private ArrayList<Alarm> alarmList = new ArrayList<Alarm>();
    private static final String TAG = AlarmAdapter.class.getSimpleName();
    private SwitchListener switchListener;

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

            // Event is called when switch changed.
            flag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = getAdapterPosition();

                    // AlarmAdapter's alarmList update
                    Alarm alarm = alarmList.get(position);
                    alarm.setFlag(isChecked);
                    alarmList.set(position,alarm);

                    switchListener.onDetectChangeSwitch(isChecked,position);
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
     * AlarmAdatper constructor.
     * @param switchListener
     */
    public AlarmAdapter(SwitchListener switchListener) {
        this.switchListener = switchListener;
    }

    /**
     * Add item in alarmList
     * AlarmList is sorted in ascending order.
     * @param alarm alarm class have meridiem, hourofDay, minute information.
     */
    public void addItem(Alarm alarm){
        alarmList.add(alarm);

        // Ascending comparator implemented as anonymous class
        Comparator<Alarm> comparator = new Comparator<Alarm>() {
            @Override
            public int compare(Alarm o1, Alarm o2) {
                return o1.toString().compareTo(o2.toString());
            }
        };

        Collections.sort(alarmList,comparator);
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
        holder.getMeridiem().setText(alarmList.get(position).getMeridiem());
        holder.getHourOfDay().setText(String.format("%02d시", alarmList.get(position).getHourOfday()));
        holder.getMinute().setText(String.format("%02d분", alarmList.get(position).getMinute()));
        holder.getFlag().setChecked(alarmList.get(position).getFlag());
    }

    /**
     * Get number of views to be displayed.
     * @return number of views.
     */
    @Override
    public int getItemCount() {
        return alarmList.size();
    }

}
