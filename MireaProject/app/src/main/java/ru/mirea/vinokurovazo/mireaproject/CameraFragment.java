package ru.mirea.vinokurovazo.mireaproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class CameraFragment extends Fragment {

    private ImageView imageView;
    private EditText editTextName, editTextOrganisation, editTextTitle;
    private TextView textViewIntro, textViewFullNotice;
    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == requireActivity().RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("ru/mirea/vinokurovazo/mireaproject/data");
                    imageView.setImageBitmap(imageBitmap);
                } else {
                    Toast.makeText(requireContext(), "Не удалось сделать снимок", Toast.LENGTH_SHORT).show();
                }
            });
    private final ActivityResultLauncher<String> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    launchCamera();
                } else {
                    Toast.makeText(requireContext(), "Для съемки требуется разрешение на использование камеры устройства", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        textViewIntro = view.findViewById(R.id.textViewIntro);
        imageView = view.findViewById(R.id.imageView);
        editTextName = view.findViewById(R.id.editTextName);
        editTextOrganisation = view.findViewById(R.id.editTextOrganisation);
        editTextTitle = view.findViewById(R.id.editTextTitle);
        Button buttonAddImage = view.findViewById(R.id.buttonAddImage);
        Button buttonSave = view.findViewById(R.id.buttonSave);

        buttonAddImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA);
            }
        });

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String organisation = editTextOrganisation.getText().toString().trim();
            String title = editTextTitle.getText().toString().trim();

            if (name.isEmpty() || organisation.isEmpty() || title.isEmpty() ) {
                Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            } else {
                String fullNotice = "ФИО: " + name + "\n" +
                        "Организация: " + organisation + "\n" +
                        "Номер: " + title + "\n";
                textViewFullNotice.setText(fullNotice);
                Toast.makeText(requireContext(), "Контакт создан", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void launchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(takePictureIntent);
    }
}