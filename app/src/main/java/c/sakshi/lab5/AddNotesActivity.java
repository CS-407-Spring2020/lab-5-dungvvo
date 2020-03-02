package c.sakshi.lab5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class AddNotesActivity extends AppCompatActivity {
    int noteid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_notes);
        EditText noteEditText = (EditText) findViewById(R.id.noteEditText);
        Intent intent = getIntent();

        noteid = intent.getIntExtra("noteid",-1);

        if (noteid != -1) {
            // display content by taking note from 2nd activity
            Note note = NotesActivity.notes.get(noteid);
            String noteContent = note.getContent();
            noteEditText.setText(noteContent);
        }
    }

    public void onClickSaveButton(View view) {
        // 1. Get editText view and the content that the user entered
        EditText noteEditText = findViewById(R.id.noteEditText);
        String content = noteEditText.getText().toString();

        // 2. Init SQLiteDatabase Instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        // 3. Init DBHelper class
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        // 4. Set username in the following variable by fetching it from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");

        // 5. Save information to DB
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if(noteid == -1) { // Add note
            title = "NOTE_" + (NotesActivity.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else { // Update note
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(title, date, content);
        }

        // 6. Go to second activity using intents
        goToNotesActivity();
    }

    public void goToNotesActivity() {
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
    }

}
