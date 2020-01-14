package com.soft.pc.precautionsandmedicinesforcommondiseases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.VARCHAR;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener {
        SQLiteDatabase drug;
        EditText editsearchname,editempname,editempmail,editempsalary;
        Button Add, Delete, Modify, View,search ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            db=openOrCreateDatabase("DrugDB", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS drug(id INTEGER PRIMARY KEY
                    AUTOINCREMENT,name VARCHAR,about VARCHAR,use VARCHAR,how VARCHAR,side VARCHAR,precaution VARCHAR,interaction VARCHAR,missed VARCHAR,storage VARCHAR,parent VARCHAR,bookmark VARCHAR,note VARCHAR);");
            editsearchname = (EditText) findViewById(R.id.edtemployeename);
            editempname = (EditText) findViewById(R.id.editText);
            editempmail = (EditText) findViewById(R.id.editText2);
            editempsalary = (EditText) findViewById(R.id.editText3);
            Add = (Button) findViewById(R.id.btnsave);
            Delete= (Button) findViewById(R.id.btndel);
            Modify= (Button) findViewById(R.id.btnupdate);
            View= (Button) findViewById(R.id. btnselect);
            search=(Button) findViewById(R.id. btnselectperticular);
            Add.setOnClickListener(this);
            Delete.setOnClickListener(this);
            Modify.setOnClickListener(this);
            View.setOnClickListener(this);
            search.setOnClickListener(this);
        }

        public void msg(Context context,String str)
        {
            Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btnsave)
            {

                if(editempname.getText().toString().trim().length()==0||
                        editempmail.getText().toString().trim().length()==0||
                        editempsalary.getText().toString().trim().length()==0)
                {
                    msg(this, "Please enter all values");
                    return;
                }
                db.execSQL("INSERT INTO Employee(EmpName,EmpMail,EmpSalary)VALUES('"+ editempname.getText()+"','"+ editempmail.getText()+ "','"+    editempsalary.getText()+"');");
                msg(this, "Record added");
            }

            else if(v.getId()==R.id.btnupdate)
            {
                if(editsearchname.getText().toString().trim().length()==0)
                {
                    msg(this, "Enter Employee  Name");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM Employee WHERE EmpName='"+ editsearchname.getText()+"'", null);
                if(c.moveToFirst()) {
                    db.execSQL("UPDATE Employee  SET EmpName ='"+ editempname.getText()+"', EmpMail='"+ editempmail.getText()+"',EmpSalary='"+      editempsalary.getText()+"' WHERE EmpName ='"+editsearchname.getText()+"'");
                    msg(this, "Record Modified");
                }
                else
                {
                    msg(this, "Invalid Employee Name");
                }
            }
            else if(v.getId()==R.id.btndel)
            {
                //code for delete data
                if(editsearchname.getText().toString().trim().length()==0)
                {
                    msg(this, " Please enter Employee  Name ");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM Employee WHERE EmpName ='"+ editsearchname.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("DELETE FROM Employee WHERE EmpName ='"+ editsearchname.getText()+"'");
                    msg(this, "Record Deleted");
                }
                else
                {
                    msg(this, "Invalid Employee Name ");
                }
            }
            else if (v.getId() == R.id.btnselect)
            {

                Cursor c=db.rawQuery("SELECT * FROM Employee", null);
                if(c.getCount()==0)
                {
                    msg(this, "No records found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(c.moveToNext())
                {
                    buffer.append("Employee Name: "+c.getString(1)+"\n");
                    buffer.append("Employee Mail: "+c.getString(2)+"\n\n");
                    buffer.append("Employee Salary: "+c.getString(3)+"\n\n");
                }
                msg(this, buffer.toString());
            }
            else if(v.getId()==R.id.btnselectperticular)
            {

                if(editsearchname.getText().toString().trim().length()==0)
                {
                    msg(this, "Enter Employee Name");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM Employee WHERE EmpName='"+editsearchname.getText()+"'", null);
                if(c.moveToFirst())
                {
                    editempname.setText(c.getString(1));
                    editempmail.setText(c.getString(2));
                    editempsalary.setText(c.getString(3));
                }
                else
                {
                    msg(this, "Invalid Employee Name");
                }
            }
        }
    }
}