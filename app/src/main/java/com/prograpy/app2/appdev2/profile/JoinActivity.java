package com.prograpy.app2.appdev2.profile;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.prograpy.app2.appdev2.LoginActivity;
import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.main.MainActivity;
import com.prograpy.app2.appdev2.main.MatchFragment;
import com.prograpy.app2.appdev2.network.NetworkProgressDialog;
import com.prograpy.app2.appdev2.network.response.ApiValue;
import com.prograpy.app2.appdev2.network.response.data.HobbyData;
import com.prograpy.app2.appdev2.network.response.result.JoinResult;
import com.prograpy.app2.appdev2.task.JoinTask;
import com.prograpy.app2.appdev2.task.UpdateFcmKeyTask;
import com.prograpy.app2.appdev2.utils.PreferenceData;
import com.prograpy.app2.appdev2.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class JoinActivity extends AppCompatActivity {
    // ㅁ 조건
    // 1. 닉네임되야한다
    // 2. 중복체크가 확인
    // 3. 성별이 빈값이 아닐때

    private ImageView join;                    // 회원가입 버튼
    private ImageView idCheckButton;          // 중복확인 버튼

    private EditText nameEdit;
    private EditText ageEdit;
    private EditText areaEdit;

    private EditText idEdit;
    private EditText passwordEdit;

    private RadioGroup genderRadio;         // 성별 남자 radiobtn
    private RadioButton man_btn, woman_btn; // 성별 여자 radiobtn

    boolean namechecked = false;

    String name;
    String age;
    String gender;
    String area;

    String id;
    String password;


    private String bh_number_1 = "";
    private String bh_number_2 = "";
    private String bh_number_3 = "";
    private String sh_number_1 = "";
    private String sh_number_2 = "";
    private String sh_number_3 = "";

    private ImageView center_image;

    private String picData = "";

    // 앨범 선택 플래그 값
    public static final int PICK_ALBUM = 102;


    private NetworkProgressDialog networkProgressDialog;

    private ArrayAdapter<CharSequence> hobby_first, hobby_second, hobby_third;
    private Spinner spinner_hobby1;
    private Spinner spinner_hobby2;
    private Spinner spinner_hobby_second1;
    private Spinner spinner_hobby_second2;
    private Spinner spinner_hobby_third1;
    private Spinner spinner_hobby_third2;

    private ImageView imgBack;

    private ArrayList<HobbyData> bigHobbyList = new ArrayList<HobbyData>();
    private ArrayList<String> bigHobbyNameList = new ArrayList<String>();

    private ArrayList<HobbyData> smallHobbyList = new ArrayList<HobbyData>();
    private ArrayList<String> smallHobbyNameList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);



        bigHobbyList = getIntent().getParcelableArrayListExtra("bigHobby");
        smallHobbyList = getIntent().getParcelableArrayListExtra("smallHobby");


        if(bigHobbyList.size() > 0){

            for (HobbyData data : bigHobbyList){
                bigHobbyNameList.add(data.getHobby_name());
            }
        }


        /*
            갤러리 접속해 이미지 불러오기
         */
        ImageButton ImageAdd = (ImageButton) findViewById(R.id.imageAdd);
        ImageAdd.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                doTakeAlbumAction();
            }
        });


        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        center_image = (ImageView) findViewById(R.id.center_image);
        center_image.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                doTakeAlbumAction();
            }
        });


        networkProgressDialog = new NetworkProgressDialog(this);

        // 성별 선택 radio 버튼
        man_btn = (RadioButton) findViewById(R.id.man);
        woman_btn = (RadioButton) findViewById(R.id.woman);

        man_btn.setOnClickListener(new RadioButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (man_btn.isChecked()) {
                    gender = "남자";
                }
            }
        });
        woman_btn.setOnClickListener(new RadioButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (woman_btn.isChecked()) {
                    gender = "여자";
                }
            }
        });


        nameEdit = findViewById(R.id.nameEdit);
        ageEdit = findViewById(R.id.ageEdit);
        areaEdit = findViewById(R.id.areaEdit);
        idEdit = findViewById(R.id.idEdit);
        passwordEdit = findViewById(R.id.passwordEdit);


        idCheckButton = (ImageView) findViewById(R.id.idCheckButton);
        idEdit = (EditText) findViewById(R.id.idEdit);
        // 닉네임 중복화인 리스너
        idCheckButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                id = idEdit.getText().toString();

                if (id.equals("")) {
                    Toast.makeText(JoinActivity.this, "값을 입력하세요.", Toast.LENGTH_SHORT).show();
                    namechecked = false;
                } else {
                    Toast.makeText(JoinActivity.this, "사용 가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
                    namechecked = true;
                    // 중복확인이 됬는지 안됬는지 확인
                }
            }
        });

        /*
           회원가입 버튼으로 인한 메인화면으로의 화면전환
           중복환인 및 성별 체크 여부 확인
        */
        join = (ImageView) findViewById(R.id.join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameEdit.getText().toString().trim();
                age = ageEdit.getText().toString().trim();
                area = areaEdit.getText().toString().trim();
                id = idEdit.getText().toString().trim();
                password = passwordEdit.getText().toString().trim();

                // String은 == 으로 비교하지말고 .equals 로 비교할것
                if (gender.equals("")) {
                    Toast.makeText(JoinActivity.this, "성별을 선택하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!namechecked) {
                    Toast.makeText(JoinActivity.this, "중복확인을 하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!Utils.isValidId(id)){
                    Toast.makeText(JoinActivity.this, "아이디는 영소문자+숫자 조합으로 4~16자 이내로 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!Utils.isValidPw(password)){
                    Toast.makeText(JoinActivity.this, "비밀번호는 영소문자+숫자 조합으로 8~16자 이내로 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (area.equals("")) {
                    Toast.makeText(JoinActivity.this, "거주지역을 입력 하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (picData.equals("")) {
                    Toast.makeText(JoinActivity.this, "사진을 추가 해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (bh_number_1.equals("") || bh_number_2.equals("") || bh_number_3.equals("") ||
                        sh_number_1.equals("") || sh_number_2.equals("") || sh_number_3.equals("")) {
                    Toast.makeText(JoinActivity.this, "취미를 모두 선택 해주세요.", Toast.LENGTH_SHORT).show();
                    return;

                }

                networkProgressDialog.show();

                // 서버로 요청할 task를 만들어준다.
                // 생성할때 해당 task 생성자에 task에서 선언해준 인터페이스를 구현해줘야 하므로 해당 인터페이스의 내용도
                // 마찬가지로 선언해준다.
                JoinTask joinTask = new JoinTask(new JoinTask.JoinTaskResultHandler() {

                    @Override
                    public void onSuccessTask(JoinResult result) {

                        networkProgressDialog.dismiss();

                        if (result != null) {

                            // 서버에서 파싱한 데이터중 성공여부에 대한 데이터가 성공일때만 화면이동
                            if (result.isSuccess()) {

                                PreferenceData.setKeyUserId(id);
                                PreferenceData.setKeyUserPw(password);
                                PreferenceData.setKeyUserLoginSuccess(true);

                                updateFcmKey();

                                Intent i = new Intent(JoinActivity.this, LoginActivity.class);
                                i.putParcelableArrayListExtra("bigHobby", bigHobbyList);
                                i.putParcelableArrayListExtra("smallHobby", smallHobbyList);
                                startActivity(i);
                                ActivityCompat.finishAffinity(JoinActivity.this);

                            } else {
                                Toast.makeText(JoinActivity.this, result.getError(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(JoinActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailTask() {
                        networkProgressDialog.dismiss();

                        Toast.makeText(JoinActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelTask() {
                        networkProgressDialog.dismiss();

                        Toast.makeText(JoinActivity.this, "서버 통신을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                });


                // execute 함수를 호출하는 순간 task의 내용들이 실행된다
                // execute 함수 안에 넘겨주는 파라미터 값들은 doinBackground에서 strings.... 에 들어가는 내용들
                joinTask.execute(ApiValue.API_JOIN, name, gender, age, area, picData,
                        bh_number_1, bh_number_2, bh_number_3 , sh_number_1, sh_number_2,
                        sh_number_3, id, password, PreferenceData.getKeyFcmToken());

            }
        });

        /*
            취미를 받아들여옴
         */


        spinner_hobby1 = (Spinner) findViewById(R.id.hobby_first);
        spinner_hobby2 = (Spinner) findViewById(R.id.hobby_first_sub);
        hobby_first = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, bigHobbyNameList);

        spinner_hobby1.setAdapter(hobby_first);
        spinner_hobby1.setOnItemSelectedListener(spinnerSelectListener);

        spinner_hobby_second1 = (Spinner) findViewById(R.id.hobby_second);
        spinner_hobby_second2 = (Spinner) findViewById(R.id.hobby_second_sub);
        hobby_second = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, bigHobbyNameList);

        spinner_hobby_second1.setAdapter(hobby_second);
        spinner_hobby_second1.setOnItemSelectedListener(spinnerSelectListener);

        // 나중에 수정 * hobby_third => hobby_first로 통일시키기
        spinner_hobby_third1 = (Spinner) findViewById(R.id.hobby_third);
        spinner_hobby_third2 = (Spinner) findViewById(R.id.hobby_third_sub);
        hobby_third = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, bigHobbyNameList);

        spinner_hobby_third1.setAdapter(hobby_third);
        spinner_hobby_third1.setOnItemSelectedListener(spinnerSelectListener);
    }


    private void updateFcmKey(){

        UpdateFcmKeyTask updateFcmKeyTask = new UpdateFcmKeyTask(null);
        updateFcmKeyTask.execute(ApiValue.API_UPDATE_FCM_KEY, PreferenceData.getKeyUserId(), PreferenceData.getKeyFcmToken());
    }


    private AdapterView.OnItemSelectedListener spinnerSelectListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            switch (adapterView.getId()) {

                case R.id.hobby_first:
                    ArrayAdapter edit_hobby_first_adapter = null;
                    ArrayList<String> smallHobbyNameList = new ArrayList<String>();

                    smallHobbyNameList.clear();

                    for(HobbyData data : smallHobbyList){
                        if(data.getHobby_big_num() == bigHobbyList.get(i).getHobby_num()){
                            smallHobbyNameList.add(data.getHobby_name());
                        }
                    }

                    edit_hobby_first_adapter = new ArrayAdapter(view.getContext(), R.layout.support_simple_spinner_dropdown_item, smallHobbyNameList);

                    if (edit_hobby_first_adapter != null) {
                        spinner_hobby2.setAdapter(edit_hobby_first_adapter);
                        edit_hobby_first_adapter.notifyDataSetChanged();

                        spinner_hobby2.setOnItemSelectedListener(spinnerSelectListener);
                    }

                    bh_number_1 = String.valueOf(bigHobbyList.get(i).getHobby_num());

                    break;

                case R.id.hobby_second:
                    ArrayAdapter edit_hobby_second_adapter = null;
                    ArrayList<String> smallHobbyNameList2 = new ArrayList<String>();

                    smallHobbyNameList2.clear();

                    for(HobbyData data : smallHobbyList){
                        if(data.getHobby_big_num() == bigHobbyList.get(i).getHobby_num()){
                            smallHobbyNameList2.add(data.getHobby_name());
                        }
                    }

                    edit_hobby_second_adapter = new ArrayAdapter(view.getContext(), R.layout.support_simple_spinner_dropdown_item, smallHobbyNameList2);

                    if (edit_hobby_second_adapter != null) {
                        spinner_hobby_second2.setAdapter(edit_hobby_second_adapter);
                        edit_hobby_second_adapter.notifyDataSetChanged();

                        spinner_hobby_second2.setOnItemSelectedListener(spinnerSelectListener);
                    }

                    bh_number_2 = String.valueOf(bigHobbyList.get(i).getHobby_num());
                    break;


                case R.id.hobby_third:

                    ArrayAdapter edit_hobby_third_adapter = null;
                    ArrayList<String> smallHobbyNameList3 = new ArrayList<String>();

                    smallHobbyNameList3.clear();

                    for(HobbyData data : smallHobbyList){
                        if(data.getHobby_big_num() == bigHobbyList.get(i).getHobby_num()){
                            smallHobbyNameList3.add(data.getHobby_name());
                        }
                    }

                    edit_hobby_third_adapter = new ArrayAdapter(view.getContext(), R.layout.support_simple_spinner_dropdown_item, smallHobbyNameList3);

                    if (edit_hobby_third_adapter != null) {
                        spinner_hobby_third2.setAdapter(edit_hobby_third_adapter);
                        edit_hobby_third_adapter.notifyDataSetChanged();

                        spinner_hobby_third2.setOnItemSelectedListener(spinnerSelectListener);
                    }

                    bh_number_3 = String.valueOf(bigHobbyList.get(i).getHobby_num());
                    break;


                case R.id.hobby_first_sub:

                    for (HobbyData data : smallHobbyList){
                        if(data.getHobby_name().equals((String)adapterView.getSelectedItem())){
                            sh_number_1 = String.valueOf(data.getHobby_num());
                            break;
                        }
                    }

                    break;

                case R.id.hobby_second_sub:

                    for (HobbyData data : smallHobbyList){
                        if(data.getHobby_name().equals((String)adapterView.getSelectedItem())){
                            sh_number_2 = String.valueOf(data.getHobby_num());
                            break;
                        }
                    }

                    break;

                case R.id.hobby_third_sub:
                    for (HobbyData data : smallHobbyList){
                        if(data.getHobby_name().equals((String)adapterView.getSelectedItem())){
                            sh_number_3 = String.valueOf(data.getHobby_num());
                            break;
                        }
                    }

                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case PICK_ALBUM: // 사진 앨범에서 선택

                if (data == null) {
                    break;

                } else {
                    Uri imageUri = data.getData();

                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri), null, options);

                        if (center_image.getWidth() > 0 || center_image.getHeight() > 0) {
                            options.inSampleSize = calculateInSampleSize(options, center_image.getWidth(), center_image.getHeight());
                        }

                        options.inJustDecodeBounds = false;
                        options.inPurgeable = true;

                        Bitmap photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri), null, options);

                        photo = rotate(new ExifInterface(getRealPathFromURI(imageUri)), photo, 100);

                        center_image.setImageBitmap(photo);

                        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOS);

                        byte[] byteArray = byteArrayOS.toByteArray();   //바이트로 변환
                        picData = Base64.encodeToString(byteArray, 0);  //스트링으로 변환

                    } catch (Exception e) {
                        e.printStackTrace();

                        Toast.makeText(JoinActivity.this, "다른 사진을 선택 해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }

                break;


        }

    }

    /**
     * 앨범에서 이미지 가져오기
     */
    private void doTakeAlbumAction() {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_ALBUM);
    }


    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 2;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private Bitmap rotate(ExifInterface ei, Bitmap bitmap, int quality) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);

        // 이미지 정보 객체에서 회전에 대한 정보를 추출
        int exifOrientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        // 해당 정보를 기반으로 int 회전각 수치를 추출
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        if (exifDegree != 0 && bitmap != null) {
            Matrix m = new Matrix();

            // 회전각을 적용 시키고 일단은 해당 사진 크기의 절반 정도 크기로 줄인다
            m.setRotate(exifDegree, bitmap.getWidth(), bitmap.getHeight());

            try {
                // 회전 !
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);

                if (bitmap != converted) {
                    bitmap.recycle();
                    bitmap = converted;
                }
            } catch (OutOfMemoryError ex) {
                // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환.
                ex.printStackTrace();
            }
        }
        return bitmap;
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            // url에 _data 컬럼 데이터를 전체 가져온다
            cursor = getContentResolver().query(contentUri, projection, null, null, null);

            //커서를 처음으로 이동시키고
            if (cursor != null && cursor.moveToFirst()) {
                // 지정한 컬럼 인덱스 가져온 뒤
                final int index = cursor.getColumnIndexOrThrow(column);
                // 해당하는 정보를 string으로 반환
                return cursor.getString(index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
}
