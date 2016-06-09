package speakbox.ui.fragments;


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
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import speakbox.model.Response;
import speakbox.util.Constants;

/**
 * Created by Christine on 4/28/2016.
 */
public class ChartFragment extends Fragment {

    private LineChart chart;
    private LineDataSet set1;
    private ArrayList<String> xVals;
    private ArrayList<String> yValStrings;
    private ArrayList<Entry> yValues;

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
        displayData();
        return rootView;
    }

    public void initializeScreen(View rootView) {
        chart = (LineChart) rootView.findViewById(R.id.chart);
        xVals = new ArrayList<>();
        yValStrings = new ArrayList<>();
        yValues = new ArrayList<Entry>();
    }

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
                    xVals.add(next.getResponseDate());
                    yValStrings.add(next.getAnswer());
                }
                System.out.println(xVals);
                System.out.println(yValStrings);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("didn't work");
            }
        });

        int index = 0;
        for (String yValue: yValStrings){
            float yVal = Float.parseFloat(yValue);
            Entry entry = new Entry(yVal,index);
            yValues.add(entry);
            index ++;
        }

        set1 = new LineDataSet(yValues, "Responses");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
    }

    public void displayData(){
        ArrayList<ILineDataSet> dataSet = new ArrayList<>();
        dataSet.add(set1);



        ArrayList testVals = new ArrayList<>();
        testVals.add("1");
        testVals.add("2");
        testVals.add("3");
        testVals.add("4");
        testVals.add("5");
        testVals.add("6");
        testVals.add("7");
        testVals.add("8");
        testVals.add("9");
        testVals.add("10");
        testVals.add("11");
        testVals.add("12");
        testVals.add("13");


    //TODO: change the xaxis values to something that works for the graph.
        LineData data = new LineData(testVals, dataSet);
        chart.setData(data);
        chart.invalidate();
    }
}
