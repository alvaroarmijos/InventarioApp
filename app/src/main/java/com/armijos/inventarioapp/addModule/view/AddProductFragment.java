package com.armijos.inventarioapp.addModule.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.armijos.inventarioapp.R;
import com.armijos.inventarioapp.addModule.AddProductPresenterClass;
import com.armijos.inventarioapp.addModule.addProductPresenter;
import com.armijos.inventarioapp.common.pojo.Product;
import com.armijos.inventarioapp.common.utils.CommonUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends DialogFragment implements DialogInterface.OnShowListener,
        AddProductView {


    @BindView(R.id.etPhotoUrl)
    EditText etPhotoUrl;
    @BindView(R.id.imagePhoto)
    AppCompatImageView imagePhoto;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etQuantity)
    EditText etQuantity;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.contentMain)
    ConstraintLayout contentMain;

    Unbinder unbinder;

    private addProductPresenter mPresenter;

    public AddProductFragment(){
        mPresenter = new AddProductPresenterClass(this);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.addProduct_title)
                .setPositiveButton(R.string.addProduct_dialog_ok, null)
                .setNegativeButton(R.string.common_dialog_cancel, null);

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_product, null);
        unbinder = ButterKnife.bind(this, view);
        builder.setView(view);

        configFocus();
        configEditText();


        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);
        return dialog;

    }

    private void configEditText() {
        etPhotoUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                final String photoUrl = etPhotoUrl.getText().toString().trim();
                if (photoUrl.isEmpty()){
                    imagePhoto.setImageDrawable(null);
                } else {
                    if (getActivity() != null){
                        RequestOptions options = new RequestOptions().centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop();

                        Glide.with(getActivity())
                                .load(photoUrl)
                                .apply(options)
                                .into(imagePhoto);
                    }
                }
            }
        });
    }

    private void configFocus() {
        etName.requestFocus();
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        final AlertDialog dialog = (AlertDialog)getDialog();
        if (dialog != null){
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product product = new Product();
                    if (CommonUtils.validateProduct(getActivity(), etName, etQuantity, etPhotoUrl)) {
                        product.setName(etName.getText().toString().trim());
                        product.setQuantity(Integer.valueOf(etQuantity.getText().toString().trim()));
                        product.setPhotoUrl(etPhotoUrl.getText().toString().trim());
                        mPresenter.addProduct(product);
                    }
                }
            });

            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        mPresenter.onShow();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.onDestroy();
    }


    @Override
    public void enableUIElements() {
        etName.setEnabled(true);
        etQuantity.setEnabled(true);
        etPhotoUrl.setEnabled(true);

    }

    @Override
    public void disableUIElements() {
        etName.setEnabled(false);
        etQuantity.setEnabled(false);
        etPhotoUrl.setEnabled(false);

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void productAdded() {
        Toast.makeText(getActivity(), R.string.addProduct_message_added_successfully, Toast.LENGTH_SHORT)
                .show();
        dismiss();
    }

    @Override
    public void showError(int resMsg) {
        Snackbar.make(contentMain, resMsg, Snackbar.LENGTH_INDEFINITE).setAction(
                R.string.addProduct_snackbar_action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();

    }

    @Override
    public void maxValueError(int resMSG) {
        etQuantity.setError(getString(resMSG));
        etQuantity.requestFocus();
    }
}
