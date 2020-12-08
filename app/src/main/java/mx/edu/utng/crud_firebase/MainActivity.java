package mx.edu.utng.crud_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mx.edu.utng.crud_firebase.model.Persona;

public class MainActivity extends AppCompatActivity {

    private List<Persona> listPerson = new ArrayList<Persona>();
    ArrayAdapter<Persona> arrayAdapatarPer;

    EditText nomP, appP, correoP, LPpp, passw;
    ListView listV_Personas;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseRe;

    Persona personaSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomP = findViewById(R.id.etNombre);
        appP = findViewById(R.id.etApellido);
        correoP = findViewById(R.id.etCorreo);
        LPpp = findViewById(R.id.etLPrograma);
        passw = findViewById(R.id.etPsswd);

        listV_Personas = findViewById(R.id.lv_datosPersonas);
        inicializarFirebase();
        listadatos();

        listV_Personas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personaSelected = (Persona) parent.getItemAtPosition(position);
                nomP.setText(personaSelected.getNombre());
                appP.setText(personaSelected.getApellidos());
                correoP.setText(personaSelected.getCorreo());
                LPpp.setText(personaSelected.getLProgramacion());
                passw.setText(personaSelected.getPassword());
            }
        });
    }

    private void listadatos() {
        databaseRe.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                listPerson.clear();
                for(DataSnapshot objSnaptshot : datasnapshot.getChildren()){
                    Persona p = objSnaptshot.getValue(Persona.class);
                    listPerson.add(p);

                    arrayAdapatarPer = new ArrayAdapter<Persona>(MainActivity.this, android.R.layout.simple_list_item_1, listPerson);
                    listV_Personas.setAdapter(arrayAdapatarPer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseRe= firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String nombre = nomP.getText().toString();
        String correo = correoP.getText().toString();
        String password = passw.getText().toString();
        String Apellidos = appP.getText().toString();
        String Lpro = LPpp.getText().toString();

        switch (item.getItemId()){
            case R.id.icon_add:{
                if(nombre.equals("") || correo.equals("") || password.equals("") || Apellidos.equals("") || Lpro.equals("")){
                    validacion();

                }else {
                    Persona p = new Persona();
                    p.setpId(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setApellidos(Apellidos);
                    p.setCorreo(correo);
                    p.setLProgramacion(Lpro);
                    p.setPassword(password);
                    databaseRe.child("Persona").child(p.getpId()).setValue(p);
                    Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
                    limpiarCajas();

                }
                break;
            }
            case R.id.icon_delete:{
                Persona p = new Persona();
                p.setpId(personaSelected.getpId());
                databaseRe.child("Persona").child(p.getpId()).removeValue();

                Toast.makeText(this, "Eliminado", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            case R.id.icon_save:{
                Persona p = new Persona();
                p.setpId(personaSelected.getpId());
                p.setNombre(nomP.getText().toString());
                p.setApellidos(appP.getText().toString());
                p.setCorreo(correoP.getText().toString().trim());
                p.setLProgramacion(LPpp.getText().toString().trim());
                p.setPassword(passw.getText().toString().trim());
                databaseRe.child("Persona").child(p.getpId()).setValue(p);

                Toast.makeText(this, "Actualizado", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            default:break;
        }


        return true;
    }

    private void limpiarCajas() {
        nomP.setText("");
        correoP.setText("");
        passw.setText("");
        LPpp.setText("");
        appP.setText("");
    }

    private void validacion() {
        String nombre = nomP.getText().toString();
        String correo = correoP.getText().toString();
        String password = passw.getText().toString();
        String Apellidos = appP.getText().toString();
        String Lpro = LPpp.getText().toString();

        if(nombre.equals("")){
            nomP.setError("Required");
        }
        if(correo.equals("")){
            correoP.setError("Required");
        }
        if(password.equals("")){
            passw.setError("Required");
        }
        if(Apellidos.equals("")){
            appP.setError("Required");
        }
        if(Lpro.equals("")){
            LPpp.setError("Required");
        }

    }
}