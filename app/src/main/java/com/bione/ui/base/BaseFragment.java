package com.bione.ui.base;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bione.network.ApiError;


/**
 * Developer: Bione
 */
public abstract class BaseFragment extends Fragment implements BaseView, View.OnClickListener {

    private Context mContext;
    private AlertDialog mNotificationDialog;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * for getting string from id
     *
     * @param msgId string id
     * @return string message
     */

    public String getStrings(final int msgId) {

        String msg = "";
        if (msgId != 0) {
            msg = getString(msgId);
        }
        return msg;
    }

//    /**
//     * replace fragment from existing layout
//     *
//     * @param mFragment the  fragment instance
//     * @param mBundle   bundle to pass data
//     */
//    public void replaceFragment(final Fragment mFragment, final Bundle mBundle) {
//        if (mBundle != null) {
//            mFragment.setArguments(mBundle);
//        }
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.flFragment, mFragment, mFragment.getClass().getSimpleName());
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }

    @Override
    public boolean isNetworkConnected() {
        return ((BaseActivity) mContext).isNetworkConnected();
    }

    @Override
    public void showLoading() {
        ((BaseActivity) mContext).showLoading();
    }

    @Override
    public void showLoading(final String message) {
        ((BaseActivity) mContext).showLoading(message);
    }

    @Override
    public void hideLoading() {
        ((BaseActivity) mContext).hideLoading();
    }

    @Override
    public void showErrorMessage(final int stringId) {
        ((BaseActivity) mContext).showErrorMessage(stringId);
    }

    @Override
    public void showErrorMessage(final ApiError apiError) {
        ((BaseActivity) mContext).showErrorMessage(apiError);
    }

    @Override
    public void showErrorMessage(final ApiError apiError, final OnErrorHandleCallback mOnErrorHandleCallback) {
        ((BaseActivity) mContext).showErrorMessage(apiError, mOnErrorHandleCallback);
    }

//    @Override
//    public void showErrorMessageWithHeader(int message, int header) {
//        ((BaseActivity) mContext).showErrorMessageWithHeader(message, header);
//    }
//
//    @Override
//    public void showErrorMessageWithHeader(String message, String header) {
//        ((BaseActivity) mContext).showErrorMessageWithHeader(message, header);
//    }
//
//    @Override
//    public void showErrorMessage(final int stringId, final OnErrorHandleCallback mOnErrorHandleCallback) {
//        ((BaseActivity) mContext).showErrorMessage(stringId, mOnErrorHandleCallback);
//    }

    @Override
    public void showErrorMessage(final String message) {
        ((BaseActivity) mContext).showErrorMessage(message);
    }

    @Override
    public void showErrorMessage(final String message, final OnErrorHandleCallback mOnErrorHandleCallback) {
        ((BaseActivity) mContext).showErrorMessage(message, mOnErrorHandleCallback);
    }

//    /**
//     * showErrorMessage
//     *
//     * @param notifiMessage msg
//     * @param title         tit
//     */
//    public void showErrorMessage(final String notifiMessage, final String title) {
//        /*mNotificationDialog = new AlertDialog.Builder(BaseActivity.this)
//                .setMessage(notifiMessage)
//                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(final DialogInterface dialog, final int which) {
//                        // handle your direction
//                    }
//                }).show();*/
//
//        ((BaseActivity) mContext).showErrorMessageWithHeader(notifiMessage, title);
//
//    }


}
