package br.com.enterprise.pytaco.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.PacoteCompraItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.PacoteCompra;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;
import br.com.enterprise.pytaco.util.StringUtil;

public class CompraActivity extends BaseRecyclerActivity implements PurchasesUpdatedListener {

    private String produtoComprado = "";
    private BillingClient billingClient;
    private List<SkuDetails> lstSkuDetails;
    private TextView lblQtdPytaco;
    private PacoteCompraItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        RecyclerView lsvPacotes = getRecyclerView();
        lblQtdPytaco = findViewById(R.id.compras_lblQtdPytaco);

        adapter = new PacoteCompraItemAdapter(this, new ArrayList<PacoteCompra>(), R.layout.lst_pacote_compra_item);
        lsvPacotes.setAdapter(adapter);
    }

    private void pSetupBillingClient() {
        pDisableScreen();

        if (billingClient == null) {
            billingClient = BillingClient.newBuilder(this)
                    .enablePendingPurchases()
                    .setListener(this).build();
        }

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    pLoadSkus();
                } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.SERVICE_DISCONNECTED) {
                    pServicoNaoDisponivel();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                makeShortToast("Serviço desconectado");
            }
        });
    }

    private void pLoadSkus() {
        if (billingClient.isReady()) {
            List<String> skuList = new ArrayList<>();

            for (PacoteCompra item : adapter.getLst()) {
                skuList.add(item.getNome());
            }

            SkuDetailsParams params = SkuDetailsParams.newBuilder()
                    .setSkusList(skuList)
                    .setType(BillingClient.SkuType.INAPP)
                    .build();

            billingClient.querySkuDetailsAsync(params, new SkuDetailsResponseListener() {
                @Override
                public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null && !list.isEmpty()) {
                        pEnableScreen();
                        lstSkuDetails = list;
                    } else {
                        pServicoNaoDisponivel();
                    }
                }
            });
        } else {
            pServicoNaoDisponivel();
        }
    }

    private void pServicoNaoDisponivel() {
        pEnableScreen();
        billingClient.endConnection();
        billingClient = null;
        makeShortToast("Serviço não disponível no momento");
        onBackPressed();
    }

    private void pAtualizaPytacos() {
        lblQtdPytaco.setText(StringUtil.numberToStr(usuario.getQtdPytaco()));
    }

    private void pListaPacoteCompra() {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.listaPacoteCompra();
    }

    private boolean pExisteDialogAberto() {
        return dialogLoading != null && dialogLoading.getDialog().isShowing();
    }

    private void pTrataRespostaListaPacoteCompra(String response) {
        try {
            adapter.getLst().clear();
            JSONArray resp = new JSONObject(response).getJSONArray("entry");

            if (resp.length() > 0 && !resp.getJSONObject(0).getString("id_pacote").isEmpty()) {
                for (int i = 0; i < resp.length(); i++) {
                    JSONObject item = resp.getJSONObject(i);
                    PacoteCompra pacoteCompra = new PacoteCompra();
                    pacoteCompra.setId(Integer.parseInt(item.getString("id_pacote")));
                    pacoteCompra.setNome(item.getString("nomepacote"));
                    pacoteCompra.setResumo(item.getString("resumopacote"));
                    pacoteCompra.setValor(StringUtil.strToNumber(item.getString("valorpacote").substring(3).replace(",", ".")));
                    pacoteCompra.setDescricao(item.getString("descricaopacote"));
                    adapter.getLst().add(pacoteCompra);
                }
            }

            pSetupBillingClient();
        } catch (JSONException ignored) {
        } finally {
            adapter.notifyDataSetChanged();
        }
    }

    private void pTratarCompra(@NotNull Purchase compra) {
        if (compra.getSku().equals(produtoComprado) && compra.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            makeShortToast("Compra realizada com sucesso");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        pAtualizaPytacos();

        if (!pExisteDialogAberto()) {
            pListaPacoteCompra();
        }
    }

    @Override
    public void onError(@NotNull VolleyError error) {
        if (pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_PACOTE_COMPRA)) {
            adapter.getLst().clear();
            adapter.notifyDataSetChanged();
        }

        super.onError(error);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return findViewById(R.id.compras_lsvPacotes);
    }

    @Override
    public void onLstItemClick(int position) {
        if (lstSkuDetails != null && !lstSkuDetails.isEmpty()) {
            PacoteCompra pacoteCompra = adapter.getLst().get(position);
            SkuDetails skuDetails = null;

            for (SkuDetails item : lstSkuDetails) {
                if (item.getSku().equals(pacoteCompra.getNome())) {
                    skuDetails = item;
                    produtoComprado = pacoteCompra.getNome();
                    break;
                }
            }

            assert skuDetails != null;
            BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                    .setSkuDetails(skuDetails)
                    .build();
            billingClient.launchBillingFlow(this, billingFlowParams);
        }
    }

    @Override
    public void onSucess(String response) {
        if (!this.isDestroyed()) {
            if (pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_PACOTE_COMPRA)) {
                pCancelLoading();
                pEnableScreen();
                pTrataRespostaListaPacoteCompra(response);
            }
        }
        super.onSucess(response);
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
        int resposta = billingResult.getResponseCode();
        if (resposta == BillingClient.BillingResponseCode.OK && list != null && !list.isEmpty()) {
            for (Purchase compra : list) {
                pTratarCompra(compra);
            }
        }
    }
}