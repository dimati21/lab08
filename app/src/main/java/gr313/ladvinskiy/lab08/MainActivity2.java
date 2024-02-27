package gr313.ladvinskiy.lab08;

import static gr313.ladvinskiy.lab08.MainActivity.db;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    EditText txt;
    boolean isForEdit;
    Intent mainIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txt = findViewById(R.id.txtContent);
        txt.setInputType(InputType.TYPE_CLASS_TEXT);
        mainIntent = this.getIntent();
        isForEdit = mainIntent.getBooleanExtra("isForEdit", false);
        if (isForEdit) {
            fillContent();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_save) {
            onSaveIconClick();
        } else {
            onDeleteIconClick();
        }

        return super.onOptionsItemSelected(item);
    }
    public void fillContent() {
        String noteTxt = mainIntent.getStringExtra("noteTxt");
        txt.setText(noteTxt);
    }

    public void onDeleteIconClick() {
        if (isForEdit) {
            deleteNote();
        } else {
            finish();
        }
    }

    public void onSaveIconClick() {
        if (isForEdit) {
            editNote();
        } else {
            createNewNote();
        }
    }
    private void deleteNote(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Вы уверены?");
        dialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int noteId = mainIntent.getIntExtra("noteId", -1);
                db.delete(noteId);
                Toast.makeText(MainActivity.context, "Заметка удалена", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        dialog.setNegativeButton("Нет", null);
        dialog.show();
    }
    public void editNote() {
        String txtStr = txt.getText().toString();
        if (txtStr.isEmpty()) {
            Toast.makeText(MainActivity.context, "Заполните поле!", Toast.LENGTH_SHORT).show();
        }
        else {
            int noteId = mainIntent.getIntExtra("noteId", -1);
            db.update(noteId, txtStr);
            Toast.makeText(MainActivity.context, "Заметка обновлена", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    public void createNewNote() {
        String txtStr = txt.getText().toString();
        if (txtStr.isEmpty()) {
            Toast.makeText(MainActivity.context, "Заполните поле!", Toast.LENGTH_SHORT).show();
            return;
        }
        db.insert(txtStr);
        finish();
    }
}