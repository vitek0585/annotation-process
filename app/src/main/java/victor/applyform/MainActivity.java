package victor.applyform;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    ViewHolder v;
    ViewModel vm = new ViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v = new ViewHolder();
        v.init(this.getWindow().getDecorView());

        vm.Name = "Viteckeh";
        vm.Date = 12123123l;

        ViewModel.Address a = new ViewModel.Address();
        a.street = "Levobereg";
        vm.address = a;

        ViewModel.Address a1 = new ViewModel.Address();
        a1.street = "Nivki";

        ViewModel.Address a2 = new ViewModel.Address();
        a2.street = "Sevastop";

        vm.addresses.add(a1);
        vm.addresses.add(a2);

        v.set(vm);
        findViewById(R.id.save).setOnClickListener(view -> {
            ViewModel data = v.getData();
            Log.i("Tag", new Gson().toJson(data));
            String name = data.Name;
        });

    }

}
