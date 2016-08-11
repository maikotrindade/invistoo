package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jumbomob.invistoo.R;

/**
 * @author maiko.trindade
 * @since 14/07/2016
 */
public class NewBalancedInvestmentFragment extends BaseFragment {

    private View mRootView;
    private EditText percent1, percent2, percent3, selic, prefixado, ipca, aporte, total,
            selicResult, prefixadoResult, ipcaResult;
    private Button balancear;
    private long totalTitulos;

    public static NewBalancedInvestmentFragment newInstance() {
        NewBalancedInvestmentFragment fragment = new NewBalancedInvestmentFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRootView = inflater.inflate(R.layout.fragment_new_balanced_investment, container, false);

        configureElements();
        return mRootView;
    }

    private void configureElements() {
        percent1 = (EditText) mRootView.findViewById(R.id.percent1);
        percent2 = (EditText) mRootView.findViewById(R.id.percent2);
        percent3 = (EditText) mRootView.findViewById(R.id.percent3);
        selic = (EditText) mRootView.findViewById(R.id.selic);
        prefixado = (EditText) mRootView.findViewById(R.id.prefixado);
        ipca = (EditText) mRootView.findViewById(R.id.ipca);
        selicResult = (EditText) mRootView.findViewById(R.id.selicResult);
        prefixadoResult = (EditText) mRootView.findViewById(R.id.prefixadoResult);
        ipcaResult = (EditText) mRootView.findViewById(R.id.ipcaResult);
        aporte = (EditText) mRootView.findViewById(R.id.aporte);
        total = (EditText) mRootView.findViewById(R.id.total);

        balancear = (Button) mRootView.findViewById(R.id.balancear);
        balancear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long percent1T = Long.valueOf(percent1.getText().toString());
                long percent2T = Long.valueOf(percent2.getText().toString());
                long percent3T = Long.valueOf(percent3.getText().toString());

                long selicA = Long.valueOf(selic.getText().toString());
                long prefixadoA = Long.valueOf(prefixado.getText().toString());
                long ipcaA = Long.valueOf(ipca.getText().toString());
                total.setText(String.valueOf(selicA + prefixadoA + ipcaA));

                long aporteA = Long.valueOf(aporte.getText().toString());

                long vat = selicA + prefixadoA + ipcaA + aporteA;

                double selicB = vat * ((double) percent1T / 100);
                double prefixadoB = vat * ((double) percent2T / 100);
                double ipcaB = vat * ((double) percent3T / 100);
                selicResult.setText(String.valueOf(selicB));
                prefixadoResult.setText(String.valueOf(prefixadoB));
                ipcaResult.setText(String.valueOf(ipcaB));

            }
        });
    }
}
