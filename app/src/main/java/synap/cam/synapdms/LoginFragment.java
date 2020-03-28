package synap.cam.synapdms;

// https://www.journaldev.com/22299/android-jetpack-navigation-architecture#getting-started
// https://github.com/javiersantos/AppUpdater

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private static final int READ_PHONE_STATE_CODE = 100;
    private static final int REBOOT = 101;
    private static String TAG = "Synap Debug Login : ";

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
        //serial number
        TextView serial_text = view.findViewById(R.id.serial_txt);
        String SerialNumber = Build.getSerial();
        SerialNumber = SerialNumber.toUpperCase();
        SerialNumber = SerialNumber.trim();
        serial_text.setText(SerialNumber);
        //version number
        TextView version_text = view.findViewById(R.id.version_txt);

        long version = 88;

        try {
            PackageManager manager = getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    getContext().getPackageName(), 0);
            version = info.getLongVersionCode();
        } catch (Exception e) {
            Log.e("YourActivity", "Error getting version");
        }

        Log.i("Application.Version", String.valueOf(version));
        version_text.setText(String.valueOf(version));
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
}


