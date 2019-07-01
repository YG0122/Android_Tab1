package com.example.Tab5;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStorageDirectory;

public class Tab2 extends Fragment implements View.OnClickListener {

    private ImageView imgMain;
    private Button btnCamera, btnAlbum;
    GridView gridView;
    gridAdapter adapter=null;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_ALBUM = 2;
    private static final int CROP_FROM_CAMERA = 3;

    private Uri photoUri;
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private static final int MULTIPLE_PERMISSIONS = 101;
    JSONArray jArray=new JSONArray();
    String filepath;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        //checkPermissions();
    }

/*
        private boolean checkPermissions() {
            int result;
            List<String> permissionList = new ArrayList<>();
            for (String pm : permissions) {
                result = ContextCompat.checkSelfPermission(getActivity(), pm);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(pm);
                }
            }
            if (!permissionList.isEmpty()) {
                ActivityCompat.requestPermissions(getActivity(), permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
                return false;
            }
            return true;
        }
*/
/*
    private void initView() {
//      imgMain = findViewById(R.id.img_test);
        btnCamera = getView().findViewById(R.id.btn_camera);
        btnAlbum = getView().findViewById(R.id.btn_album);
        gridView= getView().findViewById(R.id.gridView1);


//        gridView.setLayoutManager(new LinearLayoutManager(this));
        btnCamera.setOnClickListener(this);
        btnAlbum.setOnClickListener(this);

    }
*/
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            //finish();
            e.printStackTrace();
        }
        if (photoFile != null) {
            photoUri = FileProvider.getUriForFile(getActivity(),
                    "com.example.gallery.provider", photoFile);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            refreshmedia(photoFile);
            filepath=photoFile.getAbsolutePath();
            getActivity().startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "nostest_" + timeStamp + "_";
//        String imageFileName= "nostest_"+String.valueOf(System.currentTimeMillis())+".jpg";
//        File storageDir = new File(Environment.getExternalStorageDirectory(), imageFileName);

//        File storageDir = new File(Environment.getExternalStorageDirectory() + "/NOSTest/");
        File storageDir = new File(getExternalStorageDirectory() + "/NOSTest/");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        return image;
//        return storageDir;
    }

    private void goToAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        getActivity().startActivityForResult(intent, PICK_FROM_ALBUM);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera:
                takePhoto();
                break;
            case R.id.btn_album:
                goToAlbum();
                break;
        }
    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equals(this.permissions[0])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        } else if (permissions[i].equals(this.permissions[1])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();

                            }
                        } else if (permissions[i].equals(this.permissions[2])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();

                            }
                        }
                    }
                } else {
                    showNoPermissionToastAndFinish();
                }
                return;
            }
        }
    }

    private void showNoPermissionToastAndFinish() {
        Toast.makeText(getActivity(), "권한 요청에 동의 해주셔야 이용 가능합니다. 설정에서 권한 허용 하시기 바랍니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap=null;
        if (resultCode != RESULT_OK) {
            Toast.makeText(getActivity(), "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode == PICK_FROM_ALBUM) {

            if (data == null) {
                return;
            }
            photoUri = data.getData();
            try {
                InputStream in = getActivity().getContentResolver().openInputStream(photoUri);
                bitmap= BitmapFactory.decodeStream(in);
                in.close();

            }catch(Exception e){
                e.printStackTrace();
            }
//            cropImage();
        } else if (requestCode == PICK_FROM_CAMERA) {
//            cropImage();
//             갤러리에 나타나게
            MediaScannerConnection.scanFile(getActivity().getApplicationContext(), new String[]{photoUri.getPath()}, null,  new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    Log.v("File scan", "file:"+path+"was scanned successfully");
                }
            });
            try {
//            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                bitmap= BitmapFactory.decodeFile(filepath);
            } catch(Exception e){
                Log.e("ERROR", e.getMessage().toString());
            }

        }


        JSONObject sObject=new JSONObject();
        try {
            sObject.put("Photo", bitmap);
            sObject.put("Uri", photoUri.toString());
            jArray.put(sObject);
        }catch(JSONException e){
            e.printStackTrace();
        }
//        } else if (requestCode == CROP_FROM_CAMERA) {
//            imgMain.setImageURI(null);
//            imgMain.setImageURI(photoUri);
//            revokeUriPermission(photoUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        }
        adapter= new gridAdapter(jArray);
        gridView.setAdapter(adapter);
    }

    public void refreshmedia(File file){
        MediaScannerConnection.scanFile(getActivity().getApplicationContext(), new String[]{file.getAbsolutePath()}, null,  new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(String path, Uri uri) {
                Log.v("File scan", "file:"+path+"was scanned successfully");
            }
        });
//        Intent intent= new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//
//        intent.setData(Uri.fromFile(file));
//        sendBroadcast(intent);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);
//      imgMain = findViewById(R.id.img_test);
        btnCamera = getView().findViewById(R.id.btn_camera);
        btnAlbum = getView().findViewById(R.id.btn_album);
        gridView= getView().findViewById(R.id.gridView1);


//        gridView.setLayoutManager(new LinearLayoutManager(this));
        btnCamera.setOnClickListener(this);
        btnAlbum.setOnClickListener(this);

        return rootView;
    }
}
