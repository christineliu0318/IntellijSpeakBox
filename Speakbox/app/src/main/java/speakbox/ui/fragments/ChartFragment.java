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
//                for (DataSnapshot response: dataSnapshot.getChildren()){
//                    Response res = response.getValue(Response.class);
//                    System.out.println(res.getAnswer());
//                }

                Iterator itr = dataSnapshot.getChildren().iterator();
                while (itr.hasNext()){
                    Object next = itr.next();
                    System.out.println(next);
                }
                System.out.println(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("didn't work");
            }
        });
        xVals = new ArrayList<>();
        xVals.add("1");
        xVals.add("2");

        ArrayList<Entry> values1 = new ArrayList<Entry>();
        Entry v1 = new Entry(50,0);
        Entry v2 = new Entry(20,1);
        values1.add(v1);
        values1.add(v2);

        set1 = new LineDataSet(values1, "Responses");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
    }

    public void displayData(){
        ArrayList<ILineDataSet> dataSet = new ArrayList<>();
        dataSet.add(set1);
        LineData data = new LineData(xVals, dataSet);
        chart.setData(data);
        chart.invalidate();
    }
}
