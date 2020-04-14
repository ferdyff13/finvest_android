package com.invisee.finvest.ui.fragments.reminder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.InvestmentAccount;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.Reminder;
import com.invisee.finvest.data.api.beans.ReminderDetail;
import com.invisee.finvest.data.api.requests.SaveReminderRequest;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.ReminderListActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.DateUtil;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import icepick.State;

/**
 * Created by fajarfatur on 3/6/16.
 */
public class ReminderAddFragment extends BaseFragment {

    public static final String TAG = ReminderAddFragment.class.getSimpleName();
    public static final String REMINDER = "Reminder";
    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";

    @Bind(R.id.sPackageName)
    Spinner sPackageName;
    @Bind(R.id.sInvestmentNo)
    Spinner sInvestmentNo;
    @Bind(R.id.sReminderInterval)
    Spinner sReminderInterval;
    @NotEmpty(messageResId = R.string.rules_no_empty_start_date)
    @Bind(R.id.edtStartDate)
    EditText edtStartDate;
    @NotEmpty(messageResId = R.string.rules_no_empty_end_date)
    @Bind(R.id.edtEndDate)
    EditText edtEndDate;
    @NotEmpty(messageResId = R.string.rules_no_empty_reminder_time)
    @Bind(R.id.edtReminderTime)
    EditText edtReminderTime;
    @NotEmpty(messageResId = R.string.rules_no_empty_top_up_amount)
    @Bind(R.id.edtTopUpAmount)
    EditText edtTopUpAmount;
    @Bind(R.id.cbAutoDebit)
    CheckBox cbAutoDebit;

    @State
    Reminder reminder;

    private ReminderAddPresenter presenter;

    public List<Packages> packageList;
    public List<InvestmentAccount> investmentAccountList;
    public ReminderDetail reminderDetail;

    private Calendar startCalendar, endCalendar;
    private DatePickerDialog datePickerDialogStart, datePickerDialogEnd;
    private TimePickerDialog timePickerDialog;
    private String interval;
    private Boolean autodebit;

    private SimpleDateFormat getFormat;

    private Calendar calendar;
    private String startDate;
    private String endDate;



    public static void showFragment(BaseActivity sourceActivity, Reminder reminder) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            Fragment fragment = new ReminderAddFragment();

