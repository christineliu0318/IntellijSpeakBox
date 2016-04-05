package speakbox.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.SpeakBox.R;

/**
 * Created by YingYing on 2016-04-03.
 * Displays the question, images and response method
 */
public class QuestionDisplayFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.display_question, container, false);
    }
}
