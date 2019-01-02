package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface;

/**
 * Created by kelry on 26/03/18.
 */

import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Result;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Retorno;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public  interface UploadService {

    @POST("salvar/SalvarChecklist")
    Call<Retorno> InserirDados(@Body Result dados);

    //@Multipart
    @POST("salvar/SalvarChecklistDocaSaida")
    Call<Retorno> InserirImagens(@Body Result user);
}
