package pl.codingexercise.codingexercise.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.codingexercise.codingexercise.R;
import pl.codingexercise.codingexercise.dialog.NotificationDialog;
import pl.codingexercise.codingexercise.model.Notification;


public class MainActivity extends AppCompatActivity {

    public static final String JSON_FILENAME = "notifications.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
    }

    @OnClick(R.id.show_dialog)
    public void showNotificationDialog() {
        Notification notification = null;
        try {
            notification = Notification.getFirstElementFromJsonArray(getJsonFromAssets(JSON_FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.error_occured), Toast.LENGTH_SHORT).show();
        }

        if (notification != null) {
            NotificationDialog notificationDialog = NotificationDialog.newInstance(notification);
            notificationDialog.show(getFragmentManager(), NotificationDialog.FRAGMENT_TAG);
        }
    }

    private String getJsonFromAssets(String fileName) throws IOException {
        InputStream inputStream = getAssets().open(fileName);
        int bufferSize = inputStream.available();
        byte[] buffer = new byte[bufferSize];
        inputStream.read(buffer);
        inputStream.close();

        return new String(buffer);
    }

}
