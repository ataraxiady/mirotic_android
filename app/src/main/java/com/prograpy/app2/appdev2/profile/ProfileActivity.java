package com.prograpy.app2.appdev2.profile;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.main.SubActivity;
import com.prograpy.app2.appdev2.network.NetworkProgressDialog;
import com.prograpy.app2.appdev2.network.response.ApiValue;
import com.prograpy.app2.appdev2.network.response.result.JoinResult;
import com.prograpy.app2.appdev2.task.JoinTask;

import java.io.ByteArrayOutputStream;

public class ProfileActivity extends AppCompatActivity {
    // ㅁ 조건
    // 1. 닉네임되야한다
    // 2. 중복체크가 확인
    // 3. 성별이 빈값이 아닐때

    private Button join;                    // 회원가입 버튼
    private Button nickname;                // 중복확인
    private EditText from;              // 닉네임 입력 칸
    private RadioGroup genderradio;         // 성별 남자 radiobtn
    private RadioButton man_btn, woman_btn; // 성별 여자 radiobtn
    private String gender = "";
    private String nick = "";
    private String area = "";
    private EditText editText;
    boolean namechecked = false;

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

    private ArrayAdapter<CharSequence> from_main, hobby_first, hobby_second, hobby_third;
    private Spinner spinner_hobby1;
    private Spinner spinner_hobby2;
    private Spinner spinner_hobby_second1;
    private Spinner spinner_hobby_second2;
    private Spinner spinner_hobby_third1;
    private Spinner spinner_hobby_third2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


