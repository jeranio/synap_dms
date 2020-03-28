package synap.cam.synapdms;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class BluetoothFragment extends Fragment {
    private static final UUID synap_uuid = UUID.fromString("3a7e319b-d0e3-4b8c-8990-5c695ff5f6a5");

    private BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
    private Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothDevice mmDevice;

    public BluetoothFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (!mBtAdapter.isEnabled())
        {
            mBtAdapter.enable();
            Toast.makeText(getActivity().getApplicationContext(), "Bluetooth Turned ON", Toast.LENGTH_SHORT).show();
        }

                if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Toast.makeText(getActivity().getApplicationContext(), "Found device -- " + deviceName + " -- " + deviceHardwareAddress, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "No paired devices found !!", Toast.LENGTH_SHORT).show();
        }


        return inflater.inflate(R.layout.fragment_bluetooth, container, false);




    }
}


