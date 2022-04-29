package de.morrisbr.reportbooklet.ui.berichte.create;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import de.morrisbr.reportbooklet.core.bericht.Bericht;
import de.morrisbr.reportbooklet.core.bericht.DayBericht;
import de.morrisbr.reportbooklet.core.template.Veriables;
import de.morrisbr.reportbooklet.core.utils.JsonConverter;
import de.morrisbr.reportbooklet.network.Networking;
import de.morrisbr.reportbooklet.ui.berichte.BerichteFragment;

import de.morrisbr.reportbooklet.R;

public class CreateBerichteView extends Activity {
    private TextView content;
    private Networking networking = new Networking();
    private Button send;

    /* Access modifiers changed, original: protected */
    @RequiresApi(0)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_createberichte);
        this.content = (TextView) findViewById(R.id.contentTextView);
        this.send = (Button) findViewById(R.id.save);
        this.content.setSingleLine(false);
        this.send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDateTime dateNow = LocalDateTime.now();
                    LocalDateTime dateEnding = null;
                    dateEnding = LocalDateTime.now().plusWeeks(1);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    String formattedDateNow = dateNow.format(formatter);
                    String formattedDateEnding = dateEnding.format(formatter);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("(");
                    stringBuilder.append(formattedDateNow);
                    stringBuilder.append(" - ");
                    stringBuilder.append(formattedDateEnding);
                    stringBuilder.append(")");
                    Bericht bericht = new Bericht(stringBuilder.toString());
                    DayBericht dayBericht = new DayBericht("test");
                    dayBericht.addTask("task", CreateBerichteView.this.content.getText().toString());
                    bericht.addDay(Veriables.MONDAY, dayBericht);
                    System.out.println(CreateBerichteView.this.content.getText().toString());
                    CreateBerichteView.this.networking.createBericht(JsonConverter.objectToJsonString(bericht));
                    BerichteFragment.instance.refresh();
                    CreateBerichteView.this.finish();
                }
            }
        });
    }
}