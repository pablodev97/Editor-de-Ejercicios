package com.pjimeno97.exerciseeditortool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ExerciseAdapter.ExerciseAdapterCallback{

    @BindView(R.id.recycler_view_exercises)
    RecyclerView rvExercises;   // Recycler view con los ejercicios
    @BindView(R.id.btn_add_exercise)
    Button btnAddExercise;      // Botón para añadir nuevos ejercicios
    @BindView(R.id.btn_json)
    Button btnShowJSON;         // Botón para mostrar el código del JSON
    @BindView(R.id.tv_json)
    TextView tvJSON;            // Texto para pintar el JSON

    private ExerciseAdapter adapter;        // Adaptador de los ejercicios
    private ArrayList<Exercise> exercises;  // Ejercicios

    private Dialog myDialog;    // Popup

    static {
        System.loadLibrary("Controller_JNI");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        myDialog = new Dialog(this);

        loadJson("/Users/PcCom/Downloads/appTest.txt");

        exercises = getCppExercises();

        setUI();
    }

    /**
     * Setter de la interfaz de usuario.
     */
    private void setUI()
    {
        showExercises();

        btnShowJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvJSON.getVisibility() == View.GONE)
                {
                    tvJSON.setVisibility(View.VISIBLE);
                }
                else{
                    tvJSON.setVisibility(View.GONE);
                }
            }
        });

        btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupExercise();
            }
        });
    }

    /**
     * Función que muestra los ejercicios en el recycler view.
     */
    public void showExercises()
    {
        adapter = new ExerciseAdapter(exercises, getApplicationContext(), this);
        rvExercises.setAdapter(adapter);
        rvExercises.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        tvJSON.setText(printExercises());
    }

    /**
     * Función que muestra una ventana emergente para añadir los valores de un nuevo ejercicio.
     */
    public void showPopupExercise()
    {
        final EditText etName;
        final EditText etDescription;
        final EditText etUrlVideo;
        final EditText etMuscle;

        Button btnAdd;
        Button btnClose;

        myDialog.setContentView(R.layout.activity_popup_exercise);
        etName = (EditText) myDialog.findViewById(R.id.et_exercise_name);
        etDescription = (EditText) myDialog.findViewById(R.id.et_exercise_description);
        etUrlVideo = (EditText) myDialog.findViewById(R.id.et_exercise_url);
        etMuscle = (EditText) myDialog.findViewById(R.id.et_exercise_muscle);

        btnAdd = (Button) myDialog.findViewById(R.id.btn_add_new_exercise);
        btnClose = (Button) myDialog.findViewById(R.id.btn_close);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exercise exercise = new Exercise(etName.getText().toString(), etDescription.getText().toString(), etUrlVideo.getText().toString(), etMuscle.getText().toString());
                addExercise(exercise);
                exercises.clear();
                exercises = (ArrayList<Exercise>) getCppExercises().clone();
                showExercises();
                myDialog.dismiss();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    /**
     * Función que muestra una ventana emergente para editar los valores de un ejercicio seleccionado.
     * @param exercise Ejercicio a editar.
     * @param position Posición del ejercicio.
     */
    public void showPopupExercise(Exercise exercise, final int position)
    {
        final EditText etName;
        final EditText etDescription;
        final EditText etUrlVideo;
        final EditText etMuscle;

        Button btnAdd;
        Button btnClose;

        myDialog.setContentView(R.layout.activity_popup_exercise);
        etName = (EditText) myDialog.findViewById(R.id.et_exercise_name);
        etName.setText(exercise.getName());
        etDescription = (EditText) myDialog.findViewById(R.id.et_exercise_description);
        etDescription.setText(exercise.getDescription());
        etUrlVideo = (EditText) myDialog.findViewById(R.id.et_exercise_url);
        etUrlVideo.setText(exercise.getUrlVideo());
        etMuscle = (EditText) myDialog.findViewById(R.id.et_exercise_muscle);
        etMuscle.setText(exercise.getMuscle());

        btnAdd = (Button) myDialog.findViewById(R.id.btn_add_new_exercise);
        btnClose = (Button) myDialog.findViewById(R.id.btn_close);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exercise exercise = new Exercise(etName.getText().toString(), etDescription.getText().toString(), etUrlVideo.getText().toString(), etMuscle.getText().toString());
                editExercise(position, exercise);
                exercises.clear();
                exercises = (ArrayList<Exercise>) getCppExercises().clone();
                showExercises();
                myDialog.dismiss();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    /************************  Native methods  **********************/
    /**
     * A native methods that are implemented by the 'Controller_JNI' native library,
     * which is packaged with this application.
     */

    /**
     * Función que carga un archivo JSON para mostrar los ejercicios.
     * @param javaPath Ruta al archivo.
     */
    public native void loadJson(String javaPath);

    /**
     * Función que devuelve un string del archivo JSON.
     * @return
     */
    public native String printExercises();

    /**
     * Función que devuelve un array list con los ejercicios del json.
     * @return
     */
    public native ArrayList<Exercise> getCppExercises();

    /**
     * Función que añade un nuevo ejercicio al json.
     * @param exercise
     */
    public native void addExercise(Exercise exercise);

    /**
     * Función que edita un ejercicio en concreto del json.
     * @param position Posición del ejercicio dentro del json.
     * @param exercise Valores nuevos del ejercicio.
     */
    public native void editExercise(int position, Exercise exercise);

    /**
     * Función que elimina un ejercicio en concreto del json.
     * @param position Posición del ejercicio dentro del json.
     */
    public native void deleteExercise(int position);


    /************************  Exercise Adapter Callback **********************/

    /**
     * Función que es llamada cuando el usuario pulsa el botón 'Delete' del ejercicio.
     * @param position
     */
    @Override
    public void onDeleteClicked(int position) {
        deleteExercise(position);
        exercises.clear();
        exercises = (ArrayList<Exercise>) getCppExercises().clone();
        showExercises();
    }

    /**
     * Función que es llamada cuando el usuario pulsa el botón 'Edit' del ejercicio.
     * @param position
     */
    @Override
    public void onEditClicked(int position) {
        Exercise exercise = exercises.get(position);
        showPopupExercise(exercise, position);
    }
}
