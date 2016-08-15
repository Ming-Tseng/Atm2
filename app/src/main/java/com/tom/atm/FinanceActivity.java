package com.tom.atm;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class FinanceActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(FinanceActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        MyDBHelper myDBHelper = new MyDBHelper(this, "expense.db", null, 1);
        list = (ListView) findViewById(R.id.listView);
        Cursor c = myDBHelper.getReadableDatabase()
                .query("exp", null, null, null, null, null, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                c,
                new String[] {"info", "amount"},
                new int[] {android.R.id.text1, android.R.id.text2},
                0
        );
        list.setAdapter(adapter);
    }

}
