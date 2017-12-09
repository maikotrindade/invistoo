package com.jumbomob.invistoo.ui.callback;

/**
 * @author maiko.trindade<mt@ubby.io>
 * @since 8/20/17
 */

public interface BalanceAssetCallback {

    void onEditBalance(long assetId, final Double oldValue, final int position);
}
