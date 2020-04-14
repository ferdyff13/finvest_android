package com.invisee.finvest.ui.fragments.pending_order;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.TransactionHistory;
import com.invisee.finvest.data.api.responses.DocTransactionResponse;
import com.invisee.finvest.data.api.responses.PendingOrderDetailResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;

import com.invisee.finvest.util.AmountFormatter;
import com.invisee.finvest.util.DateUtil;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static android.app.Activity.RESULT_OK;

/**
 * Created by pandu.abbiyuarsyah on 30/03/2017.
 */

@RuntimePermissions
public class DetailPendingOrderFragment extends BaseFragment {

    public static final String TAG = DetailPendingOrderFragment.class.getSimpleName();

    @Bind(R.id.txvOrderNumber)
    TextView txvOrderNumber;
    @Bind(R.id.txvPaymentType)
    TextView txvPaymentType;
    @Bind(R.id.txvTransactionDate)
    TextView txvTransactionDate;
    @Bind(R.id.txvTransactionTime)
    TextView txvTransactionTime;
    @Bind(R.id.txvTransactionStatus)
    TextView txvTransactionStatus;
    @Bind(R.id.txvNavDate)
    TextView txvNavDate;
    @Bind(R.id.txvPackageName)
    TextView txvPackageName;
    @Bind(R.id.txvTransactionType)
    TextView txvTransactionType;
    @Bind(R.id.txvProductName)
    TextView txvProductName;
    @Bind(R.id.txvComposition)
    TextView txvComposition;
    @Bind(R.id.txvInvestmentNumberDetail)
    TextView txvInvestmentNumberDetail;
    @Bind(R.id.txvFee)
    TextView txvFee;
    @Bind(R.id.txvTotal)
    TextView txvTotal;
    @Bind(R.id.txvNetTotal)
    TextView txvNetTotal;
    @Bind(R.id.imgOrder)
    ImageView imgOrder;
    @Bind(R.id.txvFileName)
    TextView txvFileName;
    @Bind(R.id.lnImageBox)
    LinearLayout lnImageBox;
    @Bind(R.id.lnPaymentType)
    LinearLayout lnPaymentType;

    @State
    PendingOrderDetailResponse response;
    @State
    DocTransactionResponse docTransactionResponse;

    private DetailPendingOrderPresenter presenter;

    private static final String TRANSACTION_HISTORY = "transcationHistory";

    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 4;

    private int choosenTask = 0;
    private boolean permittedInternal = false;
    private boolean permittedExternal = false;

    private File tempFile;
    private Uri picUri;

    public String convertedDate, convertedDatePrice;

    @State
    TransactionHistory transactionHistory;

