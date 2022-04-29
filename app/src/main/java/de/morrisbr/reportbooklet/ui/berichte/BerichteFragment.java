package de.morrisbr.reportbooklet.ui.berichte;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import de.morrisbr.reportbooklet.MainActivity;
import de.morrisbr.reportbooklet.core.bericht.Bericht;
import de.morrisbr.reportbooklet.databinding.FragmentBerichteBinding;
import de.morrisbr.reportbooklet.listener.DoubleClickListener;
import de.morrisbr.reportbooklet.network.Networking;
import de.morrisbr.reportbooklet.ui.berichte.create.CreateBerichteView;
import de.morrisbr.reportbooklet.ui.berichte.view.BerichteContentView;

public class BerichteFragment extends Fragment {

    public static BerichteFragment instance;

    private BerichteViewModel homeViewModel;
    private FragmentBerichteBinding binding;
    private ListView listView;
    private Networking networking = new Networking();
    private ImageButton button;

    public BerichteFragment() {}


    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        instance = this;

        homeViewModel = new ViewModelProvider(this).get(BerichteViewModel.class);

        binding = FragmentBerichteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textBerichte;
        listView = binding.listview;
        button = binding.addBerichtButton;

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //    textView.setText(s);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openActivity(CreateBerichteView.class);
            }
        });




        ArrayList<String> arrayList = new ArrayList<>();
        networking.getBerichteAsync().thenAccept(berichts -> {
            for (Bericht bericht: berichts) {
                arrayList.add(bericht.getTitle());
                System.out.println(bericht.getTitle());
                bericht.save();
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(arrayAdapter);
            registerListender(arrayAdapter, arrayList);
        });

        //networking.getBerichteAsync().thenAccept(berichts -> berichts.forEach(bericht -> System.out.println("bericht: " + bericht)));
        //arrayList.add("test");





        //new Networking().getBericht("test");

        return root;
    }


    public void registerListender(ArrayAdapter arrayAdapter, ArrayList<String> arrayList) {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final String item = (String) parent.getItemAtPosition(position).toString();

                view.animate().setDuration(500).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                arrayList.remove(item);
                                arrayAdapter.notifyDataSetChanged();
                                view.setAlpha(1f);
                                networking.removeBericht(item);
                            }
                        });
                return true;
            };

        });




        listView.setOnItemClickListener(new DoubleClickListener() {
            @Override
            public void onSingleClick(AdapterView<?> adapterView, View view, int position, long l) {

            }

            @Override
            public void onDoubleClick(AdapterView<?> adapterView, View view, int position, long l) {

                final String item = (String) adapterView.getItemAtPosition(position).toString();
                MainActivity.instance.openViewWhifObject(BerichteContentView.class, "name", item);
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void refresh() {
        ArrayList<String> arrayList = new ArrayList<>();
        networking.getBerichteAsync().thenAccept(berichts -> {
            for (Bericht bericht: berichts) {
                arrayList.add(bericht.getTitle());
                System.out.println(bericht.getTitle());
                bericht.save();
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(arrayAdapter);
            registerListender(arrayAdapter, arrayList);
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void openActivity(Class viewClass) {
        MainActivity.instance.openView(viewClass);
    }
}