package com.jumbomob.invistoo.business;

/**
 * @author maiko.trindade
 * @since 28/08/2016
 */
public class OperationsManager {

    private final static String TAG = OperationsManager.class.getSimpleName();

//    private double calculateIncomeTax(Investment investment) {
//        final AssetDAO assetDAO = AssetDAO.getInstance();
//        final AssetTypeEnum assetTypeEnum = AssetTypeEnum.getById(investment.getAssetType());
//        final Asset asset = assetDAO.findAssetLastById(assetTypeEnum.getId());
//        double amount = 0;
//        if (asset != null) {
//            amount = (getValidDouble(investment.getPrice()));// * investment.getQuantity());
//            final Date assetDueDate = DateUtil.stringToDate(asset.getDueDate(), DateUtil.SIMPLE_DATE_FORMAT);
//            final DateTime dueDate = new DateTime(assetDueDate);
//            final DateTime date = new DateTime(investment.getCreationDate());
//
//            final Duration timeDifference = new Duration(date, dueDate);
//            final long differenceInDays = timeDifference.getStandardDays();
//            if (differenceInDays < Tax.IncomeTax.INCOME_LESS_THAN_180.getDays()) {
//                amount *= (1 - Tax.IncomeTax.INCOME_LESS_THAN_180.getRate());
//            } else if (differenceInDays > Tax.IncomeTax.INCOME_LESS_THAN_180.getDays() && differenceInDays < Tax.IncomeTax.INCOME_LESS_THAN_360.getDays()) {
//                amount *= (1 - Tax.IncomeTax.INCOME_LESS_THAN_360.getRate());
//            } else if (differenceInDays > Tax.IncomeTax.INCOME_LESS_THAN_360.getDays() && differenceInDays < Tax.IncomeTax.INCOME_LESS_THAN_720.getDays()) {
//                amount *= (1 - Tax.IncomeTax.INCOME_LESS_THAN_720.getRate());
//            } else { // Tax.IncomeTax.INCOME_MORE_THAN_720
//                amount *= (1 - Tax.IncomeTax.INCOME_MORE_THAN_720.getRate());
//            }
//        }
//        Log.d(TAG, "calculateIncomeTax:" + amount + "\n\n");
//        return amount;
//    }

}