        /*
            갤러리 접속해 이미지 불러오기
         */
        Button ImageAdd = (Button) findViewById(R.id.imageAdd);
        ImageAdd.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                doTakeAlbumAction();
            }
        });


        center_image = (ImageView) findViewById(R.id.center_image);
        center_image.setBackground(new ShapeDrawable(new OvalShape()));
        if(Build.VERSION.SDK_INT >= 21){
            center_image.setClipToOutline(true);
        }

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


        join = (Button) findViewById(R.id.join);

        nickname = (Button) findViewById(R.id.nickname_btn);
        editText = (EditText) findViewById(R.id.nameText);
        // 닉네임 중복화인 리스너
        nickname.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                nick = editText.getText().toString();

                if (nick.equals("")) {
                    Toast.makeText(ProfileActivity.this, "값을 입력하세요.", Toast.LENGTH_SHORT).show();
                    namechecked = false;
                } else {
                    Toast.makeText(ProfileActivity.this, "사용 가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
                    namechecked = true;
                    // 중복확인이 됬는지 안됬는지 확인
                }
            }
        });

        /*
           회원가입 버튼으로 인한 메인화면으로의 화면전환
           중복환인 및 성별 체크 여부 확인
        */
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nick = editText.getText().toString();  // 버튼 클릭 시  edittext에서 정보 받아들임
                area = from.getText().toString();

                // String은 == 으로 비교하지말고 .equals 로 비교할것
                if (gender.equals("")) {
                    Toast.makeText(ProfileActivity.this, "성별을 선택하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                // 사는지역 spinner
                final ArrayAdapter<CharSequence> from_main;

                if (!namechecked) {
                    Toast.makeText(ProfileActivity.this, "중복확인을 하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (nick.equals("")) {
                    Toast.makeText(ProfileActivity.this, "닉네임을 입력 하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (from.equals("")) {
                    Toast.makeText(ProfileActivity.this, "거주지역을 입력 하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (picData.equals("")) {
                    Toast.makeText(ProfileActivity.this, "사진을 추가 해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (bh_number_1.equals("") || bh_number_2.equals("") || bh_number_3.equals("") ||
                        sh_number_1.equals("") || sh_number_2.equals("") || sh_number_3.equals("")) {
                    Toast.makeText(ProfileActivity.this, "취미를 모두 선택 해주세요.", Toast.LENGTH_SHORT).show();
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
                                Intent i = new Intent(ProfileActivity.this, SubActivity.class);
                                startActivity(i);
                                finish();

                            } else {
                                Toast.makeText(ProfileActivity.this, result.getError(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(ProfileActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailTask() {
                        networkProgressDialog.dismiss();

                        Toast.makeText(ProfileActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelTask() {
                        networkProgressDialog.dismiss();

                        Toast.makeText(ProfileActivity.this, "서버 통신을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                // execute 함수를 호출하는 순간 task의 내용들이 실행된다
                // execute 함수 안에 넘겨주는 파라미터 값들은 doinBackground에서 strings.... 에 들어가는 내용들
                joinTask.execute(ApiValue.API_JOIN, nick, gender, "0", area, picData,
                        bh_number_1, bh_number_2, bh_number_3, sh_number_1, sh_number_2, sh_number_3, "kakao", "test", "1234");

            }
        });

        /*
            취미를 받아들여옴
         */
        from = (EditText) findViewById(R.id.from_main);


        spinner_hobby1 = (Spinner) findViewById(R.id.hobby_first);
        spinner_hobby2 = (Spinner) findViewById(R.id.hobby_first_sub);
        hobby_first = ArrayAdapter.createFromResource(this, R.array.spinner_hobby, R.layout.support_simple_spinner_dropdown_item);
        spinner_hobby1.setAdapter(hobby_first);
        spinner_hobby1.setOnItemSelectedListener(spinnerSelectListener);

        spinner_hobby_second1 = (Spinner) findViewById(R.id.hobby_second);
        spinner_hobby_second2 = (Spinner) findViewById(R.id.hobby_second_sub);
        hobby_second = ArrayAdapter.createFromResource(this, R.array.spinner_hobby, R.layout.support_simple_spinner_dropdown_item);
        spinner_hobby_second1.setAdapter(hobby_second);
        spinner_hobby_second1.setOnItemSelectedListener(spinnerSelectListener);

        // 나중에 수정 * hobby_third => hobby_first로 통일시키기
        spinner_hobby_third1 = (Spinner) findViewById(R.id.hobby_third);
        spinner_hobby_third2 = (Spinner) findViewById(R.id.hobby_third_sub);
        hobby_third = ArrayAdapter.createFromResource(this, R.array.spinner_hobby, R.layout.support_simple_spinner_dropdown_item);
        spinner_hobby_third1.setAdapter(hobby_third);
        spinner_hobby_third1.setOnItemSelectedListener(spinnerSelectListener);
    }


    private AdapterView.OnItemSelectedListener spinnerSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            switch (adapterView.getId()) {

                case R.id.hobby_first:
                    ArrayAdapter<CharSequence> hobby_first_adapter = null;

                    if (hobby_first.getItem(i).equals("대분류")) {
                        hobby_first_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_empty, android.R.layout.simple_spinner_dropdown_item);

                    } else if (hobby_first.getItem(i).equals("운동")) {
                        bh_number_1 = "0";
                        hobby_first_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_sport, android.R.layout.simple_spinner_dropdown_item);

                    } else if (hobby_first.getItem(i).equals("음악")) {
                        bh_number_1 = "1";
                        hobby_first_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_music, android.R.layout.simple_spinner_dropdown_item);

                    } else if (hobby_first.getItem(i).equals("영화")) {
                        bh_number_1 = "2";
                        hobby_first_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_movie, android.R.layout.simple_spinner_dropdown_item);

                    }

                    if (hobby_first_adapter != null) {
                        spinner_hobby2.setAdapter(hobby_first_adapter);
                        spinner_hobby2.setOnItemSelectedListener(spinnerSelectListener);
                    }

                    break;

                case R.id.hobby_second:
                    ArrayAdapter<CharSequence> hobby_second_adapter = null;

                    if (hobby_second.getItem(i).equals("대분류")) {
                        hobby_second_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_empty, R.layout.support_simple_spinner_dropdown_item);

                    } else if (hobby_second.getItem(i).equals("운동")) {
                        bh_number_2 = "0";
                        hobby_second_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_sport, R.layout.support_simple_spinner_dropdown_item);

                    } else if (hobby_second.getItem(i).equals("음악")) {
                        bh_number_2 = "1";
                        hobby_second_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_music, R.layout.support_simple_spinner_dropdown_item);

                    } else if (hobby_second.getItem(i).equals("영화")) {
                        bh_number_2 = "2";
                        hobby_second_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_movie, R.layout.support_simple_spinner_dropdown_item);
                    }

                    if (hobby_second_adapter != null) {
                        spinner_hobby_second2.setAdapter(hobby_second_adapter);
                        spinner_hobby_second2.setOnItemSelectedListener(spinnerSelectListener);
                    }

                    break;


                case R.id.hobby_third:

                    ArrayAdapter<CharSequence> hobby_third_adapter = null;

                    if (hobby_third.getItem(i).equals("대분류")) {
                        hobby_third_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_empty, R.layout.support_simple_spinner_dropdown_item);

                    } else if (hobby_third.getItem(i).equals("운동")) {
                        bh_number_3 = "0";
                        hobby_third_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_sport, R.layout.support_simple_spinner_dropdown_item);

                    } else if (hobby_third.getItem(i).equals("음악")) {
                        bh_number_3 = "1";
                        hobby_third_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_music, R.layout.support_simple_spinner_dropdown_item);

                    } else if (hobby_third.getItem(i).equals("영화")) {
                        bh_number_3 = "2";
                        hobby_third_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_movie, R.layout.support_simple_spinner_dropdown_item);

                    }

                    if (hobby_third_adapter != null) {
                        spinner_hobby_third2.setAdapter(hobby_third_adapter);
                        spinner_hobby_third2.setOnItemSelectedListener(spinnerSelectListener);
                    }

                    break;


                case R.id.hobby_first_sub:
                    sh_number_1 = String.valueOf(i);
                    break;

                case R.id.hobby_second_sub:
                    sh_number_2 = String.valueOf(i);
                    break;

                case R.id.hobby_third_sub:
                    sh_number_3 = String.valueOf(i);
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

                        Toast.makeText(ProfileActivity.this, "다른 사진을 선택 해주세요.", Toast.LENGTH_SHORT).show();
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
