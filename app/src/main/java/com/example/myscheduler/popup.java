package com.example.myscheduler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class popup extends AppCompatDialogFragment {

    private EditText password;
    private popuplistener poplisten;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.popup,null);

        builder.setView(v).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Verify", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String pass = password.getText().toString();
                poplisten.sendpassword(pass);
            }
        });

        password = v.findViewById(R.id.password);
        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            poplisten = (popuplistener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"implement listener");
        }
    }
    public interface popuplistener{
        void sendpassword(String password);
    }
}
