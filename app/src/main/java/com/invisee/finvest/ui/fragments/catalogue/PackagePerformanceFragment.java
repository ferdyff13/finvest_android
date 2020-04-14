package com.invisee.finvest.ui.fragments.catalogue;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.responses.PackagesPerformanceResponse;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.eventBus.RxBusObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.OnClick;
import icepick.State;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by fajarfatur on 1/22/16.
 */
public class PackagePerformanceFragment extends BaseFragment {

    public static final String TAG = PackagePerformanceFragment.class.getSimpleName();
    private static final String PACKAGES = "packages";

    @Bind(R.id.lnrActions)
    LinearLayout lnrActions;
    /*    @Bind(R.id.chart)
        LineChartView chart;*/
    @Bind(R.id.progressbar)
    ProgressBar progressbar;
    @Bind(R.id.chart1)
    LineChart chart1;
    @Bind(R.id.value)
    TextView value;
    @Bind(R.id.valueDate)
    TextView valueDate;
    @Bind(R.id.lnIndication)
    LinearLayout lnIndication;


    @Bind(R.id.btn1month)
    ToggleButton btn1month;
    @Bind(R.id.btn6months)
    ToggleButton btn6months;
    @Bind(R.id.btn1year)
    ToggleButton btn1year;
    @Bind(R.id.btn3years)
    ToggleButton btn3years;
    @Bind(R.id.btn5years)
    ToggleButton btn5years;

    @BindColor(R.color.colorPrimary)
    int colorPrimary;
    @BindColor(R.color.colorPrimaryDark)
    int colorPrimaryDark;
    @BindDrawable(R.drawable.button_primary)
    Drawable buttonPrimary;
    @BindDrawable(R.drawable.button_primary_dark)
    Drawable buttonPrimaryStroke;

    private PackagePerformancePresenter presenter;

    @State
    Packages packages;
    @State
    PackagesPerformanceResponse response;

    private LineChartData data;
    private int numberOfLines = 0;
    private int numberOfPoints = 0;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = false;

    public String[] date;

    public static Fragment getFragment(Packages packages) {
        Fragment f = new PackagePerformanceFragment();

        Bundle extras = new Bundle();
        extras.putSerializable(PACKAGES, packages);

        f.setArguments(extras);
        return f;
    }

