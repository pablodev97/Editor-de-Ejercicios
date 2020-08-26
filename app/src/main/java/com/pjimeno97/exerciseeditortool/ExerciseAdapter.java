package com.pjimeno97.exerciseeditortool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private ArrayList<Exercise> exercisesViewModels; // Exercises that will be shown
    private Context context;                         // Handle to the system
    private ExerciseAdapterCallback listener;        // Callback to MyTrainingFragment

    /**
     * Constructor of the adapter.
     * @param exercisesViewModels Exercises that you want to show.
     * @param context Handle to the system.
     * @param listener Callback to TrainingDetailFragment.
     */
    public ExerciseAdapter(ArrayList<Exercise> exercisesViewModels, Context context, ExerciseAdapterCallback listener) {
        if(exercisesViewModels == null){
            this.exercisesViewModels = null;
        }else{
            this.exercisesViewModels = exercisesViewModels;
        }
        this.context = context;
        this.listener = listener;
    }

    /**
     * Methods that are implemented in TrainingDetailFragment.
     */
    public interface ExerciseAdapterCallback {
        void onDeleteClicked(int position);
        void onEditClicked(int position);
    }

    /**
     * Method called when RecyclerView needs a new RecyclerView.
     * @param parent
     * @param viewType
     * @return The new RecyclerView.
     */
    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        // The view of the exercise
        View itemView = LayoutInflater.from(context).inflate(R.layout.exercise_item, parent, false);

        return new ExerciseViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The exercise display.
     * @param position The position of the exercise in exercisesViewModels.
     */
    @Override
    public void onBindViewHolder(final ExerciseViewHolder holder, final int position) {
        final Exercise exerciseViewModel = exercisesViewModels.get(position);

        // Setter of the all the variables with the exercise given
        holder.tvNameTraining.setText(exerciseViewModel.getName());
        holder.tvDescription.setText("Descripción: " + exerciseViewModel.getDescription());
        holder.tvUrlVideo.setText("Url: " + exerciseViewModel.getUrlVideo());
        holder.tvMuscle.setText("Músculo: " + exerciseViewModel.getMuscle());

        // On click method to navigate to the exercise description
        holder.holder_adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.isClicked)
                {
                    holder.showButtons();
                }
                else
                {
                    holder.hideButtons();
                }
            }
        });

        // On click listener to delete the exercise
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClicked(position);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditClicked(position);
            }
        });

        holder.hideButtons();
    }

    /**
     * Getter of the number of exercises.
     * @return Number of exercises.
     */
    @Override
    public int getItemCount() {
        return exercisesViewModels == null ? 0 : exercisesViewModels.size();
    }

    /**
     * Class that holds all the information of the exercise item.
     */
    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_name_exercise)
        TextView tvNameTraining;                // Name of the exercise
        @BindView(R.id.layout_holder_adapter)
        LinearLayout holder_adapter;            // Layout that holds all the widgets
        @BindView(R.id.tv_exercise_description)
        TextView tvDescription;                 // Text with the exercise description
        @BindView(R.id.tv_url_video_exercise)
        TextView tvUrlVideo;                    // Text with the url video
        @BindView(R.id.tv_muscle_exercise)
        TextView tvMuscle;                      // Text with the muscle

        @BindView(R.id.layout_holder_buttons)
        LinearLayout holderButtons;             // Layout that holds the buttons
        @BindView(R.id.delete_button)
        Button btnDelete;                       // Button to delete the exercise
        @BindView(R.id.edit_button)
        Button btnEdit;                         // Button to edit the exercise

        boolean isClicked = false;              // Flag to know if the exercise has been clicked

        /**
         * Constructor that binds the view.
         * @param itemView
         */
        public ExerciseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Method to change the visibility to GONE.
         */
        @SuppressLint("RestrictedApi")
        public void hideButtons()
        {
            isClicked = false;
            holderButtons.setVisibility(View.GONE);
        }

        /**
         * Method to change the visibility to VISIBLE.
         */
        @SuppressLint("RestrictedApi")
        public void showButtons()
        {
            isClicked = true;
            holderButtons.setVisibility(View.VISIBLE);
        }
    }
}
