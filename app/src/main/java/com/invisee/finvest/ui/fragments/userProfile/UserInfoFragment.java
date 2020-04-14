package com.invisee.finvest.ui.fragments.userProfile;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.SecurityQuestion;
import com.invisee.finvest.data.api.requests.SaveUserInfoRequest;
import com.invisee.finvest.data.api.responses.UserInfoResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.ChangePasswordActivity;
import com.invisee.finvest.ui.activities.KycActivityNew;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import static android.app.Activity.RESULT_OK;

@RuntimePermissions
public class UserInfoFragment extends BaseFragment {

    private static final String TAG = "UserInfoFragment";
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 4;
    final int CROP_PIC = 2;

    @Bind(R.id.txvFirstName)
    TextView txvFirstName;
    @Bind(R.id.txvLastName)
    TextView txvLastName;
    @Bind(R.id.txvMobileNumber)
    TextView txvMobileNumber;
    @Bind(R.id.edtMobileNumberArea)
    EditText edtMobileNumberArea;
    @Bind(R.id.edtMobileNumberProfile)
    EditText edtMobileNumberProfile;
    @Bind(R.id.txvEmail)
    TextView txvEmail;
    @Bind(R.id.txvSecurityQuestion)
    TextView txvSecurityQuestion;
    @Bind(R.id.txvAnswer)
    TextView txvAnswer;
    @Bind(R.id.edtAnswer)
    EditText edtAnswer;
    @Bind(R.id.txvEdit)
    TextView txvEdit;
    @Bind(R.id.sprSecurityQuestion)
    Spinner sprSecurityQuestion;
    @Bind(R.id.ivUserPhoto)
    ImageView imvUserPhoto;
    @Bind(R.id.ivChangeProfileWarning)
    ImageView imvChangeProfileWarning;
    @Bind(R.id.lnProgressBar)
    LinearLayout lnProgressBar;
    @Bind(R.id.lnDismissBar)
    RelativeLayout lnDismissBar;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;
    @Bind(R.id.lnConnectionError)
    LinearLayout lnConnectionError;

    private UserInfoPresenter presenter;
    private int choosenTask = 0;

    UserInfoResponse userInfo;
    List<SecurityQuestion> securityQuestionList;
    SecurityQuestion securityQuestionValue;
    private Uri picUri;
    public File tempFile;

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, new UserInfoFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_user_info;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserInfoPresenter(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.userInfo();
        loadPicture();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPicture();
        dismissProgressDialog();
    }

    public void loadPicture() {
        try {
            if (PrefHelper.getString(PrefKey.IMAGE).equals(null) || PrefHelper.getString(PrefKey.IMAGE).trim() == "") {
                imvUserPhoto.setImageResource(R.drawable.photo);
            } else {
                Picasso.with(getContext()).load(InviseeService.IMAGE_DOWNLOAD_URL + PrefHelper.getString(PrefKey.IMAGE) + "&token=" + PrefHelper.getString(PrefKey.TOKEN)).into(imvUserPhoto);
            }
        } catch (Exception e) {
            imvUserPhoto.setImageResource(R.drawable.photo);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        UserInfoFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void startGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECT_FILE);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void startCamera() {
        tempFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        picUri = Uri.fromFile(tempFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, REQUEST_CAMERA);

    }

    void onCaptureImageResult() {
        // Setting option to resize image
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(tempFile.getPath(),options);
        final int REQUIRED_SIZE = 310;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;

        // Load file with option parameter, then compress it
        Bitmap b = BitmapFactory.decodeFile(tempFile.getPath(),options);

        Matrix matrix = new Matrix();

        try {
            ExifInterface exif = null;
            exif = new ExifInterface(tempFile.getPath());
            String orientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            if (orientation.equals(ExifInterface.ORIENTATION_NORMAL)) {
                // Do nothing. The original image is fine.
            } else if (orientation.equals(ExifInterface.ORIENTATION_ROTATE_90+"")) {
                matrix.postRotate(90);
            } else if (orientation.equals(ExifInterface.ORIENTATION_ROTATE_180+"")) {
                matrix.postRotate(180);
            } else if (orientation.equals(ExifInterface.ORIENTATION_ROTATE_270+"")) {
                matrix.postRotate(270);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Bitmap resizedBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 70, bytes);

        FileOutputStream fo;
        try {
            fo = new FileOutputStream(tempFile);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doUpload(tempFile);
            }
        }, 2000);
    }

