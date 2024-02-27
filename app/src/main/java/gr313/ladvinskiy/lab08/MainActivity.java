package gr313.ladvinskiy.lab08;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {


    ArrayAdapter<Note> adp;
    static DB db;
    ListView notesView;
    @SuppressLint("StaticFieldLeak")
    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adp = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1);
        db = new DB(this, "mynotes.db", null, 1);

        context = this.getApplicationContext();

        notesView = findViewById(R.id.notesView);
        notesView.setAdapter(adp);

        update_list();

        notesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onEditClick(position);
            }
        });
    }
    private void onEditClick(int position) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("isForEdit", true);
        Note currNote = adp.getItem(position);
        int noteId = currNote.id;
        String noteTxt = currNote.txt;
        intent.putExtra("noteId", noteId);
        intent.putExtra("noteTxt", noteTxt);
        startActivityForResult(intent, 222);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.update_list();
    }
    private void update_list() {
        adp.clear();
        adp.addAll(db.getAllNotes());
        adp.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_new) {
            onNewClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onNewClick() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivityForResult(intent, 2);
    }


}