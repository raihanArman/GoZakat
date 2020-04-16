package id.co.myproject.gozakat.view.profil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import id.co.myproject.gozakat.BuildConfig;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.model.Muzakki;
import id.co.myproject.gozakat.model.Value;
import id.co.myproject.gozakat.request.ApiRequest;
import id.co.myproject.gozakat.request.RetrofitRequest;
import id.co.myproject.gozakat.util.ConvertBitmap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class EditProfilActivity extends AppCompatActivity implements ConvertBitmap {

    public static final int REQUEST_GALERY = 96;
    public static final int REQUEST_CAMERA = 98;
    public static final int CAMERA_PERMISSION = 90;
    ImageView ivBack, ivUser, ivCamera;
    TextView tvCamera, tvGalery;
    Button btnUpdate;
    EditText etNama, etUsername, etAlamat;
    Bitmap bitmap = null;
    String photoUser = null;
    int idUser;
    ApiRequest apiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);

        ivBack = findViewById(R.id.iv_back);
        ivUser = findViewById(R.id.iv_user);
        ivCamera = findViewById(R.id.iv_camera);
        etNama = findViewById(R.id.et_nama);
        etUsername = findViewById(R.id.et_username);
        etAlamat = findViewById(R.id.et_alamat);
        btnUpdate = findViewById(R.id.btn_update);

        idUser = getIntent().getIntExtra("id_user", 0);

        loadDataUser();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_GALERY);
            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_GALERY);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });

    }

    private void editProfile() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Proses ...");
        progressDialog.show();

        String nama = etNama.getText().toString();
        String username = etUsername.getText().toString();
        String alamat = etAlamat.getText().toString();
        Call<Value> editProfilRequest = apiRequest.editProfilRequest(idUser, nama, username, alamat, photoUser);
        editProfilRequest.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()){
                    Toast.makeText(EditProfilActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if (response.body().getValue() == 1){
                        progressDialog.dismiss();
                        ProfilActivity.statusUpdate = true;
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(EditProfilActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataUser() {

        Call<Muzakki> muzakkiCall = apiRequest.getMuzakkiItemRequest(idUser);
        muzakkiCall.enqueue(new Callback<Muzakki>() {
            @Override
            public void onResponse(Call<Muzakki> call, Response<Muzakki> response) {
                if (response.isSuccessful()){
                    Muzakki muzakki = response.body();
                    String avatar = muzakki.getAvatar();
                    if (muzakki.getAvatar().equals("")){
                        avatar = BuildConfig.BASE_URL_GAMBAR+"muzakki/muzakki.png";
                        Glide.with(EditProfilActivity.this).load(avatar).into(ivUser);
                    }else {
                        Glide.with(EditProfilActivity.this).load(BuildConfig.BASE_URL_GAMBAR+"muzakki/"+avatar).into(ivUser);
                    }
                    etNama.setText(muzakki.getNama());
                    etUsername.setText(muzakki.getUsername());
                    etAlamat.setText(muzakki.getAlamat());
                }
            }

            @Override
            public void onFailure(Call<Muzakki> call, Throwable t) {
                Toast.makeText(EditProfilActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_CAMERA);
            }else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_GALERY){
            if(resultCode == RESULT_OK && data != null){
                Uri imageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    ivUser.setImageBitmap(bitmap);
                    new LoadBitmapConverterCallback(this, EditProfilActivity.this::bitmapToString).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if(requestCode == REQUEST_CAMERA){
            if (resultCode == RESULT_OK && data != null){
                bitmap = (Bitmap) data.getExtras().get("data");
                ivUser.setImageBitmap(bitmap);
                new LoadBitmapConverterCallback(this, EditProfilActivity.this::bitmapToString).execute();
            }
        }
    }

    @Override
    public void bitmapToString(String imgConvert) {
        photoUser = imgConvert;
    }

    private class LoadBitmapConverterCallback extends AsyncTask<Void, Void, String> {

        private WeakReference<Context> weakContext;
        private WeakReference<ConvertBitmap> weakCallback;

        public LoadBitmapConverterCallback(Context context, ConvertBitmap convertBitmap){
            this.weakContext = new WeakReference<>(context);
            this.weakCallback = new WeakReference<>(convertBitmap);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakContext.get();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] imgByte = byteArrayOutputStream.toByteArray();
            String imgBitmap = Base64.encodeToString(imgByte, Base64.DEFAULT);
            return imgBitmap;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            weakCallback.get().bitmapToString(s);
        }
    }

}
