package de.morrisbr.reportbooklet.network;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import de.morrisbr.reportbooklet.core.bericht.Bericht;
import de.morrisbr.reportbooklet.core.utils.JsonConverter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Networking {
    public void createBericht(Bericht bericht) {
        System.out.println();
        AndroidNetworking.get((String)"http://192.168.178.107:82/createBericht").addQueryParameter("name", bericht.getTitle()).setTag((Object)"test").setPriority(Priority.MEDIUM).build().getAsString(new StringRequestListener(){

            public void onError(ANError aNError) {
                System.out.println(aNError.getErrorDetail());
            }

            public void onResponse(String string2) {
            }
        });
    }

    public void createBericht(String json) {
        Bericht bericht = (Bericht) JsonConverter.jsonStringToObject(json, Bericht.class);
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create((MediaType)MediaType.parse((String)"application/json; charset=utf-8"), (String)JsonConverter.objectToJsonString(bericht));
        Request request = new Request.Builder().url("http://192.168.178.107:82/createBericht").post(requestBody).build();
        try {
            okHttpClient.newCall(request).execute();
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
    }

    public void editBericht(String name, Bericht bericht) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create((MediaType)MediaType.parse((String)"application/json; charset=utf-8"), (String)JsonConverter.objectToJsonString(bericht));
        Request request = new Request.Builder().url("http://192.168.178.107:82/createBericht").post(requestBody).build();
        try {
            okHttpClient.newCall(request).execute();
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
    }

    public String getBericht(String name) {
        ANResponse aNResponse = AndroidNetworking.get((String)"http://192.168.178.107:82/getBericht").addQueryParameter("name", name).setExecutor((Executor)Executors.newSingleThreadExecutor()).getResponseOnlyFromNetwork().build().executeForString();
        if (aNResponse.isSuccess()) {
            String json = (String)aNResponse.getResult();
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("----------------------------|");
            stringBuilder.append(json);
            printStream.println(stringBuilder.toString());
            return json;
        }
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error!");
        stringBuilder.append((Object)aNResponse.getError());
        printStream.println(stringBuilder.toString());
        return "Not found!";
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<ArrayList<Bericht>> getBerichteAsync() {
        CompletableFuture<ArrayList<Bericht>> completableFuture = new CompletableFuture<>();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Bericht>>() {}.getType();
        AtomicReference<ArrayList<Bericht>> atomicReference = new AtomicReference<>();
        ArrayList<Bericht> berichts = new ArrayList<>();
        @SuppressLint({"NewApi", "LocalSuppress"}) final CompletableFuture<ArrayList<Bericht>> result = new CompletableFuture<>();


        AndroidNetworking.get("http://37.114.47.77:82/getAllBerichte")
                .setTag(this)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {

                    @Override
                    public void onResponse(String responce) {
                        System.out.println("------------------------------------");
                        System.out.println(responce);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            atomicReference.set(gson.fromJson(responce, type));
                            completableFuture.complete(gson.fromJson(responce, type));
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println("------------ERROR------------");
                        System.out.println(anError.getErrorDetail());
                        System.out.println("------------ERROR------------");
                    }
                });
        return completableFuture;
    }


    public void removeBericht(String string2) {
        AndroidNetworking.get((String)"http://192.168.178.107:82/removeBericht").addQueryParameter("name", string2).setTag((Object)this).setPriority(Priority.LOW).build().prefetch();
    }

}