    void doUpload(File targetFile){
        presenter.uploadPhoto(targetFile);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SELECT_FILE:
                if (resultCode == RESULT_OK) {

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    File file = new File(picturePath);
                    long length = file.length() / 1024; // Size in KB

                    if (length > 2000) {
                        showProgressDialog(loading);
                        showFailedDialog(getResources().getString(R.string.uploadlimit));
                    } else {
                        presenter.uploadPhoto(file);
                    }

                }
                break;

            case REQUEST_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    onCaptureImageResult();
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setView(boolean completeness) {
        txvFirstName.setText(userInfo.getData().getFirstName());
        txvLastName.setText(userInfo.getData().getLastName());
        txvMobileNumber.setText(userInfo.getData().getMobileNumber());

        try{
            String[] splitedPhoneNumber = userInfo.getData().getMobileNumber().split("-");
            edtMobileNumberArea.setText(splitedPhoneNumber[0]);
            edtMobileNumberProfile.setText(splitedPhoneNumber[1]);
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

        txvEmail.setText(userInfo.getData().getEmail());
        txvSecurityQuestion.setText(userInfo.getQuestion().getQuestionText());
        if (userInfo.getAnswer()==null || userInfo.getAnswer().getAnswerNote()==null) {
            txvAnswer.setText("");
        } else {
            txvAnswer.setText(userInfo.getAnswer().getAnswerNote());
        }

        if (userInfo.getAnswer()==null || userInfo.getAnswer().getAnswerNote()==null) {
            edtAnswer.setText("");
        } else {
            edtAnswer.setText(userInfo.getAnswer().getAnswerNote());
        }


        if (completeness) {
            imvChangeProfileWarning.setVisibility(View.GONE);
        } else {
            imvChangeProfileWarning.setVisibility(View.VISIBLE);
        }
    }


    public void setUpSpinnerQuestion(List<SecurityQuestion> securityQuestions, String selectionCountryId){
        if (securityQuestions == null) securityQuestions = new ArrayList<>();
        if (selectionCountryId != null) { // set selection to saved data from realm / ws
            int i = 0;
            for (SecurityQuestion question : securityQuestions) {
                if (question.getId() == Integer.valueOf(selectionCountryId)) {
                    sprSecurityQuestion.setSelection(i, false);
                    this.securityQuestionValue = question;
                    break;
                }
                i++;
            }
        }
        ArrayAdapter<SecurityQuestion> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, securityQuestions);
        sprSecurityQuestion.setAdapter(spinnerArrayAdapter);
        sprSecurityQuestion.setSelection(0, false); // set to default (Indonesia)

    }

    public void showEditView() {
        edtAnswer.setVisibility(View.VISIBLE);
        edtMobileNumberArea.setVisibility(View.VISIBLE);
        edtMobileNumberProfile.setVisibility(View.VISIBLE);
        sprSecurityQuestion.setVisibility(View.VISIBLE);
        txvAnswer.setVisibility(View.GONE);
        txvMobileNumber.setVisibility(View.GONE);
        txvSecurityQuestion.setVisibility(View.GONE);
        edtMobileNumberProfile.requestFocus();
        txvEdit.setText(R.string.save);
    }

    public void hideEditView() {
        ((BaseActivity) getActivity()).hideKeyboard();
        edtAnswer.setVisibility(View.GONE);
        edtMobileNumberArea.setVisibility(View.GONE);
        edtMobileNumberProfile.setVisibility(View.GONE);
        sprSecurityQuestion.setVisibility(View.GONE);
        txvAnswer.setVisibility(View.VISIBLE);
        txvMobileNumber.setVisibility(View.VISIBLE);
        txvSecurityQuestion.setVisibility(View.VISIBLE);
        txvAnswer.setText(edtAnswer.getText());
        txvMobileNumber.setText(edtMobileNumberArea.getText() + "-" + edtMobileNumberProfile.getText());
        if(sprSecurityQuestion.getSelectedItem() != null){
            txvSecurityQuestion.setText(sprSecurityQuestion.getSelectedItem().toString());
        }

        txvEdit.setText(R.string.edit);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("OUT >> " + bm);
        imvUserPhoto.setImageBitmap(bm);
    }

    public void loadImageProfile() {
        Log.d("img", PrefHelper.getString(PrefKey.IMAGE));
        Log.d("token", PrefHelper.getString(PrefKey.TOKEN));
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imvUserPhoto.setImageBitmap(thumbnail);
    }

    public void saveUser() {
        SaveUserInfoRequest request = new SaveUserInfoRequest();
        request.setAnswerNote(edtAnswer.getText().toString());
        request.setEmail(txvEmail.getText().toString());
        request.setFirstName(txvFirstName.getText().toString());
        request.setImageKey(PrefHelper.getString(PrefKey.IMAGE));
        request.setLastName(txvLastName.getText().toString());
        request.setMobileNumber(txvMobileNumber.getText().toString());
        request.setQuestion(Integer.toString(securityQuestionList.get(sprSecurityQuestion.getSelectedItemPosition()).getId()));

        presenter.saveUserInfo(request);
    }


    @OnClick(R.id.rl_change_profile)
    public void onKycClicked() {
        PrefHelper.setString(PrefKey.NO_HANDPHONE, txvMobileNumber.getText().toString());
        KycActivityNew.startActivity((BaseActivity) getActivity(), 0);
    }

    @OnClick(R.id.btnChangePassword)
    public void onChangePasswordClicked() {
        ChangePasswordActivity.startActivity((BaseActivity) getActivity());
    }

    @OnClick(R.id.btnSendPIN)
    public void onRiskProfileClicked() {
        presenter.sendPIN();
    }

    @OnClick(R.id.lnrEdit)
    public void onEditClicked() {

        //EDIT
        if (txvEdit.getText().toString().equalsIgnoreCase(getString(R.string.edit))) {
            showEditView();
        }
        //SAVE
        else {
            if (edtMobileNumberArea.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Mobile number area cannot empty", Toast.LENGTH_SHORT).show();
            } else if (edtMobileNumberProfile.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Mobile number cannot empty", Toast.LENGTH_SHORT).show();
            } else if (edtAnswer.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Answer cannot empty", Toast.LENGTH_SHORT).show();
            } else {
                hideEditView();
                saveUser();
            }
        }

    }

    @OnClick(R.id.btnChangePicture)
    void onChangePictureClicked() {
        new MaterialDialog.Builder(getActivity())
                .title("Change Picture")
                .items(R.array.change_picture)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == 0) {
                            choosenTask = REQUEST_CAMERA;
                            UserInfoFragmentPermissionsDispatcher.startCameraWithCheck(UserInfoFragment.this);
                        } else {
                            choosenTask = SELECT_FILE;
                            UserInfoFragmentPermissionsDispatcher.startGalleryWithCheck(UserInfoFragment.this);
                        }
                    }
                })
                .show();
    }

    public void showProgressBar(){
        pbLoading.setVisibility(View.VISIBLE);
        lnConnectionError.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.VISIBLE);
        lnDismissBar.setVisibility(View.GONE);
    }

    public void dismissProgressBar(){
        lnProgressBar.setVisibility(View.GONE);
        lnDismissBar.setVisibility(View.VISIBLE);
    }

    public void connectionError() {
        lnProgressBar.setVisibility(View.VISIBLE);
        lnDismissBar.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        lnConnectionError.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tvTryAgain)
    void retryConnection(){
        presenter.userInfo();
    }

}
