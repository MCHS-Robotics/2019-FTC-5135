package org.firstinspires.ftc.teamcode.nathan;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by student on 11/29/18.
 */
@Autonomous(name="AutoDepotSideNoSamplingNoLatchingV2")
public class AutoDepotSideNoSamplingNoLatchingV2 extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor lift = null;
    private DcMotor fBucket = null;
    private CRServo collection = null;
    private CRServo bucket = null;
    private CRServo wrist = null;
    private DcMotor extension = null;

    @Override
    public void runOpMode() {

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
        right.setDirection(DcMotor.Direction.REVERSE);
        //lift.setDirection(DcMotor.Direction.FORWARD);
        // fBucket.setDirection(DcMotor.Direction.FORWARD);

        NormalDriveEncoders drive = new NormalDriveEncoders(left, right, telemetry, .7f, this);
        waitForStart();
        runtime.reset();

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.forward(35);
        wrist.setPower(.8);
        sleep(300);
        collection.setPower(.7);
        sleep(2000);
        collection.setPower(0);



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
}
