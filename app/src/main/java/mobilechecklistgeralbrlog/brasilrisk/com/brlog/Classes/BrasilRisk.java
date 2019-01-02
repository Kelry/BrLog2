package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes;

import android.content.Context;
import android.content.SharedPreferences;
import com.brasilrisk.mobilechecklistgeralbrlog.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.UploadService;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Utils.Shered_Cache;

public class BrasilRisk {

    public static String URL_Conetcion = Shered_Cache.URL_HOMOLOGAÇÂO;
    public UploadService uploadService;
    public static Result GetResult(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getResources().getString(R.string.sharedpreferences), 0);
        String result_string = preferences.getString("Result", null);
        if (result_string == null)
            return new Result();
        Type type2 = new TypeToken<Result>() {
        }.getType();
        return new Gson().fromJson(result_string, type2);
    }
    public static void SetResult(Context context, Result result) {
        SharedPreferences preferences = context.getSharedPreferences(context.getResources().getString(R.string.sharedpreferences), 0);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(result);
        editor.putString("Result", json);
        editor.apply();
    }
}


