package jp.ac.meijou.android.s241205161;

import android.content.Context;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import java.util.Optional;

import io.reactivex.rxjava3.core.Single;

public class PrefDataStore {
    private static PrefDataStore instance;
    private final RxDataStore<Preferences> dataStore;

    private PrefDataStore(RxDataStore<Preferences> dataStore) {
        this.dataStore = dataStore;
    }

    public static PrefDataStore getInstance(Context context) {
        if (instance == null) {
            var dataStore = new RxPreferenceDataStoreBuilder(
                    context.getApplicationContext(), "settings").build();
            instance = new PrefDataStore(dataStore);
        }
        return instance;
    }

    public <T> void set(String key, T value) {
        dataStore.updateDataAsync(prefsIn -> {
                    var mutablePreferences = prefsIn.toMutablePreferences();
                    if (value instanceof Integer)
                        mutablePreferences.set(PreferencesKeys.intKey(key), (int)value);
                    else if (value instanceof String)
                        mutablePreferences.set(PreferencesKeys.stringKey(key), (String)value);
                    else if (value instanceof Boolean)
                        mutablePreferences.set(PreferencesKeys.booleanKey(key), (Boolean)value);
                    else if (value instanceof Double)
                        mutablePreferences.set(PreferencesKeys.doubleKey(key), (Double)value);
                    else if (value instanceof Float)
                        mutablePreferences.set(PreferencesKeys.floatKey(key), (Float)value);
                    else if (value instanceof Long)
                        mutablePreferences.set(PreferencesKeys.longKey(key), (Long)value);
                    else
                        mutablePreferences.set(PreferencesKeys.stringKey(key), value.toString());
                    return Single.just(mutablePreferences);
                })
                .subscribe();
    }
    public <T> Optional<T> get(String key, Class<T> type){
        return dataStore.data().map(prefs -> {
            Preferences.Key<?> prefKey = null;
            switch (type.getSimpleName()){
                case "Integer":
                    prefKey = PreferencesKeys.intKey(key);
                    break;
                case "String":
                    prefKey = PreferencesKeys.stringKey(key);
                    break;
                case "Boolean":
                    prefKey = PreferencesKeys.booleanKey(key);
                    break;
                case "Double":
                    prefKey = PreferencesKeys.doubleKey(key);
                    break;
                case "Float":
                    prefKey = PreferencesKeys.floatKey(key);
                    break;
                case "Long":
                    prefKey = PreferencesKeys.longKey(key);
                    break;
                default:
                    return Optional.<T>empty();
            }
            return Optional.ofNullable(type.cast(prefs.get(prefKey)));
        }).blockingFirst();
    }
}
