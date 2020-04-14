package com.invisee.finvest.ui.adapters.rv;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Reminder;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.ReminderActivity;
import com.invisee.finvest.ui.fragments.reminder.ReminderListPresenter;
import com.invisee.finvest.ui.receiver.ReminderReceiver;
import com.invisee.finvest.util.AmountFormatter;
import com.invisee.finvest.util.DateUtil;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fajarfatur on 3/6/16.
 */
public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderHolder> {

    private Context context;
    private List<Reminder> list;
    private Calendar alarmCal;
    private ReminderListPresenter presenter;

    public ReminderAdapter(Context context, List<Reminder> list, ReminderListPresenter presenter) {
        this.context = context;
        this.list = list;
        this.presenter = presenter;
    }

    @Override
    public ReminderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_reminder, parent, false);
        return new ReminderHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ReminderHolder holder, final int position) {
        final Reminder item = list.get(position);
        holder.itemView.setTag(item);

        final String packageName = item.getFundPackageName();
        String investmentNumber = item.getInvestmentAccountNo();
        String reminderAmount = AmountFormatter.format(item.getReminderAmount());
        String reminderTime = item.getReminderStartTime();
        String lastDate = item.getLastTrigger();

        String data = lastDate + "'T'" + reminderTime;

        if (lastDate.trim().equalsIgnoreCase("") || lastDate.length() < 1) {
            holder.txvNextDate.setVisibility(View.GONE);
         /*   holder.compatSwitch.setVisibility(View.GONE);*/
        } else {
            alarmCal = Calendar.getInstance();

            if (DateUtil.format(data, DateUtil.DD_MM_YYYY_HH_MM) != null)
                alarmCal.setTime(DateUtil.format(data, DateUtil.DD_MM_YYYY_HH_MM));
        }

        boolean autodebit = item.getAutodebit();
        holder.tvAutodebit.setText("Autodebet");

        if(autodebit == true) {
            holder.tvAutodebit.setVisibility(View.VISIBLE);
        } else {
            holder.tvAutodebit.setVisibility(View.GONE);
        }

        holder.txvPackageName.setText(packageName);
        holder.txvAccountNumber.setText(investmentNumber);
        holder.txvInvestmentAmount.setText(reminderAmount);
        holder.txvReminderTime.setText(reminderTime);
        holder.txvNextDate.setText(lastDate);

        holder.icClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.dialog(item.getReminderId());
            }
        });


/*        holder.compatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.compatSwitch.isChecked()) {

                    holder.imvAlarm.setImageResource(R.drawable.ic_alarm_on_grey_32px);
                    holder.imvAlarm.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

                    if (alarmCal != null)
                        setAlarm(alarmCal);

                } else {

                    holder.imvAlarm.setImageResource(R.drawable.ic_alarm_off_grey_32px);
                    holder.imvAlarm.setColorFilter(ContextCompat.getColor(context, R.color.grey_500), PorterDuff.Mode.SRC_ATOP);

                }
            }
        });*/

/*        holder.compatSwitch.setChecked(item.isAlarm());*/

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReminderActivity.startActivity((BaseActivity) context, item);
            }
        });
        
    }

    public void removeAt(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    private void setAlarm(Calendar targetCal) {

        Intent intent = new Intent(context, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ReminderHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.row)
        LinearLayout row;
        @Bind(R.id.txvPackageName)
        TextView txvPackageName;
        @Bind(R.id.txvAccountNumber)
        TextView txvAccountNumber;
        @Bind(R.id.txvInvestmentAmount)
        TextView txvInvestmentAmount;
        @Bind(R.id.txvReminderTime)
        TextView txvReminderTime;
        @Bind(R.id.txvNextDate)
        TextView txvNextDate;
        @Bind(R.id.txvAutodebit)
        TextView tvAutodebit;
        @Bind(R.id.icClose)
        ImageView icClose;
//        @Bind(R.id.imvAlarm)
//        ImageView imvAlarm;
   /*     @Bind(R.id.compatSwitch)
        SwitchCompat compatSwitch;*/

        public ReminderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