    public static void showFragment(BaseActivity sourceActivity, TransactionHistory transactionHistory) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);

            Fragment fragment = new DetailPendingOrderFragment();

            if (transactionHistory != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(TRANSACTION_HISTORY, transactionHistory);
                fragment.setArguments(bundle);
            }
            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_detail_pending_order;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        presenter = new DetailPendingOrderPresenter(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (getArguments() != null && getArguments().containsKey(TRANSACTION_HISTORY))
            transactionHistory = (TransactionHistory) getArguments().getSerializable(TRANSACTION_HISTORY);
        else
            transactionHistory = null;

        presenter.getPendingOrderDetail(presenter.constructDetailPendingOrderRequest());
        //presenter.getDocTransaction(presenter.constructDocTransactionRequest());
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (transactionHistory.getTransactionType().equals("REDEMPTION")) {
            lnPaymentType.setVisibility(View.GONE);
        }

    }

    public void loadValue() {

        lnImageBox.setVisibility(View.VISIBLE);
        txvOrderNumber.setText(transactionHistory.getOrderNumber());
        txvPaymentType.setText(response.getData().getPaymentType());
        txvTransactionTime.setText(response.getData().getTime());
        txvTransactionStatus.setText(transactionHistory.getTransactionStatus());

        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.YYYY_MM_DD, Locale.getDefault());
        Date dateParse = null;
        try {
            dateParse = sdf.parse(response.getData().getDate());
            SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.DD__MMM__YYYY , Locale.getDefault());
            convertedDate= formatter.format(dateParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txvTransactionDate.setText(convertedDate);

        SimpleDateFormat sdfPrice = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());
        Date datePriceParse = null;
        try {
            datePriceParse = sdfPrice.parse(response.getData().getPriceDate());
            SimpleDateFormat formatterDatePrice = new SimpleDateFormat(DateUtil.DD__MMM__YYYY , Locale.getDefault());
            String convertedDatePrice = formatterDatePrice.format(datePriceParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txvNavDate.setText(convertedDatePrice);

        txvPackageName.setText(response.getData().getOrderDetail().get(0).getFundPackageName());
        txvProductName.setText(response.getData().getOrderDetail().get(0).getProductDto().get(0).getUtProductName());
        txvInvestmentNumberDetail.setText(response.getData().getOrderDetail().get(0).getInvesmentNumber());
        txvFee.setText(response.getData().getOrderDetail().get(0).getFee().toString() + " " + "%");
        txvTotal.setText(AmountFormatter.format(response.getData().getOrderDetail().get(0).getProductDto().get(0).getOrderAmount()));
        txvNetTotal.setText(AmountFormatter.format(response.getData().getOrderDetail().get(0).getProductDto().get(0).getNetAmount()));
        txvTransactionType.setText(transactionHistory.getTransactionType());

        if (response.getData().getPaymentType().equals("VISEEPAY") || (response.getData().getPaymentType().equals("TRANSFER VA")) ) {
            lnImageBox.setVisibility(View.GONE);
        } else if(response.getData().getPaymentType().equals("PAYPRO")) {
            lnImageBox.setVisibility(View.GONE);
        }else if(transactionHistory.getTransactionType().equalsIgnoreCase("redemption")) {
            lnImageBox.setVisibility(View.GONE);
        }else {
            lnImageBox.setVisibility(View.VISIBLE);
        }

        //new
        presenter.getDocTransaction(presenter.constructDocTransactionRequest());


    }

    public void loadImage(){

        final ProgressDialog pd = new ProgressDialog(getActivity());

        try{
            /*
            Picasso.with(getContext())
                    .load(InviseeService.IMAGE_CUSTOMER_DOWNLOAD_URL + docTransactionResponse.getData().get(0).getFileKey() + "&token=" + PrefHelper.getString(PrefKey.TOKEN))
                    .error(R.drawable.upload)
                    .into(imgOrder);
            */

            pd.show();
            Picasso.with(getContext()).load(InviseeService.IMAGE_CUSTOMER_DOWNLOAD_URL + docTransactionResponse.getData().get(0).getFileKey() + "&token=" + PrefHelper.getString(PrefKey.TOKEN))
                    .error(R.drawable.upload)
                    .into(imgOrder, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess(){
                            System.out.println("===============>success");
                            pd.dismiss();
                        }

                        @Override
                        public void onError(){
                            System.out.println("===============>error");
                            pd.dismiss();
                        }
                    });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void downloadImage(){
        txvFileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(InviseeService.IMAGE_CUSTOMER_DOWNLOAD_URL + docTransactionResponse.getData().get(0).getFileKey() + "&token=" + PrefHelper.getString(PrefKey.TOKEN)));
                    request.setDescription("");
                    request.setTitle("Download File");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.allowScanningByMediaScanner();
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "File Download");

                    // get download service and enqueue file
                    DownloadManager manager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(request);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void startGallery() {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
        presenter.uploadTransaction(transactionHistory.getOrderNumber(), targetFile);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        DetailPendingOrderFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnClick(R.id.imgOrder)
    void onChangePictureClicked() {
            new MaterialDialog.Builder(getActivity())
                    .title("Change Picture")
                    .items(R.array.change_picture)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            if (which == 0) {
                                choosenTask = REQUEST_CAMERA;
                                DetailPendingOrderFragmentPermissionsDispatcher.startCameraWithCheck(DetailPendingOrderFragment.this);
                            } else {
                                choosenTask = SELECT_FILE;
                                DetailPendingOrderFragmentPermissionsDispatcher.startGalleryWithCheck(DetailPendingOrderFragment.this);
                            }
                        }
                    })
                    .show();
        }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                    presenter.uploadTransaction(transactionHistory.getOrderNumber(), file);
                }
                break;
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    onCaptureImageResult();
                }
        }
    }


}
