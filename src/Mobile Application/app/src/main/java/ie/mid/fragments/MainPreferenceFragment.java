package ie.mid.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;

import ie.mid.ProfileCreationActivity;
import ie.mid.ProfileSelectionActivity;
import ie.mid.R;
import ie.mid.handler.DatabaseHandler;

public class MainPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_main);

        // gallery EditText change listener
        bindPreferenceSummaryToValue(findPreference(getString(R.string.key_server_address)));

        // feedback preference click listener
        Preference feedbackPref = findPreference(getString(R.string.key_send_feedback));
        feedbackPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                sendFeedback(getActivity());
                return true;
            }
        });

        Preference deletePref = findPreference(getString(R.string.key_delete_all));
        deletePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                deleteAll(getActivity());
                return true;
            }
        });
    }

    private void deleteAll(final Context context) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        DatabaseHandler handler = new DatabaseHandler(context);
                        handler.open();
                        handler.purgeDatabase();
                        handler.close();
                        Intent intent = new Intent(context,ProfileSelectionActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AlertDialog);
        builder.setMessage("Are you sure you wish to delete all data? This process is irreversible").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(preferenceChangeListener);

        preferenceChangeListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener preferenceChangeListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();

            if (preference instanceof EditTextPreference) {
                if (preference.getKey().equals("key_server_address")) {
                    preference.setSummary(stringValue);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    public static void sendFeedback(Context context) {
        String body = null;
        try {
            body = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            body = "\n\n----Device information----\n Device OS: Android \n Device OS version: " +
                    Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + Build.BRAND +
                    "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER;
        } catch (PackageManager.NameNotFoundException e) {
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"cillian.mcneill2@mail.dcu.ie"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "MiD App Query");
        intent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.choose_email_client)));
    }
}
