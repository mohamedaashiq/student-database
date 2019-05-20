package com.example.aashiqmohamed.sqllite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editId,editName,editEmail,editCC;
    Button mAdd,mView,mUpdate,mDelete,mViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        editId = findViewById(R.id.editText_id);
        editName = findViewById(R.id.editText_name);
        editEmail = findViewById(R.id.editText_email);
        editCC = findViewById(R.id.editText_CC);

        mAdd = findViewById(R.id.button_add);
        mView = findViewById(R.id.button_view);
        mUpdate = findViewById(R.id.button_update);
        mDelete = findViewById(R.id.button_delete);
        mViewAll = findViewById(R.id.button_viewAll);

//        showMessage("TEST","Testing Done !");
        AddData();
        getData();
        getAllData();
        updateData();
        deleteData();

    }

    public void AddData(){

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                String courseCount = editCC.getText().toString();

                if (name.equals(String.valueOf(""))){
                    editName.setError("Enter name");
                    return;
                }

                if (email.equals(String.valueOf(""))){
                    editEmail.setError("Enter email");
                    return;
                }

                if (courseCount.equals(String.valueOf(""))){
                    editCC.setError("Enter course count");
                    return;
                }

                boolean isInserted = myDb.insertData(name,email,courseCount);

                if (isInserted == true){
                    Toast.makeText(MainActivity.this,"Data inserted succesfully",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                    }

            }
        });

    }

    public void getData(){

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = editId.getText().toString();

                if (id.equals(String.valueOf(""))) {
                    editId.setError("Enter id");
                    return;
                }

                Cursor cursor = myDb.getData(id);
                String data = null;

                if (cursor.moveToNext()) {
                    data = "ID: " + cursor.getString(0) + "\n" +
                                "Name: " + cursor.getString(1) + "\n" +
                                "Email: " + cursor.getString(2) + "\n" +
                                "Course Count: " + cursor.getString(3);
                    }
                    showMessage("DATA", data);


                }

        });

    }

    public void getAllData(){

        mViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDb.getAllData();

                if (cursor.getCount()==0){
                    Toast.makeText(MainActivity.this,"No data",Toast.LENGTH_SHORT).show();
                    return;
                }

               StringBuffer buffer = new StringBuffer();
               while (cursor.moveToNext()){
                   buffer.append("ID "+cursor.getString(0)+"\n");
                   buffer.append("NAME: "+cursor.getString(1)+"\n");
                   buffer.append("EMAIL: "+cursor.getString(2)+"\n");
                   buffer.append("Course Count: "+cursor.getString(3)+"\n\n");
               }
               showMessage("DATA",buffer.toString());
            }
        });

    }

    public void updateData(){

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editId.getText().toString().equals(String.valueOf(""))){
                    editId.setError("Enter id");
                    return;
                }

                if (editName.getText().toString().equals(String.valueOf(""))){
                    editName.setError("Enter name");
                    return;
                }

                if (editEmail.getText().toString().equals(String.valueOf(""))){
                    editEmail.setError("Enter name");
                    return;
                }

                if (editCC.getText().toString().equals(String.valueOf(""))){
                    editCC.setError("Enter name");
                    return;
                }

                boolean isInserted = myDb.updateData(editId.getText().toString(),editName.getText().toString(),editEmail.getText().toString(),editCC.getText().toString());

                if (isInserted==true){
                    Toast.makeText(MainActivity.this,"Data updated succesfully",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void deleteData(){

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer deletedRow = myDb.deleteData(editId.getText().toString());

                if (deletedRow > 0){
                    Toast.makeText(MainActivity.this,"Deletion success",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void showMessage(String title,String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

}
