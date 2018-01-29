package ie.mid;

import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import ie.mid.fragments.MainPreferenceFragment;

public class SettingsActivity extends PreferenceActivity {
    private static final String TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        resid = R.style.AppBaseTheme;
        super.onApplyThemeResource(theme, resid, true);

    }

}