    @Override
    protected int getLayout() {
        return R.layout.f_package_performance;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (packages == null) {
            Bundle extras = getArguments();
            if (extras != null && extras.containsKey(PACKAGES)) {
                packages = (Packages) extras.getSerializable(PACKAGES);
            }
        }

        if (packages != null) {
            presenter = new PackagePerformancePresenter(this);

            if (packages.getId() == 93 || packages.getId() == 72 || packages.getId() == 62) {
                chart1.setVisibility(View.GONE);
                lnrActions.setVisibility(View.GONE);
                progressbar.setVisibility(View.GONE);
                lnIndication.setVisibility(View.VISIBLE);

            } else {
                onClickBtn1month(btn1month);

            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();

/*
        chart.setOnValueTouchListener(new LineChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
                DecimalFormat df = new DecimalFormat("0.00");
                Toast.makeText(getActivity(), String.valueOf(df.format(value.getY()) + " " + "%"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {

            }
        });
*/


    }

    public void showLoading() {
        progressbar.setVisibility(View.VISIBLE);
        chart1.setVisibility(View.GONE);
    }

    public void hideLoading() {
        progressbar.setVisibility(View.GONE);
        chart1.setVisibility(View.VISIBLE);
    }

/*
    public void setupChart() {

        List<String> list = response.getData().getPerformanceDate();

        List<Double> dataList = response.getData().getPerformanceData().get(0).getValue();
        List<Double> percentage = new ArrayList<>();
        for (int i = 0;i < dataList.size(); ++i) {
            percentage.add(dataList.get(i) * 100);
        }


        if (dataList.size() > 10)
            dataList = dataList.subList(0, 20);

*/
/*        Collections.reverse(dataList);*//*


        numberOfLines = dataList.size() - 1;
        numberOfPoints = dataList.size();

        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < numberOfLines; ++i) {


            List<PointValue> values = new ArrayList<>();
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, percentage.get(j).floatValue()));
            }


            Line line = new Line(values);

*/
/*            line.setHasLabelsOnlyForSelected(true);
            line.setColor(colorPrimary);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);*//*


            lines.add(line);
        }

        data = new LineChartData(lines);


        if (hasAxes) {

            ArrayList<AxisValue> axisXValues = new ArrayList<AxisValue>();
            for (int i = 0; i < list.size(); i++) {
                axisXValues.add(new AxisValue(i).setLabel(list.get(i).substring(0, 11)));
            }

            Axis axisX = new Axis();
            axisX.setValues(axisXValues);
            axisX.setHasTiltedLabels(true);
            Axis axisY = new Axis().setHasLines(true);

            if (hasAxesNames) {
                axisX.setName("");
                axisY.setName("");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }



        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart.setLineChartData(data);


        getBus().send(new RxBusObject(RxBusObject.RxBusKey.SCROLL_TO_BOTTOM, 0));

    }
*/

/*
    void drawChart() {
        List<PointValue> values = new ArrayList<>();

        List<Double> dataList = response.getData().getPerformanceData().get(0).getValue();
        List<String> list = response.getData().getPerformanceDate();

        ArrayList<AxisValue> axisXValues = new ArrayList<>();
        ArrayList<AxisValue> axisYValues = new ArrayList<>();

        for (int i = 0; i < dataList.size(); i++) {

            values.add(new PointValue(i, dataList.get(i).floatValue() * 100));
            axisXValues.add(new AxisValue(i).setLabel(list.get(i).substring(0, 11)));
        }

        Line line = new Line(values);
        line.setColor(cAppsPrimary);
        line.setHasPoints(true);
        line.setPointRadius(1);
        line.setStrokeWidth(3);
        line.setFilled(true);

        List<Line> lines = new ArrayList<>();
        lines.add(line);
        LineChartData lineChartData = new LineChartData(lines);



        Axis axisX = new Axis().setValues(axisXValues).setHasTiltedLabels(true).setHasLines(false);
        Axis axisY = new Axis().setHasTiltedLabels(true).setHasLines(false);
        lineChartData.setAxisXBottom(axisX);
        lineChartData.setAxisYLeft(axisY);

        chart.setLineChartData(lineChartData);
    }
*/

    public void cubicLineChart()  {
        final ArrayList<Entry> yVals = new ArrayList<Entry>();
        List<Double> dataList = response.getData().getPerformanceData().get(0).getValue();
        final List<String> datelist = response.getData().getPerformanceDate();

        for (int i = 0; i < datelist.size(); i++) {
            yVals.add(new Entry(i, dataList.get(i).floatValue() * 100));
        }
        LineDataSet set1;

        Log.d("this is my array", "arr: " + Arrays.toString(date));

        if (chart1.getData() != null &&
                chart1.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart1.getData().getDataSetByIndex(0);
            set1.setValues(yVals);
            chart1.getData().notifyDataChanged();
            chart1.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "DataSet 1");
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            //set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            /*set1.setDrawHorizontalHighlightIndicator(false);*/
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -50;
                }
            });

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); //

            final LineData data = new LineData(dataSets);
            data.setDrawValues(false);
            chart1.setViewPortOffsets(0, 0, 0, 50);

            // no description text
            chart1.getDescription().setEnabled(false);

            // enable touch gestures
            chart1.setTouchEnabled(true);

            // enable scaling and dragging
            chart1.setDragEnabled(true);
            chart1.setScaleEnabled(true);

            // if disabled, scaling can be done on x- and y-axis separately
            chart1.setPinchZoom(false);

            chart1.setDrawGridBackground(false);
            chart1.setMaxHighlightDistance(300);
            chart1.setData(data);
            chart1.setBackgroundColor(cGray);
            chart1.getLegend().setEnabled(false);
            chart1.getDescription().setEnabled(false);
            chart1.getAxisRight().setEnabled(false);




