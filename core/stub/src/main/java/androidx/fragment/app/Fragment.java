package androidx.fragment.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

public class Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }

    public void onCreate(Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }

    public void onStart() {
        throw new RuntimeException("Stub!");
    }

    public void onResume() {
        throw new RuntimeException("Stub!");
    }

    public void onPause() {
        throw new RuntimeException("Stub!");
    }

    public void onSaveInstanceState(Bundle outState) {
        throw new RuntimeException("Stub!");
    }

    public void onStop() {
        throw new RuntimeException("Stub!");
    }

    public void onDestroyView() {
        throw new RuntimeException("Stub!");
    }

    public void onDestroy() {
        throw new RuntimeException("Stub!");
    }

    final public boolean isRemoving() {
        throw new RuntimeException("Stub!");
    }

    public final FragmentActivity getActivity() {
        throw new RuntimeException("Stub!");
    }

    public Fragment getParentFragment() {
        throw new RuntimeException("Stub!");
    }

    public FragmentManager getChildFragmentManager() {
        throw new RuntimeException("Stub!");
    }
}