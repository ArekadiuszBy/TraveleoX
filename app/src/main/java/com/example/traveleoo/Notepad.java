package com.example.traveleoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traveleoo.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class Notepad extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fabButton;

    //  FIREBASE
 private DatabaseReference mDatabase;

    //Progress Dialog

    private ProgressDialog mDialog;

    //Recycler
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);

        toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Note Book");

        //  FIREBASE
        mDatabase= FirebaseDatabase.getInstance().getReference().child("All Note");

        mDialog=new ProgressDialog(Notepad.this);

        //Recycler view
        recyclerView=findViewById(R.id.recyclerId);
        LinearLayoutManager layoutManager=new LinearLayoutManager(Notepad.this);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        fabButton=findViewById(R.id.addbtn);

        fabButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                inputData();
            }
        });
    }


    private void inputData(){
        final AlertDialog.Builder mydialog = new AlertDialog.Builder(Notepad.this);

        LayoutInflater inflater =LayoutInflater.from(Notepad.this);

        View myview=inflater.inflate(R.layout.inputitem, null);

        mydialog.setView(myview);

        final AlertDialog dialog=mydialog.create();

        final EditText title=myview.findViewById(R.id.title);
        final EditText note=myview.findViewById(R.id.note);
        Button btnSave=myview.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mTitle=title.getText().toString().trim();
                String mNote=note.getText().toString().trim();

                if(TextUtils.isEmpty(mTitle)) {
                    title.setError("Required Field..");
                    return;
                }
                if (TextUtils.isEmpty(mNote)){
                    note.setError("Reqired Field..");
                    return;

                }

                mDialog.setMessage("Processing..");
                mDialog.show();

                String mDate= DateFormat.getDateInstance().format(new Date());
                String id=mDatabase.push().getKey();

                //Insert data
                Data data=new Data(mTitle,mNote,id,mDate);
                mDatabase.child(id).setValue(data);
                Toast.makeText(getApplicationContext(),"Data Inserted", Toast.LENGTH_LONG).show();

                mDialog.dismiss();      //Zamykanie ikonki przetwarzania
                dialog.dismiss();

            }
        });

        dialog.show();
    }

        //Wyświetlanie notatek
    @Override
    protected void onStart() {
        super.onStart();

//        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>(
//        )
//                .setQuery(mDatabase, Data.class).build();
//
//        FirebaseRecyclerAdapter<Data, RecyclerView.ViewHolder> firebaseRecyclerAdapter =
//                new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position, @NonNull Data model) {
//
//                        viewHolder.setTitle(model.getTitle());
//                        viewHolder.setNote(model.getNote());
//                        viewHolder.setDate(model.getDate());
//                    }
//
//                    @NonNull
//                    @Override
//                    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        View view = LayoutInflater.from(parent.getContext()).inflate();
//                        return new MyViewHolder(view);
//                    }
//                };
//
//        firebaseRecyclerAdapter.startListening();
//        recyclerView.setAdapter(firebaseRecyclerAdapter);


        //Sposób drugi:

//        FirebaseRecyclerAdapter<Data, MyViewHolder>recyclerAdapter=new FirebaseRecyclerAdapter<Data, MyViewHolder>
//                (
//                        Data.class,
//                        R.layout.inputitem2,
//                        MyViewHolder.class,
//                        mDatabase
//                ) {
//            @NonNull
//            @Override
//            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return null;
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Data model) {
//
//                holder.setTitle(model.getTitle());
//                holder.setNote(model.getNote());
//                holder.setDate(model.getDate());
//            }
//        };
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setTitle(String title) {
            TextView mTitle=mView.findViewById(R.id.item_title);
            mTitle.setText(title);
        }

        public void setNote(String note) {
            TextView mNote=mView.findViewById(R.id.item_note);
            mNote.setText(note);
        }

        public void setDate(String date){
            TextView mDate=mView.findViewById(R.id.item_date);
            mDate.setText(date);
        }
    }

}
