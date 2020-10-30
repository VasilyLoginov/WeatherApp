package vasiliyloginov.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

public class secondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if (getResources().getConfiguration(). orientation ==
                Configuration. ORIENTATION_LANDSCAPE ) {
// Если устройство перевернули в альбомную ориентацию, то надо эту activity закрыть
            finish();
            return ;
        }

        if (savedInstanceState == null ) {
// Если эта activity запускается первый раз (с каждым новым гербом первый раз)
// то перенаправим параметр фрагменту
            secondFragment details = new secondFragment();
            details.setArguments(getIntent().getExtras());
// Добавим фрагмент на activity
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id. frameLayoutOfSecondActivity , details).commit();
        }
    }


}