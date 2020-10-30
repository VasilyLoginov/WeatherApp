package vasiliyloginov.weatherapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import static vasiliyloginov.weatherapp.secondFragment.PARCEL;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link firstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class firstFragment extends Fragment {

    boolean isExistCoatOfArms ; // Можно ли расположить рядом фрагмент с гербом
    //int currentPosition = 0 ;
    Parcel currentParcel ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public firstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment firstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static firstFragment newInstance(String param1, String param2) {
        firstFragment fragment = new firstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle
            savedInstanceState) {
        super .onViewCreated(view, savedInstanceState);
        initList(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super .onActivityCreated(savedInstanceState);
// Определение, можно ли будет расположить рядом герб в другом фрагменте
                isExistCoatOfArms = getResources().getConfiguration(). orientation == Configuration. ORIENTATION_LANDSCAPE ;
// Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null ) {
// Восстановление текущей позиции.
            //currentPosition = savedInstanceState.getInt( "CurrentCity" , 0 );
            currentParcel = (Parcel) savedInstanceState.getSerializable("CurrentCity");
        } else {
            currentParcel = new Parcel(getResources().getStringArray(R.array.temperature)[0], getResources().getStringArray(R.array.cities)[0]);
        }
// Если можно нарисовать рядом герб, то сделаем это
        if ( isExistCoatOfArms ) {
            showSecondFragment(currentParcel);
        }
    }

    @Override
    public void onSaveInstanceState( @NonNull Bundle outState) {
        outState.putSerializable( "CurrentCity" , currentParcel );
        super .onSaveInstanceState(outState);
    }

    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout)view;
        String[] cities = getResources().getStringArray(R.array. cities );
// В этом цикле создаем элемент TextView,
// заполняем его значениями,
// и добавляем на экран.
// Кроме того, создаем обработку касания на элемент
        for ( int i= 0 ; i < cities. length ; i++){
            String city = cities[i];
            TextView tv = new TextView(getContext());
            tv.setText(city);
            tv.setTextSize( 30 );
            layoutView.addView(tv);
            final int fi = i;
            tv.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // currentPosition = fi ;
                    currentParcel = new Parcel(getResources().getStringArray(R.array. temperature) [fi], getResources().getStringArray(R.array. cities )[ fi ]);
                    showSecondFragment(currentParcel);
                }
            });
        }
    }

    private void showSecondFragment(Parcel parcel) {
        if ( isExistCoatOfArms ) {
            // Проверим, что фрагмент с гербом существует в activity
            secondFragment detail = (secondFragment)
                    getFragmentManager().findFragmentById(R.id.frameForSecondFragment );
                // Если есть необходимость, то выведем герб
           // if (detail == null || detail.getIndex() != currentPosition ) {
            if (detail == null || detail.getParcel().getTemperatureValue() != parcel.getTemperatureValue()) {
                // Создаем новый фрагмент с текущей позицией для вывода герба
                detail = secondFragment. create ( parcel );
                // Выполняем транзакцию по замене фрагмента
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id. frameForSecondFragment , detail); // замена фрагмента
                ft.setTransition(FragmentTransaction. TRANSIT_FRAGMENT_FADE );
                ft.commit();
            }
        } else {
                    // Если нельзя вывести герб рядом, откроем вторую activity
            Intent intent = new Intent();
            intent.setClass(getActivity(), secondActivity. class );
                        // и передадим туда параметры
            //intent.putExtra( "index" , currentPosition );
            intent.putExtra(PARCEL, parcel);
            startActivity(intent);
        }
    }

}