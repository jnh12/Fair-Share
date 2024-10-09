package com.example.fair_share;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;


public class cameraFragment extends Fragment {
    private PreviewView previewView;
    private ImageCapture imageCapture;
    private static final int REQUEST_CAMERA_PERMISSION = 1001;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        previewView = view.findViewById(R.id.previewView);
        cameraPermissions();
        return view;
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();
        imageCapture = new ImageCapture.Builder().build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
    }

    private void cameraPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    public void captureImageAndExtractText() {
        if (imageCapture != null) {
            imageCapture.takePicture(ContextCompat.getMainExecutor(requireContext()), new ImageCapture.OnImageCapturedCallback() {
                @Override
                public void onCaptureSuccess(@NonNull ImageProxy image) {
                    @SuppressLint("UnsafeExperimentalUsageError")
                    Bitmap bitmap = imageProxyToBitmap(image);
                    InputImage inputImage = InputImage.fromBitmap(bitmap, image.getImageInfo().getRotationDegrees());
                    extractTextFromImage(inputImage);

                    byte[] imageData = bitmapToByteArray(bitmap);
                    sendImageToBackend(imageData);

                    image.close();
                }

                @Override
                public void onError(@NonNull ImageCaptureException exception) {
                    exception.printStackTrace();
                }
            });
        }
    }

    private void sendParsedTextToBackend(String resultText) {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/api/ocr/parseText");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                String jsonInputString = "\"" + resultText + "\"";
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int code = conn.getResponseCode();
                Log.d("Backend TEXT Response", "Code: " + code);

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendTextToBackend(String resultText) {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/api/ocr/saveText");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                String jsonInputString = "\"" + resultText + "\"";
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int code = conn.getResponseCode();
                Log.d("Backend TEXT Response", "Code: " + code);

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendImageToBackend(byte[] resultImage) {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/api/ocr/saveImage");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/octet-stream"); // Set to octet-stream for binary data
                conn.setDoOutput(true);

                // Send the byte array directly
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(resultImage); // Send the byte array
                }

                // Read the response code
                int code = conn.getResponseCode();
                Log.d("Backend IMAGE Response", "Code: " + code);

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    private Bitmap imageProxyToBitmap(ImageProxy image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void extractTextFromImage(InputImage image) {
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        recognizer.process(image)
                .addOnSuccessListener(result -> {
                    String resultText = result.getText();
                    Log.d("OCR Result", resultText);
                    sendTextToBackend(resultText);
                    sendParsedTextToBackend(resultText);

                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                });
    }

    //  private File saveBitmapToFile(Bitmap bitmap) {
//        File imageFile = new File(requireContext().getExternalFilesDir(null), "captured_image.jpg");
//        try (FileOutputStream out = new FileOutputStream(imageFile)) {
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); // Save the bitmap as a JPEG file
//            return imageFile;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
   // public void uploadReceiptImage(File imageFile, OCRCallback callback) {
//        MediaType mediaType = MediaType.parse("image/jpeg");
//
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("api_key", "TEST") // Your API key
//                .addFormDataPart("recognizer", "auto") // Recognizer type
//                .addFormDataPart("ref_no", "ocr_java_123") // Reference number (optional)
//                .addFormDataPart("file", imageFile.getName(), RequestBody.create(mediaType, imageFile))
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://ocr.asprise.com/api/v1/receipt")
//                .post(requestBody)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                // Call the callback with an error message
//                callback.onError(e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String responseBody = response.body().string();
//                    // Call the callback with the result
//                    callback.onSuccess(responseBody);
//                } else {
//                    // Call the callback with an error message
//                    callback.onError("Request failed: " + response.message());
//                }
//            }
//        });
//
}
