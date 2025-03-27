package fr.upjv.miage2025;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import fr.upjv.miage2025.model.Book;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore DBFireStore;
    private Button connectFireStoreButton;
    private Button pushDataButton;
    private Button pushNamedDocumentButton;
    private Button readDocumentButton;

    private TextView textViewReadDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.connectFireStoreButton = findViewById(R.id.id_button_connect_firestore);
        this.pushDataButton = findViewById(R.id.id_button_push_data);
        this.pushNamedDocumentButton = findViewById(R.id.id_button_push_named_document);
        this.readDocumentButton = findViewById(R.id.id_button_read_doc);
        this.textViewReadDocument = findViewById(R.id.id_textview_read_document);
        connectFireStoreButton.setOnClickListener(
                View -> {
                    this.DBFireStore = FirebaseFirestore.getInstance();
                    Toast.makeText(MainActivity.this,
                            ""+ Objects.nonNull(this.DBFireStore),
                            Toast.LENGTH_LONG).show();
                }
        );
        pushDataButton.setOnClickListener(
                View -> {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("login", "ani@gmail.com");
                    map.put("nickname", "ani");
                    this.DBFireStore
                            .collection("collection_1")
                            .add(map).addOnCompleteListener(
                                    task -> {
                                        Toast.makeText(MainActivity.this,
                                                "Push Data - Complete: " + task.isComplete(),
                                                Toast.LENGTH_LONG).show();
                                    }
                            );
                }
        );
        pushNamedDocumentButton.setOnClickListener(
                View -> {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("c1", "c1");
                    map.put("c2", 2);
                    this.DBFireStore
                            .collection("collection_2")
                            .document()
                            .set(map)
                            .addOnSuccessListener(
                                    task -> {
                                        Toast.makeText(MainActivity.this,
                                                "Push Named Document  - Success: ",
                                                Toast.LENGTH_LONG).show();
                                    }
                            );
                    map.clear();
                    map.put("k1", 1);
                    map.put("k2", 2);
                    this.DBFireStore
                            .collection("collection_2")
                            .document("named_document")
                            .set(map)
                            .addOnCompleteListener(
                                    task -> {
                                        Toast.makeText(MainActivity.this,
                                                "Push Data - Complete: " + task.isComplete(),
                                                Toast.LENGTH_LONG).show();
                                    }
                            );
                    Book l1 = new Book("Le Suicide Français", "Éric Zemmour", 544);
                    Book l2 = new Book("La France n’a pas dit son dernier mot", "Éric Zemmour", 352);
                    Book l3 = new Book("Destin Français", "Éric Zemmour", 500);
                    this.DBFireStore
                            .collection("books")
                            .document(l1.getTitle())
                            .set(l1)
                            .addOnSuccessListener(
                                    task -> {
                                        Toast.makeText(MainActivity.this,
                                                "Book saved : ",
                                                Toast.LENGTH_LONG).show();
                                    }
                            );
                }
        );
        readDocumentButton.setOnClickListener(
                View -> {
                    this.DBFireStore
                            .collection("collection_2")
                            .document("named_document")
                            .get()
                            .addOnCompleteListener(
                                    task -> {
                                        if(task.isComplete())
                                            Toast.makeText(MainActivity.this,
                                                    "Read Document success",
                                                    Toast.LENGTH_LONG).show();
                                        DocumentSnapshot ds = task.getResult();
                                        Map<String, Object> map = ds.getData();
                                        this.textViewReadDocument.append(map.toString()+"\n");
                                    }
                            );
                }
        );
    }
}