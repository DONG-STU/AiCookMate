package com.sdc.aicookmate

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.channels.FileChannel

fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun loadModel(context: Context, modelPath: String): Interpreter {
    val assetFileDescriptor = context.assets.openFd(modelPath)
    val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
    val fileChannel = fileInputStream.channel
    val startOffset = assetFileDescriptor.startOffset
    val declaredLength = assetFileDescriptor.declaredLength
    val modelBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    return Interpreter(modelBuffer)
}

fun runYoloModel(interpreter: Interpreter, bitmap: Bitmap): List<String> {
    // 이미지 전처리 (YOLO 모델 입력 크기에 맞게 크기 조정)
    val inputSize = 416 // 예제 입력 크기 (YOLO 모델에 따라 조정 필요)
    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)

    // FloatArray로 변환
    val inputTensor = convertBitmapToTensor(resizedBitmap)

    // 추론 결과 저장 공간
    val outputTensor = Array(1) { Array(2535) { FloatArray(85) } } // YOLOv4 출력 예시

    // 모델 실행
    interpreter.run(inputTensor, outputTensor)

    // 감지된 클래스 추출
    return parseDetectionResults(outputTensor)
}

fun convertBitmapToTensor(bitmap: Bitmap): Array<Array<Array<FloatArray>>> {
    val inputSize = bitmap.width
    val tensor = Array(1) { Array(inputSize) { Array(inputSize) { FloatArray(3) } } }
    for (x in 0 until inputSize) {
        for (y in 0 until inputSize) {
            val pixel = bitmap.getPixel(x, y)
            tensor[0][x][y][0] = (pixel shr 16 and 0xFF) / 255.0f // Red
            tensor[0][x][y][1] = (pixel shr 8 and 0xFF) / 255.0f  // Green
            tensor[0][x][y][2] = (pixel and 0xFF) / 255.0f        // Blue
        }
    }
    return tensor
}

fun parseDetectionResults(outputTensor: Array<Array<FloatArray>>): List<String> {
    val detectedClasses = mutableListOf<String>()
    for (result in outputTensor[0]) {
        val confidence = result[4] // Objectness score
        if (confidence > 0.5) { // 감지 기준
            val classIndex = result.slice(5 until result.size).indexOfMax()
            detectedClasses.add("Class $classIndex")
        }
    }
    return detectedClasses
}

fun List<Float>.indexOfMax(): Int = this.withIndex().maxByOrNull { it.value }?.index ?: -1