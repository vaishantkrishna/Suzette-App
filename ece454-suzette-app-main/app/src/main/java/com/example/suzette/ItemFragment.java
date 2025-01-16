package com.example.suzette;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ItemFragment extends Fragment {

    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_DESC = "arg_desc";

    private String text;
    private String description;
    PromptRepository prompt = new PromptRepository();


    // Factory method to create a new instance of the fragment with a given text
    public static ItemFragment newInstance(String text, String description) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        args.putString(ARG_DESC, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve the text from arguments
        if (getArguments() != null) {
            text = getArguments().getString(ARG_TEXT);
            description = getArguments().getString(ARG_DESC);
        }

        // Access the views defined in fragment_item.xml
        // and set text or click listener as necessary
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(text);

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click here
                openChat(text);

            }
        });

        Button infoButton = view.findViewById(R.id.infoButton);
        infoButton.setOnClickListener(v -> showInfoDialog());
    }

    private void openChat(String text) {
        // Open another activity here
        String first = prompt.getInitialSuzettePrompt();
        String second = prompt.getRecipePrompt(text);
        Intent intent = new Intent(getActivity(), CookingActivity.class);
        intent.putExtra("firstPrompt", first);
        intent.putExtra("secondPrompt", second);
        startActivity(intent);
    }

    private void showInfoDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Information")
                .setMessage(description)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

}