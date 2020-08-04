package com.bione.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseFragment;
import com.bione.utils.ImageChooser;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends BaseFragment implements ImageChooser.OnImageSelectListener {

    private View rootView;
    private String text = "Hello";
    private AppCompatTextView tvHeading;
    private AppCompatImageView ivHead;
    private Context mContext;

    private ImageChooser imageChooser;
    private File photoFile;
    private Uri uriImage;
    private String imageLink;
    private CircleImageView cvProfilePic;
    private AppCompatImageView ivAddProfile;


    public ProfileFragment(String text) {
        this.text = text;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_profile, container, false);
            tvHeading = rootView.findViewById(R.id.tvHeading);
            ivHead = rootView.findViewById(R.id.ivHead);


            cvProfilePic = rootView.findViewById(R.id.cvProfilePic);
            ivAddProfile = rootView.findViewById(R.id.ivAddProfile);
            ivAddProfile.setOnClickListener(this);

        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAddProfile:
                imagePicker();

                break;
        }
    }

    /**
     * Image picker
     */
    private void imagePicker() {
        imageChooser = new ImageChooser.Builder(this).setCropEnabled(false).build();
        imageChooser.selectImage(new ImageChooser.OnImageSelectListener() {
            @Override
            public void loadImage(final List<ChosenImage> list) {
                photoFile = new File(list.get(0).getThumbnailPath());
                uriImage = Uri.parse(list.get(0).getQueryUri());
                if (uriImage != null) {
                    Glide.with(mContext)
                            .load(uriImage)
                            .apply(RequestOptions.circleCropTransform())
                            .into(cvProfilePic);
                }
            }

            @Override
            public void croppedImage(final File mCroppedImage) {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        imageChooser.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent data) {
        if (imageChooser != null) {
            imageChooser.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void loadImage(List<ChosenImage> list) {
        photoFile = new File(list.get(0).getThumbnailPath());

        Glide.with(mContext)
                .load(photoFile)
                .apply(RequestOptions.circleCropTransform())
                .into(cvProfilePic);
    }

    @Override
    public void croppedImage(File mCroppedImage) {

    }
}
