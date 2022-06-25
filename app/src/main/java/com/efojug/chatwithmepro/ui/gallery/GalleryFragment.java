package com.efojug.chatwithmepro.ui.gallery;

import static com.efojug.chatwithmepro.MainActivity.LoginLevel;
import static com.efojug.chatwithmepro.MainActivity.username;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.efojug.chatwithmepro.R;
import com.efojug.chatwithmepro.RootChecker;
import com.efojug.chatwithmepro.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public static String Rooted = "";
    public static String givenRooted = "";
    public static String Busybox = "";
    public static String Model = "";
    public static String Id = "";
    public static String Device = "";
    public static String User = "";
    public static String Manufacturer = "";
    public static Boolean isChecked = false;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        super.onCreate(savedInstanceState); // Always call the superclass first
        // Check whether we're recreating a previously destroyed instance
        if (!isChecked) {
            try {
                ((TextView) root.findViewById(R.id.MODEL)).setText(Build.MODEL);
                ((TextView) root.findViewById(R.id.ID)).setText(Build.ID);
                ((TextView) root.findViewById(R.id.DEVICE)).setText(Build.DEVICE);
                ((TextView) root.findViewById(R.id.USER)).setText(Build.USER);
                ((TextView) root.findViewById(R.id.MANUFACTURER)).setText(Build.MANUFACTURER);
                ((TextView) root.findViewById(R.id.ROOT)).setText(new RootChecker().getRootData().substring(0, 1));
                ((TextView) root.findViewById(R.id.givenROOT)).setText(new RootChecker().getRootData().substring(1, 2));
                ((TextView) root.findViewById(R.id.BusyBox)).setText(new RootChecker().getRootData().substring(2, 5));
                Model = Build.MODEL;
                Id = Build.ID;
                Device = Build.DEVICE;
                User = Build.USER;
                Manufacturer = Build.MANUFACTURER;
                Rooted = new RootChecker().getRootData().substring(0, 1);
                givenRooted = new RootChecker().getRootData().substring(1, 2);
                Busybox = new RootChecker().getRootData().substring(2, 5);
                isChecked = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ((TextView) root.findViewById(R.id.MODEL)).setText(Model);
            ((TextView) root.findViewById(R.id.ID)).setText(Id);
            ((TextView) root.findViewById(R.id.DEVICE)).setText(Device);
            ((TextView) root.findViewById(R.id.USER)).setText(User);
            ((TextView) root.findViewById(R.id.MANUFACTURER)).setText(Manufacturer);
            ((TextView) root.findViewById(R.id.ROOT)).setText(Rooted);
            ((TextView) root.findViewById(R.id.givenROOT)).setText(givenRooted);
            ((TextView) root.findViewById(R.id.BusyBox)).setText(Busybox);
        }
        if (LoginLevel == 0) {
            root.findViewById(R.id.changeUsername).setVisibility(View.INVISIBLE);
            root.findViewById(R.id.changeUsernameOK).setVisibility(View.INVISIBLE);
            root.findViewById(R.id.username).setEnabled(false);
            root.findViewById(R.id.Login).setVisibility(View.INVISIBLE);
            root.findViewById(R.id.outLogin).setVisibility(View.INVISIBLE);
            root.findViewById(R.id.BindAuth).setVisibility(View.VISIBLE);
        } else if (LoginLevel == 1) {
            root.findViewById(R.id.changeUsername).setVisibility(View.INVISIBLE);
            root.findViewById(R.id.changeUsernameOK).setVisibility(View.INVISIBLE);
            root.findViewById(R.id.username).setEnabled(false);
            root.findViewById(R.id.Login).setVisibility(View.VISIBLE);
            root.findViewById(R.id.outLogin).setVisibility(View.INVISIBLE);
            root.findViewById(R.id.BindAuth).setVisibility(View.INVISIBLE);
        } else if (LoginLevel == 2) {
            root.findViewById(R.id.changeUsername).setVisibility(View.VISIBLE);
            root.findViewById(R.id.changeUsernameOK).setVisibility(View.INVISIBLE);
            root.findViewById(R.id.username).setEnabled(false);
            root.findViewById(R.id.Login).setVisibility(View.INVISIBLE);
            root.findViewById(R.id.outLogin).setVisibility(View.VISIBLE);
            root.findViewById(R.id.BindAuth).setVisibility(View.INVISIBLE);
        }

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putString("username", username);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
}