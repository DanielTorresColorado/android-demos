/*
Demo sacado del Repositorio "A"
https://github.com/google/mediapipe
** Principalmente Demos en JAVA
-- Especificamente el demo de Hand esta en: https://github.com/google/mediapipe/tree/master/mediapipe/examples/android/solutions/hands

Repositorio B
** Principalmente en Kotlin

 */

// Copyright 2021 The MediaPipe Authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.example.hellomediapipev4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.exifinterface.media.ExifInterface;
// ContentResolver dependency
import com.google.mediapipe.formats.proto.LandmarkProto.Landmark;
import com.google.mediapipe.formats.proto.LandmarkProto.NormalizedLandmark;
import com.google.mediapipe.solutioncore.CameraInput;
import com.google.mediapipe.solutioncore.SolutionGlSurfaceView;
import com.google.mediapipe.solutioncore.VideoInput;
import com.google.mediapipe.solutions.hands.HandLandmark;
import com.google.mediapipe.solutions.hands.Hands;
import com.google.mediapipe.solutions.hands.HandsOptions;
import com.google.mediapipe.solutions.hands.HandsResult;
import java.io.IOException;
import java.io.InputStream;

/** Main activity of MediaPipe Hands app. */
public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  private Hands hands;
  // Run the pipeline and the model inference on GPU or CPU.
  private static final boolean RUN_ON_GPU = true;

  private enum InputSource {
    UNKNOWN,
    IMAGE,
    VIDEO,
    CAMERA,
  }
  private InputSource inputSource = InputSource.UNKNOWN;

  // Image demo UI and image loader components.
  private ActivityResultLauncher<Intent> imageGetter;
  private HandsResultImageView imageView;
  // Video demo UI and video loader components.
  private VideoInput videoInput;
  private ActivityResultLauncher<Intent> videoGetter;
  // Live camera demo UI and camera components.
  private CameraInput cameraInput;

  private SolutionGlSurfaceView<HandsResult> glSurfaceView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupStaticImageDemoUiComponents();
    setupVideoDemoUiComponents();
    setupLiveDemoUiComponents();
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (inputSource == InputSource.CAMERA) {
      // Restarts the camera and the opengl surface rendering.
      cameraInput = new CameraInput(this);
      cameraInput.setNewFrameListener(textureFrame -> hands.send(textureFrame));
      glSurfaceView.post(this::startCamera);
      glSurfaceView.setVisibility(View.VISIBLE);
    } else if (inputSource == InputSource.VIDEO) {
      videoInput.resume();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (inputSource == InputSource.CAMERA) {
      glSurfaceView.setVisibility(View.GONE);
      cameraInput.close();
    } else if (inputSource == InputSource.VIDEO) {
      videoInput.pause();
    }
  }

  private Bitmap downscaleBitmap(Bitmap originalBitmap) {
    double aspectRatio = (double) originalBitmap.getWidth() / originalBitmap.getHeight();
    int width = imageView.getWidth();
    int height = imageView.getHeight();
    if (((double) imageView.getWidth() / imageView.getHeight()) > aspectRatio) {
      width = (int) (height * aspectRatio);
    } else {
      height = (int) (width / aspectRatio);
    }
    return Bitmap.createScaledBitmap(originalBitmap, width, height, false);
  }

  private Bitmap rotateBitmap(Bitmap inputBitmap, InputStream imageData) throws IOException {
    int orientation =
        new ExifInterface(imageData)
            .getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
    if (orientation == ExifInterface.ORIENTATION_NORMAL) {
      return inputBitmap;
    }
    Matrix matrix = new Matrix();
    switch (orientation) {
      case ExifInterface.ORIENTATION_ROTATE_90:
        matrix.postRotate(90);
        break;
      case ExifInterface.ORIENTATION_ROTATE_180:
        matrix.postRotate(180);
        break;
      case ExifInterface.ORIENTATION_ROTATE_270:
        matrix.postRotate(270);
        break;
      default:
        matrix.postRotate(0);
    }
    return Bitmap.createBitmap(
        inputBitmap, 0, 0, inputBitmap.getWidth(), inputBitmap.getHeight(), matrix, true);
  }

  /** Sets up the UI components for the static image demo. */
  private void setupStaticImageDemoUiComponents() {
    // The Intent to access gallery and read images as bitmap.
    imageGetter =
        registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
              Intent resultIntent = result.getData();
              if (resultIntent != null) {
                if (result.getResultCode() == RESULT_OK) {
                  Bitmap bitmap = null;
                  try {
                    bitmap =
                        downscaleBitmap(
                            MediaStore.Images.Media.getBitmap(
                                this.getContentResolver(), resultIntent.getData()));
                  } catch (IOException e) {
                    Log.e(TAG, "Bitmap reading error:" + e);
                  }
                  try {
                    InputStream imageData =
                        this.getContentResolver().openInputStream(resultIntent.getData());
                    bitmap = rotateBitmap(bitmap, imageData);
                  } catch (IOException e) {
                    Log.e(TAG, "Bitmap rotation error:" + e);
                  }
                  if (bitmap != null) {
                    hands.send(bitmap);
                  }
                }
              }
            });
    Button loadImageButton = findViewById(R.id.button_load_picture);
    loadImageButton.setOnClickListener(
        v -> {
          if (inputSource != InputSource.IMAGE) {
            stopCurrentPipeline();
            setupStaticImageModePipeline();
          }
          // Reads images from gallery.
          Intent pickImageIntent = new Intent(Intent.ACTION_PICK);
          pickImageIntent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
          imageGetter.launch(pickImageIntent);
        });
    imageView = new HandsResultImageView(this);
  }

  /** Sets up core workflow for static image mode. */
  private void setupStaticImageModePipeline() {
    this.inputSource = InputSource.IMAGE;
    // Initializes a new MediaPipe Hands solution instance in the static image mode.
    hands =
        new Hands(
            this,
            HandsOptions.builder()
                .setStaticImageMode(true)
                .setMaxNumHands(2)
                .setRunOnGpu(RUN_ON_GPU)
                .build());

    // Connects MediaPipe Hands solution to the user-defined HandsResultImageView.
    hands.setResultListener(
        handsResult -> {
          logWristLandmark(handsResult, /*showPixelValues=*/ true);
          imageView.setHandsResult(handsResult);
          runOnUiThread(() -> imageView.update());
        });
    hands.setErrorListener((message, e) -> Log.e(TAG, "MediaPipe Hands error:" + message));

    // Updates the preview layout.
    FrameLayout frameLayout = findViewById(R.id.preview_display_layout);
    frameLayout.removeAllViewsInLayout();
    imageView.setImageDrawable(null);
    frameLayout.addView(imageView);
    imageView.setVisibility(View.VISIBLE);
  }

  /** Sets up the UI components for the video demo. */
  private void setupVideoDemoUiComponents() {
    // The Intent to access gallery and read a video file.
    videoGetter =
        registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
              Intent resultIntent = result.getData();
              if (resultIntent != null) {
                if (result.getResultCode() == RESULT_OK) {
                  glSurfaceView.post(
                      () ->
                          videoInput.start(
                              this,
                              resultIntent.getData(),
                              hands.getGlContext(),
                              glSurfaceView.getWidth(),
                              glSurfaceView.getHeight()));
                }
              }
            });
    Button loadVideoButton = findViewById(R.id.button_load_video);
    loadVideoButton.setOnClickListener(
        v -> {
          stopCurrentPipeline();
          setupStreamingModePipeline(InputSource.VIDEO);
          // Reads video from gallery.
          Intent pickVideoIntent = new Intent(Intent.ACTION_PICK);
          pickVideoIntent.setDataAndType(MediaStore.Video.Media.INTERNAL_CONTENT_URI, "video/*");
          videoGetter.launch(pickVideoIntent);
        });
  }

  /** Sets up the UI components for the live demo with camera input. */
  private void setupLiveDemoUiComponents() {
    Button startCameraButton = findViewById(R.id.button_start_camera);
    startCameraButton.setOnClickListener(
        v -> {
          if (inputSource == InputSource.CAMERA) {
            return;
          }
          stopCurrentPipeline();
          setupStreamingModePipeline(InputSource.CAMERA);
        });
  }

  /** Sets up core workflow for streaming mode. */
  private void setupStreamingModePipeline(InputSource inputSource) {
    this.inputSource = inputSource;
    // Initializes a new MediaPipe Hands solution instance in the streaming mode.
    hands =
        new Hands(
            this,
            HandsOptions.builder()
                .setStaticImageMode(false)
                .setMaxNumHands(2)
                .setRunOnGpu(RUN_ON_GPU)
                .build());
    hands.setErrorListener((message, e) -> Log.e(TAG, "MediaPipe Hands error:" + message));

    if (inputSource == InputSource.CAMERA) {
      cameraInput = new CameraInput(this);
      cameraInput.setNewFrameListener(textureFrame -> hands.send(textureFrame));
    } else if (inputSource == InputSource.VIDEO) {
      videoInput = new VideoInput(this);
      videoInput.setNewFrameListener(textureFrame -> hands.send(textureFrame));
    }

    // Initializes a new Gl surface view with a user-defined HandsResultGlRenderer.
    glSurfaceView =
        new SolutionGlSurfaceView<>(this, hands.getGlContext(), hands.getGlMajorVersion());
    glSurfaceView.setSolutionResultRenderer(new HandsResultGlRenderer());
    glSurfaceView.setRenderInputImage(true);
    hands.setResultListener(
        handsResult -> {
          logWristLandmark(handsResult, /*showPixelValues=*/ false);
          glSurfaceView.setRenderData(handsResult);
          glSurfaceView.requestRender();
        });

    // The runnable to start camera after the gl surface view is attached.
    // For video input source, videoInput.start() will be called when the video uri is available.
    if (inputSource == InputSource.CAMERA) {
      glSurfaceView.post(this::startCamera);
    }

    // Updates the preview layout.
    FrameLayout frameLayout = findViewById(R.id.preview_display_layout);
    imageView.setVisibility(View.GONE);
    frameLayout.removeAllViewsInLayout();
    frameLayout.addView(glSurfaceView);
    glSurfaceView.setVisibility(View.VISIBLE);
    frameLayout.requestLayout();
  }

  private void startCamera() {
    cameraInput.start(
        this,
        hands.getGlContext(),
        CameraInput.CameraFacing.FRONT,
        glSurfaceView.getWidth(),
        glSurfaceView.getHeight());
  }

  private void stopCurrentPipeline() {
    if (cameraInput != null) {
      cameraInput.setNewFrameListener(null);
      cameraInput.close();
    }
    if (videoInput != null) {
      videoInput.setNewFrameListener(null);
      videoInput.close();
    }
    if (glSurfaceView != null) {
      glSurfaceView.setVisibility(View.GONE);
    }
    if (hands != null) {
      hands.close();
    }
  }

  private void logWristLandmark(HandsResult result, boolean showPixelValues) {
    if (result.multiHandLandmarks().isEmpty()) {
      return;
    }
    NormalizedLandmark wristLandmark =
        result.multiHandLandmarks().get(0).getLandmarkList().get(HandLandmark.WRIST);
    // For Bitmaps, show the pixel values. For texture inputs, show the normalized coordinates.
    if (showPixelValues) {
      int width = result.inputBitmap().getWidth();
      int height = result.inputBitmap().getHeight();
      Log.i(
          TAG,
          String.format(
              "MediaPipe Hand wrist coordinates (pixel values): x=%f, y=%f",
              wristLandmark.getX() * width, wristLandmark.getY() * height));
    } else {
      Log.i(
          TAG,
          String.format(
              "MediaPipe Hand wrist normalized coordinates (value range: [0, 1]): x=%f, y=%f",
              wristLandmark.getX(), wristLandmark.getY()));
    }
    if (result.multiHandWorldLandmarks().isEmpty()) {
      return;
    }
    Landmark wristWorldLandmark =
        result.multiHandWorldLandmarks().get(0).getLandmarkList().get(HandLandmark.WRIST);
    Log.i(
        TAG,
        String.format(
            "MediaPipe Hand wrist world coordinates (in meters with the origin at the hand's"
                + " approximate geometric center): x=%f m, y=%f m, z=%f m",
            wristWorldLandmark.getX(), wristWorldLandmark.getY(), wristWorldLandmark.getZ()));
  }
}
