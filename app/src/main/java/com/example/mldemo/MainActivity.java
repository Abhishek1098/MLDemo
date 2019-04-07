package com.example.mldemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.test);

        FirebaseVisionImage fimage = FirebaseVisionImage.fromBitmap(image);
        FirebaseVisionDocumentTextRecognizer detector = FirebaseVision.getInstance().getCloudDocumentTextRecognizer();

        detector.processImage(fimage)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionDocumentText>() {
                    @Override
                    public void onSuccess(FirebaseVisionDocumentText result) {
                        // Task completed successfully
                        // ...

                        Log.d("test", "success!");

                        String resultText = result.getText();
                        for (FirebaseVisionDocumentText.Block block: result.getBlocks()) {
                            String blockText = block.getText();
                            Float blockConfidence = block.getConfidence();

                            Log.d("test", blockText + "confidence: " + blockConfidence);

                            List<RecognizedLanguage> blockRecognizedLanguages = block.getRecognizedLanguages();
                            Rect blockFrame = block.getBoundingBox();
                            for (FirebaseVisionDocumentText.Paragraph paragraph: block.getParagraphs()) {
                                String paragraphText = paragraph.getText();
                                Float paragraphConfidence = paragraph.getConfidence();
                                Log.d("test", paragraphText + "confidence: " + paragraphConfidence);
                                List<RecognizedLanguage> paragraphRecognizedLanguages = paragraph.getRecognizedLanguages();
                                Rect paragraphFrame = paragraph.getBoundingBox();
                                for (FirebaseVisionDocumentText.Word word: paragraph.getWords()) {
                                    String wordText = word.getText();
                                    Float wordConfidence = word.getConfidence();
                                    List<RecognizedLanguage> wordRecognizedLanguages = word.getRecognizedLanguages();
                                    Rect wordFrame = word.getBoundingBox();
                                    for (FirebaseVisionDocumentText.Symbol symbol: word.getSymbols()) {
                                        String symbolText = symbol.getText();
                                        Float symbolConfidence = symbol.getConfidence();
                                        List<RecognizedLanguage> symbolRecognizedLanguages = symbol.getRecognizedLanguages();
                                        Rect symbolFrame = symbol.getBoundingBox();
                                    }
                                }
                            }
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        // ...


                        Log.d("test", "fail" + e.getLocalizedMessage());

                    }
                });
    }
}
