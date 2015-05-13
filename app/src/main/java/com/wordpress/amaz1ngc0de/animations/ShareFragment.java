package com.wordpress.amaz1ngc0de.animations;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment {

    private Button btnShareGeneric;

    public ShareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        btnShareGeneric = (Button) view.findViewById(R.id.btn_share_generic);

        btnShareGeneric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");

                //Just for joking! =D
                intent.putExtra(Intent.EXTRA_TEXT, "https://www.pudim.com.br");
                startActivity(Intent.createChooser(intent, "Share"));
            }
        });

        return view;
    }


}
