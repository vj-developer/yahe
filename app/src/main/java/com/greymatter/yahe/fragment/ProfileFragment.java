package com.greymatter.yahe.fragment;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.greymatter.yahe.R;
import com.greymatter.yahe.helper.ApiConfig;
import com.greymatter.yahe.helper.Constant;
import com.greymatter.yahe.helper.Session;
import com.greymatter.yahe.helper.Utils;

public class ProfileFragment extends Fragment {

    public static final int SELECT_FILE = 110;
    public static final int REQUEST_IMAGE_CAPTURE = 100;
    public final int reqWritePermission = 2;
    public ImageView imgProfile;
    public FloatingActionButton fabProfile;
    public ProgressBar progressBar;
    View root;
    TextView tvChangePassword;
    Session session;
    Button btnSubmit;
    Activity activity;
    EditText edtName, edtEmail, edtMobile, edtOldPassword, edtNewPassword, edtConfirmNewPassword;
    String currentPhotoPath;
    Uri imageUri;
    String filePath = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_profile, container, false);
        activity = getActivity();

        edtName = root.findViewById(R.id.edtName);
        edtEmail = root.findViewById(R.id.edtEmail);
        edtMobile = root.findViewById(R.id.edtMobile);
        btnSubmit = root.findViewById(R.id.btnSubmit);
        tvChangePassword = root.findViewById(R.id.tvChangePassword);
        fabProfile = root.findViewById(R.id.fabProfile);
        progressBar = root.findViewById(R.id.progressBar);

        edtOldPassword = root.findViewById(R.id.edtOldPassword);
        edtNewPassword = root.findViewById(R.id.edtNewPassword);
        edtConfirmNewPassword = root.findViewById(R.id.edtConfirmNewPassword);

        setHasOptionsMenu(true);

        session = new Session(activity);

        imgProfile = root.findViewById(R.id.imgProfile);

        Picasso.get()
                .load(session.getData(Constant.PROFILE))
                .fit()
                .centerInside()
                .placeholder(R.drawable.ic_profile_placeholder)
                .error(R.drawable.ic_profile_placeholder)
                .into(imgProfile);

        fabProfile.setOnClickListener(view -> SelectProfileImage());

        tvChangePassword.setOnClickListener(v -> OpenBottomDialog(activity));

        btnSubmit.setOnClickListener(view -> {
            final String name = edtName.getText().toString();
            final String email = edtEmail.getText().toString();
            final String mobile = edtMobile.getText().toString();

            if (ApiConfig.CheckValidation(name, false, false)) {
                edtName.requestFocus();
                edtName.setError(getString(R.string.enter_name));
            } else if (ApiConfig.CheckValidation(email, false, false)) {
                edtEmail.requestFocus();
                edtEmail.setError(getString(R.string.enter_email));
            } else if (ApiConfig.CheckValidation(email, true, false)) {
                edtEmail.requestFocus();
                edtEmail.setError(getString(R.string.enter_valid_email));
            } else {
                Map<String, String> params = new HashMap<>();
                params.put(Constant.TYPE, Constant.EDIT_PROFILE);
                params.put(Constant.USER_ID, session.getData(Constant.ID));
                params.put(Constant.NAME, name);
                params.put(Constant.EMAIL, email);
                params.put(Constant.MOBILE, mobile);
                params.put(Constant.LONGITUDE, session.getCoordinates(Constant.LONGITUDE));
                params.put(Constant.LATITUDE, session.getCoordinates(Constant.LATITUDE));
                params.put(Constant.FCM_ID, session.getData(Constant.FCM_ID));
                //System.out.println("====update res " + params.toString());
                ApiConfig.RequestToVolley((result, response) -> {
                    //System.out.println ("=================* " + response);
                    if (result) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean(Constant.ERROR)) {
                                session.setData(Constant.NAME, name);
                                session.setData(Constant.EMAIL, email);
                                session.setData(Constant.MOBILE, mobile);

                            }
                            Toast.makeText(activity, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, activity, Constant.REGISTER_URL, params, true);
            }


        });

        edtName.setText(session.getData(Constant.NAME));
        edtEmail.setText(session.getData(Constant.EMAIL));
        edtMobile.setText(session.getData(Constant.MOBILE));

        return root;
    }

    public void OpenBottomDialog(final Activity activity) {
        try {
            View sheetView = activity.getLayoutInflater().inflate(R.layout.dialog_change_password, null);
            ViewGroup parentViewGroup = (ViewGroup) sheetView.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeAllViews();
            }

            final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(requireActivity(), R.style.BottomSheetTheme);
            mBottomSheetDialog.setContentView(sheetView);
            mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            EditText edtOldPassword = sheetView.findViewById(R.id.edtOldPassword);
            EditText edtNewPassword = sheetView.findViewById(R.id.edtNewPassword);
            EditText edtConfirmNewPassword = sheetView.findViewById(R.id.edtConfirmNewPassword);
            ImageView imgChangePasswordClose = sheetView.findViewById(R.id.imgChangePasswordClose);
            Button btnChangePassword = sheetView.findViewById(R.id.btnChangePassword);

            edtOldPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pass, 0, R.drawable.ic_show, 0);
            edtNewPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pass, 0, R.drawable.ic_show, 0);
            edtConfirmNewPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pass, 0, R.drawable.ic_show, 0);

            Utils.setHideShowPassword(edtOldPassword);
            Utils.setHideShowPassword(edtNewPassword);
            Utils.setHideShowPassword(edtConfirmNewPassword);
            mBottomSheetDialog.setCancelable(true);


            imgChangePasswordClose.setOnClickListener(v -> mBottomSheetDialog.dismiss());

            btnChangePassword.setOnClickListener(view -> {
                String oldpsw = edtOldPassword.getText().toString();
                String password = edtNewPassword.getText().toString();
                String cpassword = edtConfirmNewPassword.getText().toString();

                if (!password.equals(cpassword)) {
                    edtConfirmNewPassword.requestFocus();
                    edtConfirmNewPassword.setError(activity.getString(R.string.pass_not_match));
                } else if (ApiConfig.CheckValidation(oldpsw, false, false)) {
                    edtOldPassword.requestFocus();
                    edtOldPassword.setError(activity.getString(R.string.enter_old_pass));
                } else if (ApiConfig.CheckValidation(password, false, false)) {
                    edtNewPassword.requestFocus();
                    edtNewPassword.setError(activity.getString(R.string.enter_new_pass));
                } else if (!oldpsw.equals(new Session(activity).getData(Constant.PASSWORD))) {
                    edtOldPassword.requestFocus();
                    edtOldPassword.setError(activity.getString(R.string.no_match_old_pass));
                } else {
                    ChangePassword(password);
                }
            });

            mBottomSheetDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void ChangePassword(String password) {

        final Map<String, String> params = new HashMap<>();
        params.put(Constant.TYPE, Constant.CHANGE_PASSWORD);
        params.put(Constant.PASSWORD, password);
        params.put(Constant.USER_ID, session.getData(Constant.ID));

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        // Setting Dialog Message
        alertDialog.setTitle(getString(R.string.change_pass));
        alertDialog.setMessage(getString(R.string.reset_alert_msg));
        alertDialog.setCancelable(false);
        final AlertDialog alertDialog1 = alertDialog.create();

        // Setting OK Button
        alertDialog.setPositiveButton(getString(R.string.yes), (dialog, which) -> ApiConfig.RequestToVolley((result, response) -> {
            //  System.out.println("=================*change_password " + response);
            if (result) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (!object.getBoolean(Constant.ERROR)) {
                        session.logoutUser(activity);
                    }
                    Toast.makeText(activity, object.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, activity, Constant.REGISTER_URL, params, true));
        alertDialog.setNegativeButton(getString(R.string.no), (dialog, which) -> alertDialog1.dismiss());
        // Showing Alert Message
        alertDialog.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        Constant.TOOLBAR_TITLE = getString(R.string.profile);
        activity.invalidateOptionsMenu();
        hideKeyboard();
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(root.getApplicationWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.toolbar_logout).setVisible(true);
        menu.findItem(R.id.toolbar_search).setVisible(false);
        menu.findItem(R.id.toolbar_sort).setVisible(false);
        menu.findItem(R.id.toolbar_cart).setVisible(false);
        menu.findItem(R.id.toolbar_layout).setVisible(false);
    }

    public void SelectProfileImage() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, reqWritePermission);
        } else if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, reqWritePermission);
        } else {
            selectDialog();
        }
    }

    public void selectDialog() {
        final CharSequence[] items = {getString(R.string.from_library), getString(R.string.from_camera), getString(R.string.cancel)};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        builder.setTitle("Add Photo!");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals(getString(R.string.from_library))) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_FILE);
            } else if (items[item].equals(getString(R.string.from_camera))) {
                dispatchTakePictureIntent();
            } else if (items[item].equals(getString(R.string.cancel))) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "ECART_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                imageUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                imageUri = data.getData();
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setOutputCompressQuality(90)
                        .setRequestedSize(300, 300)
                        .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                        .setAspectRatio(1, 1)
                        .start(activity);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setOutputCompressQuality(90)
                        .setRequestedSize(300, 300)
                        .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                        .setAspectRatio(1, 1)
                        .start(activity);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                assert result != null;
                filePath = result.getUriFilePath(activity, true);
                UpdateProfile(activity);
            }
        }
    }

    public void UpdateProfile(Activity activity) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> fileParams = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.ID));
        fileParams.put(Constant.PROFILE, filePath);
        params.put(Constant.TYPE, Constant.UPLOAD_PROFILE);

        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean(Constant.ERROR)) {
                        session.setData(Constant.PROFILE, jsonObject.getString(Constant.PROFILE));
                        Picasso.get()
                                .load(session.getData(Constant.PROFILE))
                                .fit()
                                .centerInside()
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .into(imgProfile);

                        Picasso.get()
                                .load(session.getData(Constant.PROFILE))
                                .fit()
                                .centerInside()
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .into(DrawerFragment.imgProfile);
                    }
                    Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.REGISTER_URL, params, fileParams);
    }
}