package speakbox.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.SpeakBox.R;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import speakbox.ui.login.LoginActivity;
import speakbox.util.Constants;

/**
 * Created by YingYing on 16-05-08.
 */
public class ProfileFragment extends Fragment {

    private TextView username;
    private Button logout;
    private Firebase fb;

    public static ProfileFragment newInstance() {
        ProfileFragment profileFragment = new ProfileFragment();
        return profileFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fb = new Firebase(Constants.FIREBASE_URL);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        initializeScreen(rootView);
        getUserFirstName();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fb.unauth();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return rootView;
    }

    public void initializeScreen(View rootView) {
        username = (TextView) rootView.findViewById(R.id.profile_user);
        logout = (Button) rootView.findViewById(R.id.profile_logout);
    }

    private void getUserFirstName() {

        if (fb.getAuth() != null) {

            AuthData ad = fb.getAuth();
            String uid = ad.getUid();

            Firebase userLocation = new Firebase(Constants.FIREBASE_URL + "/users/" + uid + "/name");

            userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String userFirstName = dataSnapshot.getValue().toString();
                    username.setText(userFirstName);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("didn't work");
                }
            });
        }
    }
}
