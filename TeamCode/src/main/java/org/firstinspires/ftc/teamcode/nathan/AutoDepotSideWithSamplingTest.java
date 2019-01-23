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
@Autonomous(name = "AutoDepotSideTest")
public class AutoDepotSideWithSamplingTest extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor left = null;
    private DcMotor right = null;

    private int roll = -1;


    @Override
    public void runOpMode() {


        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");

        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        left.setDirection(DcMotor.Direction.FORWARD);
        right.setDirection(DcMotor.Direction.REVERSE);
        //lift.setDirection(DcMotor.Direction.FORWARD);
        // fBucket.setDirection(DcMotor.Direction.FORWARD);

        NormalDriveEncoders drive = new NormalDriveEncoders(left, right, telemetry, .3f, this);
        waitForStart();
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Forward(12);


//        switch (roll) {
//            case 1:
//                drive.pivotRight(90);
//                break;
//            case 2:
//                drive.pivotRight(135);
//                break;
//            case 3:
//                drive.pivotRight(180);
//                break;
//
//        }
//
//        drive.forward(60);
//        extension.setTargetPosition(1180 * 3);
//        extension.setPower(.25);
//        while(extension.isBusy()){}
//        extension.setPower(0);

    }

    private void Forward(int inches)
    {
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode((DcMotor.RunMode.STOP_AND_RESET_ENCODER));
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setTargetPosition(1120 * inches);
        left.setPower(.8);
        right.setPower(.8);
        telemetry.addData("Status", "Before while loop");
        telemetry.update();
        while(opModeIsActive())
        {
            telemetry.addData("Status", "During while loop");
            telemetry.update();
            left.setTargetPosition(right.getCurrentPosition());
            if(right.getCurrentPosition() >= right.getTargetPosition())
            {break;}

        }

        left.setPower(0);
        right.setPower(0);
    }

    private void HighRoller(NormalDriveEncoders drive) {
        while (opModeIsActive()) {
//            int highRoll = (int) (Math.random() * 3 + 1);
            int highRoll = 2;
            telemetry.addData("You Rolled a ", highRoll);
            telemetry.update();
//                           telemetry.addData("Silver1X", "Initialized");     if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
//                           telemetry.addData("Silver2X", "Initialized");         left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                            right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            if (roll == 1) {
                telemetry.addData("Go ", "left!");
                drive.pivotLeft(45);
                drive.forward(-12);
                drive.pivotRight(45);
                drive.forward(-12);

            } else if (roll == 3) {
                telemetry.addData("Go ", "right!");
                drive.pivotRight(45);
                drive.forward(-12);
                drive.pivotLeft(45);
                drive.forward(-12);
            } else {
                telemetry.addData("Go ", "straight!");
                drive.forward(-12);
            }
            telemetry.update();
            break;
        }
    }
}





