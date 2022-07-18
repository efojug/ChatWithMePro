package com.efojug.chatwithmepro.ui.gallery;

import static com.efojug.chatwithmepro.MainActivity.Login;
import static com.efojug.chatwithmepro.MainActivity.toast;
import static com.efojug.chatwithmepro.MainActivity.user;
import static com.efojug.chatwithmepro.MainActivity.username;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

import com.efojug.chatwithmepro.MainActivity;
import com.efojug.chatwithmepro.MyApplication;
import com.efojug.chatwithmepro.R;
import com.efojug.chatwithmepro.RootChecker;
import com.efojug.chatwithmepro.databinding.FragmentGalleryBinding;

import java.util.concurrent.Executor;

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
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.Vibrate(2);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        postponeEnterTransition();
        super.onCreate(savedInstanceState); // Always call the superclass first
        TransitionInflater iinflater = TransitionInflater.from(requireContext());
        setEnterTransition(iinflater.inflateTransition(R.transition.slide_right));
        setExitTransition(iinflater.inflateTransition(R.transition.fade));
        // Check whether we're recreating a previously destroyed instance
        root.findViewById(R.id.Login).setOnClickListener(view -> biometricPrompt.authenticate(promptInfo));
        try {
            Executor executor = ContextCompat.getMainExecutor(MyApplication.context);
            biometricPrompt = new BiometricPrompt(getActivity(),
                    executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    toast("请先设置屏幕密码和指纹");
                    super.onAuthenticationError(errorCode, errString);
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    username = "efojug";
                    ((TextView) root.findViewById(R.id.username)).setText(username);
                    root.findViewById(R.id.changeUsername).setVisibility(View.VISIBLE);
                    root.findViewById(R.id.changeUsernameOK).setVisibility(View.INVISIBLE);
                    root.findViewById(R.id.username).setEnabled(false);
                    root.findViewById(R.id.Login).setVisibility(View.INVISIBLE);
                    root.findViewById(R.id.outLogin).setVisibility(View.VISIBLE);
                    Login = true;
                    user[0] = true;
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    toast("验证失败");
                }
            });

            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("验证您的身份")
                    .setNegativeButtonText("取消")
                    .build();
        } catch (Exception e) {
            toast("验证失败：" + e.getMessage());
            e.printStackTrace();
        }
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
        if (!Login) {
            ((EditText) root.findViewById(R.id.username)).setText(username);
            root.findViewById(R.id.changeUsername).setVisibility(View.INVISIBLE);
            root.findViewById(R.id.changeUsernameOK).setVisibility(View.INVISIBLE);
            root.findViewById(R.id.username).setEnabled(false);
            root.findViewById(R.id.Login).setVisibility(View.VISIBLE);
            root.findViewById(R.id.outLogin).setVisibility(View.INVISIBLE);
        } else {
            root.findViewById(R.id.changeUsername).setVisibility(View.VISIBLE);
            root.findViewById(R.id.changeUsernameOK).setVisibility(View.INVISIBLE);
            root.findViewById(R.id.username).setEnabled(false);
            root.findViewById(R.id.Login).setVisibility(View.INVISIBLE);
            root.findViewById(R.id.outLogin).setVisibility(View.VISIBLE);
        }
        startPostponedEnterTransition();
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