/*            chart1.setData(data);
            chart1.setTouchEnabled(true);
            chart1.setDragEnabled(false);
            chart1.setViewPortOffsets(0, 0, 0, 80);
            chart1.setScaleEnabled(false);
            chart1.setPinchZoom(false);
            chart1.setDrawGridBackground(false);
            chart1.setMaxHighlightDistance(400);
            chart1.setBackgroundColor(colorPrimary);
            chart1.getDescription().setEnabled(false);
            chart1.getAxisRight().setEnabled(false);
            chart1.getLegend().setEnabled(false);
            chart1.animateXY(3500, 3500);*/

            XAxis x = chart1.getXAxis();
            x.setEnabled(false);
            x.setDrawGridLines(false);
        /*    x.setAxisMinimum(0f);
            x.setGranularity(1f);
            x.setLabelRotationAngle(-30);
            x.setLabelCount(yVals.size(), false);
            x.setTextColor(Color.WHITE);
            x.setEnabled(false);
            x.setDrawGridLines(false);
            x.setAxisLineColor(Color.WHITE);*/
            x.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    Integer a = (int) value;
                    if (a > 16) {
                        a = 15;
                    }
                    return date[(int) value]; // xVal is a string array
                }
            });

            YAxis y = chart1.getAxisLeft();
            y.setLabelCount(yVals.size(), false);
            y.setTextColor(Color.WHITE);
            y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            y.setDrawGridLines(false);
            y.setAxisLineColor(Color.WHITE);
            y.setYOffset(-5);
/*            MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
            mv.setChartView(chart1); // For bounds control
            chart1.setMarker(mv);*/

            chart1.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    chart1.getXAxis().getValueFormatter().getFormattedValue(e.getX(), chart1.getXAxis());
                    Log.d("axisX",  chart1.getXAxis().getValueFormatter().getFormattedValue(e.getX(), chart1.getXAxis()));
                    valueDate.setText(chart1.getXAxis().getValueFormatter().getFormattedValue(e.getX(), chart1.getXAxis()));
                    value.setText(Utils.formatNumber(e.getY(), 2, true) + " %");

                }

                @Override
                public void onNothingSelected() {

                }
            });


            List<ILineDataSet> sets = chart1.getData()
                    .getDataSets();

//            for (ILineDataSet iSet : sets) {
//
//                LineDataSet set = (LineDataSet) iSet;
//                set.setMode(set.getMode() == LineDataSet.Mode.CUBIC_BEZIER
//                        ? LineDataSet.Mode.LINEAR
//                        :  LineDataSet.Mode.CUBIC_BEZIER);
//            }j

            for (ILineDataSet iSet : sets) {

                LineDataSet set = (LineDataSet) iSet;

                if (set.isDrawFilledEnabled())
                    set.setDrawFilled(false);
                else
                    set.setDrawFilled(true);
            }

            chart1.invalidate();

        }


    }




    @OnClick(R.id.btn1month)
    void onClickBtn1month(View view) {
        date = new String[0];
        btn1month.setChecked(true);
        btn6months.setChecked(false);
        btn1year.setChecked(false);
        btn3years.setChecked(false);
        btn5years.setChecked(false);
        presenter.packagePerformanceInfo(packages, PackagePerformancePresenter.ONE_MONTH);
    }

    @OnClick(R.id.btn6months)
    void onClickBtn6months(View view) {
        date = new String[0];
        btn1month.setChecked(false);
        btn6months.setChecked(true);
        btn1year.setChecked(false);
        btn3years.setChecked(false);
        btn5years.setChecked(false);
        presenter.packagePerformanceInfo(packages, PackagePerformancePresenter.SIX_MONTH);
    }

    @OnClick(R.id.btn1year)
    void onClickBtn1year(View view) {
        date = new String[0];
        btn1month.setChecked(false);
        btn6months.setChecked(false);
        btn1year.setChecked(true);
        btn3years.setChecked(false);
        btn5years.setChecked(false);
        presenter.packagePerformanceInfo(packages, PackagePerformancePresenter.ONE_YEAR);
    }

    @OnClick(R.id.btn3years)
    void onClickBtn3Years(View view) {
        date = new String[0];
        btn1month.setChecked(false);
        btn6months.setChecked(false);
        btn1year.setChecked(false);
        btn3years.setChecked(true);
        btn5years.setChecked(false);
        presenter.packagePerformanceInfo(packages, PackagePerformancePresenter.THREE_YEARS);
    }

    @OnClick(R.id.btn5years)
    void onClickBtn5years(View view) {
        date = new String[0];
        btn1month.setChecked(false);
        btn6months.setChecked(false);
        btn1year.setChecked(false);
        btn3years.setChecked(false);
        btn5years.setChecked(true);
        presenter.packagePerformanceInfo(packages, PackagePerformancePresenter.FIVE_YEARS);
    }


}
