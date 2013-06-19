package com.goodhearted.smokebegone;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	SmokeDataSource DAO;
	TextView tv, tvdate, tvsince;
	Button plus, minus, info;
	
	MenuHandler handler;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        setContentView(R.layout.activity_main);
        
        readyMenu();
        
        DAO = new SmokeDataSource(this);
        plus = (Button) findViewById(R.id.plusbutton);
        plus.setOnClickListener(this);
        minus = (Button) findViewById(R.id.minusbutton);
        minus.setOnClickListener(this);
        info = (Button) findViewById(R.id.info);
        info.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.tvSMOKES);
        tvdate = (TextView) findViewById(R.id.tvDATE);
        tvsince = (TextView) findViewById(R.id.tvSINCE);
        updateTV();
        updateTVDATE();
    }
    
    public boolean onOptionsItemSelected(MenuItem item){
        SlideHolder x = (SlideHolder) findViewById(R.id.bla);
        x.toggle();
        return true;

    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()) {
		case R.id.plusbutton:
			DAO.createSmoke();
			break;
		case R.id.minusbutton:
			if(DAO.getTotalSmokes() > 0) {
				DAO.removeLastSmoke(DAO.getLastSmoke());
			}
			break;
		case R.id.info:
			Intent i = new Intent(this, Info_Activity.class);
			this.startActivity(i);
			break;
		}
		
		updateTV();
		updateTVDATE();
	}
	
	private void updateTV() {
		int total = DAO.getTotalSmokes();
		tv.setText("Total smokes: " + total);
	}
	private void updateTVDATE() {
		if(DAO.getTotalSmokes() < 1) {
			tvdate.setText("None smokes yet");
		} else {
			Smoke x = DAO.getLastSmoke();
			Date y = x.getDateDate();
			Date z = new Date();
			long g = z.getTime() - y.getTime();
			tvdate.setText("last smoke was at: " + y.toGMTString());
			tvsince.setText("since: " + g);
		}
	}
	
	private void readyMenu() {
		handler = new MenuHandler(this);
		for(int i = 0; i < MenuHandler.allitems.length; i ++) {
			findViewById(MenuHandler.allitems[i]).setOnClickListener(handler);
		}
	}
}
