package org.firstinspires.ftc.teamcode.nathan;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by student on 11/29/18.
 */
//@Autonomous(name="AutoCraterSideV1")
public class AutoCraterSide extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor lift = null;
    private DcMotor fBucket = null;
    private CRServo collection = null;
    private CRServo bucket = null;

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "Acbhfjv/////AAAAGQwVGIAmv0V3nNH7nrtPGJVSuirk278Uo+j+394hRfZGLNmucewzhlA4ux8ZUQz0OpL1mJuPW+lKbjippfmpGiiXsvpnm2p6prlsNHzZNthjThZyFArOkXDkjL5bYlVT3zlo3oc+XNg/W4rBdXHeBpw6OgO1a7D0xhGHkqaihUWqeESvWtcH+uSJXha/umtRGu0DIPRW0n4a3Z0cjbNzhicHvrGOWuwuVhyua1IcrOqed3/bTdts+JhuG3TakwFBb1GpIkWXNziiorUUXVstPVNVrty3AnesNZ11gsJyHVu3YCKi//itjkqr/6xmINr4LDrwdf1Pg2e9L9iT9qtFWjrrnEQYGzxgpnrj0+PvzWWw";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    @Override
    public void runOpMode() {
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");
        //lift = hardwareMap.get(DcMotor.class, "lift");
        //bucket = hardwareMap.crservo.get("bucket");
        //fBucket = hardwareMap.get(DcMotor.class, "fBucket");
        //fBucket.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //collection = hardwareMap.crservo.get("collection");
        //lift.setZeroPowerBehavior(DcMotor.ZeroPowerBeh/xreversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        left.setDirection(DcMotor.Direction.FORWARD);
        right.setDirection(DcMotor.Direction.REVERSE);
        //lift.setDirection(DcMotor.Direction.FORWARD);
        //fBucket.setDirection(DcMotor.Direction.FORWARD);

        NormalDriveEncoders drive = new NormalDriveEncoders(left, right, telemetry, .3f, this);



        waitForStart();
        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            runtime.reset();
        }
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Sample(drive);
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


//        fBucket.setPower(-.75);
//        while (runtime.milliseconds() < 250) {}
//
//        fBucket.setPower(0);
//        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift.setTargetPosition((int)(1180 * 2.9));
//        lift.setPower(.75);
//        while (lift.isBusy()){}
//        lift.setPower(0);
//        drive.forward(1);
//        lift.setTargetPosition(0);
//        while(lift.getCurrentPosition() > lift.getTargetPosition()) {
//            lift.setPower(.75);
//        }
//        while(lift.isBusy()){}
//        lift.setPower(0);

    }
    private void Sample(NormalDriveEncoders drive)
    {
        while (opModeIsActive()) {
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    for (int i = updatedRecognitions.size() - 1; i >= 0; i--) {
                        Recognition r = updatedRecognitions.get(i);
                        telemetry.addData(i + ": Top", r.getTop());
                        telemetry.addData(i + ": Bottom", r.getBottom());
                        if (r.getTop() > 300) {
                            updatedRecognitions.remove(i);
                        }
                    }
                    telemetry.update();
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
                        telemetry.addData("GoldX", goldMineralX);
                        telemetry.addData("Silver1X", silverMineral1X);
                        telemetry.addData("Silver2X", silverMineral2X);
                        telemetry.update();
//                           telemetry.addData("Silver1X", "Initialized");     if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
//                           telemetry.addData("Silver2X", "Initialized");         left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                            right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Left");
//                            drive.pivotLeft(45);
//                            drive.forward(34);
//                            drive.pivotRight(45);
//                            drive.forward(34);

                        } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Right");
//                            drive.pivotRight(45);
//                            drive.forward(34);
//                            drive.pivotLeft(45);
//                            drive.forward(34);

                        } else {
                            telemetry.addData("Gold Mineral Position", "Center");
//                            drive.forward(48);
                        }
//                            telemetry.update();
//                            break;
                    }
                }
            }
        }
    }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

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
        // tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod = new CustomTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
