package com.android.app.function.upload;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.app.R;
import com.android.app.function.upload.upload.FileUpload;
import com.android.app.function.upload.upload.FileUploadCenter;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.UUID;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by frodo on 2015/4/7.
 */
public class UploadActivity extends Activity {

    private static final String TAG = "Upload";
    //    private static final String SERVER = "http://172.17.234.2:8080/";
    private static final String SERVER = "http://172.22.203.81:8080/";

    private ProgressBar progressBar;
    private Button uploadButton;
    private EditText serverUrl;
    private EditText fileToUpload;
    private EditText parameterName;

    private final AbstractUploadServiceReceiver uploadReceiver = new AbstractUploadServiceReceiver() {

        @Override
        public void onProgress(String uploadId, int progress) {
            progressBar.setProgress(progress);

            Log.i(TAG, "The progress of the upload with ID " + uploadId + " is: " + progress);
        }

        @Override
        public void onError(String uploadId, Exception exception) {
            progressBar.setProgress(0);

            String message = "Error in upload with ID: " + uploadId + ". " + exception.getLocalizedMessage();
            Log.e(TAG, message, exception);
        }

        @Override
        public void onCompleted(String uploadId, int serverResponseCode, String serverResponseMessage) {
            progressBar.setProgress(0);

            String message = "Upload with ID " + uploadId + " is completed: " + serverResponseCode + ", "
                    + serverResponseMessage;
            Log.i(TAG, message);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_upload_layout);

        // Set your application namespace to avoid conflicts with other apps
        // using this library
        UploadService.NAMESPACE = "com.alexbbb";

        progressBar = (ProgressBar) findViewById(R.id.uploadProgress);
        serverUrl = (EditText) findViewById(R.id.serverURL);
        fileToUpload = (EditText) findViewById(R.id.fileToUpload);
        parameterName = (EditText) findViewById(R.id.parameterName);
        uploadButton = (Button) findViewById(R.id.uploadButton);

        uploadButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                onUploadButtonClick();
//                uploadToPersonServer();
                uploadFileToServer();
            }
        });

        progressBar.setMax(100);
        progressBar.setProgress(0);

        // De-comment this line to enable self-signed SSL certificates in HTTPS connections
        // WARNING: Do not use in production environment. Recommended for development only
        // AllCertificatesAndHostsTruster.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        uploadReceiver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        uploadReceiver.unregister(this);
    }

    private boolean userInputIsValid(final String serverUrlString, final String fileToUploadPath,
                                     final String paramNameString) {
        if (serverUrlString.length() == 0) {
            Toast.makeText(this, getString(R.string.provide_valid_server_url), Toast.LENGTH_LONG).show();
            return false;
        }

        try {
            new URL(serverUrlString.toString());
        } catch (Exception exc) {
            Toast.makeText(this, getString(R.string.provide_valid_server_url), Toast.LENGTH_LONG).show();
            return false;
        }

        if (fileToUploadPath.length() == 0) {
            Toast.makeText(this, getString(R.string.provide_file_to_upload), Toast.LENGTH_LONG).show();
            return false;
        }

        if (!new File(fileToUploadPath).exists()) {
            Toast.makeText(this, getString(R.string.file_does_not_exist), Toast.LENGTH_LONG).show();
            return false;
        }

        if (paramNameString.length() == 0) {
            Toast.makeText(this, getString(R.string.provide_param_name), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    /**
     * 系统原生方式
     */
    private void onUploadButtonClick() {
        final String serverUrlString = serverUrl.getText().toString();
        final String fileToUploadPath = fileToUpload.getText().toString();
        final String paramNameString = parameterName.getText().toString();

        if (!userInputIsValid(serverUrlString, fileToUploadPath, paramNameString))
            return;

        final UploadRequest request = new UploadRequest(this, UUID.randomUUID().toString(), serverUrlString);

        request.addFileToUpload(fileToUploadPath, paramNameString, "test", ContentType.APPLICATION_OCTET_STREAM);

        request.setNotificationConfig(R.drawable.ic_launcher, getString(R.string.app_name),
                getString(R.string.uploading), getString(R.string.upload_success),
                getString(R.string.upload_error), false);

        try {
            UploadService.startUpload(request);
        } catch (Exception exc) {
            Toast.makeText(this, "Malformed upload request. " + exc.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 采用了retrofit上传服务器
     */
    private void uploadToPersonServer() {
        final String fileToUploadPath = fileToUpload.getText().toString();
        final String paramNameString = parameterName.getText().toString();

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(SERVER).build();
        String mimeType = "image/jpg";
        File file = new File(fileToUploadPath);
        TypedFile fileToSend = new TypedFile(mimeType, file);
        FileWebService fileWebService = restAdapter.create(FileWebService.class);
        fileWebService.upload(file.getName(), paramNameString, fileToSend, new Callback<String>() {
            @Override
            public void success(String s, final Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UploadActivity.this, "上传成功 " + response.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(final RetrofitError error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UploadActivity.this, String.format("上传失败 %s", Arrays.toString(error.getStackTrace())), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void uploadFileToServer() {
        LinkedList<String> files = getImagePathFromSD();

        FileUpload fileUpload = new FileUpload();
        fileUpload.group = "For Test";
        fileUpload.files = files;

        FileUploadCenter fileUploadCenter = FileUploadCenter.from(this);
        fileUploadCenter.init(SERVER);
        fileUploadCenter.addTask(fileUpload);
    }


    /**
     * 从sd卡获取图片资源
     *
     * @return
     */
    private LinkedList<String> getImagePathFromSD() {
        // 图片列表
        LinkedList<String> picList = new LinkedList<>();

        String imagePath = /*Environment.getExternalStorageDirectory().toString()*/"/storage/emulated/0/" + "/image/";
        File mfile = new File(imagePath);
        File[] files = mfile.listFiles();
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (File file : files) {
            if (checkIsImageFile(file.getPath())) {
                picList.offer(file.getPath());
            }
        }
        // 返回得到的图片列表
        return picList;
    }

    /**
     * 检查扩展名，得到图片格式的文件
     */
    private boolean checkIsImageFile(String fName) {
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        return FileEnd.equals("jpg") || FileEnd.equals("gif")
                || FileEnd.equals("png") || FileEnd.equals("jpeg")
                || FileEnd.equals("bmp");
    }

    public interface FileWebService {
        @Multipart
        @POST("/manager/file/upload")
        void upload(@Part("name") String name,
                    @Part("md5") String md5,
                    @Part("file") TypedFile file,
                    Callback<String> callback);
    }
}

