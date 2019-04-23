package com.team8.lower.MyoePat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.team8.lower.MyoePat.adapter.StationAdapter2;
import com.team8.lower.MyoePat.adapter.TrainAdapter;
import com.team8.lower.MyoePat.dbhandler.TrainDatabaseHandler;
import com.team8.lower.MyoePat.model.StationData;
import com.team8.lower.MyoePat.model.Train;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by User on 01/11/2016.
 */
public class TrainListActivityNoon extends AppCompatActivity {
    private TextView toolbarTxt1,toolbarTxt2;
    String path;
    private RecyclerView recyclerView;
    private TrainAdapter trainAdapter;
    private StationAdapter2 stationAdapter;
    private List<Train> trainList;
    private List<StationData> stationdataList;
    private PrefManager prefManager ;
    Toolbar toolbar,statoolbar;
    String[] stationName = {"ရန္ကုန္ဘူတာ", "ဘုရားလမ္း", "လမ္းမေတာ္", "ျပည္လမ္း",
            "ရွမ္းလမ္း", "အလံုလမ္း", "ပန္းလိွဳင္", "ၾကည့္ျမင္တိုင္", "ဟံသာဝတီ", "လွည္းတန္း",
            "ကမာရြတ္ဘူတာ", "သီရိၿမိဳင္", "အုတ္က်င္း", "သမို္င္းဘူတာ", "သမို္င္းၿမိဳ႕သစ္",
            "ႀကိဳ႕ကုန္း", "အင္းစိန္ဘူတာ", "ရြာမ", "ေဖာ့ကန္", "ေအာင္ဆန္း", "ဒညင္းကုန္းဘူတာ", "ေဂါက္ကြင္း",
            "က်ိဳက္ကလဲ့", "မဂၤလာဒံုေစ်း", "မဂၤလာဒံုဘူတာ", "ေဝဘာဂီ", "ဥကၠလာဘူတာ", "ပုရြက္ဆိတ္ကုန္း",
            "ေက်ာက္ေရတြင္း", "တံတားကေလးဘူတာ", "ေရကူး", "ပါရမီ", "ကံဘဲ့ဘူတာ", "ေဘာက္ေထာ္ ", "တာေမြ",
            "ေမတၱာညြန္႔", "မလႊကုန္း", "ပုဇြန္ေတာင္", "နွင္းဆီကုန္း", "သကၤန္းကၽြန္း", "ငမိုးရိပ္", "တိုးေၾကာင္ကေလး",
            "ဒဂံုတကၠသိုလ္", "ရြာသာႀကီး", "စက္မႈွွဇုန္ ( ၂ )", "ေရႊျပည္သာ", "သာဓုကန္", "ေလွာ္ကား", "ကြန္ျပဴတာတကၠသိုလ္",
            "စက္မႈွွဇုန္(၁)", "ေကြ႕မ", "ေအာင္သုခ", "အုတ္ဖိုစု", "ဂ်မား", "ရန္ကုန္အေရွ႕ပိုင္းတကၠသိုလ္"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_list_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        trainList = new ArrayList<>();

        Log.i("TrainListActivityNoon","Index ");
        TrainDatabaseHandler dbhandler = new TrainDatabaseHandler(TrainListActivityNoon.this);
        trainList=dbhandler.getNoon();

        trainAdapter = new TrainAdapter(trainList,getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.train_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(TrainListActivityNoon.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(trainAdapter);

        trainAdapter.setOnClickListener(new CustomClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.i("TrainListActivity","OnitemClick");
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(TrainListActivityNoon.this);
                LayoutInflater inflater = TrainListActivityNoon.this.getLayoutInflater();
                View convertView = inflater.inflate(R.layout.station_main2, null);

                //To work Recyclerview
                stationdataList = new ArrayList<>();

                Log.i(" Path - " +"",trainAdapter.train.getPath());
                path = trainAdapter.train.getPath();
                path = path.substring(1, path.length() - 1);
                Log.i("path", "Result >>" + path);
                String[] pathArr = path.split(",");
//                String first = pathArr[0];
//                String last = pathArr[pathArr.length - 1];

                Log.i("TrainListActivityNoon","Index >> getStations(s) ");
                TrainDatabaseHandler dbhandler = new TrainDatabaseHandler(TrainListActivityNoon.this);
                String s=trainAdapter.train.getTname();
                String s1=trainAdapter.train.convertAMPM(Integer.parseInt(trainAdapter.train.getTime()));
                Log.i("TrainListActivityNoon","User select s "+s);
                stationdataList=dbhandler.getStations(s);

                Log.i("TrainListActivityNoon", "StationDataList " + stationdataList.size());
                recyclerView = (RecyclerView) convertView.findViewById(R.id.my_recycler_view2);
                toolbarTxt1=(TextView)convertView.findViewById(R.id.toolbarText1);
               /* statoolbar=(Toolbar)convertView.findViewById(R.id.statoolbar);
                setSupportActionBar(statoolbar);
                getSupportActionBar().setTitle(s);*/
                toolbarTxt1.setText(s);
                toolbarTxt2 = (TextView) convertView.findViewById(R.id.toolbarText2);
                toolbarTxt2.setText(s1);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TrainListActivityNoon.this);
                stationAdapter = new StationAdapter2(TrainListActivityNoon.this, stationdataList);
                recyclerView.setHasFixedSize(true);
//                RecyclerView.ItemDecoration itemDecoration = new
//                        DividerItemDecoration(TrainListActivity.this, DividerItemDecoration.VERTICAL_LIST);
                recyclerView.setLayoutManager(mLayoutManager);
//                recyclerView.addItemDecoration(itemDecoration);
                recyclerView.setAdapter(stationAdapter);

                alertDialog.setView(convertView);
                alertDialog.create();
                alertDialog.show();
            }
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(trainAdapter);
    }
}
