package org.firstinspires.ftc.teamcode.nathan;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by student on 11/29/18.
 */
//@Autonomous(name="AutoCraterSideNoSampling")
public class AutoCraterSideNoSampling extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor lift = null;
    private DcMotor fBucket = null;
    private CRServo collection = null;
    private CRServo bucket = null;
    @Override
    public void runOpMode() {

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
        right.setDirection(DcMotor.Direction.FORWARD);
        //lift.setDirection(DcMotor.Direction.FORWARD);
        //fBucket.setDirection(DcMotor.Direction.FORWARD);

        NormalDriveEncoders drive = new NormalDriveEncoders(left, right, telemetry, .7f, this);
        telemetry.addData("Left Encoder", left.getCurrentPosition());
        telemetry.addData("Right Encoder", right.getCurrentPosition());
        telemetry.addData("test", getRuntime());
        waitForStart();
        runtime.reset();
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.forward((int)(4*Math.PI));
//        left.setTargetPosition(1120);
//        right.setTargetPosition(1120);
//        left.setPower(.5);
//        right.setPower(.5);
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.pivotLeft(58);
        drive.forward(40);
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.pivotLeft(115);
        drive.forward(60);
        //TODO deploy marker
        drive.forward(-100);
        telemetry.update();
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
