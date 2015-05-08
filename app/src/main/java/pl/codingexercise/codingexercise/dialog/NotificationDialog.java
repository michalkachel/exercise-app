package pl.codingexercise.codingexercise.dialog;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.codingexercise.codingexercise.R;
import pl.codingexercise.codingexercise.model.Notification;

/**
 * Created by michalkachel on 08.05.2015.
 */
public class NotificationDialog extends DialogFragment {
    public static final String FRAGMENT_TAG = NotificationDialog.class.getName();
    public static final String NOTIFICATION_ARG = "notification_arg";

    private Notification mNotification;

    @InjectView(R.id.dialog_title)
    TextView mDialogTitle;
    @InjectView(R.id.dialog_message)
    TextView mDialogMessage;
    @InjectView(R.id.dialog_close)
    TextView mDialogClose;
    @InjectView(R.id.dialog_image)
    ImageView mDialogImage;

    public static NotificationDialog newInstance(Notification notification) {
        Bundle args = new Bundle();
        args.putParcelable(NOTIFICATION_ARG, notification);
        NotificationDialog notificationDialog = new NotificationDialog();
        notificationDialog.setArguments(args);
        return notificationDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNotification = getArguments().getParcelable(NOTIFICATION_ARG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.dialog_notification, container, false);
        ButterKnife.inject(this, rootView);

        mDialogTitle.setText(mNotification.getTitle());
        mDialogClose.setText(mNotification.getButtonTitle());
        if (mNotification.getDescription() != null) {
            mDialogMessage.setText(mNotification.prepareMessage());
        }

        try {
            mDialogImage.setImageBitmap(getBitmapFromAssets(mNotification.getImagePath()));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), R.string.image_error, Toast.LENGTH_SHORT).show();
            mDialogImage.setVisibility(View.GONE);
        }

        return rootView;
    }

    private Bitmap getBitmapFromAssets(String imagePath) throws IOException {
        InputStream inputStream = getActivity().getAssets().open(imagePath);
        return BitmapFactory.decodeStream(inputStream);
    }

    @OnClick(R.id.dialog_close)
    public void closeSelf() {
        dismiss();
    }
}