            if (reminder != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(REMINDER, reminder);
                fragment.setArguments(bundle);
            }

            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_reminder_add;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(REMINDER))
            reminder = (Reminder) getArguments().getSerializable(REMINDER);
        else
            reminder = null;

        presenter = new ReminderAddPresenter(this);
        presenter.getPackageNameByToken();

    }


    public void setDefaultValue() {
        if (reminder != null) {
            for (int i = 0; i < packageList.size(); i++) {
                if (reminder.getFundPackageName().equalsIgnoreCase(packageList.get(i).getFundPackageName())) {
                    sPackageName.setSelection(i);
                }
            }

            startCalendar.setTime(DateUtil.format(reminder.getDurationStartDate(), DateUtil.DD_MM_YYYY_));
            endCalendar.setTime(DateUtil.format(reminder.getDurationStopDate(), DateUtil.DD_MM_YYYY_));

            startCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(reminder.getReminderStartTime().split(":")[0]));
            startCalendar.set(Calendar.MINUTE, Integer.parseInt(reminder.getReminderStartTime().split(":")[1]));
            endCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(reminder.getReminderStartTime().split(":")[0]));
            endCalendar.set(Calendar.MINUTE, Integer.parseInt(reminder.getReminderStartTime().split(":")[1]));

            edtStartDate.setText(reminder.getDurationStartDate());
            edtEndDate.setText(reminder.getDurationStopDate());
            edtReminderTime.setText(reminder.getReminderStartTime());
            edtTopUpAmount.setText(Integer.toString(reminder.getReminderAmount().intValue()));

        }
    }

    public void setSpinnerPackage() {
        ArrayAdapter<Packages> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, packageList);
        sPackageName.setAdapter(spinnerArrayAdapter);
        sPackageName.setSelection(0, false);
    }

    public void setSpinnerInvestmentNo() {
        ArrayAdapter<InvestmentAccount> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, investmentAccountList);
        sInvestmentNo.setAdapter(spinnerArrayAdapter);
        sInvestmentNo.setSelection(0, false);
    }

    @OnItemSelected(R.id.sPackageName)
    void onPackageItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

        if (packageList != null && packageList.size() > 0) {
            presenter.getInvestmentNumberByPackage(Long.toString(packageList.get(position).getId()));
        }
    }

    @OnClick(R.id.edtStartDate)
    void onStartDateClicked() {
        startDate();
    }

    @OnClick(R.id.edtEndDate)
    void onEndDateClicked() {
        endDate();
    }

    @OnClick(R.id.edtReminderTime)
    void onTimeClicked() {
        reminderTime();
    }

    @OnClick(R.id.bSave)
    void onSaveClicked() {
        getValidator().validate();

    }

    private void startDate() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.date_picker, null, false);

        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.datepicker);

        new AlertDialog.Builder(getContext()).setView(view)
                .setTitle("Tanggal Mulai Pengingat")

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                int day = myDatePicker.getDayOfMonth();
                                int month = myDatePicker.getMonth();
                                int year = myDatePicker.getYear();

                                calendar = Calendar.getInstance();
                                calendar.set(year, month, day);

                                SimpleDateFormat format = new SimpleDateFormat(DateUtil.DD_MM_YYYY_);
                                getFormat = new SimpleDateFormat(DateUtil.YYYY_MM_DD);

                                startDate = getFormat.format(calendar.getTime());

                                edtStartDate.setText(format.format(calendar.getTime()));
                                dialog.cancel();
                            }
                        }
                ).show();
    }

    private void endDate() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.date_picker, null, false);

        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.datepicker);

        new AlertDialog.Builder(getContext()).setView(view)
                .setTitle("Tanggal Akhir Pengingat")

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                int day = myDatePicker.getDayOfMonth();
                                int month = myDatePicker.getMonth();
                                int year = myDatePicker.getYear();

                                calendar = Calendar.getInstance();
                                calendar.set(year, month, day);

                                SimpleDateFormat format = new SimpleDateFormat(DateUtil.DD_MM_YYYY_);
                                getFormat = new SimpleDateFormat(DateUtil.YYYY_MM_DD);

                                endDate = getFormat.format(calendar.getTime());

                                edtEndDate.setText(format.format(calendar.getTime()));
                                dialog.cancel();
                            }
                        }
                ).show();
    }

    private void reminderTime() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.time_picker, null, false);

        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);

        new AlertDialog.Builder(getContext()).setView(view)
                .setTitle("Waktu")

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                int hour = timePicker.getCurrentHour();
                                int min = timePicker.getCurrentMinute();
                                /*edtReminderTime.setText(new StringBuilder().append(hour).append(":").append(min));*/
                                edtReminderTime.setText(String.format("%02d:%02d", hour, min));
                            }
                        }
                ).show();
    }


    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();

        StringTokenizer tokens = new StringTokenizer(edtReminderTime.getText().toString(), ":");
        String first = tokens.nextToken();
        String second = tokens.nextToken();
        String time  = first+second;

        if(sReminderInterval.getSelectedItem().toString().equals("Mingguan")) {
            interval = "WEEKLY";
        } else {
            interval = "MONTH";
        }

        autodebit = cbAutoDebit.isChecked();

        getFormat = new SimpleDateFormat(DateUtil.YYYY_MM_DD);

        if (packageList.size() > 0) {
            presenter.saveOrUpdate(new SaveReminderRequest(PrefHelper.getString(PrefKey.TOKEN),
                    startDate,
                    endDate,
                    time,
                    reminder != null ? Integer.toString(reminder.getReminderId()) : "",
                    Long.toString(packageList.get(sPackageName.getSelectedItemPosition()).getId()),
                    investmentAccountList.get(sInvestmentNo.getSelectedItemPosition()).getId(),
                    edtTopUpAmount.getText().toString(),
                    interval,
                    autodebit
            ));

        } else {
            new MaterialDialog.Builder(getActivity())
                    .iconRes(R.mipmap.ic_launcher_finvest)
                    .backgroundColor(cDanger)
                    .title(getString(R.string.failed).toUpperCase())
                    .titleColor(Color.WHITE)
                    .content(R.string.reminder_save_failed)
                    .contentColor(Color.WHITE)
                    .positiveText(R.string.ok)
                    .positiveColor(Color.WHITE)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            ReminderListActivity.startActivity((BaseActivity) getActivity());
                        }
                    })
                    .cancelable(false)
                    .show();
        }

    }
}
