package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by kelry on 29/03/18.
 */

public class RetornoDec implements JsonDeserializer{

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement jsonElement=json.getAsJsonObject();
        if(json.getAsJsonObject()!=null){

            jsonElement=json.getAsJsonObject();
        }
        return (new Gson().fromJson(jsonElement,Retorno.class));
    }
}
