package org.firstinspires.ftc.teamcode.nathan;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
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
@Autonomous(name = "AutoChallengeMichi")
// @Disabled
public class AutoAutoChallengeMichi extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor left = null;
    private DcMotor right = null;

    //path 1 = left - path 2 = middle - path 3 = right
    private Position position = Position.CENTER;
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = " ATiuXRv/////AAABmX/tug8P6EteuRJz2PTAi6JMBeLa9Te+gCRaTBPeDZ77UloArIT7REsZPIosl4YG0JLDyl4+yj3lpzfzKIkpOQNRfsgfAjS6tTbwBRHJsnStRDKMwb4Fj5l3rTCxB8qHn8GW45O1BGLuAROQ+DrNs26ktJV3HTEr6N4XYXSdDD3UX+2Yj8u4CmJ6xk4kY0JdX/Kklw4Ai0Mba5vFviXXjue5UMQRZTIy45y2h8UpEcSFeqiLLKdGktA5qL5NufN0/KZXI3EQNHjmrAi52oqWiO7JBAolc9uC7B910YGiGI6E0a/KJAvxLY6zlKuXI+XkQP9WgGwfXUZhU8nTyKnEDi4HY0v/+uSmKfcyIrDWf2KW";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    private Recognition what;
    private Recognition maxRecognition;
    private Recognition minRecognition;

    @Override
    public void runOpMode() {
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");

        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        extension.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        collection = hardwareMap.crservo.get("collection");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        left.setDirection(DcMotor.Direction.REVERSE);
        right.setDirection(DcMotor.Direction.FORWARD);

        NormalDriveEncoders drive = new NormalDriveEncoders(left, right, telemetry, .3f, this);
        if (tfod != null) {
            /** Activate Tensor Flow Object Detection. */
            tfod.activate();
        }
        while (!opModeIsActive()) {
            hang();
            detect();
            telemetry.addData("POS", position.toString());
            telemetry.update();
        }
        if (opModeIsActive()) {
            runtime.reset();
        }

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        while()
        //Sample(drive);


    }

    private void detect() {
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

            if (updatedRecognitions != null) {
                // selection sort recognitions by left coordinate
                for (int i = 0; i < updatedRecognitions.size() - 1; i++) {
                    int index = i;
                    for (int j = i + 1; j < updatedRecognitions.size(); j++) {
                        if (updatedRecognitions.get(j).getLeft() < updatedRecognitions.get(index).getLeft()) {
                            index = j;
                        }
                    }
                    Recognition temp = updatedRecognitions.get(index);
                    updatedRecognitions.set(index, updatedRecognitions.get(i));
                    updatedRecognitions.set(i, temp);
                }

                telemetry.addData("# Object Detected", updatedRecognitions.size());
                /*if (updatedRecognitions.size() == 2 || updatedRecognitions.size() == 3) {
                    if (updatedRecognitions.get(0).getLabel().equals(LABEL_GOLD_MINERAL))
                        position = Position.LEFT;
                    else if (updatedRecognitions.get(1).getLabel().equals(LABEL_GOLD_MINERAL))
                        position = Position.CENTER;
                    else
                        position = Position.RIGHT;

                } else {
                    position = Position.CENTER;
                }*/
                //find the max coordinate and min left coordinate of the minerals
                // find the middle, so robot can go to middle of seen minerals
                for(int i = 1; i < updatedRecognitions.size(); i++)
                {
                    what = updatedRecognitions.get(i).getLeft();
                    if(what.getLeft > updatedRecognition.get(i).getLeft())
                    {
                        maxRecognition =
                    }
                }
            }
        }
    }

    private void Sample(NormalDriveEncoders drive) {
        /*if (position == Position.LEFT) {
            telemetry.addData("Gold Mineral Position", "Left");
            telemetry.update();
            drive.pivotLeft(35);
            drive.forward(24);
            drive.pivotRight(20);
            drive.forward(6);
            drive.backward(6);
        } else if (position == Position.RIGHT) {
            telemetry.addData("Gold Mineral Position", "Right");
            telemetry.update();
            drive.pivotRight(35);
            drive.forward(24);
            drive.pivotLeft(20);
            drive.forward(6);
            drive.backward(6);
        } else {
            telemetry.addData("Gold Mineral Position", "Center");
            telemetry.update();
            drive.forward(18);
            drive.backward(6);
        }*/

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
