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
@Autonomous(name="AutoCraterSideNoLatchingNoSamplingV2")
public class AutoCraterSideNoLatchingNoSamplingV2 extends LinearOpMode{
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

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = " ATiuXRv/////AAABmX/tug8P6EteuRJz2PTAi6JMBeLa9Te+gCRaTBPeDZ77UloArIT7REsZPIosl4YG0JLDyl4+yj3lpzfzKIkpOQNRfsgfAjS6tTbwBRHJsnStRDKMwb4Fj5l3rTCxB8qHn8GW45O1BGLuAROQ+DrNs26ktJV3HTEr6N4XYXSdDD3UX+2Yj8u4CmJ6xk4kY0JdX/Kklw4Ai0Mba5vFviXXjue5UMQRZTIy45y2h8UpEcSFeqiLLKdGktA5qL5NufN0/KZXI3EQNHjmrAi52oqWiO7JBAolc9uC7B910YGiGI6E0a/KJAvxLY6zlKuXI+XkQP9WgGwfXUZhU8nTyKnEDi4HY0v/+uSmKfcyIrDWf2KW";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");
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
        wrist = hardwareMap.crservo.get("wrist");
        collection = hardwareMap.crservo.get("collection");
        //lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        left.setDirection(DcMotor.Direction.REVERSE);
        right.setDirection(DcMotor.Direction.FORWARD);
        //lift.setDirection(DcMotor.Direction.FORWARD);
        // fBucket.setDirection(DcMotor.Direction.FORWARD);

        NormalDriveEncoders drive = new NormalDriveEncoders(left, right, telemetry, .3f, this);

        waitForStart();
        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            runtime.reset();
        }
        //



        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        drive.forward(24);
        extension.setPower(-.9);
        sleep(2000);
        extension.setPower(0);

        }

}
