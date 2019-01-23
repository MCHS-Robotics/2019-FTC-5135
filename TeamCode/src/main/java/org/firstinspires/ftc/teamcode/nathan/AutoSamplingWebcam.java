package org.firstinspires.ftc.teamcode.nathan;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.nathan.NormalDriveEncoders;


import java.util.List;

/**
 * Finished by student on 11/15/18.
 */

//@Autonomous(name="Auto Sampling Webcam *******")
public class AutoSamplingWebcam extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "Acbhfjv/////AAAAGQwVGIAmv0V3nNH7nrtPGJVSuirk278Uo+j+394hRfZGLNmucewzhlA4ux8ZUQz0OpL1mJuPW+lKbjippfmpGiiXsvpnm2p6prlsNHzZNthjThZyFArOkXDkjL5bYlVT3zlo3oc+XNg/W4rBdXHeBpw6OgO1a7D0xhGHkqaihUWqeESvWtcH+uSJXha/umtRGu0DIPRW0n4a3Z0cjbNzhicHvrGOWuwuVhyua1IcrOqed3/bTdts+JhuG3TakwFBb1GpIkWXNziiorUUXVstPVNVrty3AnesNZ11gsJyHVu3YCKi//itjkqr/6xmINr4LDrwdf1Pg2e9L9iT9qtFWjrrnEQYGzxgpnrj0+PvzWWw";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    private DcMotor left;
    private DcMotor right;
    private NormalDriveEncoders drive = new NormalDriveEncoders(left, right, telemetry, 0.5f, this);

    @Override
    public void runOpMode() {
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");

        telemetry.addData("Left", left.getConnectionInfo());
        telemetry.addData("right", right.getConnectionInfo());
        telemetry.update();

        drive = new NormalDriveEncoders(left, right, telemetry, 0.3f, this);

        waitForStart();
        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 3) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }
                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                left.setDirection(DcMotorSimple.Direction.REVERSE);
                                left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                                right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                    drive.pivotLeft(45);
                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                    drive.pivotRight(45);
                                } else {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                }
                                drive.forward(24);
                                telemetry.update();
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}

