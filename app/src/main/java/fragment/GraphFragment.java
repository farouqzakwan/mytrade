package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.practice.another.anotherpractice.R;

import java.util.ArrayList;


public class GraphFragment extends Fragment {

    ArrayList<CandleEntry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();

    public void populate(ArrayList<CandleEntry> entries,ArrayList<String> labels){
        this.entries = entries;
        this.labels = labels;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_graph, container, false);


        CandleStickChart candleStickChart = (CandleStickChart)view.findViewById(R.id.chart);


        CandleDataSet dataset = new CandleDataSet(entries, "# Daily chart");


        CandleData data = new CandleData(labels, dataset);
        candleStickChart.setData(data);
        candleStickChart.setAutoScaleMinMaxEnabled(true);
        candleStickChart.setGridBackgroundColor(getResources().getColor(R.color.Navy));
        YAxis yAxis = candleStickChart.getAxisLeft();
        yAxis.setStartAtZero(false);
        //candleStickChart.setDoubleTapToZoomEnabled(true);
        //candlestick end

        return view;
    }


}
//[TODO] CREATE LISTENER TO COMMUNICATIE WITH


