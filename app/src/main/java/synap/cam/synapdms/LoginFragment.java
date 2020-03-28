package synap.cam.synapdms;
// https://www.journaldev.com/22299/android-jetpack-navigation-architecture#getting-started

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private static final int READ_PHONE_STATE_CODE = 100;
    private static final int REBOOT = 101;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View.OnClickListener s = Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_menuFragment);
        Button button = view.findViewById(R.id.login_btn);
        button.setOnClickListener(s);

        //permissions
        checkPermission(Manifest.permission.READ_PHONE_STATE,READ_PHONE_STATE_CODE);
        checkPermission(Manifest.permission.REBOOT,REBOOT);

        String SerialNumber = Build.getSerial();
        TextView serial_text = view.findViewById(R.id.serial_txt);
        SerialNumber = SerialNumber.toUpperCase();
        SerialNumber = SerialNumber.trim();
        serial_text.setText(SerialNumber);
    }

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                    new String[] { permission },
                    requestCode);
        }
        else {
            Toast.makeText(Objects.requireNonNull(getContext()),
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }
   //https://productionhalostorage.blob.core.windows.net/dmsystem/Synap_1.0_2020-03-27%2012-23.apk
    public class OnUpgradeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            File file = new File(Environment.getExternalStorageDirectory(), "app-debug.apk"); // mention apk file path here
//            if (file.exists()) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                startActivity(intent);
//            } else Toast.makeText(context, "Update File Not Found ! ", Toast.LENGTH_SHORT).show();
            try {
                doRestart();
            } catch (Exception e)
            {
                Toast.makeText(context,"Error Restarting!", Toast.LENGTH_LONG).show();
            }
        }
    }
    private synchronized void runRootUpdate() throws Exception {
        // Install Updated APK
        String command = "pm install -r " +  "https://productionhalostorage.blob.core.windows.net/dmsystem/Synap_1.0_2020-03-27%2012-23.apk";
        Process proc = Runtime.getRuntime().exec(new String[] {"su", "-c", command});
        int test = proc.waitFor(); // Error is here.
    }

    private void doRestart() {
        Intent mStartActivity = new Intent(getContext(), MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(getContext(), mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) Objects.requireNonNull(getActivity()).getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }


}
