package org.firstinspires.ftc.teamcode.nathan;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

/**
 * Created by student on 11/29/18.
 */
@Autonomous(name="AutoDepotSideWithSamplingAndLatchingV3")
// @Disabledl
public class AutoDepotSideWithSamplingV3 extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor lift = null;
    private CRServo wrist = null;
    //private DcMotor fBucket = null;
    private CRServo collection = null;
    private CRServo bucket = null;
    private DcMotor extension = null;
    //path 1 = left - path 2 = middle - path 3 = right
    private int path = -1;
    private int goldMineralX = -1;
    private int silverMineral1X = -1;
    private int silverMineral2X = -1;

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = " ATiuXRv/////AAABmX/tug8P6EteuRJz2PTAi6JMBeLa9Te+gCRaTBPeDZ77UloArIT7REsZPIosl4YG0JLDyl4+yj3lpzfzKIkpOQNRfsgfAjS6tTbwBRHJsnStRDKMwb4Fj5l3rTCxB8qHn8GW45O1BGLuAROQ+DrNs26ktJV3HTEr6N4XYXSdDD3UX+2Yj8u4CmJ6xk4kY0JdX/Kklw4Ai0Mba5vFviXXjue5UMQRZTIy45y2h8UpEcSFeqiLLKdGktA5qL5NufN0/KZXI3EQNHjmrAi52oqWiO7JBAolc9uC7B910YGiGI6E0a/KJAvxLY6zlKuXI+XkQP9WgGwfXUZhU8nTyKnEDi4HY0v/+uSmKfcyIrDWf2KW";

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

        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");
        wrist = hardwareMap.crservo.get("wrist");
        extension = hardwareMap.get(DcMotor.class, "extension");
        lift = hardwareMap.get(DcMotor.class, "lift");


        //lift = hardwareMap.get(DcMotor.class, "lift");
        bucket = hardwareMap.crservo.get("bucket");
        //fBucket = hardwareMap.get(DcMotor.class, "fBucket");
        //fBucket.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        extension.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        collection = hardwareMap.crservo.get("collection");
        //lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        left.setDirection(DcMotor.Direction.REVERSE);
        right.setDirection(DcMotor.Direction.FORWARD);
        //lift.setDirection(DcMotor.Direction.FORWARD);
        // fBucket.setDirection(DcMotor.Direction.FORWARD);

        NormalDriveEncoders drive = new NormalDriveEncoders(left, right, telemetry, .3f, this);
        Robot robot = new Robot(lift, extension, wrist, bucket, collection, drive);

        if (tfod != null) {
            /** Activate Tensor Flow Object Detection. */
            tfod.activate();
        }
        while(!opModeIsActive()) {
            Detect();
        }
        if (opModeIsActive()) {
            runtime.reset();
        }
        //


        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.liftUp();
        drive.forward(2);
        robot.liftDown();
        robot.wristDown();
        Sample(drive);
        robot.wristUp();
        robot.extendOut();
        collection.setPower(-0.8);
        sleep(1000);
        collection.setPower(0);
        switch(path){
            case 1:
                //TODO turn robot and got to crater
                break;
            case 2:
                //TODO turn robot and got to crater
                break;
            case 3:
                //TODO turn robot and got to crater
                break;
        }


    }

    private void Detect() {
        //while (opModeIsActive()) {
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    if (updatedRecognitions.size() == 3|| updatedRecognitions.size() == 2){
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

                    }
                    else {
                        goldMineralX = -1;
                        silverMineral1X = -1;
                        silverMineral2X = -1;
                    }
                }
            }
        //}
    }


    private void Sample(NormalDriveEncoders drive) {

        if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
            telemetry.addData("Gold Mineral Position", "Left");
            drive.pivotRight(45);
            drive.forward(24);
            drive.pivotLeft(30);
            drive.forward(9);
            path = 1;
        } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
            telemetry.addData("Gold Mineral Position", "Right");
            drive.pivotLeft(45);
            drive.forward(24);
            drive.pivotRight(45);
            drive.forward(9);
            path = 3;
        } else {
            telemetry.addData("Gold Mineral Position", "Center");
            drive.forward(24);
            path = 2;
        }
        telemetry.update();
    }

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