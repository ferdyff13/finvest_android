package com.invisee.finvest.ui.fragments.portfolio;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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
import com.invisee.finvest.data.api.beans.InvestmentPerformance;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.ui.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by fajarfatur on 3/2/16.
 */
public class PortfolioInvesmentPerformanceFragment extends BaseFragment {

    private static final String PORTFOLIO = "portfolio";

//    @Bind(R.id.lineChart)
//    LineChartView lineChartView;

    @Bind(R.id.chart1)
    LineChart chart1;
    @Bind(R.id.value)
    TextView valueChart;
    @Bind(R.id.valueDate)
    TextView valueDate;

    public String[] date;

    private PortfolioInvesmentPerformancePresenter presenter;
    private PortfolioInvestment investment;

    public static PortfolioInvesmentPerformanceFragment initiateFragment(PortfolioInvestment investment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PORTFOLIO, investment);
        PortfolioInvesmentPerformanceFragment fragment = new PortfolioInvesmentPerformanceFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.f_portfolio_investment_performance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PortfolioInvesmentPerformancePresenter(this);
        investment = (PortfolioInvestment) getArguments().get(PORTFOLIO);
    }

    @Override
    public void onResume() {
        super.onResume();

//        lineChartView.setOnValueTouchListener(new LineChartOnValueSelectListener() {
//            @Override
//            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
//                Toast.makeText(getActivity(), String.valueOf(value.getY() + " " + "%"), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onValueDeselected() {
//
//            }
//        });


        presenter.getInvestmentPerformance(investment.getInvestmentAccountNumber());
    }

//    void drawChart(List<InvestmentPerformance> data) {
//        int numberOfPoints = data.size();
//        List<PointValue> values = new ArrayList<>();
//        ArrayList<AxisValue> axisXValues = new ArrayList<>();
//        ArrayList<AxisValue> axisYValues = new ArrayList<>();
//
//        for (int i = 0; i < numberOfPoints; i++) {
//            values.add(new PointValue(i, (float) data.get(i).getValue() * 100));
//            axisXValues.add(new AxisValue(i).setLabel(data.get(i).getDate().substring(0, 11)));
//            axisYValues.add(new AxisValue(i).setLabel(Double.toString(data.get(i).getValue()) + "%"));
//        }
//
//        Line line = new Line(values);
//        line.setColor(cAppsPrimary);
//
//        List<Line> lines = new ArrayList<>();
//        lines.add(line);
//        LineChartData lineChartData = new LineChartData(lines);
//
//        Axis axisX = new Axis().setValues(axisXValues).setHasTiltedLabels(true).setHasLines(true);
//        Axis axisY = new Axis().setHasTiltedLabels(true).setHasLines(true);
//        lineChartData.setAxisXBottom(axisX);
//        lineChartData.setAxisYLeft(axisY);
//
//
//        //lineChartData.setBaseValue(Float.NEGATIVE_INFINITY);
//        lineChartView.setLineChartData(lineChartData);
//    }


    public void chart(List<InvestmentPerformance> value){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < value.size(); i++) {
            yVals.add(new Entry(i, value.get(i).getValue().floatValue() * 100));
        }

        LineDataSet set1;

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
                    valueChart.setText(Utils.formatNumber(e.getY(), 2, true) + " %");

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
}
