package com.deadpixel.dpixel.a4student;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Por extends AppCompatActivity {

    BaseDados db = new BaseDados(this);

    ConstraintLayout clfile;
    ListView listFile;
    TextView verPor2;

    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    String nome;

    private static final String TAG = "Por";

    private static final int REQUEST_CODE = 6384; // onActivityResult request
    // code

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    File dir;
    String dirPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_por);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        nome = getIntent().getStringExtra("NOMEP");

        final Portfolio portfolio = db.selectPortfolio(nome);

        Objects.requireNonNull(getSupportActionBar()).setTitle(nome);

        dir = new File(getFilesDir() + File.separator + Integer.toString(portfolio.getIdp()));
        dirPath = getFilesDir() + File.separator + Integer.toString(portfolio.getIdp());
        if (!dir.exists()) {
            dir.mkdir();
            if (portfolio.getNfiles() > 0) {
                db.updatePortfolio(new Portfolio(portfolio.getIdp(), portfolio.getNomep(), 0, ""));
                File dir = new File(dirPath);
                if (dir.isDirectory()) {
                    String[] children = dir.list();
                    for (int i = 0; i < children.length; i++) {
                        new File(dir, children[i]).delete();
                    }
                }
            }
        }

        FloatingActionButton fabAddFile = findViewById(R.id.fabAddFile);
        fabAddFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissionREAD_EXTERNAL_STORAGE(Por.this)) showChooser();
            }
        });

        clfile = findViewById(R.id.clfile);
        listFile = findViewById(R.id.listFile);
        verPor2 = findViewById(R.id.verPor2);

        if (portfolio.getNfiles() != 0) {
            clfile.setVisibility(View.GONE);
            listFiles();
        } else {
            clfile.setVisibility(View.VISIBLE);
        }

        listFile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Portfolio portfolio1 = db.selectPortfolio(nome);
                String[] filenames = portfolio1.getFilenames().split("/0/1/");
                File file = new File(dirPath + "/" + filenames[position]);
                MimeTypeMap map = MimeTypeMap.getSingleton();
                String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
                String type = map.getMimeTypeFromExtension(ext);

                if (type == null)
                   type = "*/*";

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri data1 = FileProvider.getUriForFile(Por.this, BuildConfig.APPLICATION_ID + ".provider", file);

                    intent.setDataAndType(data1, type);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(Por.this, getString(R.string.erro_abrir_ficheiro), Toast.LENGTH_LONG).show();
                }
            }
        });
        listFile.setLongClickable(true);
        listFile.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Portfolio portfolio1 = db.selectPortfolio(nome);
                AlertDialog.Builder builder = new AlertDialog.Builder(Por.this);
                builder.setTitle(getString(R.string.eliminar_ficheiro));
                builder.setMessage(getString(R.string.certeza_del_ficheiro));
                builder.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String[] filenames = portfolio1.getFilenames().split("/0/1/");
                        String[] filenames2 = new String[filenames.length - 1];
                        boolean verp = false;
                        for (int c = 0; c < filenames2.length; c++) {
                            if (!verp) {
                                if (c == position) {
                                    filenames2[c] = filenames[c+1];
                                    verp = true;
                                } else {
                                    filenames2[c] = filenames[c];
                                }
                            } else {
                                filenames2[c] = filenames[c+1];
                            }
                        }
                        String filenamess = "";
                        for (int c = 0; c < filenames2.length; c++) {
                            if (c == 0) {
                                filenamess += filenames2[c];
                            } else {
                                filenamess += "/0/1/" + filenames2[c];
                            }
                        }
                        db.updatePortfolio(new Portfolio(portfolio1.getIdp(), portfolio1.getNomep(), portfolio1.getNfiles() - 1, filenamess));
                        File file = new File(dirPath + File.separator + filenames[position]);
                        try {
                            file.delete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Portfolio portfolio2 = db.selectPortfolio(nome);
                        if (portfolio2.getNfiles() == 0) {
                            clfile.setVisibility(View.VISIBLE);
                        } else {
                            clfile.setVisibility(View.GONE);
                            listFiles();
                        }
                    }
                });
                builder.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {}
                });
                AlertDialog dialog1 = builder.create();
                dialog1.show();
                return true;
            }
        });

        verPor2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                Portfolio portfolio1 = db.selectPortfolio(nome);
                if (portfolio1.getNfiles() != 0) {
                    clfile.setVisibility(View.GONE);
                    listFiles();
                } else {
                    clfile.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            Intent intent = new Intent(Por.this, CEstudo.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Por.this, CEstudo.class);
        startActivity(intent);
        finish();
    }

    public void listFiles() {
        Portfolio portfolio = db.selectPortfolio(nome);
        String[] filenames = portfolio.getFilenames().split("/0/1/");
        arrayList = new ArrayList<>(Arrays.asList(filenames));
        adapter = new ArrayAdapter<>(Por.this, android.R.layout.simple_list_item_1, arrayList);
        listFile.setAdapter(adapter);
    }

    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        Log.i(TAG, "Uri = " + uri.toString());
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(Por.this, uri);
                            if (!path.equals("null")) {
                                //Toast.makeText(Por.this, "File Selected: " + path, Toast.LENGTH_LONG).show();
                                String filename = path.substring(path.lastIndexOf(File.separator) + 1);
                                Portfolio portfolio = db.selectPortfolio(nome);
                                String[] filenames2 = portfolio.getFilenames().split("/0/1/");
                                if ((portfolio.getNfiles() > 1 && Arrays.asList(filenames2).contains(filename)) || (portfolio.getNfiles() == 1 && filename.equals(portfolio.getFilenames()))) {
                                    Toast.makeText(Por.this, getString(R.string.erro_ja_existe_ficheiro), Toast.LENGTH_LONG).show();
                                } else {
                                    String filenames;
                                    if (portfolio.getNfiles() == 0) {
                                        filenames = filename;
                                    } else {
                                        filenames = portfolio.getFilenames() + "/0/1/" + filename;
                                    }
                                    db.updatePortfolio(new Portfolio(portfolio.getIdp(), portfolio.getNomep(), portfolio.getNfiles() + 1, filenames));
                                    listFiles();
                                    clfile.setVisibility(View.GONE);
                                    copyFileOrDirectory(path, dirPath);
                                }
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "File select error", e);
                            Toast.makeText(Por.this, getString(R.string.ficheiro_invalido), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog(getString(R.string.armazenamento_externo), context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle(getString(R.string.permissao_necessaria));
        alertBuilder.setMessage(msg + getString(R.string.permissão_necessaria2));
        alertBuilder.setPositiveButton(getString(R.string.sim),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showChooser();
                } else {
                    Toast.makeText(Por.this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    public static void copyFileOrDirectory(String srcDir, String dstDir) {

        try {
            File src = new File(srcDir);
            File dst = new File(dstDir, src.getName());

            if (src.isDirectory()) {

                String files[] = src.list();
                int filesLength = files.length;
                for (int i = 0; i < filesLength; i++) {
                    String src1 = (new File(src, files[i]).getPath());
                    String dst1 = dst.getPath();
                    copyFileOrDirectory(src1, dst1);

                }
            } else {
                copyFile(src, dst);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }
}
