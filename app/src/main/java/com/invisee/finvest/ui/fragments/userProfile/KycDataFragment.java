package com.invisee.finvest.ui.fragments.userProfile;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.Country;
import com.invisee.finvest.data.api.requests.KycDataRequest;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.util.Constant;
import com.invisee.finvest.util.DateUtil;
import com.invisee.finvest.util.LocationHelper;
import com.invisee.finvest.util.eventBus.RxBusObject;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.joda.time.DateTime;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static android.app.Activity.RESULT_OK;

/**
 * Created by fajarfatur on 2/5/16.
 */

@RuntimePermissions
public class KycDataFragment extends KycBaseFragment implements AdapterView.OnItemSelectedListener, LocationHelper.Callback {

    public static final String TAG = KycDataFragment.class.getSimpleName();

    @Bind(R.id.sSalutation)
    Spinner sSalutation;

    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etFirstName)
    EditText etFirstName;

    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etLastName)
    EditText etLastName;

    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etDay)
    EditText etDay;

    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etMonth)
    EditText etMonth;

    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etYear)
    EditText etYear;

    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etBirthplace)
    EditText etBirthplace;


    @Bind(R.id.sGender)
    Spinner sGender;
    @Bind(R.id.sNationality)
    Spinner sNationality;
    @Bind(R.id.sMaritalStatus)
    Spinner sMaritalStatus;

    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etMotherMaidenName)
    EditText etMotherMaidenName;

    @Bind(R.id.sEducationBackground)
    Spinner sEducationBackground;
    @Bind(R.id.sReligion)
    Spinner sReligion;
    @Bind(R.id.sPreferedMailingAddress)
    Spinner sPreferedMailingAddress;
    @Bind(R.id.sOccupation)
    Spinner sOccupation;
    @Bind(R.id.sNatureOfBusiness)
    Spinner sNatureOfBusiness;

    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etIdNumber)
    EditText etIdNumber;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etIdDay)
    EditText etIdDay;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etIdMonth)
    EditText etIdMonth;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etIdYear)
    EditText etIdYear;

    @Bind(R.id.btIdPhoto)
    Button btIdPhoto;

    @Bind(R.id.doc1)
    TextView doc1;
    @Bind(R.id.doc3)
    TextView doc3;
    @Bind(R.id.checkboxId)
    CheckBox cbId;
    @Bind(R.id.imvIdPhoto)
    ImageView imvIdPhoto;
    @Bind(R.id.imvSignaturePhoto)
    ImageView imvSignaturePhoto;

    //new
    @Bind(R.id.imvSelfiePhoto)
    ImageView imvSelfiePhoto;
    @Bind(R.id.doc5)
    TextView doc5;
    public String ImageKeyDoc5;

    private final int SELECT_SELFIE_PHOTO_ID = 4;
    private final int REQUEST_SELFIE_CAMERA_ID = 5;
    private String selfiePhoto;

    private LocationHelper locationHelper;
    private Country country;
    private String dateBirthdate;
    private String dateId;
    private String dateBirthdateTemp;
    private String dateIdTemp;
    private boolean check = false;
    private boolean emptyString = false;

    private boolean emptySGender = true;
    private boolean emptySMaritalStatus = true;
    private boolean emptySEducationBackground = true;
    private boolean emptySReligion = true;
    private boolean emptySOccupation = true;
    private boolean emptySNaturOfBusiness = true;
    private boolean emptyPictureDoc1= true;
    private boolean emptyPictureDoc2= true;

    private final int SELECT_PHOTO_ID = 0;
    private final int SELECT_PHOTO_SIGNATURE = 1;
    private final int REQUEST_CAMERA_ID = 2;
    private final int REQUEST_CAMERA_SIGNATURE = 3;
    final int CROP_PIC = 2;
    private int choosenTask = 0;

    public String ImageKeyDoc1;
    public String ImageKeyDoc2;


    private KycDataPresenter presenter;
    private boolean status;
    private File tempFile;
    private Uri picUri;
    public int day, month, year;
    public int dayId, monthId, yearId;

    private final static String BASE_URL_DEV = "http://52.76.229.157:8080/avantrade-portal-core/";
    private String idNationality;


    @Override
    protected int getLayout() {
        return R.layout.f_kyc_data;
    }

    public KycDataFragment() {
    }

    public static KycDataFragment initiateFragment(KycDataRequest kycDataRequest) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KYC_DATA_REQUEST, kycDataRequest);
        KycDataFragment fragment = new KycDataFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new KycDataPresenter(this);
        Log.d(TAG, "onCreate: " + "masuk");

        if (BASE_URL_DEV.equals(InviseeService.BASE_URL)) {
            idNationality = "52";
        } else {
            idNationality = "653";
        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        status = false;
        emptySGender = true;
        emptySMaritalStatus = true;
        emptySEducationBackground = true;
        textChangedValidation();
    }

    @Override
    public void onResume() {
        super.onResume();
        defaultValue();

        setSalutation();
        etFirstName.setText(request.getFirstName());

        if (request.getMiddleName() != null) {
            request.setMiddleName(request.getMiddleName());
        } else {
            request.setMiddleName(null);
        }

        etLastName.setText(request.getLastName());
        etBirthplace.setText(request.getBirthPlace());
        setupSpinnerWithSpecificLookupSelection(sGender, getKycLookupFromRealm(Constant.KYC_CAT_GENDER), request.getGender());
        setupSpinnerWithSpecificLookupSelection(sMaritalStatus, getKycLookupFromRealm(Constant.KYC_CAT_MARITAL_STATUS), request.getMaritalStatus());
        etMotherMaidenName.setText(request.getMotherMaidenName());
        setupSpinnerWithSpecificLookupSelection(sEducationBackground, getKycLookupFromRealm(Constant.KYC_CAT_EDUCATION_BACKGROUND), request.getEducationBackground());
        setupSpinnerWithSpecificLookupSelection(sReligion, getKycLookupFromRealm(Constant.KYC_CAT_RELIGION), request.getReligion());
        setupSpinnerWithSpecificLookupSelection(sPreferedMailingAddress, getKycLookupFromRealm(Constant.KYC_CAT_STATEMENT_TYPE), request.getPreferredMailingAddress());
        setupSpinnerWithSpecificLookupSelection(sOccupation, getKycLookupFromRealm(Constant.KYC_CAT_OCCUPATION), request.getOccupation());
        setupSpinnerWithSpecificLookupSelection(sNatureOfBusiness, getKycLookupFromRealm(Constant.KYC_CAT_NATURE_OF_BUSINESS), request.getNatureOfBusiness());
        etIdNumber.setText(request.getIdNumber());
        setupBirthDate();
        setUpIdNumber();

        locationHelper = new LocationHelper(getApi(), this);
        locationHelper.loadCountriesStatesCities();

        presenter.loadImageDoc1();


    }


    private void setSalutation() {
        if (request.getSalutation() != null) {
            int spinnerPosition = ((ArrayAdapter<CharSequence>) sSalutation.getAdapter()).getPosition(request.getSalutation());
            sSalutation.setSelection(spinnerPosition);
        }
    }

    private void defaultValue() {
        if (request.getCitizenship() == null) {
            request.setCitizenship("DOM"); //WNI
        }
        if (request.getNationality() == null) {
            request.setNationality(idNationality); //INDONESIA
        }
        if (request.getPreferredMailingAddress() == null) {
            request.setPreferredMailingAddress("2"); //E-Statement
        }
        if (request.getIdType() == null) {
            request.setIdType("IDC"); //KTP
        }

    }


    private void setupBirthDate() {
        if (request.getBirthDate() != null) {
            Date date;
            if (request.getBirthDate().length() == 8) { // if date on realm saved as DDMMYYYY format
                date = DateUtil.format(request.getBirthDate(), DateUtil.MMDDYYYY);
            } else {
                date = DateUtil.format(request.getBirthDate(), "yyyy-MM-dd'T'HH:mm:ssXXX");
//                date = DateUtil.format(request.getBirthDate(), DateUtil.INVISEE_RETURN_FORMAT2);
            }
            DateTime dateTime = new DateTime(date);
            etDay.setText(String.valueOf(dateTime.getDayOfMonth()));
            etMonth.setText(String.valueOf(dateTime.getMonthOfYear()));
            etYear.setText(String.valueOf(dateTime.getYear()));
        }
    }

    private void setUpIdNumber() {
        if (request.getIdExpirationDate() != null) {
            Date date;
            if (request.getIdExpirationDate().length() == 8) { // if date on realm saved as DDMMYYYY format
                date = DateUtil.format(request.getIdExpirationDate(), DateUtil.MMDDYYYY);
            } else {
                date = DateUtil.format(request.getIdExpirationDate(), "yyyy-MM-dd'T'HH:mm:ssXXX");
            }
            DateTime dateTime = new DateTime(date);
            etIdDay.setText(String.valueOf(dateTime.getDayOfMonth()));
            etIdMonth.setText(String.valueOf(dateTime.getMonthOfYear()));
            etIdYear.setText(String.valueOf(dateTime.getYear()));
        }
    }


    private void generateRequest() {
        request.setSalutation((String) sSalutation.getSelectedItem());
        request.setFirstName(getAndTrimValueFromEditText(etFirstName));
        request.setMiddleName(request.getMiddleName());
        request.setLastName(getAndTrimValueFromEditText(etLastName));
        request.setEmail(PrefHelper.getString(PrefKey.EMAIL));
        request.setBirthPlace(getAndTrimValueFromEditText(etBirthplace));

        request.setGender(getKycLookupCodeFromSelectedItemSpinner(sGender));

        if (country == null) country = locationHelper.getSelectedCountry();
        request.setHomeCountry(country != null ? String.valueOf(country.getId()) : null);

        request.setMaritalStatus(getKycLookupCodeFromSelectedItemSpinner(sMaritalStatus));
        request.setMotherMaidenName(getAndTrimValueFromEditText(etMotherMaidenName));
        request.setEducationBackground(getKycLookupCodeFromSelectedItemSpinner(sEducationBackground));
        request.setReligion(getKycLookupCodeFromSelectedItemSpinner(sReligion));
        request.setPreferredMailingAddress(getKycLookupCodeFromSelectedItemSpinner(sPreferedMailingAddress));
        request.setOccupation(getKycLookupCodeFromSelectedItemSpinner(sOccupation));
        request.setNatureOfBusiness(getKycLookupCodeFromSelectedItemSpinner(sNatureOfBusiness));
        request.setIdNumber(getAndTrimValueFromEditText(etIdNumber));

//        int dayId = Integer.valueOf(getAndTrimValueFromEditText(etIdDay));
//        int monthId =  Integer.valueOf(getAndTrimValueFromEditText(etIdMonth));
//        int yearId = Integer.valueOf(getAndTrimValueFromEditText(etIdYear));
//        dateIdTemp = setFormatDate(yearId, monthId, dayId);
//        request.setIdExpirationDate(dateIdTemp);

//        setBirthDateandExpiration();

        //----Try to merge settlement with other data--//
        request.setToken(PrefHelper.getString(PrefKey.TOKEN));

        System.out.println("OUT >> 1 " + request.getBirthPlace());
    }

    private String setFormatDate(int year, int monthOfYear, int dayOfMonth) {
        return DateUtil.format(getDateTime(year, monthOfYear, dayOfMonth).toDate(), DateUtil.MMDDYYYY);
    }

    @NonNull
    private DateTime getDateTime(int year, int monthOfYear, int dayOfMonth) {
        return new DateTime(year, monthOfYear, dayOfMonth, 0, 0, 0, 0);
    }


    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        if (!status) {
            if(PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("ACT")) {
                setBirthDateandExpiration();
                validasiBirthDate();
                emptyStringValidation();
                if(check == true ){
                    if (emptyString == true) {
                        generateRequest();
                        getBus().send(new RxBusObject(RxBusObject.RxBusKey.SUBMIT_KYC_DATA, request));
                    } else{
                        if (emptySGender == false) {
                            ((TextView)sGender.getSelectedView()).setError("Mohon isi kolom ini");
                        } else if (emptySMaritalStatus == false) {
                            ((TextView)sMaritalStatus.getSelectedView()).setError("Mohon isi kolom ini");
                        } else if(emptySEducationBackground == false) {
                            ((TextView)sEducationBackground.getSelectedView()).setError("Mohon isi kolom ini");
                        } else if (emptySReligion == false) {
                            ((TextView)sReligion.getSelectedView()).setError("Mohon isi kolom ini");
                        } else if (emptySOccupation == false) {
                            ((TextView)sOccupation.getSelectedView()).setError("Mohon isi kolom ini");
                        } else if (emptySNaturOfBusiness == false) {
                            ((TextView)sNatureOfBusiness.getSelectedView()).setError("Mohon isi kolom ini");
                        } else if (emptyPictureDoc1 == false) {
                            Toast.makeText(getContext(), "Mohon upload foto KTP anda", Toast.LENGTH_SHORT).show();
                        } else if (emptyPictureDoc2 == false) {
                            Toast.makeText(getContext(), "Mohon upload foto tanda tangan anda", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    etYear.setError("Usia minimal 17 tahun");
                    Toast.makeText(getContext(), "Kamu harus berusia setidaknya 17 tahun untuk berinvestasi di Finvest", Toast.LENGTH_SHORT).show();
                }
            } else {
                setBirthDateandExpiration();
                getBus().send(new RxBusObject(RxBusObject.RxBusKey.NEXT_FORM, null));
            }
        } else {
            if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("VER") || PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("PEN")) {
                showDialogToSaveData();
            } else {
                setBirthDateandExpiration();
                validasiBirthDate();
                emptyStringValidation();
                if(check == true ){
                    if (emptyString == true) {
                        generateRequest();
                        getBus().send(new RxBusObject(RxBusObject.RxBusKey.SUBMIT_KYC_DATA, request));
                    } else{
                        if (emptySGender == false) {
                            ((TextView)sGender.getSelectedView()).setError("Mohon isi kolom ini");
                        } else if (emptySMaritalStatus == false) {
                            ((TextView)sMaritalStatus.getSelectedView()).setError("Mohon isi kolom ini");
                        } else if(emptySEducationBackground == false) {
                            ((TextView)sEducationBackground.getSelectedView()).setError("Mohon isi kolom ini");
                        } else if (emptySReligion == false) {
                            ((TextView)sReligion.getSelectedView()).setError("Mohon isi kolom ini");
                        } else if (emptySOccupation == false) {
                            ((TextView)sOccupation.getSelectedView()).setError("Mohon isi kolom ini");
                        } else if (emptySNaturOfBusiness == false) {
                            ((TextView)sNatureOfBusiness.getSelectedView()).setError("Mohon isi kolom ini");
                        } else if (emptyPictureDoc1 == false) {
                            Toast.makeText(getContext(), "Mohon upload foto KTP anda", Toast.LENGTH_SHORT).show();
                        } else if (emptyPictureDoc2 == false) {
                            Toast.makeText(getContext(), "Mohon upload foto tanda tangan anda", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    etYear.setError("Usia minimal 17 tahun");
                    Toast.makeText(getContext(), "Kamu harus berusia setidaknya 17 tahun untuk berinvestasi di Finvest", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.sNationality:
                String countryName = request.getNationality();
                country = (Country) sNationality.getSelectedItem();
                locationHelper.loadStatesCitiesBySelectedCountry(String.valueOf(country.getId()));
                break;
        }
    }

    @Override
    public void onRetrieveLocationDataComplete(LocationHelper.LoadType loadType) {
        dismissProgressDialog();
        switch (loadType) {
            case COUNTRIES_STATES_CITIES:
                setupSpinnerCountry(locationHelper.getCountries(), request.getHomeCountry());
                break;
        }
    }

    public void setupSpinnerCountry(List<Country> countries, String selectionCountryId) {
        if (countries == null) countries = new ArrayList<>();
        ArrayAdapter<Country> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, countries);
        sNationality.setAdapter(spinnerArrayAdapter);
        sNationality.setOnItemSelectedListener(this);
        sNationality.setSelection(locationHelper.getDefaultIndexCountry(), false); // set to default (Indonesia)
        if (selectionCountryId != null) { // set selection to saved data from realm / ws
            int i = 0;
            for (Country country : countries) {
                if (country.getId() == Integer.valueOf(selectionCountryId)) {
                    sNationality.setSelection(i, false);
                    this.country = country;
                    break;
                }
                i++;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void startGalleryId() {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECT_PHOTO_ID);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void startCameraId() {
        tempFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        picUri = Uri.fromFile(tempFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, REQUEST_CAMERA_ID);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void startGallerySignature(){
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECT_PHOTO_SIGNATURE);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void startCameraSignature(){
        tempFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        picUri = Uri.fromFile(tempFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, REQUEST_CAMERA_SIGNATURE);
    }


    //new
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void startGallerySelfie() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECT_SELFIE_PHOTO_ID);
    }


    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void startCameraSelfie(){
        tempFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        picUri = Uri.fromFile(tempFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, REQUEST_SELFIE_CAMERA_ID);
    }


    void onCaptureImageResultId() {
        // Setting option to resize image
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(tempFile.getPath(), options);
        final int REQUIRED_SIZE = 310;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;

        // Load file with option parameter, then compress it
        Bitmap b = BitmapFactory.decodeFile(tempFile.getPath(), options);

        Matrix matrix = new Matrix();

        try {
            ExifInterface exif = null;
            exif = new ExifInterface(tempFile.getPath());
            String orientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            if (orientation.equals(ExifInterface.ORIENTATION_NORMAL)) {
                // Do nothing. The original image is fine.
            } else if (orientation.equals(ExifInterface.ORIENTATION_ROTATE_90 + "")) {
                matrix.postRotate(90);
            } else if (orientation.equals(ExifInterface.ORIENTATION_ROTATE_180 + "")) {
                matrix.postRotate(180);
            } else if (orientation.equals(ExifInterface.ORIENTATION_ROTATE_270 + "")) {
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
                doUploadId(tempFile);
            }
        }, 2000);
    }

    void doUploadId(File targetFile) {
        if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("VER")) {
            presenter.uploadVerDoc1(presenter.constructUpload(), targetFile);
        } else {
            presenter.uploadDoc1(presenter.constructUpload(), targetFile);
        }

    }

    void onCaptureImageResultISignature() {
        // Setting option to resize image
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(tempFile.getPath(), options);
        final int REQUIRED_SIZE = 310;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;

        // Load file with option parameter, then compress it
        Bitmap b = BitmapFactory.decodeFile(tempFile.getPath(), options);

        Matrix matrix = new Matrix();

        try {
            ExifInterface exif = null;
            exif = new ExifInterface(tempFile.getPath());
            String orientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            if (orientation.equals(ExifInterface.ORIENTATION_NORMAL)) {
                // Do nothing. The original image is fine.
            } else if (orientation.equals(ExifInterface.ORIENTATION_ROTATE_90 + "")) {
                matrix.postRotate(90);
            } else if (orientation.equals(ExifInterface.ORIENTATION_ROTATE_180 + "")) {
                matrix.postRotate(180);
            } else if (orientation.equals(ExifInterface.ORIENTATION_ROTATE_270 + "")) {
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
                doUploadSignature(tempFile);
            }
        }, 2000);
    }

    void doUploadSignature(File targetFile) {
       /* if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("VER")) {
            presenter.uploadVerDoc3(presenter.constructUploadSignature(), targetFile);
        } else {
            presenter.uploadDoc3(presenter.constructUploadSignature(), targetFile);
        }*/

        presenter.uploadDoc3(presenter.constructUploadSignature(), targetFile);

    }


    @OnClick({R.id.etDay, R.id.etMonth, R.id.etYear})
    void birthDate() {
        DialogBirthdate();
    }

    @OnClick({R.id.etIdDay, R.id.etIdMonth, R.id.etIdYear})
    void IdDate() {
        DialogId();
    }

    @OnClick(R.id.checkboxId)
    public void CheckUsingPhoneNumber() {
        //KTP seumur hidup
        if (cbId.isChecked()) {
            etIdDay.setText(String.valueOf(1));
            etIdMonth.setText(String.valueOf(0 + 1));
            etIdYear.setText(String.valueOf(2100));

            Calendar calendar = Calendar.getInstance();
            calendar.set(2100, 0, 1);

            SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
            dateId = format.format(calendar.getTime());
        }
    }

    @OnClick(R.id.btIdPhoto)
    void onChangePictureIdClicked() {
        generateRequest();
//        startGallery();
        new MaterialDialog.Builder(getActivity())
                .title("Change Picture")
                .items(R.array.change_picture)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == 0) {
                            choosenTask = REQUEST_CAMERA_ID;
                            KycDataFragmentPermissionsDispatcher.startCameraIdWithCheck(KycDataFragment.this);
                        } else {
                            choosenTask = SELECT_PHOTO_ID;
                            KycDataFragmentPermissionsDispatcher.startGalleryIdWithCheck(KycDataFragment.this);
                        }
                    }
                })
                .show();
    }


    @OnClick(R.id.btSignaturePhoto)
    void onChangePictureSigantureClicked() {
        generateRequest();
        new MaterialDialog.Builder(getActivity())
                .title("Change Picture")
                .items(R.array.change_picture)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == 0) {
                            choosenTask = REQUEST_CAMERA_SIGNATURE;
                            KycDataFragmentPermissionsDispatcher.startCameraSignatureWithCheck(KycDataFragment.this);
                        } else {
                            choosenTask = SELECT_PHOTO_SIGNATURE;
                            KycDataFragmentPermissionsDispatcher.startGallerySignatureWithCheck(KycDataFragment.this);
                        }
                    }
                })
                .show();
    }



    @OnClick(R.id.btSelfiePhoto)
    void onChangePictureSelfieClicked(){
        new MaterialDialog.Builder(getActivity())
                .title("Change Picture")
                .items(R.array.change_picture)
                .itemsCallback(new MaterialDialog.ListCallback(){
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text){
                        if(which == 0){
                            choosenTask = REQUEST_SELFIE_CAMERA_ID;
                            KycDataFragmentPermissionsDispatcher.startCameraSelfieWithCheck(KycDataFragment.this);
                        }else{
                            choosenTask = SELECT_SELFIE_PHOTO_ID;
                            KycDataFragmentPermissionsDispatcher.startGallerySelfieWithCheck(KycDataFragment.this);
                        }
                    }
                })
                .show();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        KycDataFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case SELECT_PHOTO_ID:
                if (resultCode == RESULT_OK) {

                    Uri selectedImage = imageReturnedIntent.getData();
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
                        if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equalsIgnoreCase("VER")){
                            presenter.uploadVerDoc1(presenter.constructUpload(), file);
                        } else {
                            presenter.uploadDoc1(presenter.constructUpload(), file);
                        }
                    }
                }
                break;
            case SELECT_PHOTO_SIGNATURE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
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
                        showUploadSuccessDialog(getResources().getString(R.string.uploadlimit));
                    } else {
                        if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equalsIgnoreCase("VER")) {
                            presenter.uploadVerDoc3(presenter.constructUploadSignature(), file);
                        } else {
                            presenter.uploadDoc3(presenter.constructUploadSignature(), file);
                        }

                    }
                }
                break;
            case REQUEST_CAMERA_ID:
                if (resultCode == Activity.RESULT_OK) {
                    onCaptureImageResultId();
                }
                break;
            case REQUEST_CAMERA_SIGNATURE:
                if (resultCode == Activity.RESULT_OK) {
                    onCaptureImageResultISignature();
                }
                break;
            case  SELECT_SELFIE_PHOTO_ID:
                if(resultCode == RESULT_OK){

                    Uri selectedImage = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    File file = new File(picturePath);
                    long length = file.length() / 1024; // Size in KB

                    if(length > 2000){
                        showProgressDialog(loading);
                        showFailedDialog(getResources().getString(R.string.uploadlimit));
                    } else {
                        presenter.uploadDoc5(presenter.constructUploadSelfie(), file);
                    }
                }

                break;
            case REQUEST_SELFIE_CAMERA_ID:
                if (resultCode == Activity.RESULT_OK) {
                    System.out.println("=============> i'm here");
                    onCaptureImageResultSelfieId();
                }
                break;


        }
    }


    public void DialogBirthdate(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.date_picker, null, false);

        if(request.getBirthDate() != null){
            final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.datepicker);
            myDatePicker.updateDate(Integer.parseInt(etYear.getText().toString()), Integer.parseInt(etMonth.getText().toString()) - 1, Integer.parseInt(etDay.getText().toString()));

            new AlertDialog.Builder(getContext()).setView(view)
                    .setTitle(R.string.date_birth)
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }
                    )
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    int day   = myDatePicker.getDayOfMonth();
                                    int month = myDatePicker.getMonth();
                                    int year  = myDatePicker.getYear();

                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(year, month, day);

                                    SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
                                    dateBirthdate = format.format(calendar.getTime());

                                    etDay.setText(String.valueOf(day));
                                    etMonth.setText(String.valueOf(month + 1));
                                    etYear.setText(String.valueOf(year));
                                    dialog.cancel();
                                }
                            }
                    ).show();
        } else {
            final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.datepicker);

            new AlertDialog.Builder(getContext()).setView(view)
                    .setTitle(R.string.date_birth)
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }
                    )
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    int day = myDatePicker.getDayOfMonth();
                                    int month = myDatePicker.getMonth();
                                    int year = myDatePicker.getYear();

                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(year, month, day);

                                    SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
                                    dateBirthdate = format.format(calendar.getTime());

                                    etDay.setText(String.valueOf(day));
                                    etMonth.setText(String.valueOf(month + 1));
                                    etYear.setText(String.valueOf(year));
                                    dialog.cancel();
                                }
                            }
                    ).show();
        }
    }

    public void DialogId() {
        if (request.getIdExpirationDate() != null) {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.date_picker, null, false);

            final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.datepicker);
            myDatePicker.updateDate(Integer.parseInt(etIdYear.getText().toString()), Integer.parseInt(etIdMonth.getText().toString()) - 1, Integer.parseInt(etIdDay.getText().toString()));

            new AlertDialog.Builder(getContext()).setView(view)
                    .setTitle(R.string.date_id)

                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }
                    )
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {

                                    int day   = myDatePicker.getDayOfMonth();
                                    int month = myDatePicker.getMonth();
                                    int year  = myDatePicker.getYear();

                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(year, month, day);

                                    /*new*/
                                    Calendar calCurr = Calendar.getInstance();
                                    int currYear, currMonth, currDay;
                                    currYear  = calCurr.get(Calendar.YEAR);
                                    currMonth = calCurr.get(Calendar.MONTH);
                                    currDay   = calCurr.get(Calendar.DAY_OF_MONTH);

                                    int flak = 0;
                                    if(year >= currYear){
                                        if(year == currYear){
                                            if(((month + 1) >= (currMonth + 1)) && (day >= currDay)){
                                                flak = 1;
                                            }
                                        }else{
                                            flak = 1;
                                        }
                                    }

                                    SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
                                    if(flak == 1){
                                        dateId = format.format(calendar.getTime());
                                        etIdDay.setText(String.valueOf(day));
                                        etIdMonth.setText(String.valueOf(month + 1));
                                        etIdYear.setText(String.valueOf(year));
                                    }else{
                                        dateId = format.format(calCurr.getTime());
                                        etIdDay.setText(String.valueOf(currDay));
                                        etIdMonth.setText(String.valueOf(currMonth + 1));
                                        etIdYear.setText(String.valueOf(currYear));
                                        Toast.makeText((BaseActivity) getActivity(), "Tanggal expired KTP harus lebih besar dari tanggal sekarang!", Toast.LENGTH_SHORT).show();
                                    }
                                    /*end*/



                                    //old
                                    /*SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
                                    dateId = format.format(calendar.getTime());
                                    etIdDay.setText(String.valueOf(day));
                                    etIdMonth.setText(String.valueOf(month + 1));
                                    etIdYear.setText(String.valueOf(year));
                                    */
                                    dialog.cancel();
                                }
                            }
                    ).show();
        } else {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.date_picker, null, false);

            final DatePicker myDatePicker = (DatePicker) view
                    .findViewById(R.id.datepicker);

            new AlertDialog.Builder(getContext()).setView(view)
                    .setTitle(R.string.date_id)

                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }
                    )
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {

                                    int day = myDatePicker.getDayOfMonth();
                                    int month = myDatePicker.getMonth();
                                    int year = myDatePicker.getYear();

                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(year, month, day);


                                    /*new*/
                                    Calendar calCurr = Calendar.getInstance();
                                    int currYear, currMonth, currDay;
                                    currYear  = calCurr.get(Calendar.YEAR);
                                    currMonth = calCurr.get(Calendar.MONTH);
                                    currDay   = calCurr.get(Calendar.DAY_OF_MONTH);

                                    int flak = 0;
                                    if(year >= currYear){
                                        if(year == currYear){
                                            if(((month + 1) >= (currMonth + 1)) && (day >= currDay)){
                                                flak = 1;
                                            }
                                        }else{
                                            flak = 1;
                                        }
                                    }

                                    SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
                                    if(flak == 1){
                                        dateId = format.format(calendar.getTime());
                                        etIdDay.setText(String.valueOf(day));
                                        etIdMonth.setText(String.valueOf(month + 1));
                                        etIdYear.setText(String.valueOf(year));
                                    }else{
                                        dateId = format.format(calCurr.getTime());
                                        etIdDay.setText(String.valueOf(currDay));
                                        etIdMonth.setText(String.valueOf(currMonth + 1));
                                        etIdYear.setText(String.valueOf(currYear));
                                        Toast.makeText((BaseActivity) getActivity(), "Tanggal expired KTP harus lebih besar dari tanggal sekarang!", Toast.LENGTH_SHORT).show();
                                    }
                                    /*end*/

                                    //old
                                    /*SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
                                    dateId = format.format(calendar.getTime());
                                    etIdDay.setText(String.valueOf(day));
                                    etIdMonth.setText(String.valueOf(month + 1));
                                    etIdYear.setText(String.valueOf(year));*/
                                    dialog.cancel();
                                }
                            }
                    ).show();
        }

    }


    void textChangedValidation() {
        etFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                status = true;
            }
        });

        etLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                status = true;
            }
        });

        etBirthplace.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                status = true;
            }
        });

        etMotherMaidenName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                status = true;
            }
        });

        etIdNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                status = true;
            }
        });

        etDay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                status = true;
                return false;
            }
        });

        etMonth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                status = true;
                return false;
            }
        });

        etYear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                status = true;
                return false;
            }
        });

        sGender.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                status = true;
                return false;
            }
        });

        sMaritalStatus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                status = true;
                return false;
            }
        });

        etMotherMaidenName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                status = true;
            }
        });

        sEducationBackground.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                status = true;
                return false;
            }
        });

        sReligion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                status = true;
                return false;
            }
        });

        sNatureOfBusiness.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                status = true;
                return false;
            }
        });

        etIdNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                status = true;
            }
        });

        etIdDay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                status = true;
                return false;
            }
        });

        etIdMonth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                status = true;
                return false;
            }
        });

        etIdYear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                status = true;
                return false;
            }
        });
    }

    void setBirthDateandExpiration() {
        day = Integer.valueOf(getAndTrimValueFromEditText(etDay));
        month = Integer.valueOf(getAndTrimValueFromEditText(etMonth));
        year = Integer.valueOf(getAndTrimValueFromEditText(etYear));
        dayId = Integer.valueOf(getAndTrimValueFromEditText(etIdDay));
        monthId = Integer.valueOf(getAndTrimValueFromEditText(etIdMonth));
        yearId = Integer.valueOf(getAndTrimValueFromEditText(etIdYear));
        dateBirthdateTemp = setFormatDate(year, month, day);
        request.setBirthDate(dateBirthdateTemp);
        dateIdTemp = setFormatDate(yearId, monthId, dayId);
        request.setIdExpirationDate(dateIdTemp);
    }

    void showDialogToSaveData(){
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.infortmation).toUpperCase())
                .titleColor(Color.WHITE)
                .content(R.string.kyc_update_data)
                .contentColor(Color.WHITE)
                .positiveText(R.string.btn_dialog_ya)
                .positiveColor(Color.WHITE)
                .negativeText(R.string.btn_dialog_tidak)
                .negativeColor(cPrimary)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        setBirthDateandExpiration();
                        validasiBirthDate();
                        if(check == true){
                            generateRequest();
                            getBus().send(new RxBusObject(RxBusObject.RxBusKey.SUBMIT_KYC_DATA, request));
                        } else {
                            etYear.setError("Usia minimal 17 tahun");
                            Toast.makeText(getContext(), "Kamu harus berusia setidaknya 17 tahun untuk berinvestasi di Finvest", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        cancelUpdate();
                        status = false;
                        getBus().send(new RxBusObject(RxBusObject.RxBusKey.NEXT_FORM, null));
                    }
                })
                .show();
    }

    void validasiBirthDate(){
        SimpleDateFormat dtf = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        Date localDate = new Date();
        String sysDate = dtf.format(localDate);

        String[] partsCurrent = sysDate.split("-");
        int dayCurrent = Integer.parseInt(partsCurrent[1]);
        int monthCurrent = Integer.parseInt(partsCurrent[0]);
        int yearCurrent = Integer.parseInt(partsCurrent[2]);

        if (year < (yearCurrent-17)) {
            check = true;
        } else if (year == (yearCurrent-17)){
            if (month < monthCurrent) {
                check = true;
            } else if (month == monthCurrent) {
                check = day <= dayCurrent;
            } else {
                check = false;
            }
        } else {
            check = false;
        }
    }

    void emptyStringValidation () {
        if (sGender.getSelectedItem().toString().equals("")) {
            emptySGender = false;
            emptySMaritalStatus = true;
            emptySEducationBackground = true;
            emptySReligion = true;
            emptySOccupation = true;
            emptySNaturOfBusiness = true;
            emptyPictureDoc1 = true;
            emptyPictureDoc2 = true;

            emptyString = false;
        } else if(sMaritalStatus.getSelectedItem().toString().equals("")) {
            emptySGender = true;
            emptySMaritalStatus = false;
            emptySEducationBackground = true;
            emptySReligion = true;
            emptySOccupation = true;
            emptySNaturOfBusiness = true;
            emptyPictureDoc1 = true;
            emptyPictureDoc2 = true;

            emptyString = false;
        } else if(sEducationBackground.getSelectedItem().toString().equals("")) {
            emptySGender = true;
            emptySMaritalStatus = true;
            emptySEducationBackground = false;
            emptySReligion = true;
            emptySOccupation = true;
            emptySNaturOfBusiness = true;
            emptyPictureDoc1 = true;
            emptyPictureDoc2 = true;

            emptyString = false;
        } else if(sReligion.getSelectedItem().toString().equals("")) {
            emptySGender = true;
            emptySMaritalStatus = true;
            emptySEducationBackground = true;
            emptySReligion = false;
            emptySOccupation = true;
            emptySNaturOfBusiness = true;
            emptyPictureDoc1 = true;
            emptyPictureDoc2 = true;

            emptyString = false;
        } else if (sOccupation.getSelectedItem().toString().equals("")) {
            emptySGender = true;
            emptySMaritalStatus = true;
            emptySEducationBackground = true;
            emptySReligion = true;
            emptySOccupation = false;
            emptySNaturOfBusiness = true;
            emptyPictureDoc1 = true;
            emptyPictureDoc2 = true;

            emptyString = false;
        } else if (sNatureOfBusiness.getSelectedItem().toString().equals("")) {
            emptySGender = true;
            emptySMaritalStatus = true;
            emptySEducationBackground = true;
            emptySReligion = true;
            emptySOccupation = true;
            emptySNaturOfBusiness = false;
            emptyPictureDoc1 = true;
            emptyPictureDoc2 = true;

            emptyString = false;
        } else if(ImageKeyDoc1 == null) {
            emptySGender = true;
            emptySMaritalStatus = true;
            emptySEducationBackground = true;
            emptySReligion = true;
            emptySOccupation = true;
            emptySNaturOfBusiness = true;
            emptyPictureDoc1 = false;
            emptyPictureDoc2 = true;

            emptyString = false;
        } else if (ImageKeyDoc2 == null) {
            emptySGender = true;
            emptySMaritalStatus = true;
            emptySEducationBackground = true;
            emptySReligion = true;
            emptySOccupation = true;
            emptySNaturOfBusiness = true;
            emptyPictureDoc1 = true;
            emptyPictureDoc2 = false;

            emptyString = false;
        }else {
            emptySGender = true;
            emptySMaritalStatus = true;
            emptySEducationBackground = true;
            emptySReligion = true;
            emptySOccupation = true;
            emptySNaturOfBusiness = true;

            emptyString = true;
        }
    }

    void cancelUpdate(){
        defaultValue();
        setSalutation();
        etFirstName.setText(request.getFirstName());
        etLastName.setText(request.getLastName());
        etBirthplace.setText(request.getBirthPlace());
        setupSpinnerWithSpecificLookupSelection(sGender, getKycLookupFromRealm(Constant.KYC_CAT_GENDER), request.getGender());
        setupSpinnerWithSpecificLookupSelection(sMaritalStatus, getKycLookupFromRealm(Constant.KYC_CAT_MARITAL_STATUS), request.getMaritalStatus());
        etMotherMaidenName.setText(request.getMotherMaidenName());
        setupSpinnerWithSpecificLookupSelection(sEducationBackground, getKycLookupFromRealm(Constant.KYC_CAT_EDUCATION_BACKGROUND), request.getEducationBackground());
        setupSpinnerWithSpecificLookupSelection(sReligion, getKycLookupFromRealm(Constant.KYC_CAT_RELIGION), request.getReligion());
        setupSpinnerWithSpecificLookupSelection(sPreferedMailingAddress, getKycLookupFromRealm(Constant.KYC_CAT_STATEMENT_TYPE), request.getPreferredMailingAddress());
        setupSpinnerWithSpecificLookupSelection(sOccupation, getKycLookupFromRealm(Constant.KYC_CAT_OCCUPATION), request.getOccupation());
        setupSpinnerWithSpecificLookupSelection(sNatureOfBusiness, getKycLookupFromRealm(Constant.KYC_CAT_NATURE_OF_BUSINESS), request.getNatureOfBusiness());
        etIdNumber.setText(request.getIdNumber());
        setupBirthDate();
        setUpIdNumber();

        locationHelper = new LocationHelper(getApi(), this);
        locationHelper.loadCountriesStatesCities();

        setBirthDateandExpiration();
    }


    //new
    void onCaptureImageResultSelfieId(){
        // Setting option to resize image
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(tempFile.getPath(), options);
        final int REQUIRED_SIZE = 310;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE) scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;

        // Load file with option parameter, then compress it
        Bitmap b = BitmapFactory.decodeFile(tempFile.getPath(), options);

        Matrix matrix = new Matrix();

        try {
            ExifInterface exif = null;
            exif = new ExifInterface(tempFile.getPath());
            String orientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            if (orientation.equals(ExifInterface.ORIENTATION_NORMAL)) {
                // Do nothing. The original image is fine.
            } else if (orientation.equals(ExifInterface.ORIENTATION_ROTATE_90 + "")) {
                matrix.postRotate(90);
            } else if (orientation.equals(ExifInterface.ORIENTATION_ROTATE_180 + "")) {
                matrix.postRotate(180);
            } else if (orientation.equals(ExifInterface.ORIENTATION_ROTATE_270 + "")) {
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
                doUploadSelfie(tempFile);
            }
        }, 2000);

    }


    //new
    void doUploadSelfie(File targetFile){
        presenter.uploadDoc5(presenter.constructUploadSelfie(), targetFile);
    }
    //end
}
