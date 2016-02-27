package com.example.chini.myapplication2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    String[] NameList = {"","","","",""};
    String[] DescList = {"","","","",""};
    String[] imageURLList = {"","","","",""};







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_main);
        Firebase.setAndroidContext(this);
       final ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(new myAdapter(this, NameList, DescList, imageURLList));
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        Firebase myFirebaseRef = new Firebase("https://famiytree.firebaseio.com/");

        myFirebaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String id = dataSnapshot.getKey();
                Integer position = Integer.parseInt(id);
                String Name = (String) dataSnapshot.child("Name").getValue();
                String Desc = (String) dataSnapshot.child("Desc").getValue();
                String imageURL = (String) dataSnapshot.child("imageURL").getValue();
                NameList[position] = Name;
                DescList[position] = Desc;
                imageURLList[position] = imageURL;
                ( (myAdapter)list.getAdapter()).notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String id = dataSnapshot.getKey();
                Integer position = Integer.parseInt(id);
                String Name = (String) dataSnapshot.child("Name").getValue();
                String Desc = (String) dataSnapshot.child("Desc").getValue();
                String imageURL = (String) dataSnapshot.child("imageURL").getValue();
                NameList[position] = Name;
                DescList[position] = Desc;
                imageURLList[position] = Desc;
                ( (myAdapter)list.getAdapter()).notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public  class myAdapter extends ArrayAdapter<String> {
        Context context;
        String[] NameList;
        String[] DescList;
        String[] imageURLList;


        public myAdapter(Context context, String[] NameList, String[] DescList, String[] imageURLList) {
            super(context, R.layout.activity_main, NameList);
            this.context = context;
            this.NameList = NameList;
            this.DescList = DescList;
            this.imageURLList = imageURLList;
        }
       @Override
       public View getView(int position, View convertView, ViewGroup parent) {
           View rowView = convertView;
           if (rowView == null) {
               LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               rowView = inflater.inflate(R.layout.activity_main, parent, false);
               TextView textView = (TextView) rowView.findViewById(R.id.t1);
               TextView textView2 = (TextView) rowView.findViewById(R.id.textView2);
               ImageView imgv = (ImageView) rowView.findViewById(R.id.imageView2);
               rowView.setTag(new ViewContainer(textView, textView2, imgv));
               return rowView;
           }


           ViewContainer v = (ViewContainer) rowView.getTag();
           v.nameView.setText(NameList[position]);
           v.descView.setText(DescList[position]);

          if(imageURLList[position]!="") Picasso.with(context)
                   .load(imageURLList[position])
                   .into(v.imageView);

           return rowView;
       }

       class ViewContainer {
           TextView nameView;
           TextView descView;
           ImageView imageView;

           ViewContainer(TextView nameView,
                         TextView descView,
                         ImageView imageView) {
               this.descView = descView;
               this.imageView = imageView;
               this.nameView = nameView;
           }
       }
}

        }

