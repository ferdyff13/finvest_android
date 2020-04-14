package com.invisee.finvest.ui.fragments.reminder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.InvestmentAccount;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.Reminder;
import com.invisee.finvest.data.api.beans.ReminderDetail;
import com.invisee.finvest.data.api.requests.SaveReminderRequest;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.DateUtil;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import icepick.State;

/**
 * Created by pandu.abbiyuarsyah on 08/06/2017.
 */

public class ReminderEditFragment extends BaseFragment {

    private ReminderEditPresenter presenter;

    public static final String TAG = ReminderEditFragment.class.getSimpleName();

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


    public ReminderDetail reminderDetail;
    public List<Packages> packageList;
    public List<InvestmentAccount> investmentAccountList;

    private String interval;
    private Boolean autodebit;

    private SimpleDateFormat getFormat;
    private Calendar calendar;
    private String startDate;
    private String endDate;
    private String getStartDate;
    private String getEndDate;



    public static void showFragment(BaseActivity sourceActivity, Reminder reminder) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            Fragment fragment = new ReminderEditFragment();

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(REMINDER))
            reminder = (Reminder) getArguments().getSerializable(REMINDER);
        else
            reminder = null;

        presenter =  new ReminderEditPresenter(this);
        presenter.getPackageNameByToken();
    }

    public void loadDetail() {
        boolean autodebit = reminderDetail.getAutodebit();

        if (reminderDetail.getReminderType().equals("WEEKLY")) {
            sReminderInterval.setSelection(0, false);
        } else {
            sReminderInterval.setSelection(1, false);
        }

        if(autodebit == true) {
            cbAutoDebit.setChecked(true);
        } else {
            cbAutoDebit.setChecked(false);
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.YYYY_MM_DD,    Locale.getDefault());
        //  Date date3 = sdf.parse("2017-02-03T11:44:52.6152Z");
        Date startDateValue = null;
        try {
            startDateValue = sdf.parse(reminderDetail.getDurationStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.DD_MM_YYYY_, Locale.getDefault());
        String convertedStartDate= formatter.format(startDateValue);
        edtStartDate.setText(convertedStartDate);

        //  Date date3 = sdf.parse("2017-02-03T11:44:52.6152Z");
        Date endDateValue = null;
        try {
            endDateValue = sdf.parse(reminderDetail.getDurationStopDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedEndDate= formatter.format(endDateValue);
        edtEndDate.setText(convertedEndDate);



        edtReminderTime.setText(reminderDetail.getReminderStartTime());
        edtTopUpAmount.setText(String.valueOf(reminderDetail.getReminderAmount()));

    }



    public void setSpinnerPackage() {
        ArrayAdapter<Packages> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, packageList);
        sPackageName.setAdapter(spinnerArrayAdapter);
        for (int i = 0; i < packageList.size(); i++) {
            if (reminderDetail.getFundPackageRefId() == packageList.get(i).getId()) {
                sPackageName.setSelection(i);
            }
        }
    /*    sPackageName.setSelection(i, false);*/

    }

    public void setSpinnerInvestmentNo() {
        ArrayAdapter<InvestmentAccount> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, investmentAccountList);
        sInvestmentNo.setAdapter(spinnerArrayAdapter);
        for (int i = 0; i < investmentAccountList.size(); i++) {
            String invNo = reminder.getInvestmentAccountNo();
            String invNo2 = investmentAccountList.get(i).getInvestmentAccountNo();
            if (invNo.equalsIgnoreCase(invNo2)) {
                sInvestmentNo.setSelection(i);
            }
        }
    }

    @OnClick(R.id.bSave)
    void onSaveClicked() {
        getValidator().validate();

    }

    @OnClick(R.id.edtStartDate)
    void startDatePicker() {
        startDate();
    }

    @OnClick(R.id.edtEndDate)
    void endDatePicker() {
        endDate();
    }

    @OnClick(R.id.edtReminderTime)
    void reminderTimePicker() {
        reminderTime();
    }

    @OnItemSelected(R.id.sPackageName)
    void onPackageItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

        if (packageList != null && packageList.size() > 0) {
            presenter.getInvestmentNumberByPackage(Long.toString(packageList.get(position).getId()));
        }

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

        getFormat = new SimpleDateFormat(DateUtil.DD_MM_YYYY_);

        Date dateObject;
        Date dateObject2;

        try{
            String dob_var=(edtStartDate.getText().toString());
            dateObject = getFormat.parse(dob_var);

            String dob_var_2 = (edtEndDate.getText().toString());
            dateObject2 = getFormat.parse(dob_var_2);

            getStartDate = new SimpleDateFormat(DateUtil.YYYY_MM_DD).format(dateObject);
            getEndDate = new SimpleDateFormat(DateUtil.YYYY_MM_DD).format(dateObject2);
        }

        catch (java.text.ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i("E11111111111", e.toString());
        }

        presenter.saveOrUpdate(new SaveReminderRequest(PrefHelper.getString(PrefKey.TOKEN),
                getStartDate,
                getEndDate,
                time,
                reminder != null ? Integer.toString(reminder.getReminderId()) : "",
                Long.toString(packageList.get(sPackageName.getSelectedItemPosition()).getId()),
                investmentAccountList.get(sInvestmentNo.getSelectedItemPosition()).getId(),
                edtTopUpAmount.getText().toString(),
                interval,
                autodebit
        ));
    }

/*
    @OnItemSelected(R.id.sPackageName)
    void onPackageItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {



    }
*/




}
