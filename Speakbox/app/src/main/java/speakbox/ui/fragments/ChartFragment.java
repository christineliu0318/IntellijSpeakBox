package speakbox.ui.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.SpeakBox.R;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Iterator;

import speakbox.model.Response;
import speakbox.util.Constants;

/**
 * Created by Christine on 4/28/2016.
 */
public class ChartFragment extends Fragment {

    private LineChart chart;
    private LineDataSet set1;
    private ArrayList<String> xAxes;
    private ArrayList<String> responseValues;
    private ArrayList<Entry> yAxes;
    private int numDataPoints;

    public static ChartFragment newInstance() {
        ChartFragment cFragment = new ChartFragment();
        return cFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chart, container, false);
        initializeScreen(rootView);
        retrieveData();
        return rootView;
    }

    public void initializeScreen(View rootView) {
        chart = (LineChart) rootView.findViewById(R.id.chart);
        xAxes = new ArrayList<>();
        responseValues = new ArrayList<>();
        yAxes = new ArrayList<>();

        // no description text
        chart.setDescription("");
        chart.setNoDataTextDescription("You need to provide data for the chart.");

        //enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

    }

    /**
     * This method retrieves the x and y values form the specified users responses. Then modifies
     * the values to appropriate string and interger values for array lists.
     */
    public void retrieveData(){

        Firebase fb = new Firebase(Constants.FIREBASE_URL);
        AuthData ad = fb.getAuth();
        String uid = ad.getUid();

        final Firebase responseLocation = new Firebase(Constants.FIREBASE_URL + "/responses/" + uid);
        Query queryRef = responseLocation.orderByValue();

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> itr = dataSnapshot.getChildren().iterator();
                while (itr.hasNext()){
                    Response next = itr.next().getValue(Response.class);
                    xAxes.add(next.getResponseDate());
                    responseValues.add(next.getAnswer());
                }
                int index = 0;
                for (String response: responseValues){
                    float yVal = Float.parseFloat(response);
                    Entry entry = new Entry(yVal,index);
                    yAxes.add(entry);
                    index ++;
                }

                set1 = new LineDataSet(yAxes, "Responses");
                set1.setDrawCircles(false);
                set1.setColor(Color.BLUE);

                ArrayList<ILineDataSet> dataSet = new ArrayList<>();
                dataSet.add(set1);

                //TODO: Need to figure out how to incoporate dates into this.
                ArrayList testVals = new ArrayList<>();
                for(int i=0;i< yAxes.size();i++){
                    testVals.add(String.valueOf(i + 1));
                }


                LineData data = new LineData(testVals, dataSet);


                XAxis xAxis = chart.getXAxis();

                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                chart.setVisibleXRangeMaximum(10);
                chart.setData(data);
                chart.invalidate();

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("didn't work");
            }
        });

    }

}
