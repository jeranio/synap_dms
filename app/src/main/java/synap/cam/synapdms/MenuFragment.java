package synap.cam.synapdms;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View.OnClickListener wifiListener = Navigation.createNavigateOnClickListener(R.id.action_menuFragment_to_wifiFragment);
        Button wifiBtn = view.findViewById(R.id.wifi_btn);
        wifiBtn.setOnClickListener(wifiListener);

        View.OnClickListener bluetoothListener = Navigation.createNavigateOnClickListener(R.id.action_menuFragment_to_bluetoothFragment);
        Button bluetoothBtn = view.findViewById(R.id.bluetooth_btn);
        bluetoothBtn.setOnClickListener(bluetoothListener);
    }
}